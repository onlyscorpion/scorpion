package org.scorpion.kernel.servicemanager;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.scorpion.api.configuration.PlatformConfiguration.Scanner;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpComponent;
import org.scorpion.api.kernel.AbsTscpServiceProxy;
import org.scorpion.api.kernel.IAnnotationAnalyzerListener;
import org.scorpion.api.kernel.ITscpJarScanAndHandler;
import org.scorpion.api.kernel.ITscpSystemClassScanner;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.context.SystemContext;
import org.scorpion.kernel.util.TscpActionUtil;
import org.scorpion.kernel.util.TscpServiceUtil;

import java.util.Set;

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
public class TscpServiceManager extends AbsTscpComponent {

	private String[] systemclasspath;

	private Map<String, ITscpSystemClassScanner> classScanners;

	private Set<String> applicationJarsAndClasses;

	private String regexExpression;

	@Override
	public void start(Map<String, String> arguments) throws TscpBaseException {

		loadSystemConfiguration();

		checkSystemEnv();

		findApplicationJarAndClasses();

		scanApplicationJarAndClasses();

	}

	/**
	 * @throws TscpBaseException
	 */
	public final void loadSystemConfiguration() throws TscpBaseException {

		constructAppBulidPath();
		constructRegexExpression();
		List<Scanner> clazzs = SystemContext.getApplicationContext().getSystemCoreConfig().getPlatformconfiguration().getScanners();
		classScanners = new HashMap<String, ITscpSystemClassScanner>();

		for (Scanner scanner : clazzs) {

			try {
				ITscpSystemClassScanner iscanner = (ITscpSystemClassScanner) Class.forName(scanner.getClazz(),true,SystemContext.getApplicationContext().getSystemClassLoader()).newInstance();

				for (Map<String, String> map : scanner.getAnalyzers()) {
					for (Entry<String, String> entry : map.entrySet())
						iscanner.registerAnnotationAnalyzer((IAnnotationAnalyzerListener) Class.forName(entry.getValue(),true,SystemContext.getApplicationContext().getSystemClassLoader()).newInstance());
				}

				classScanners.put(scanner.getName(),(ITscpSystemClassScanner) Class.forName(scanner.getClazz(),true,SystemContext.getApplicationContext().getSystemClassLoader()).newInstance());

			} catch (Exception e) {
				throw new TscpBaseException("TCS002:Initializing the scanner of class failure !", e);
			}
		}

	}

	/**
	 * @throws TscpBaseException
	 */
	private void constructAppBulidPath() throws TscpBaseException {

		systemclasspath = SystemContext.getApplicationContext().getSystemBuildPath();

		if (systemclasspath == null || systemclasspath.length < 1)
			throw new TscpBaseException("TCS5002:The path of scanning service can't find ！");

		systemclasspath = SystemUtils.pathTransformer(systemclasspath,Constant.PATH_ABSOLUTE);
		constructDefaultLibPath();
		generatorExtensionBuildDir();
		((SystemContext) SystemContext.getApplicationContext()).setBuildPath(systemclasspath);
	}

	/**
	 * @throws TscpBaseException
	 */
	private void constructRegexExpression() throws TscpBaseException {

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
	 * @throws TscpBaseException
	 */
	private void generatorExtensionBuildDir() throws TscpBaseException {

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
	 * @throws TscpBaseException
	 */
	private void checkSystemEnv() throws TscpBaseException {

		if (systemclasspath == null || systemclasspath.length < 1)
			throw new TscpBaseException("TCP3003:Don't set class scanning path ！");

		if (regexExpression.length() == 1) {
			regexExpression = ".*";

		} else if (regexExpression.indexOf("|") == 0) {
			regexExpression = regexExpression.substring(1,regexExpression.length());

		} else if (regexExpression.indexOf("|") == regexExpression.length() - 1) {
			regexExpression = regexExpression.substring(0,regexExpression.length() - 1);

		}

		if (classScanners == null || systemclasspath.length < 1)
			throw new TscpBaseException("TCP4006:System don't register class scanner !");

	}

	/**
	 * @throws TscpBaseException
	 */
	public final void findApplicationJarAndClasses() throws TscpBaseException {

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
	 * @throws TscpBaseException
	 */
	public final void scanApplicationJarAndClasses() throws TscpBaseException {

		ITscpJarScanAndHandler handler = new TscpJarScanAndHandler();

		for (Entry<String, ITscpSystemClassScanner> entry : classScanners.entrySet()) {
			try {
				handler.systemJarAndClassScanHandler(applicationJarsAndClasses,entry.getValue());
				reorganizeResource();
			} catch (TscpBaseException e) {
				throw new TscpBaseException("TCC-6601:Execute class scanner failure !  The name of class scanner is ["+ entry.getKey() + "] !", e);
			}
		}
	}

	private void reorganizeResource() throws TscpBaseException {

	}

	@Override
	public void handler() throws TscpBaseException {

		super.handler();
		registerProxy(TscpServiceProxyFacotry.getInstance().produceInstance());
	}

	/**
	 * @description Register service proxy ...
	 * 
	 * @param proxy
	 * 
	 * @throws TscpBaseException
	 */
	private void registerProxy(AbsTscpServiceProxy proxy)throws TscpBaseException {

		if (proxy == null)
			throw new TscpBaseException("TSCP-0864:The service proxy don't allow to be null !");

		TscpServiceUtil.serviceProxy = proxy;
		TscpActionUtil.actionProxy = proxy;
	}

	@Override
	public void destroy() throws TscpBaseException {

		ITscpSystemClassScanner.annotationAnalyzers.clear();
		super.destroy();

	}

}
