package org.scorpion.common.logfw.messagequeue;

import java.net.URI;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.common.context.SystemContext;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class MQBroker {
	
	
	/**
	 * 
	 * @throws TscpBaseException
	 */
	public static void startBroker()throws TscpBaseException{
		
	    String BROKER_NAME = SystemContext.getApplicationContext().getSystemCoreConfig().getLogframeworkInfo().getBrokerName();
	
	    String BROKER_URL ="broker:vm://"+BROKER_NAME+"?marshal=false&broker.persistent=false&broker.useJmx=false";
		
		try {
			final BrokerService broker = BrokerFactory.createBroker(new URI(BROKER_URL));
			broker.setBrokerName(BROKER_NAME);
			broker.setUseJmx(false);
			broker.setPersistent(false);
			broker.start();
		} catch (Throwable e) {
			throw new TscpBaseException("TSCP-3458：start broker exception !",e);
		}
	}
	

}
