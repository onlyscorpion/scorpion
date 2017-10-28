package com.scorpion.huakerongtong.kernel.servicemanager;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.scorpion.huakerongtong.api.configuration.PlatformConfiguration.Scanner;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.AbsScorpionComponent;
import com.scorpion.huakerongtong.api.kernel.AbsScorpionServiceProxy;
import com.scorpion.huakerongtong.api.kernel.IAnnotationAnalyzerListener;
import com.scorpion.huakerongtong.api.kernel.IScorpionJarScanAndHandler;
import com.scorpion.huakerongtong.api.kernel.IScorpionSystemClassScanner;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.api.util.SystemUtils;
import com.scorpion.huakerongtong.common.context.SystemContext;
import com.scorpion.huakerongtong.kernel.util.ScorpionActionUtil;
import com.scorpion.huakerongtong.kernel.util.ScorpionServiceUtil;

import java.util.Set;

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
public class ScorpionServiceManager extends AbsScorpionComponent {

	private String[] systemclasspath;

	private Map<String, IScorpionSystemClassScanner> classScanners;

	private Set<String> applicationJarsAndClasses;

	private String regexExpression;

	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {

		loadSystemConfiguration();

		checkSystemEnv();

		findApplicationJarAndClasses();

		scanApplicationJarAndClasses();

	}

	/**
	 * @throws ScorpionBaseException
	 */
	public final void loadSystemConfiguration() throws ScorpionBaseException {

		constructAppBulidPath();
		constructRegexExpression();
		List<Scanner> clazzs = SystemContext.getApplicationContext().getSystemCoreConfig().getPlatformconfiguration().getScanners();
		classScanners = new HashMap<String, IScorpionSystemClassScanner>();

		for (Scanner scanner : clazzs) {

			try {
				IScorpionSystemClassScanner iscanner = (IScorpionSystemClassScanner) Class.forName(scanner.getClazz(),true,SystemContext.getApplicationContext().getSystemClassLoader()).newInstance();

				for (Map<String, String> map : scanner.getAnalyzers()) {
					for (Entry<String, String> entry : map.entrySet())
						iscanner.registerAnnotationAnalyzer((IAnnotationAnalyzerListener) Class.forName(entry.getValue(),true,SystemContext.getApplicationContext().getSystemClassLoader()).newInstance());
				}

				classScanners.put(scanner.getName(),(IScorpionSystemClassScanner) Class.forName(scanner.getClazz(),true,SystemContext.getApplicationContext().getSystemClassLoader()).newInstance());

			} catch (Exception e) {
				throw new ScorpionBaseException("TCS002:Initializing the scanner of class failure !", e);
			}
		}

	}

	/**
	 * @throws ScorpionBaseException
	 */
	private void constructAppBulidPath() throws ScorpionBaseException {

		systemclasspath = SystemContext.getApplicationContext().getSystemBuildPath();

		if (systemclasspath == null || systemclasspath.length < 1)
			throw new ScorpionBaseException("TCS5002:The path of scanning service can't find ！");

		systemclasspath = SystemUtils.pathTransformer(systemclasspath,Constant.PATH_ABSOLUTE);
		constructDefaultLibPath();
		generatorExtensionBuildDir();
		((SystemContext) SystemContext.getApplicationContext()).setBuildPath(systemclasspath);
	}

	/**
	 * @throws ScorpionBaseException
	 */
	private void constructRegexExpression() throws ScorpionBaseException {

		String jarexpression = SystemContext.getApplicationContext().getSystemCoreConfig().getPlatformconfiguration().getJarexpression();
		String classexpression = SystemContext.getApplicationContext().getSystemCoreConfig().getPlatformconfiguration().getClassexpression();
		regexExpression = (jarexpression == null ? "" : jarexpression) + "|"+ (classexpression == null ? "" : classexpression);
	}

	/**
	 * @Description Get file directory ....
	 * 
	 * @param systemBuildPath
	 * 
	 * @return
	 */
	private void constructDefaultLibPath() {

		String defaultLibPath = new File(systemclasspath[0]).getParent()+ File.separator + "lib";

		if (new File(defaultLibPath).exists()) {
			String[] lib = new String[2];
			lib[0] = systemclasspath[0];
			lib[1] = defaultLibPath;
			systemclasspath = lib;
		}
	}

	/**
	 * 
	 * @throws ScorpionBaseException
	 */
	private void generatorExtensionBuildDir() throws ScorpionBaseException {

		String[] extensionsLib = SystemContext.getApplicationContext().getSystemCoreConfig().getPlatformconfiguration().getUserDefineLibPath();

		if (extensionsLib == null || extensionsLib.length < 1 || "".equals(extensionsLib[0].trim()))
			return;

		String[] temp = new String[systemclasspath.length + extensionsLib.length];
		System.arraycopy(systemclasspath, 0, temp, 0, systemclasspath.length);
		System.arraycopy(extensionsLib, 0, temp, systemclasspath.length,extensionsLib.length);
		systemclasspath = temp;

	}

	/**
	 * 
	 * @throws ScorpionBaseException
	 */
	private void checkSystemEnv() throws ScorpionBaseException {

		if (systemclasspath == null || systemclasspath.length < 1)
			throw new ScorpionBaseException("TCP3003:Don't set class scanning path ！");

		if (regexExpression.length() == 1) {
			regexExpression = ".*";

		} else if (regexExpression.indexOf("|") == 0) {
			regexExpression = regexExpression.substring(1,regexExpression.length());

		} else if (regexExpression.indexOf("|") == regexExpression.length() - 1) {
			regexExpression = regexExpression.substring(0,regexExpression.length() - 1);

		}

		if (classScanners == null || systemclasspath.length < 1)
			throw new ScorpionBaseException("TCP4006:System don't register class scanner !");

	}

	/**
	 * @throws ScorpionBaseException
	 */
	public final void findApplicationJarAndClasses() throws ScorpionBaseException {

		applicationJarsAndClasses = new HashSet<String>();

		for (String path : systemclasspath)
			applicationJarsAndClasses.addAll(SystemUtils.systemFileFilter(path,new String[] { regexExpression }, true, true));

		String[] envJarArray = System.getProperty("java.class.path").length() > 0 ? System.getProperty("java.class.path").split(System.getProperty("path.separator")) : null;

		if (envJarArray == null)
			return;

		for (String jarfile : envJarArray) {

			if (jarfile == null || "".equals(jarfile)|| (jarfile.indexOf("/") < 0 && jarfile.indexOf("\\") < 0))
				return;

			String tempFile = jarfile.replace("\\", "/").substring(jarfile.replace("\\", "/").lastIndexOf("/") + 1,jarfile.replace("\\", "/").length());
			
			if (!SystemUtils.regularExpressionValidate(tempFile, SystemContext.getApplicationContext().getSystemCoreConfig().getPlatformconfiguration().getJarexpression()))
				continue;

			applicationJarsAndClasses.add(jarfile);
		}
	}

	/**
	 * @throws ScorpionBaseException
	 */
	public final void scanApplicationJarAndClasses() throws ScorpionBaseException {

		IScorpionJarScanAndHandler handler = new ScorpionJarScanAndHandler();

		for (Entry<String, IScorpionSystemClassScanner> entry : classScanners.entrySet()) {
			try {
				handler.systemJarAndClassScanHandler(applicationJarsAndClasses,entry.getValue());
				reorganizeResource();
			} catch (ScorpionBaseException e) {
				throw new ScorpionBaseException("TCC-6601:Execute class scanner failure !  The name of class scanner is ["+ entry.getKey() + "] !", e);
			}
		}
	}

	private void reorganizeResource() throws ScorpionBaseException {

	}

	@Override
	public void handler() throws ScorpionBaseException {

		super.handler();
		registerProxy(ScorpionServiceProxyFacotry.getInstance().produceInstance());
	}

	/**
	 * @description Register service proxy ...
	 * 
	 * @param proxy
	 * 
	 * @throws ScorpionBaseException
	 */
	private void registerProxy(AbsScorpionServiceProxy proxy)throws ScorpionBaseException {

		if (proxy == null)
			throw new ScorpionBaseException("scorpion-0864:The service proxy don't allow to be null !");

		ScorpionServiceUtil.serviceProxy = proxy;
		ScorpionActionUtil.actionProxy = proxy;
	}

	@Override
	public void destroy() throws ScorpionBaseException {

		IScorpionSystemClassScanner.annotationAnalyzers.clear();
		super.destroy();

	}

}
