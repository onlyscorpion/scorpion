package org.scorpion.kernel.bootstrap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TimeZone;

import org.quartz.impl.StdSchedulerFactory;
import org.scorpion.api.common.Lifecycle;
import org.scorpion.api.configuration.ComponentInformation;
import org.scorpion.api.configuration.SystemProfile;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpComponent;
import org.scorpion.api.kernel.ApplicationContext;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.context.SystemContext;
import org.scorpion.common.enums.RunningMode;
import org.scorpion.common.lifecycle.TscpLifecycleManager;
import org.scorpion.kernel.tscpclassloader.TscpClassLoaderFactory;

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
public class TscpPlatformatManager implements Lifecycle {

	/**
	 * @description GET SYSTEM CONTEXT INFORMATION
	 */
	private ApplicationContext context = SystemContext.getApplicationContext();

	/**
	 * @description GENERATOR COMPONENT SEQUENCE CODE
	 */
	private List<String> componentSequence = new ArrayList<String>();

	@Override
	public void initialization() throws TscpBaseException {

		try {
			initServerInformation();

			scanSystemConfigByBuildPath();

			checkSystemEnv();

			initMicorKernelComponent();

		} catch (IOException e) {
			throw new TscpBaseException("TSCP-9768:Initialize kernel component exception !", e);
		}
	}

	@Override
	public void handler() throws TscpBaseException {

		for (String key : componentSequence) {
			try {

				AbsTscpComponent component = (AbsTscpComponent) Class.forName(SystemContext.getApplicationContext().getCoreComponets().get(key).getClazzName(),true, context.getSystemClassLoader()).newInstance();

				context.registerSystemComponent(key, component);

				SystemContext.getApplicationContext().getCoreComponets().get(key).setLoad(true);
			
			} catch (Exception e) {
				throw new TscpBaseException("TSCP-9087:Register kernel component [" + key+ "] failure ", e);
			}
		}
	}

	/**
	 * @description Research system core configuration file
	 * 
	 * @throws TscpBaseException
	 * 
	 */
	private void scanSystemConfigByBuildPath() throws TscpBaseException {

		for (String path : SystemContext.getApplicationContext().getSystemBuildPath()) {
		
			scanSystemCoreConfigFile(path);
		
			scanExceptionConfigFile(path);
		
			scanDatasourceConfigFile(path);
		
			scanSQLConfigFile(path);
		
		}

	}

	/**
	 * @description SQL File Manager
	 * 
	 * @param path
	 * 
	 * @throws TscpBaseException
	 * 
	 */
	private void scanSQLConfigFile(String path) throws TscpBaseException {

		File[] sqlconfig = SystemUtils.systemFileFilter(new File(path),new String[] { Constant.SQL_CONFIG_PATTERN }, true, false);

		if (sqlconfig != null && sqlconfig.length > 0) {
			List<File> sqlLis = new ArrayList<File>();

			for (File sql : sqlconfig) {
				sqlLis.add(sql);
			}

			SystemContext.getApplicationContext().getSystemConfigFile().setSqlConfigFiles(sqlLis);
		}
	}

	/**
	 * @description SCAN EXCEPTION CONFIGURATION FILE
	 * 
	 * @param path
	 * 
	 * @throws TscpBaseException
	 * 
	 */
	private void scanExceptionConfigFile(String path) throws TscpBaseException {

		File[] exceptionconfig = SystemUtils.systemFileFilter(new File(path),new String[] { Constant.EXCEPTION_CONFIG_PATTERN },true, false);

		if (exceptionconfig != null && exceptionconfig.length > 0) {
			List<File> exceptionLis = new ArrayList<File>();

			for (File file : exceptionconfig)
				exceptionLis.add(file);

			SystemContext.getApplicationContext().getSystemConfigFile().getUserDefaultExceptionPropertiesFiles().addAll(exceptionLis);
		}
	}

	/**
	 * @description Research data source file
	 * 
	 * @param path
	 * 
	 * @throws TscpBaseException
	 * 
	 */
	private void scanDatasourceConfigFile(String path) throws TscpBaseException {

		File[] datasourceconfig = SystemUtils.systemFileFilter(new File(path),new String[] { Constant.DATASOURCE_CONFIG_PATTERN }, true,false);

		if (datasourceconfig != null && datasourceconfig.length > 0)
			SystemContext.getApplicationContext().getSystemConfigFile().setDataSourceFile(datasourceconfig[0]);

	}

	/**
	 * @description SCAN SYSTEM CORE CONFIGURATION FILE
	 * 
	 * @param path
	 * 
	 * @throws TscpBaseException
	 */
	private void scanSystemCoreConfigFile(String path) throws TscpBaseException {

		File[] coreconfig = SystemUtils.systemFileFilter(new File(path),new String[] { Constant.CORE_CONFIG_PATTERN }, true, false);

		if (coreconfig != null && coreconfig.length > 0)
			SystemContext.getApplicationContext().getSystemConfigFile().setCoreConfigFile(coreconfig[0]);

	}

	/**
	 * @description Initialization server information
	 * 
	 * @throws TscpBaseException
	 */
	private void initServerInformation() throws TscpBaseException {

		SystemProfile systemProfile = SystemContext.getApplicationContext().getSystemProfile();
		systemProfile.setJdkname(ManagementFactory.getRuntimeMXBean().getVmName());
		systemProfile.setJdkversion(ManagementFactory.getRuntimeMXBean().getVmVersion());
		systemProfile.setSystemDefaultClassPath(ManagementFactory.getRuntimeMXBean().getClassPath());
		systemProfile.setXmxSize(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax());
		systemProfile.setUnHeadCommited(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getCommitted());
		systemProfile.setUnHeadMaxSize(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getMax());
		systemProfile.setUnHeapMinSize(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getInit());
	    System.setProperty(StdSchedulerFactory.PROPERTIES_FILE, Constant.TSCP_PERSISTENCE_FILE);
		ManagementFactory.getThreadMXBean().setThreadCpuTimeEnabled(true);
		ManagementFactory.getThreadMXBean().setThreadContentionMonitoringEnabled(true);
		context.setSystemClassLoader(TscpClassLoaderFactory.getNewInstance().produceInstance());

	}

	@Override
	public void destroy() throws TscpBaseException {
	}

	/**
	 * @description Initialization system micro kernel component
	 * 
	 * @throws TscpBaseException
	 * 
	 * @throws IOException
	 */
	private void initMicorKernelComponent() throws TscpBaseException, IOException {

		InputStream in = null;

		try {
			in = this.getClass().getResourceAsStream(Constant.MICRO_KERNEL_CONFIG);
			Properties properties = new Properties();

			if (in == null)
				throw new TscpBaseException("TSCP-9076:The micro confinguration file not found !");

			properties.load(in);

			for (Entry<Object, Object> entry : properties.entrySet()) {
				((SystemContext) SystemContext.getApplicationContext()).getCoreComponets().put((String) entry.getKey(),generateComponent(entry));
				componentSequence.add((String) entry.getKey());
			}

		} catch (IOException e) {
			throw new TscpBaseException("TSCP-9087:Load micro kernel file failure !",e);
		} finally {
			if (in != null)
				in.close();
		}
	}

	/**
	 * @param entry
	 * 
	 * @return
	 */
	private ComponentInformation generateComponent(Entry<Object, Object> entry) {

		ComponentInformation component = new ComponentInformation();
		component.setName((String) entry.getKey());
		component.setClazzName((String) entry.getValue());
		return component;

	}

	public static void main(String[] args) throws TscpBaseException {

		long startTime = System.currentTimeMillis();

		synchronized (TscpPlatformatManager.class) {

			if (Constant.RUNNING == SystemContext.getApplicationContext().getSystemProfile().getApplicationState()|| Constant.STARTING == SystemContext.getApplicationContext().getSystemProfile().getApplicationState()) {
			
				PlatformLogger.error("TSCP-1065:The application have been already started, please stop first !");
			
				SystemUtils.exitApplication();
			}

			SystemContext.getApplicationContext().getSystemProfile().setApplicationState(Constant.STARTING);
		}

		String[] urls = new String[] { TscpPlatformatManager.class.getResource("/").getPath().substring(1,TscpPlatformatManager.class.getResource("/").getPath().length()) };

		((SystemContext) SystemContext.getApplicationContext()).setBuildPath(urls);

		TscpLifecycleManager.registerLifecycleSingle(new TscpPlatformatManager());

		SystemContext.getApplicationContext().getSystemProfile().setApplicationState(Constant.RUNNING);

		PlatformLogger.info("本次启动扫描了["+ SystemContext.getApplicationContext().getScanInfo().getJarNum().get()+ "]jar包,扫描了["+ SystemContext.getApplicationContext().getScanInfo().getClassNum().get()+ "]class文件,加载了["+ SystemContext.getApplicationContext().getScanInfo().getServiceNum() + "]服务");

		PlatformLogger.info("系统启动耗时["+ (System.currentTimeMillis() - startTime) + "]");

		PlatformLogger.info("====系统启动完成!====");
	}

	/**
	 * 
	 * @throws TscpBaseException
	 */
	private void checkSystemEnv() throws TscpBaseException {

		if (SystemContext.getApplicationContext().getSystemConfigFile().getCoreConfigFile() == null)
			throw new TscpBaseException("TSC-9005:The core configuration file can't be found , please check environment configuration！");

		ApplicationContext context = SystemContext.getApplicationContext();
		StringBuffer serverInfo = new StringBuffer("启动太极安全自主可控平台[TAIJI Security Controllable Platform](").append(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date())).append(")，服务器信息:\r\n\r\n");
		serverInfo.append("\t服务器-主配置文件: ").append(context.getSystemConfigFile().getCoreConfigFile().getPath()).append("\r\n");
		serverInfo.append("\t服务器-服务实例名: ").append(context.getSystemCoreConfig().getServer()).append("\r\n");
		serverInfo.append("\t服务器-服务结点名: ").append(context.getSystemCoreConfig().getNodeName()).append("\r\n");
		serverInfo.append("\t服务器-机架ID: " + context.getSystemCoreConfig().getMachine()).append("\r\n");
		serverInfo.append("\t服务器-主机名/用户名/IP: "+ context.getSystemProfile().getHostName()).append(" / ").append(context.getSystemProfile().getUser()).append(" / ").append(context.getSystemProfile().getIp()).append("\r\n");
		serverInfo.append("\t服务器-JVM进程编号: " + context.getSystemProfile().getPid()).append("\r\n");
		serverInfo.append("\t服务器-运行模式: ");

		if (RunningMode.PRODUCT.name().equals(context.getSystemCoreConfig().getRunModel())) {
			serverInfo.append("Product Mode");
		} else if (RunningMode.DEVELOP.name().equals(context.getSystemCoreConfig().getRunModel())) {
			serverInfo.append("Develop Mode");
		} else if (RunningMode.TEST.name().equals(context.getSystemCoreConfig().getRunModel())) {
			serverInfo.append("Test Mode");
		} else {
			serverInfo.append("Other Mode");
		}

		serverInfo.append("\r\n");
		serverInfo.append("\tJava虚拟机-操作系统: ").append(context.getSystemProfile().getOperatingSystem()).append(" ").append(context.getSystemProfile().getOperatingSystemVersion()).append("(").append(context.getSystemProfile().getOperatingSystemPatch()).append("),").append(context.getSystemProfile().getOSArch()).append("\r\n");
		serverInfo.append("\tJava虚拟机-可用处理器数目: ").append(context.getSystemProfile().getAvailableProcessors()).append("\r\n");
		serverInfo.append("\tJava虚拟机-可使用的最大内存容量(M): ").append((context.getSystemProfile().getXmxMaxSize() / 1024 / 1024)).append("\r\n");
		serverInfo.append("\tJava虚拟机-可使用的最大DirectMemory容量(M): ").append((SystemUtils.getMaxDirectMemory() / 1024 / 1024)).append("\r\n");
		serverInfo.append("\tJava虚拟机-是否启用EPoll模式：").append(false).append("\r\n");
		serverInfo.append("\tJava虚拟机-位置: ").append(SystemUtils.getJavaHome()).append("\r\n");
		serverInfo.append("\tJava虚拟机-JDK: ").append(SystemUtils.getJavaVendor()).append(" ").append(SystemUtils.getJavaVersion()).append("\r\n");
		serverInfo.append("\tJava虚拟机-VM启动参数: ").append(SystemUtils.getInputArguments()).append("\r\n");
		serverInfo.append("\tJava虚拟机-信息: ").append(SystemUtils.getJVMVendor()).append(",");
		serverInfo.append(SystemUtils.getJavaRuntimeVersion()).append(",");
		serverInfo.append(SystemUtils.getSunArchDataModel()).append("bit,");
		serverInfo.append(SystemUtils.getCompilerInfo()).append(",");
		serverInfo.append(SystemUtils.getJVMMode()).append("\r\n");

		if (ManagementFactory.getRuntimeMXBean().isBootClassPathSupported()) {
			serverInfo.append("\tJava虚拟机-BootClassPath: ").append(SystemUtils.getClassPathInfo()).append("\r\n");
		}

		serverInfo.append("\tJava虚拟机-ClassPath: ").append(SystemUtils.getClassPathInfo()).append("\r\n");
		serverInfo.append("\tJava虚拟机-动态链接库搜索路径: ").append(SystemUtils.getLibraryScanPath()).append("\r\n");
		serverInfo.append("\tJava虚拟机-默认临时文件路径: ").append(SystemUtils.getTempFileDir()).append("\r\n");
		serverInfo.append("\tJava虚拟机-默认字符集: ").append(SystemUtils.getDefaultCharsetName()).append("\r\n");

		final TimeZone defaultTimeZone = TimeZone.getDefault();
		serverInfo.append("\tJava虚拟机-默认时区：").append(defaultTimeZone.getDisplayName()).append("(").append(defaultTimeZone.getID()).append(")").append("\r\n");
		serverInfo.append("\tJava虚拟机-默认地区：").append(Locale.getDefault()).append("\r\n");
		serverInfo.append("\tJava虚拟机-Zip Alt Encoding: ").append(SystemUtils.getZipAltEncoding()).append("\r\n");
		serverInfo.append("\tJava虚拟机-默认显示语言: ").append(SystemUtils.getDefaultLanguage()).append("\r\n");
		serverInfo.append("\tJava虚拟机-用户当前目录: ").append(SystemUtils.getUserDir()).append("\r\n");
		serverInfo.append("\tJava虚拟机-磁盘空闲空间情况(MB): ").append(SystemUtils.getFreeSpace('m')).append("\r\n");

		PlatformLogger.info(serverInfo.toString());

	}

}
