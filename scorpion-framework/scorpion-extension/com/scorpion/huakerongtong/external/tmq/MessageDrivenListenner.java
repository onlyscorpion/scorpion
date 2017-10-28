package com.scorpion.huakerongtong.external.tmq;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import com.scorpion.huakerongtong.api.common.AbsMessageListener;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IMessageReceiveHandler;
import com.scorpion.huakerongtong.api.kernel.IScorpionReqMedia;
import com.scorpion.huakerongtong.api.kernel.IScorpionRespMedia;
import com.scorpion.huakerongtong.api.log.PlatformLogger;

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
public class MessageDrivenListenner extends AbsMessageListener{

	@Override
	public void onMessage(Message message) throws ScorpionBaseException, JMSException {
		for(IMessageReceiveHandler handler:handlers){
			if(message instanceof TextMessage){
				textmessagehandler(handler,message);
			}else if(message instanceof ObjectMessage){
				if(((ObjectMessage) message).getObject() instanceof IScorpionReqMedia){
					internalMessageHandler(handler,message);
				}else{
					objectmessageHandler(handler,message);
				}
			}else{
				throw new ScorpionBaseException("scorpion-9874:Unknow message type !");
			}
		}
	}
	
	/**
	 * 
	 * @param handler
	 * @param message
	 * @throws ScorpionBaseException
	 * @throws JMSException
	 */
	public void textmessagehandler(IMessageReceiveHandler handler,Message message) throws ScorpionBaseException, JMSException{
		if(!message.getBooleanProperty("SFTB"))
			handler.receiveXml(((TextMessage) message).getText());
		else{
			String result = handler.receiveXml(((TextMessage) message).getText());
			replyMessage(message,result);
		}
	}
	
	
	/**
	 * @param handler
	 * @param message
	 * @throws ScorpionBaseException
	 * @throws JMSException
	 */
	public void internalMessageHandler(IMessageReceiveHandler handler,Message message) throws ScorpionBaseException, JMSException{
		if(!message.getBooleanProperty("SFTB"))
			handler.receveInternalMessage((IScorpionReqMedia)((ObjectMessage) message).getObject());
		else{
			IScorpionRespMedia result = handler.receveInternalMessage((IScorpionReqMedia)((ObjectMessage) message).getObject());
			replyMessage(message,result);
		}
	}
	
	
	/**
	 * @param handler
	 * @param message
	 * @throws ScorpionBaseException
	 * @throws JMSException
	 */
	public void objectmessageHandler(IMessageReceiveHandler handler,Message message) throws ScorpionBaseException, JMSException{
		if(!message.getBooleanProperty("SFTB"))
			handler.receiveObj(((ObjectMessage) message).getObject());
		else{
			Object result = handler.receiveObj(((ObjectMessage) message).getObject());
			replyMessage(message,result);
		}
	}
	
	
	/**
	 * @param message
	 * 
	 * @param retMessage
	 * 
	 * @throws JMSException
	 */
	public void replyMessage(Message message,Object retMessage) throws JMSException{
		  if(message.getStringProperty("REPLYDZ")!=null&&message.getStringProperty("REPLYDL")!=null){
			   PlatformLogger.info("=====回复消息sessionId ["+ message.getStringProperty("sessionId")+"]地址["+message.getStringProperty("REPLYDZ")+"]回复队列["+message.getStringProperty("REPLYDL")+"]");
			   ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(message.getStringProperty("REPLYDZ"));
			   ActiveMQConnection connection = null; MessageProducer producer = null; ActiveMQSession session = null;
			   try{
				   connection = (ActiveMQConnection) connectionFactory.createConnection();
				   session = (ActiveMQSession) connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
				   Destination destination = session.createQueue(message.getStringProperty("REPLYDL"));
				   producer = session.createProducer(destination);
				   connection.start();
				   ObjectMessage mg = session.createObjectMessage((Serializable)retMessage);
				   mg.setStringProperty("sessionId", message.getStringProperty("sessionId"));
				   mg.setObject((Serializable) retMessage);
				   producer.send(mg);
			   }finally{
				   if(connection != null)
					   connection.close();
				   if(producer != null)
					   producer.close();
				   if(session != null)
					   session.close();
			   }
		  }
	}

}
