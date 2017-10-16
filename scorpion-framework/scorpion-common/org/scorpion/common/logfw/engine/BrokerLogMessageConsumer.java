package org.scorpion.common.logfw.engine;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.scorpion.api.common.ICustomHandler;
import org.scorpion.api.common.ILogMessage;
import org.scorpion.api.common.IMessageConsumer;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;

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
