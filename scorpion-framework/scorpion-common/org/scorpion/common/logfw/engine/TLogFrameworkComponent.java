package org.scorpion.common.logfw.engine;

import java.util.Map;

import org.scorpion.api.common.ICustomHandler;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionComponent;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.common.annotation.Component;
import org.scorpion.common.context.SystemContext;
import org.scorpion.common.logfw.messagequeue.MQBroker;

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
@Component(name="TLogFrameworkComponent", sequence = 4)
public class TLogFrameworkComponent extends AbsScorpionComponent{

	
	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {
	
		if(SystemContext.getApplicationContext().getSystemCoreConfig().getLogframeworkInfo() == null||!SystemContext.getApplicationContext().getSystemCoreConfig().getLogframeworkInfo().isEnable())
			return;
		
		MQBroker.startBroker();
		if(!SystemContext.getApplicationContext().getSystemCoreConfig().getLogframeworkInfo().isLocalQueue()){
			ScorpionLogEngineUtil.producer = new BrokerLogMessageProducer();
			
			for(int i=0;i<SystemContext.getApplicationContext().getSystemCoreConfig().getLogframeworkInfo().getLogs().size();i++)
				try {
					BrokerLogMessageConsumer.handlers.add((ICustomHandler) Class.forName(SystemContext.getApplicationContext().getSystemCoreConfig().getLogframeworkInfo().getLogs().get(i).get(Constant.CLASS)).newInstance());
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								BrokerLogMessageConsumer.getInstance().init();
								BrokerLogMessageConsumer.getInstance().consumer();
							} catch (ScorpionBaseException e) {
								PlatformLogger.error(e);
							}
						}
					}).start();
				} catch (Exception e) {
					throw new ScorpionBaseException(e);
				}
		
		}else{
			ScorpionLogEngineUtil.producer = new QueueLogMessageProducer();
		}
		
	}

}
