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
public interface ITscpConnectionPool<T> {
	
	
	/**
	 * @throws TscpBaseException
	 */
	void initConnPool() throws TscpBaseException;
	
	/**
	 * @return
	 * @throws TscpBaseException
	 */
	T getConnection()throws TscpBaseException;
	
	
	/**
	 * @throws TscpBaseException
	 */
	void extendConnection()throws TscpBaseException;
	
	
	void close(T t)throws TscpBaseException;
	
	
	/**
	 * @throws TscpBaseException
	 */
	void statusCheck()throws TscpBaseException;
	
	/**
	 * @throws TscpBaseException
	 */
	void destroy()throws TscpBaseException;
	
}
