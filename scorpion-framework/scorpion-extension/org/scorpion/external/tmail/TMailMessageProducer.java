package org.scorpion.external.tmail;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.scorpion.api.common.IMessageProducer;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.common.logfw.engine.BrokerLogMessageProducer;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
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
	
	

	public TMailMessageProducer() throws TscpBaseException {
		init();
	}

	@Override
	public void init() throws TscpBaseException {
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
			throw new TscpBaseException(e);
		}
	}

	@Override
	public void produce(Object logMessage) throws TscpBaseException {
		try{
			MessageProducer consumer = session.createProducer(session.createQueue("tmail"));
			ObjectMessage message = session.createObjectMessage();
			message.setObject((TMailMessage)logMessage);
			consumer.send(message);
		}catch(JMSException e){
			throw new TscpBaseException(e);
		}
	}

	@Override
	public void destroy() throws TscpBaseException {
	
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
	 * @throws TscpBaseException
	 */
	public synchronized static IMessageProducer getInstance() throws TscpBaseException{
		if(producer == null)
			producer = new TMailMessageProducer();
		return producer;
	}
	
	
	

}
