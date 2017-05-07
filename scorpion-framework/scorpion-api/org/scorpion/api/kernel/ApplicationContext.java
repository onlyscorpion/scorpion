package org.scorpion.api.kernel;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.scorpion.api.configuration.ComponentInformation;
import org.scorpion.api.configuration.DataSourceLis;
import org.scorpion.api.configuration.ExceptionInfo;
import org.scorpion.api.configuration.MessageSenderInfo;
import org.scorpion.api.configuration.PojoEntityInfo;
import org.scorpion.api.configuration.SystemConfigFile;
import org.scorpion.api.configuration.SystemProfile;
import org.scorpion.api.configuration.TscpCoreConfig;
import org.scorpion.api.configuration.TscpSystemScanInfo;
import org.scorpion.api.exception.TscpBaseException;

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
public interface ApplicationContext {
	
	/**
	 * @Description Return current server node name ...
	 * @return
	 * @throws TscpBaseException
	 */
	public String getServerName() throws TscpBaseException;
	
	/**
	 * @Descrtion Get application build path ...
	 * 
	 * get system build path
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public String[] getSystemBuildPath()throws TscpBaseException;
	
	
	/**
	 * @Description Configure application default classLoader ...
	 * 
	 * @param systemClassLoader
	 * 
	 * @throws TscpBaseException
	 */
	public void setSystemClassLoader(ClassLoader systemClassLoader)throws TscpBaseException;
	
	
	/**
	 * @Description Register system component manager ...
	 * 
	 * @param name
	 * 
	 * @param component
	 * 
	 * @throws TscpBaseException
	 */
	//@Deprecated
	public void registerSystemComponent(String name,AbsTscpComponent component) throws TscpBaseException;
	
	/**
	 * @Description Get component manager information ...
	 * 
	 * @throws TscpBaseException
	 */
	public Map<String,ComponentInformation> getCoreComponets() throws TscpBaseException;
	
	
	/**
	 * @description Getting exception configuration file by file name ...
	 * 
	 * @param name
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public File getExceptionPropertiesFileByName(String name)throws TscpBaseException;
	
	
	/**
	 * @Description Return SQL configuration file by SQL key name ...
	 * 
	 * @param name
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public File getSQLConfigFileByName(String name)throws TscpBaseException;
	
	
	/**
	 * @Description Get application configuration file ...
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public SystemConfigFile getSystemConfigFile() throws TscpBaseException;
	
	
	
	/**
	 * @Description Return system default classloader...
	 * 
	 * get system class loader
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public ClassLoader getSystemClassLoader() throws TscpBaseException;
	
	
	/**
	 * @Description Get system profile information ...
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public SystemProfile getSystemProfile()throws TscpBaseException;
	
	/**
     * Creates a URL from the specified <code>protocol</code>
     * name, <code>host</code> name, and <code>file</code> name. The
     * default port for the specified protocol is used.
     * <p>
     * This method is equivalent to calling the four-argument
     * constructor with the arguments being <code>protocol</code>,
     * <code>host</code>, <code>-1</code>, and <code>file</code>.
     *
     * No validation of the inputs is performed by this constructor.
     *
     * @param      protocol   the name of the protocol to use.
     * @param      host       the name of the host.
     * @param      file       the file on the host.
     * @exception  MalformedURLException  if an unknown protocol is specified.
     * @see        java.net.URL#URL(java.lang.String, java.lang.String,
     *			int, java.lang.String)
     */
	public TscpCoreConfig getSystemCoreConfig();
	
	/**
	 * @description Application scanning information...
	 * @return
	 * @throws TscpBaseException
	 */
	public TscpSystemScanInfo getScanInfo()throws TscpBaseException;
	
	/**
	 * @Description Return IOC manager ...
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
    public ITscpSystemIocManager getIocManager();
    
    /**
     * @return
     */
	public DataSourceLis getDataSource() ;
	
	/**
	 * 
	 * @return
	 */
	public PojoEntityInfo getSystemPojoInformation()throws TscpBaseException;
	
	/**
	 * 
	 * @return
	 */
	public Map<String,MessageSenderInfo> getMessageSenders();
	
	/**
	 * 
	 * @param senderName
	 * @return
	 */
	public IBaseMessageSender getMessageSenderByName(String senderName);
	
	/**
	 * @return
	 */
	public List<ExceptionInfo> getExceptionsInfo();
	
	
	/**
	 * @return
	 */
	public Map<String,ComponentInformation> getOuterComponentConfInfo();
	
	/**
	 * 
	 * @return
	 */
	public IMessageReceiveHandler getExtendMessageReciever();

    

}
