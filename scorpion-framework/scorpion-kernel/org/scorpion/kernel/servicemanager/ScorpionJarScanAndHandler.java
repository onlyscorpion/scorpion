package org.scorpion.kernel.servicemanager;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.scorpion.api.common.AbsScorpionConcurrentTask;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.common.IScorpionConurrentEngine;
import org.scorpion.api.common.IScorpionTaskGenerator;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IScorpionJarScanAndHandler;
import org.scorpion.api.kernel.IScorpionSystemClassScanner;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.concurrentcpt.ConcurrentEngineUtil;
import org.scorpion.common.concurrentcpt.ThreadPropertiesEntity;
import org.scorpion.common.context.SystemContext;
import org.scorpion.kernel.tscpclassloader.ScorpionSystemClassLoader;

/**
 * 天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>
 * com.SCORPION.Scorpion.common
 * <p>
 * File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 SCORPION.COM.CN
 * </p>
 * <p>
 * Company: SCORPION.COM.CN
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionJarScanAndHandler implements IScorpionJarScanAndHandler {

	private String JAR_END = ".jar";

	private String CLASS_END = ".class";

	@Override
	public void systemJarAndClassScanHandler(final Set<String> paths,final IScorpionSystemClassScanner scanner) throws ScorpionBaseException {

		if (paths == null || paths.size() < 1)
			throw new ScorpionBaseException("TCC-9004:The path of scanning is null, Please check the environment of application configuration ! ");

		PlatformLogger.info("开始启动并行运算框架");
		IScorpionConurrentEngine engine = ConcurrentEngineUtil.createConcurrentEngine("systemClassScanner", SystemContext.getApplicationContext().getSystemProfile().getAvailableProcessors() * 2,SystemEnumType.threadgroup.getValue(),new ThreadPropertiesEntity("并行引擎", 5, true));

		engine.fire(new IScorpionTaskGenerator() {

			@Override
			public List<AbsScorpionConcurrentTask> generateTask()throws ScorpionBaseException {
				List<AbsScorpionConcurrentTask> tasks = new ArrayList<AbsScorpionConcurrentTask>();

				for (final String path : paths) {
					tasks.add(new AbsScorpionConcurrentTask() {
						private static final long serialVersionUID = 1L;
						@Override
						public Object getParameter() throws ScorpionBaseException {
							return path;
						}
					});
				}

				return tasks;
			}
		}, new IConcurrentProcessor() {

			@Override
			public void processor(AbsScorpionConcurrentTask task)throws ScorpionBaseException {

				PlatformLogger.info(Thread.currentThread().getName() + "扫描到文件["+ task.getParameter() + "]");
				String path = (String) task.getParameter();

				if (path.lastIndexOf(JAR_END) >= 0)
					jarScanHandler(path);

				else if (path.lastIndexOf(CLASS_END) >= 0)
					classScanHandler(null, path);
			}

			/**
			 * 
			 * @param path
			 * 
			 * @throws ScorpionBaseException
			 */
			private void jarScanHandler(String path) throws ScorpionBaseException {

				File file = new File(path);
				if (!file.exists())
					throw new ScorpionBaseException("scorpion-9864:JAR file not exist [" + path+ "] ! ");

				URL url = SystemUtils.createURLFromLocalFile(file);
				JarFile jar = SystemUtils.getJarFile(url);
				((ScorpionSystemClassLoader) SystemContext.getApplicationContext().getSystemClassLoader()).loadURL(url);
				SystemContext.getApplicationContext().getScanInfo().getJarNum().incrementAndGet();

				for (Enumeration<JarEntry> jarEntrys = jar.entries(); jarEntrys.hasMoreElements();) {
					classScanHandler(file.getName(), jarEntrys.nextElement().getName());
				}
			}

			/**
			 * @param path
			 * 
			 * @throws ScorpionBaseException
			 */
			private void classScanHandler(String jarName, String path)throws ScorpionBaseException {

				if (!SystemUtils.regularExpressionValidate(path, SystemContext.getApplicationContext().getSystemCoreConfig().getPlatformconfiguration().getClassexpression()))
					return;

				SystemContext.getApplicationContext().getScanInfo().getClassNum().incrementAndGet();

				try {

					Class<?> clazz = Class.forName(SystemUtils.transformerAbsolutePath(path, SystemContext.getApplicationContext().getSystemBuildPath()), false,
							SystemContext.getApplicationContext().getSystemClassLoader());

					if (Modifier.isAbstract(clazz.getModifiers())|| clazz.isInterface() || clazz.isEnum())
						return;

					scanner.doAnnotationAnalyzerChain(clazz, jarName);
				} catch (Throwable e) {
					throw new ScorpionBaseException("TSC-6001:Loading class  [" + path + "] failure !", e);
				}
			}

			@Override
			public void processor() throws ScorpionBaseException {
			}
			
		}, true);
	}

}
