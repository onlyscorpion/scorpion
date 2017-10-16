package org.scorpion.api.common;

import java.io.Serializable;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface ILogMessage extends Serializable{

	/**
	 * 
	 * @param message
	 */
	public void putTextMessageLog(String message);
	
	/**
	 * 
	 * @param obj
	 */
	public void putObjectMessageLog(Object obj);
	
	/**
	 * 
	 * @return
	 */
	public String getTextMessageLog();
	
	/**
	 * 
	 * @return
	 */
	public Object getObjectMessageLog();
	
	/**
	 * 
	 * @return
	 */
	public String getCurrentQueueName();
	
	/**
	 * 
	 * @return
	 */
	public String setQueueName(String qName);
	
	/**
	 * 
	 * @return
	 */
	public Object getMessage();
	
	
}
