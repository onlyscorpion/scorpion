package org.scorpion.common.context;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scorpion.api.configuration.ComponentInformation;
import org.scorpion.api.configuration.DataSourceLis;
import org.scorpion.api.configuration.ExceptionInfo;
import org.scorpion.api.configuration.MessageSenderInfo;
import org.scorpion.api.configuration.PojoEntityInfo;
import org.scorpion.api.configuration.SQLConfig;
import org.scorpion.api.configuration.SystemConfigFile;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.configuration.SystemProfile;
import org.scorpion.api.configuration.SystemResourcePool;
import org.scorpion.api.configuration.TscpCoreConfig;
import org.scorpion.api.configuration.TscpSystemScanInfo;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpComponent;
import org.scorpion.api.kernel.ApplicationContext;
import org.scorpion.api.kernel.IBaseMessageSender;
import org.scorpion.api.kernel.IMessageReceiveHandler;
import org.scorpion.api.kernel.ITscpSystemIocManager;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.lifecycle.TscpLifecycleManager;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public final class SystemContext implements ApplicationContext{
	
    /**
     * EXTENSION COMPONET INFORMATION
     */
	private final Map<String,AbsTscpComponent> components;
	
	/**
	 * EXCEPTION CONFIGURATION
	 */
	private final List<ExceptionInfo> exceptions ;
	
	/**
	 * SQL CONFIGURATION FILE
	 */
    private final SQLConfig sqlconfig;
    
    /**
     * DATA SOURCE INFORMATION
     */
    private final DataSourceLis dataSource;
    
    /**
     * SYSTEM PROFILE INFORMATION
     */
    private final SystemProfile systemProfile;
    
    /**
     * SCANNING INFORMATION
     */
   	private final TscpSystemScanInfo scanInfo;
  
   	/**
   	 * PERSISTENCE OBJECT INFORMATION
   	 */
   	private final PojoEntityInfo pojoInfo;
   	
    /**
     * CORE FILE CONFIGURATION
     */
	private final TscpCoreConfig coreconfig;
	
	/**
	 * CORE COMPONENT CONFIGURATION INFORMATION
	 */
	private final Map<String,ComponentInformation> coreComponets ;
	
	/**
	 * SYSTEM CONFIGURAITON FILE
	 */
    private final SystemConfigFile systemconfigfile;
    
    /**
     * EXTENSION BULID PATH 
     */
   	private Set<String> appBuildPath;
   	
   	/**
   	 * SYSTEM DEFAULT CLASS LOADER
   	 */
   	private ClassLoader systemClassLoader;
   	
   	/**
   	 * IOC MANAGER
   	 */
   	private ITscpSystemIocManager iocManager;
   	
   	/**
   	 * SYSTEM MESSAGE SENDER
   	 */
   	private final Map<String,MessageSenderInfo> messageSenders;
   	
   	/**
   	 * SYSTEM MESSAGE RECEIVER
   	 */
   	private IMessageReceiveHandler messageReceiver;
   	
   	/**
   	 * SYSTEM CONTEXT INFROMATION
   	 */
	private static ApplicationContext context = getApplicationContext();
	
	/**
	 * 
	 */
	//private final Map<String,TimerEntity> timers;
   
	
	
	@SuppressWarnings("unchecked")
	private SystemContext() {
		
	      exceptions=  SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.systemexceptionconfigresource.getValue(),List.class);
	      sqlconfig = SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.systemsqlconfigresource.getValue(),SQLConfig.class);
	      dataSource = SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.systemdatasourceresource.getValue(),DataSourceLis.class);
	      coreconfig = SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.systemcoreconfigresource.getValue(),TscpCoreConfig.class);
	      components = SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.systemcomponentresource.getValue(),Map.class);
	   
	 //     timers = new HashMap<String,TimerEntity>();
	      scanInfo = new TscpSystemScanInfo();
	      systemProfile = new SystemProfile();
		  coreComponets = new HashMap<String,ComponentInformation>();
		  pojoInfo = new PojoEntityInfo();
		  systemconfigfile = new SystemConfigFile();
		  messageSenders = new HashMap<String,MessageSenderInfo>();
	}
	
	@Override
	public String getServerName() throws TscpBaseException {
		
		checkSystemContextInitInfo(coreconfig);
	
		return coreconfig.getServer();
	}
	
	/**
	 * @Description Get context application information ...
	 * 
	 * @return
	 * 
	 */
	public synchronized static ApplicationContext getApplicationContext() {
		
		  if(context == null) 
			 
			  context = new SystemContext();
	    
		  return context;
	}
	
	
	/**
	 * @Description validate system environment information ....
	 * 
	 * @param systemProfile
	 * 
	 * @throws TscpBaseException
	 * 
	 */
	public void checkSystemContextInitInfo(TscpCoreConfig systemProfile) throws TscpBaseException{
		
		if(systemProfile == null)
			throw new TscpBaseException("TSCP-9964:System argument don't initialize !");
	
	}
	
	
	/**
	 * @description Setting application context information .. Don't suggest use this method ..
	 * 
	 * @param context
	 * 
	 */
	@Deprecated
	public static void setContext(ApplicationContext context) {
		
		SystemContext.context = context;
	}

	
	/**
	 * @Description Get application exception configuration file ...
	 * 
	 * @return
	 * 
	 */
	public List<ExceptionInfo> getExceptionsInfo() {
	
		return exceptions;
	}

	
	/**
	 * @Description Get system message sender ...
	 * 
	 * @return
	 */
    public Map<String,MessageSenderInfo> getMessageSenders() {
	
    	return messageSenders;
	}

	/**
     * @Description Get system SQL configuration file ...
     * 
     * @return
     * 
     */
	public SQLConfig getSqlconfig() {
	
		return sqlconfig;
	}

	
	/**
	 * @description Get application datasource information
	 * 
	 * @return
	 * 
	 */
	public DataSourceLis getDataSource() {
		
		return dataSource;
	}
	
	
    /**
     * @description Get system core configuration file
     * 
	 * @param
     */
	public TscpCoreConfig getSystemCoreConfig(){
	
		return coreconfig;
	}
	
	
	/**
	 * @Description Return application outing component ...
	 * 
	 * @return
	 */
	public Map<String,AbsTscpComponent> components(){
	
		return this.components;
	}
	
	
	/**
	 * @description Register application component ...
	 * 
	 * @param name
	 * 
	 * @param component
	 */
	public void registerSystemComponent(String name,AbsTscpComponent component) throws TscpBaseException{
		
		TscpLifecycleManager.registerLifecycleSingle(component);
		
		this.components.put(name, component);
	}

	
	@Override
	public String[] getSystemBuildPath()throws TscpBaseException {
		
		if(appBuildPath == null)
			 appBuildPath = new HashSet<String>();
	
		String[] temp = new String[appBuildPath.size()];
		appBuildPath.toArray(temp);
		return temp;
	}
	
	
    /**
     * @Description Setting system building path ...
     * 
     * @param buildPath
     * 
     * @throws TscpBaseException 
     */
	public void setBuildPath(String[] buildPath) throws TscpBaseException {
		
		if(appBuildPath == null)  appBuildPath = new HashSet<String>();
		
		if(SystemUtils.getOSName().indexOf("Linux")>=0){
		
			for(int i=0;i<buildPath.length;i++) {
			
				if(buildPath[i] == null||"".equals(buildPath[i]))
					continue;
			
				buildPath[i] = buildPath[i].replace("\\", "/");
			
				if(buildPath[i].indexOf("/")>0){
					appBuildPath.add(File.separator+buildPath[i]);
				}
			}
		}else if(SystemUtils.getOSName().indexOf("Window")>=0){
		
			for(int i=0;i<buildPath.length;i++) 
				appBuildPath.add(buildPath[i]);
		}else{
			throw new TscpBaseException("TSCP9076:Don't support operating system ["+SystemUtils.getOSName()+"]");
		}
	}

	
	@Override
	public File getExceptionPropertiesFileByName(String name) throws TscpBaseException {
		
		for(File file:systemconfigfile.getUserDefaultExceptionPropertiesFiles())
			
			if(file.getName().equals(name))
				return file;
		
		return null;
	}

	
	@Override
	public File getSQLConfigFileByName(String name) throws TscpBaseException {
		
		for(File file:systemconfigfile.getSqlConfigFiles())
		 
			if(file.getName().equals(name))
				return file;
		
		return null;
	}

	@Override
	public SystemConfigFile getSystemConfigFile() throws TscpBaseException {
		
		return systemconfigfile;
	}

	@Override
	public ClassLoader getSystemClassLoader()throws TscpBaseException{
		
		return systemClassLoader;
	}

	
	@Override
	public Map<String, ComponentInformation> getCoreComponets() throws TscpBaseException{
		
		return coreComponets;
	}
    
	
	/**
	 * @description Setting application default classloader ...
	 * 
	 * @param
	 */
	public void setSystemClassLoader(ClassLoader systemClassLoader)throws TscpBaseException {
		
		this.systemClassLoader = systemClassLoader;
	}

	
	/**
	 * @return
	 * @throws TscpBaseException
	 */
	public SystemProfile getSystemProfile()throws TscpBaseException{
	
		return systemProfile;
	}
	
    /**
     * @Description Return application scanning information ...
     */
	public TscpSystemScanInfo getScanInfo()throws TscpBaseException {

		return scanInfo;
	}
	
   
	/**
     * 获取系统IOC容器
     */
	public ITscpSystemIocManager getIocManager() {
	
		return iocManager;
	}
	
	
    /**
     * @Description Setting application IOC container ...
     * 
     * @param iocManager
     */
	public void setIocManager(ITscpSystemIocManager iocManager) {
	
		this.iocManager = iocManager;
	}

	
	@Override
	public PojoEntityInfo getSystemPojoInformation() throws TscpBaseException {

		return this.pojoInfo;
	}

	@Override
	public IBaseMessageSender getMessageSenderByName(String senderName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, ComponentInformation> getOuterComponentConfInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public IMessageReceiveHandler getExtendMessageReciever() {
		
		return this.messageReceiver;
	}

	public void setMessageReceiver(IMessageReceiveHandler messageReceiver) {
		this.messageReceiver = messageReceiver;
	}
	
}
