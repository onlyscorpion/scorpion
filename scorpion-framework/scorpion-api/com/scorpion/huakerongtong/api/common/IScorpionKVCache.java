package com.scorpion.huakerongtong.api.common;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
public interface IScorpionKVCache {
	
	
	/**
	 * @param key
	 * @return
	 * @throws ScorpionBaseException
	 */
	String getValueByKey(String key)throws ScorpionBaseException;
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	void insert(String key,Object value)throws ScorpionBaseException;
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @throws ScorpionBaseException
	 */
	void insert(Object key,Object value)throws ScorpionBaseException;
	
	/**
	 * 
	 * @param key
	 * @param arguments
	 */
	void insertMapStr(String key,Map<String,String> arguments)throws ScorpionBaseException;
	
	
	/**
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	<T> T queryDataByKey(Object key,Class<T> clazz)throws ScorpionBaseException;
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	Map<String,Object> queryMapByKey(String key)throws ScorpionBaseException;
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	List<Map<String,Object>> queryLisMapByKey(String key)throws ScorpionBaseException;
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	boolean exist(String key)throws ScorpionBaseException;
	
	/**
	 * @param command
	 * @return
	 * @throws ScorpionBaseException
	 */
	Set<String> queryKeys(String command)throws ScorpionBaseException;
	
	/**
	 * @throws ScorpionBaseException
	 */
	void clearAll()throws ScorpionBaseException;
	
	
	/**
	 * @throws ScorpionBaseException
	 */
	void close()throws ScorpionBaseException;
	

}
