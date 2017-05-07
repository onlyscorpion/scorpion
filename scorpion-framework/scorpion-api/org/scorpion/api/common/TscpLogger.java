package org.scorpion.api.common;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface TscpLogger {
	
	/**
	 * @Description Setting log out information
	 * 
	 * @param object
	 */
	public void debug(Object obj);
	
	
	/**
	 * @param obj
	 */
	public void debug(Object... obj);
	
	/**
	 * 
	 * @param object
	 * @param throwable
	 */
	public void debug(Object obj,Throwable throwable);
	
	/**
	 * 
	 * @param object
	 */
	public void info(Object obj);
	
	/**
	 * 
	 * @param obj
	 */
	public void info(Object...obj);
	
	/**
	 * 
	 * @param object
	 * @param throwable
	 */
	public void info(Object obj,Throwable throwable);
	
	/**
	 * 
	 * @param object
	 */
	public void warn(Object obj);
	
	/**
	 * 
	 * @param ojb
	 */
	public void warn(Object...ojb);
	
	/**
	 * 
	 * @param object
	 * @param throwable
	 */
	public void warn(Object obj,Throwable throwable);
	
	/**
	 * 
	 * @param object
	 */
	public void error(Object obj);
	
	/**
	 * 
	 * @param obj
	 */
	public void error(Object...obj);
	
	/**
	 * 
	 * @param object
	 * @param throwable
	 */
	public void error(Object obj,Throwable throwable);
	
	/**
	 * 
	 * @param level
	 */
	public void setLogLevel(String level);
	
}
