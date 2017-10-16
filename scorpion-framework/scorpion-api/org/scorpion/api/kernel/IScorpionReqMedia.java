package org.scorpion.api.kernel;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;

import org.scorpion.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: if developer want to create a component. the developer must extends the abstract </p>
 * <p>class AScorpionComponet. the AScorpionComponent exist life cycle. developer can override</p>
 * <p>the initialization method or service method or destroy method to handle themselves business</p>
 * <p>but we don't suggest the developer do that </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface IScorpionReqMedia extends Serializable{
	
	/**
	 * 
	 * @return
	 */
	public String getServiceName();
	
	
	/**
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public Map<String,Object> getReqTempData()throws ScorpionBaseException;
	
	
	/**
	 * 
	 * @return
	 */
	public String getSessionId();
	
	
	/**
	 * @param key
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public Object getReqTempData(String key)throws ScorpionBaseException;
	
	
	/**
	 * 
	 * @param key
	 * 
	 * @param value
	 * 
	 * @throws ScorpionBaseException
	 */
	public void setReqTempData(String key,Object value)throws ScorpionBaseException;
	
	
	/**
	 * 
	 * @return
	 */
	public LinkedList<String> getServers();
	
   
	
	/**
	 * 
	 * 设置sessionId
	 * 
	 * @param sessionId
	 */
	public void setSessionId(String sessionId);
	
	
	/**
	 * @param userId
	 */
	public void setUserId(String userId);
	
	/**
	 * @return
	 */
	public String getUserId();
	
	/**
	 * 
	 * @return
	 */
	public Object[] getServiceArgument();
	
	
	/**
	 * 
	 * @param tempData
	 * 
	 * @throws ScorpionBaseException
	 */
	public void setReqTempData(Map<String,Object> tempData)throws ScorpionBaseException;
	
	/**
	 * @description add current server node
	 */
	public void addCurrentServer() throws ScorpionBaseException;
	
	
	/**
	 * @param obj
	 * 
	 * @param index
	 * 
	 * @throws ScorpionBaseException
	 */
	public void addServiceParameter(Object obj,int index)throws ScorpionBaseException;
	

}
