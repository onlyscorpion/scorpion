package com.scorpion.huakerongtong.external.tmail;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.scorpion.huakerongtong.api.common.IMessageProducer;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.common.logfw.engine.BrokerLogMessageProducer;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TMailMessageProducer implements IMessageProducer{
	
	private static IMessageProducer producer;
	ActiveMQConnectionFactory cf = null;
	ActiveMQConnection conn = null;
	Session session = null;
	static boolean inited = false;
	
	

	public TMailMessageProducer() throws ScorpionBaseException {
		init();
	}

	@Override
	public void init() throws ScorpionBaseException {
		try{
			if(inited)
				return;
			synchronized(BrokerLogMessageProducer.class){
				cf = new ActiveMQConnectionFactory("vm://tmail?marshal=false&broker.persistent=false&broker.useJmx=false");
				conn = (ActiveMQConnection) cf.createConnection();
				conn.start();
				session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
			}
		}catch(JMSException e){
			throw new ScorpionBaseException(e);
		}
	}

	@Override
	public void produce(Object logMessage) throws ScorpionBaseException {
		try{
			MessageProducer consumer = session.createProducer(session.createQueue("tmail"));
			ObjectMessage message = session.createObjectMessage();
			message.setObject((TMailMessage)logMessage);
			consumer.send(message);
		}catch(JMSException e){
			throw new ScorpionBaseException(e);
		}
	}

	@Override
	public void destroy() throws ScorpionBaseException {
	
		try {
			if(session != null)
				session.close();
		} catch (JMSException e) {
			PlatformLogger.error(e);
		}
		
		try {
			if(conn != null)
				conn.close();
		} catch (JMSException e) {
				PlatformLogger.error(e);
		}
	}
	
	
	/**
	 * @return
	 * @throws ScorpionBaseException
	 */
	public synchronized static IMessageProducer getInstance() throws ScorpionBaseException{
		if(producer == null)
			producer = new TMailMessageProducer();
		return producer;
	}
	
	
	

}
