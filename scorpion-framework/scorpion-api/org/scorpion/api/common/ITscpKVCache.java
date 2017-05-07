package org.scorpion.api.common;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
public interface ITscpKVCache {
	
	
	/**
	 * @param key
	 * @return
	 * @throws TscpBaseException
	 */
	String getValueByKey(String key)throws TscpBaseException;
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	void insert(String key,Object value)throws TscpBaseException;
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @throws TscpBaseException
	 */
	void insert(Object key,Object value)throws TscpBaseException;
	
	/**
	 * 
	 * @param key
	 * @param arguments
	 */
	void insertMapStr(String key,Map<String,String> arguments)throws TscpBaseException;
	
	
	/**
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	<T> T queryDataByKey(Object key,Class<T> clazz)throws TscpBaseException;
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	Map<String,Object> queryMapByKey(String key)throws TscpBaseException;
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	List<Map<String,Object>> queryLisMapByKey(String key)throws TscpBaseException;
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	boolean exist(String key)throws TscpBaseException;
	
	/**
	 * @param command
	 * @return
	 * @throws TscpBaseException
	 */
	Set<String> queryKeys(String command)throws TscpBaseException;
	
	/**
	 * @throws TscpBaseException
	 */
	void clearAll()throws TscpBaseException;
	
	
	/**
	 * @throws TscpBaseException
	 */
	void close()throws TscpBaseException;
	

}
