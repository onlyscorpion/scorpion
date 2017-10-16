package org.scorpion.api.common;

import org.scorpion.api.exception.ScorpionBaseException;

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
public interface IMediationPool<T> {
	
	/**
	 * 
	 * @throws ScorpionBaseException
	 */
	public void init() throws ScorpionBaseException;
	
	/**
	 * 
	 * @throws ScorpionBaseException
	 */
	public void rebuild() throws ScorpionBaseException;
	
	/**
	 * @param key
	 * @param protocolType
	 * @throws ScorpionBaseException
	 */
	public void rebuildByProtocolId(String key,String protocolType) throws ScorpionBaseException;
	
	/**
	 * 
	 * @throws ScorpionBaseException
	 */
	public void increase() throws ScorpionBaseException;
	
	/**
	 * @param key
	 * 
	 * @param protocolType
	 * 
	 * @throws ScorpionBaseException
	 */
	public void increaseByProtocolInfo(String key,String protocolType) throws ScorpionBaseException;
	
	/**
	 * 
	 * @throws ScorpionBaseException
	 */
	public void reduce() throws ScorpionBaseException;
	
	
	void clear();
	
	void clear(String key);
	
	/**
	 * 
	 * @throws ScorpionBaseException
	 */
	public AbsMediationFactor getMediation(String protocolId,AbsProtocol absProtocol) throws InterruptedException,ScorpionBaseException;
	
	
	
	/**
	 * @param protocolId
	 * 
	 * @return
	 * 
	 * @throws InterruptedException
	 * 
	 * @throws ScorpionBaseException
	 */
	public AbsMediationFactor getMediation(String protocolId) throws InterruptedException,ScorpionBaseException;
	
	
	/**
	 * 
	 * @param key
	 * 
	 * @return
	 * 
	 * @throws InterruptedException
	 * 
	 * @throws ScorpionBaseException
	 */
	//public AbsMediationFactor getInitMediation(String protocolId) throws InterruptedException,ScorpionBaseException;
	
	/**
	 * 
	 * @throws ScorpionBaseException
	 */
	public void destroy() throws ScorpionBaseException;
	
	

}
