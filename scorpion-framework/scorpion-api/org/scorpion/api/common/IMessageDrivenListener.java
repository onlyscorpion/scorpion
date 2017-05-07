package org.scorpion.api.common;

import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.IMessageReceiveHandler;

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
public interface IMessageDrivenListener {
	
	
	
	/**
	 * @description Listener queue . When message arriving . It will trigger the listener to handle business .
	 * 
	 * @Time 2015-08-25
	 * 
	 * @throws TscpBaseException JMSException
	 * 
	 * @author zhengchenglei
	 */
    void onMessage(Message message) throws TscpBaseException, JMSException;
	
	
    /**
     * 
     * @return
     */
    List<IMessageReceiveHandler> getMessageReceiveHandler(); 
    
    /**
     * 
     * @param key
     * 
     * @param handler
     */
    void addMessageReceiveHandler(IMessageReceiveHandler handler);
	

}
