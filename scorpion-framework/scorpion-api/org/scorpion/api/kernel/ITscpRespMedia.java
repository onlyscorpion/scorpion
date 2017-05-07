package org.scorpion.api.kernel;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.scorpion.api.exception.TscpBaseException;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: if developer want to create a component. the developer must extends the abstract </p>
 * <p>class ATscpComponet. the ATscpComponent exist life cycle. developer can override</p>
 * <p>the initialization method or service method or destroy method to handle themselves business</p>
 * <p>but we don't suggest the developer do that </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface ITscpRespMedia extends Serializable{
	
	
	/**
	 * @param key
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public Object getRespTempData(String key)throws TscpBaseException;
	
	
	/**
	 * 
	 * @param key
	 * 
	 * @param obj
	 * 
	 * @throws TscpBaseException
	 */
	public void setRespTempData(String key,Object value)throws TscpBaseException;
	
	/**
	 * 
	 * @param map
	 * @throws TscpBaseException
	 */
	public void setRespTempData(Map<String,Object> map)throws TscpBaseException;
	
	/**
	 * @return
	 */
	public Object getResponseValue();

	/**
	 * @return
	 */
	public int getCallLevel();
	
	/**
	 * @return
	 */
	public ConcurrentHashMap<Object, Object> getSystemContext();

	
	/**
	 * @return
	 */
	public ConcurrentHashMap<Object, Object> getTmpData();

	/**
	 * @return
	 */
	public ConcurrentHashMap<Object, Object> getApplicationContext();
	
	/**
	 * 
	 * @return
	 */
	public Map<String, Object> getTempData();

	


}
