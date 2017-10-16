package org.scorpion.api.kernel;

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
public interface IMessageReceiveHandler {
	
	
	/**
	 * @description Receive internal message ...
	 * 
	 * @param arguments
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public IScorpionRespMedia receveInternalMessage(IScorpionReqMedia arguments)throws ScorpionBaseException;
	
	
	/**
	 * @description Receive xml message ...
	 * 
	 * @param xml
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public String receiveXml(String xml)throws ScorpionBaseException;
	
	
	/**
	 * @description Receive Object message...
	 * 
	 * @param obj
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public Object receiveObj(Object obj)throws ScorpionBaseException;

	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws ScorpionBaseException
	 */
	public Object receiveExtendObjMessage(Object obj) throws ScorpionBaseException;
	
	
	/**
	 * @param xml
	 * @return
	 * @throws ScorpionBaseException
	 */
	public String receiveExtendStrMessage(String xml)throws ScorpionBaseException;
	

}
