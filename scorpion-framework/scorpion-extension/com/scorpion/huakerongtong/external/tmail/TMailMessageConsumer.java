package com.scorpion.huakerongtong.external.tmail;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.scorpion.huakerongtong.api.common.ICustomHandler;
import com.scorpion.huakerongtong.api.common.IMessageConsumer;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
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
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TMailMessageConsumer implements IMessageConsumer{
	
	private static IMessageConsumer consumer;
	private ActiveMQConnectionFactory cf = null;
	private ActiveMQConnection conn = null;
	private Session session = null;
	private MessageConsumer messageConsumer = null;
	
	

	public TMailMessageConsumer() throws ScorpionBaseException {
		super();
		init();
		consumer();
	}

	@Override
	public void init() throws ScorpionBaseException {

		  try{
			  cf = new ActiveMQConnectionFactory("vm://tmail?marshal=false&broker.persistent=false&broker.useJmx=false");
			  conn = (ActiveMQConnection) cf.createConnection();
			  conn.start();
			  session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
			  messageConsumer = session.createConsumer(session.createQueue("tmail"));
		  }catch(JMSException e){
			  throw new ScorpionBaseException(e);
		  }
	
	}

	@Override
	public void consumer() throws ScorpionBaseException {
			
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try{
						ObjectMessage message = (ObjectMessage) messageConsumer.receive(3000);
						if(message == null)
							continue;
						TMailMessage mail = (TMailMessage) message.getObject();
						for(ICustomHandler handler:handlers)
							handler.handler(mail);
					}catch(Throwable e){
						PlatformLogger.error(e);
					}
				}
			}
		
		}).start();
		
	}
	
	
	/**
	 * @return
	 * @throws ScorpionBaseException 
	 */
	public synchronized static IMessageConsumer getInstance() throws ScorpionBaseException{
		if(consumer == null)
			consumer = new TMailMessageConsumer();
		return consumer;
	}

}
