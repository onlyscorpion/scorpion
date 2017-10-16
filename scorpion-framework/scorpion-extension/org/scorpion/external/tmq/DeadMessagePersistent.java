package org.scorpion.external.tmq;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.scorpion.api.common.IMQDeadMessageDataHandler;
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
public class DeadMessagePersistent implements IMQDeadMessageDataHandler{

	
	
	@Override
	public void deadMessageHandler(Message message)throws ScorpionBaseException {
		
		
		if(message instanceof TextMessage){
			
		}else if(message instanceof ObjectMessage){
			
		}else{
			throw new ScorpionBaseException("scorpion-9978:Unknow message type !");
		}
		
		
	}

}
