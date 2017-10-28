package com.scorpion.huakerongtong.api.common;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.scorpion.huakerongtong.api.kernel.IMessageReceiveHandler;
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
public abstract class AbsMessageListener implements IMessageDrivenListener{
	
	
	public String brokerName;
	
	public String themem;
	
	public List<IMessageReceiveHandler> handlers = new ArrayList<IMessageReceiveHandler>();
	

	@Override
	public List<IMessageReceiveHandler> getMessageReceiveHandler() {
		
		return handlers;
	}

	
	@Override
	public void addMessageReceiveHandler(IMessageReceiveHandler handler) {
		
		handlers.add(handler);
	}
	
	
	protected void init() throws JMSException{
		
		if(brokerName == null||"".equals(brokerName)||themem == null||"".equals(themem))
			return ;
		
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("vm://"+brokerName+"?marshal=false&broker.persistent=false&broker.useJmx=false");
		ActiveMQConnection  conn = (ActiveMQConnection) cf.createConnection();
		conn.start();
		Session session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(themem);
		MessageConsumer consumer = session.createConsumer(destination);
		while(true){
			try{
				this.onMessage(consumer.receive());
			}catch(Exception e){
				PlatformLogger.error(e);
			}
		}
	
	}
	

	
	/**
	 * @Description listener name ...
	 * 
	 * @return
	 */
	public String getListenerName(){
		
		return this.brokerName + this.themem;
	}
	
	

}
