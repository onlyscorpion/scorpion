package com.scorpion.huakerongtong.common.logfw.engine;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.scorpion.huakerongtong.api.common.ICustomHandler;
import com.scorpion.huakerongtong.api.common.ILogMessage;
import com.scorpion.huakerongtong.api.common.IMessageConsumer;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.common.context.SystemContext;

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
public class BrokerLogMessageConsumer implements IMessageConsumer{
	
	public static IMessageConsumer consumer;
	
	 ActiveMQConnectionFactory cf = null;
	 
	 ActiveMQConnection conn = null;
	 
	 Session session = null;
	 
	 MessageConsumer messageConsumer = null;
	 

	@Override
	public void init() throws ScorpionBaseException {
		  try{
			  cf = new ActiveMQConnectionFactory("vm://"+SystemContext.getApplicationContext().getSystemCoreConfig().getLogframeworkInfo().getBrokerName()+"?marshal=false&broker.persistent=false&broker.useJmx=false");
			  conn = (ActiveMQConnection) cf.createConnection();
			  conn.start();
			  session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
			  messageConsumer = session.createConsumer(session.createQueue(SystemContext.getApplicationContext().getSystemCoreConfig().getLogframeworkInfo().getLogs().get(0).get(Constant.THEME)));
		  }catch(JMSException e){
			  throw new ScorpionBaseException(e);
		  }
	}


	@Override
	public void consumer() throws ScorpionBaseException {
		while(true){
		
			try{
				ObjectMessage message = (ObjectMessage) messageConsumer.receive(3000);
				if(message == null)
					continue;
				
				ILogMessage log = (ILogMessage) message.getObject();
				for(ICustomHandler handler:handlers)
					handler.handler(log.getMessage());
		
			}catch(Throwable e){
				PlatformLogger.error(e);
			}
		}
	}
	
	
	public synchronized static IMessageConsumer getInstance(){
		
		if(consumer == null)
			consumer = new BrokerLogMessageConsumer();
		
		return consumer;
	}
	
	
	
}
