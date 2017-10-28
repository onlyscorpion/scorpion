package com.scorpion.huakerongtong.api.kernel;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.scorpion.huakerongtong.api.configuration.ComponentInformation;
import com.scorpion.huakerongtong.api.configuration.DataSourceLis;
import com.scorpion.huakerongtong.api.configuration.ExceptionInfo;
import com.scorpion.huakerongtong.api.configuration.MessageSenderInfo;
import com.scorpion.huakerongtong.api.configuration.PojoEntityInfo;
import com.scorpion.huakerongtong.api.configuration.SystemConfigFile;
import com.scorpion.huakerongtong.api.configuration.SystemProfile;
import com.scorpion.huakerongtong.api.configuration.ScorpionCoreConfig;
import com.scorpion.huakerongtong.api.configuration.ScorpionSystemScanInfo;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface ApplicationContext {
	
	/**
	 * @Description Return current server node name ...
	 * @return
	 * @throws ScorpionBaseException
	 */
	public String getServerName() throws ScorpionBaseException;
	
	/**
	 * @Descrtion Get application build path ...
	 * 
	 * get system build path
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public String[] getSystemBuildPath()throws ScorpionBaseException;
	
	
	/**
	 * @Description Configure application default classLoader ...
	 * 
	 * @param systemClassLoader
	 * 
	 * @throws ScorpionBaseException
	 */
	public void setSystemClassLoader(ClassLoader systemClassLoader)throws ScorpionBaseException;
	
	
	/**
	 * @Description Register system component manager ...
	 * 
	 * @param name
	 * 
	 * @param component
	 * 
	 * @throws ScorpionBaseException
	 */
	//@Deprecated
	public void registerSystemComponent(String name,AbsScorpionComponent component) throws ScorpionBaseException;
	
	/**
	 * @Description Get component manager information ...
	 * 
	 * @throws ScorpionBaseException
	 */
	public Map<String,ComponentInformation> getCoreComponets() throws ScorpionBaseException;
	
	
	/**
	 * @description Getting exception configuration file by file name ...
	 * 
	 * @param name
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public File getExceptionPropertiesFileByName(String name)throws ScorpionBaseException;
	
	
	/**
	 * @Description Return SQL configuration file by SQL key name ...
	 * 
	 * @param name
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public File getSQLConfigFileByName(String name)throws ScorpionBaseException;
	
	
	/**
	 * @Description Get application configuration file ...
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public SystemConfigFile getSystemConfigFile() throws ScorpionBaseException;
	
	
	
	/**
	 * @Description Return system default classloader...
	 * 
	 * get system class loader
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public ClassLoader getSystemClassLoader() throws ScorpionBaseException;
	
	
	/**
	 * @Description Get system profile information ...
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public SystemProfile getSystemProfile()throws ScorpionBaseException;
	
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
	public ScorpionCoreConfig getSystemCoreConfig();
	
	/**
	 * @description Application scanning information...
	 * @return
	 * @throws ScorpionBaseException
	 */
	public ScorpionSystemScanInfo getScanInfo()throws ScorpionBaseException;
	
	/**
	 * @Description Return IOC manager ...
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
    public IScorpionSystemIocManager getIocManager();
    
    /**
     * @return
     */
	public DataSourceLis getDataSource() ;
	
	/**
	 * 
	 * @return
	 */
	public PojoEntityInfo getSystemPojoInformation()throws ScorpionBaseException;
	
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
