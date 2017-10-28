package com.scorpion.huakerongtong.external.tmq;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.scorpion.huakerongtong.api.common.IMQDeadMessageDataHandler;
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
