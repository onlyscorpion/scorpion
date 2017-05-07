package org.scorpion.api.common;

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
public interface IMediationPool<T> {
	
	/**
	 * 
	 * @throws TscpBaseException
	 */
	public void init() throws TscpBaseException;
	
	/**
	 * 
	 * @throws TscpBaseException
	 */
	public void rebuild() throws TscpBaseException;
	
	/**
	 * @param key
	 * @param protocolType
	 * @throws TscpBaseException
	 */
	public void rebuildByProtocolId(String key,String protocolType) throws TscpBaseException;
	
	/**
	 * 
	 * @throws TscpBaseException
	 */
	public void increase() throws TscpBaseException;
	
	/**
	 * @param key
	 * 
	 * @param protocolType
	 * 
	 * @throws TscpBaseException
	 */
	public void increaseByProtocolInfo(String key,String protocolType) throws TscpBaseException;
	
	/**
	 * 
	 * @throws TscpBaseException
	 */
	public void reduce() throws TscpBaseException;
	
	
	void clear();
	
	void clear(String key);
	
	/**
	 * 
	 * @throws TscpBaseException
	 */
	public AbsMediationFactor getMediation(String protocolId,AbsProtocol absProtocol) throws InterruptedException,TscpBaseException;
	
	
	
	/**
	 * @param protocolId
	 * 
	 * @return
	 * 
	 * @throws InterruptedException
	 * 
	 * @throws TscpBaseException
	 */
	public AbsMediationFactor getMediation(String protocolId) throws InterruptedException,TscpBaseException;
	
	
	/**
	 * 
	 * @param key
	 * 
	 * @return
	 * 
	 * @throws InterruptedException
	 * 
	 * @throws TscpBaseException
	 */
	//public AbsMediationFactor getInitMediation(String protocolId) throws InterruptedException,TscpBaseException;
	
	/**
	 * 
	 * @throws TscpBaseException
	 */
	public void destroy() throws TscpBaseException;
	
	

}
