package org.scorpion.external.tmail;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.scorpion.api.common.ICustomHandler;
import org.scorpion.api.common.IMessageConsumer;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.log.PlatformLogger;

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
public class TMailMessageConsumer implements IMessageConsumer{
	
	private static IMessageConsumer consumer;
	private ActiveMQConnectionFactory cf = null;
	private ActiveMQConnection conn = null;
	private Session session = null;
	private MessageConsumer messageConsumer = null;
	
	

	public TMailMessageConsumer() throws TscpBaseException {
		super();
		init();
		consumer();
	}

	@Override
	public void init() throws TscpBaseException {

		  try{
			  cf = new ActiveMQConnectionFactory("vm://tmail?marshal=false&broker.persistent=false&broker.useJmx=false");
			  conn = (ActiveMQConnection) cf.createConnection();
			  conn.start();
			  session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
			  messageConsumer = session.createConsumer(session.createQueue("tmail"));
		  }catch(JMSException e){
			  throw new TscpBaseException(e);
		  }
	
	}

	@Override
	public void consumer() throws TscpBaseException {
			
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
	 * @throws TscpBaseException 
	 */
	public synchronized static IMessageConsumer getInstance() throws TscpBaseException{
		if(consumer == null)
			consumer = new TMailMessageConsumer();
		return consumer;
	}

}
