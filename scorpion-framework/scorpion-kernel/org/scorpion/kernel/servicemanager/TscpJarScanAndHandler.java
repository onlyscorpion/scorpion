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

import org.scorpion.api.common.AbsTscpConcurrentTask;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.common.ITscpConurrentEngine;
import org.scorpion.api.common.ITscpTaskGenerator;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpJarScanAndHandler;
import org.scorpion.api.kernel.ITscpSystemClassScanner;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.concurrentcpt.ConcurrentEngineUtil;
import org.scorpion.common.concurrentcpt.ThreadPropertiesEntity;
import org.scorpion.common.context.SystemContext;
import org.scorpion.kernel.tscpclassloader.TscpSystemClassLoader;

/**
 * 自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.tscp.common
 * <p>
 * File: AbsTscpFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 taiji.com.cn
 * </p>
 * <p>
 * Company: taiji.com.cn
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpJarScanAndHandler implements ITscpJarScanAndHandler {

	private String JAR_END = ".jar";

	private String CLASS_END = ".class";

	@Override
	public void systemJarAndClassScanHandler(final Set<String> paths,final ITscpSystemClassScanner scanner) throws TscpBaseException {

		if (paths == null || paths.size() < 1)
			throw new TscpBaseException("TCC-9004:The path of scanning is null, Please check the environment of application configuration ! ");

		PlatformLogger.info("开始启动并行运算框架");
		ITscpConurrentEngine engine = ConcurrentEngineUtil.createConcurrentEngine("systemClassScanner", SystemContext.getApplicationContext().getSystemProfile().getAvailableProcessors() * 2,SystemEnumType.threadgroup.getValue(),new ThreadPropertiesEntity("并行引擎", 5, true));

		engine.fire(new ITscpTaskGenerator() {

			@Override
			public List<AbsTscpConcurrentTask> generateTask()throws TscpBaseException {
				List<AbsTscpConcurrentTask> tasks = new ArrayList<AbsTscpConcurrentTask>();

				for (final String path : paths) {
					tasks.add(new AbsTscpConcurrentTask() {
						private static final long serialVersionUID = 1L;
						@Override
						public Object getParameter() throws TscpBaseException {
							return path;
						}
					});
				}

				return tasks;
			}
		}, new IConcurrentProcessor() {

			@Override
			public void processor(AbsTscpConcurrentTask task)throws TscpBaseException {

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
			 * @throws TscpBaseException
			 */
			private void jarScanHandler(String path) throws TscpBaseException {

				File file = new File(path);
				if (!file.exists())
					throw new TscpBaseException("TSCP-9864:JAR file not exist [" + path+ "] ! ");

				URL url = SystemUtils.createURLFromLocalFile(file);
				JarFile jar = SystemUtils.getJarFile(url);
				((TscpSystemClassLoader) SystemContext.getApplicationContext().getSystemClassLoader()).loadURL(url);
				SystemContext.getApplicationContext().getScanInfo().getJarNum().incrementAndGet();

				for (Enumeration<JarEntry> jarEntrys = jar.entries(); jarEntrys.hasMoreElements();) {
					classScanHandler(file.getName(), jarEntrys.nextElement().getName());
				}
			}

			/**
			 * @param path
			 * 
			 * @throws TscpBaseException
			 */
			private void classScanHandler(String jarName, String path)throws TscpBaseException {

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
					throw new TscpBaseException("TSC-6001:Loading class  [" + path + "] failure !", e);
				}
			}

			@Override
			public void processor() throws TscpBaseException {
			}
			
		}, true);
	}

}
