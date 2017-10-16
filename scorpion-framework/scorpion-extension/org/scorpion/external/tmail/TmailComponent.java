package org.scorpion.external.tmail;

import java.net.URI;
import java.util.Map;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionComponent;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TmailComponent extends AbsScorpionComponent{

	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {
		initMailMessageQueue();
		TMailMessageConsumer.getInstance();
		TMailMessageConsumer.handlers.add(new TMailMessageHandler());
		TMailMessageProducer.getInstance();
	}

	
	
	/**
	 * @throws ScorpionBaseException
	 */
	private void initMailMessageQueue() throws ScorpionBaseException{
		
	    String BROKER_URL ="broker:vm://tmail?marshal=false&broker.persistent=false&broker.useJmx=false";
		
		try {
			final BrokerService broker = BrokerFactory.createBroker(new URI(BROKER_URL));
			broker.setBrokerName("tmail");
			broker.setUseJmx(false);
			broker.setPersistent(false);
			broker.start();
		} catch (Throwable e) {
			throw new ScorpionBaseException("scorpion-3459：start broker exception !",e);
		}
	
		
	}
	
	

}
