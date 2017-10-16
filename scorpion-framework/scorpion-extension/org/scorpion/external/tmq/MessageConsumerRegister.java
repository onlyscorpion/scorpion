package org.scorpion.external.tmq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.scorpion.api.common.IMessageDrivenListener;
import org.scorpion.api.configuration.DrivenBean;
import org.scorpion.api.kernel.IMessageReceiveHandler;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.common.context.ApplicationOuterContext;

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
public class MessageConsumerRegister {
	
	private Map<String,IMessageDrivenListener> mapListner;
	
	private List<MQConfEntity> mqconfigs;
	
	
	/**
	 * @description Register consumer into listener ...
	 * 
	 * @param mqconfigs
	 * 
	 * @throws JMSException
	 */
	void registerConsumer(List<MQConfEntity> mqconfigs) throws JMSException{
    	 this.mqconfigs = mqconfigs;
    	 mappedEnvironmentCheck(mqconfigs);
    	 if(initConsumer())
    		 initListener();
	}
	
	
	/**
	 * @Description Validate environment information ...
	 * 
	 * @param mqconfigs
	 */
	private void mappedEnvironmentCheck(List<MQConfEntity> mqconfigs){
		
		if(ApplicationOuterContext.getOuterContextInstance().getDrivenBeans().size()<=0)
			return;
		
		List<DrivenBean> noExistBean = new ArrayList<DrivenBean>();
	
		removeInvaildConf(noExistBean);
		
		ApplicationOuterContext.getOuterContextInstance().getDrivenBeans().removeAll(noExistBean);
	}
	
	
	
	
	/**
	 * 
	 * @param noExistBean
	 */
	private void removeInvaildConf(List<DrivenBean> noExistBean){
		
		for(DrivenBean bean:ApplicationOuterContext.getOuterContextInstance().getDrivenBeans()){
			boolean isExist = false;
			for(MQConfEntity entity:mqconfigs){
				if(bean.getBrokerName() == null||bean.getTheme() == null)
					continue;
				if(bean.getBrokerName().equals(entity.getBrokerName())&&entity.isThemeExist(bean.getTheme()))
					isExist = true;
			}
			if(!isExist){
				noExistBean.add(bean);
				PlatformLogger.error("scorpion-9125:No exist Broker or Theme ["+bean.getBrokerName()+","+bean.getTheme()+"] mapped the message driven bean");
			}
		}
	}
	
	
	/**
	 * @description Initialize consumer ...
	 * 
	 * @Time 2015-08-26
	 * 
	 * @author zhengchenglei
	 * 
	 * @throws JMSException 
	 */
	boolean initConsumer() throws JMSException{
		
		if(ApplicationOuterContext.getOuterContextInstance().getDrivenBeans().size()<=0)
			return false;
		
		mapListner = new HashMap<String, IMessageDrivenListener>();
		
		for(DrivenBean drivenBean:ApplicationOuterContext.getOuterContextInstance().getDrivenBeans())
			registerConsumer(drivenBean);
	
		return true;
		
	}
	
	
	/**
	 * 
	 * @param drivenBean
	 */
	private void registerConsumer(DrivenBean drivenBean){
		try{
			if(mapListner.containsKey(drivenBean.getBrokerName()+","+drivenBean.getTheme())){
				mapListner.get(drivenBean.getBrokerName()+","+drivenBean.getTheme()).addMessageReceiveHandler((IMessageReceiveHandler)drivenBean.getHandlerClass().newInstance());
			}else{
				MessageDrivenListenner listener = new MessageDrivenListenner();
				listener.addMessageReceiveHandler((IMessageReceiveHandler)drivenBean.getHandlerClass().newInstance());
				mapListner.put(drivenBean.getBrokerName()+","+drivenBean.getTheme(), listener);
			}
		}catch(Exception e){
			PlatformLogger.error(e);
		}
	}
	
	
	/**
	 * @description Initialize listener ...
	 * 
	 * @throws JMSException
	 */
	public void initListener() throws JMSException{
		
		for(MQConfEntity entity:mqconfigs){
			for(String theme:entity.getQueues()){
				ActiveMQConnectionFactory cf = null; ActiveMQConnection conn = null;
				Session session = null;  MessageConsumer messageConsumer = null;
				try{
					eachStartListener(cf,conn,session,messageConsumer,entity,theme);
				}finally{
					if(messageConsumer != null) messageConsumer.close();
					if(session != null) session.close();
					if(conn != null) conn.close();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param cf
	 * @param conn
	 * @param session
	 * @param messageConsumer
	 * @param entity
	 * @param theme
	 * @throws JMSException
	 */
	private void eachStartListener(ActiveMQConnectionFactory  cf,ActiveMQConnection conn,Session session,
			MessageConsumer messageConsumer,MQConfEntity entity,String theme) throws JMSException{
		if(mapListner == null)
			return;
		cf = new ActiveMQConnectionFactory("tcp://"+entity.getUrl()+"?wireFormat.maxInactivityDuration=0");
		conn = (ActiveMQConnection) cf.createConnection();
		session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
		
		if(theme == null)
			return;
		boolean signal = false;
		for(Entry<String,IMessageDrivenListener> entry:mapListner.entrySet()){
			if(theme.equals(entry.getKey().split(",")[1]))
				signal = true;
		}
		if(!signal)
			return;
		conn.start();
		messageConsumer = session.createConsumer(session.createQueue(theme));
		Thread thread  = new Thread(new Runnable() {
			private MessageConsumer messageConsumer;
			private String key;
			@Override
			public void run() {
				while(true){
					try {
						Message message = messageConsumer.receive();
						if(!mapListner.containsKey(key)){
							continue;
						}
						mapListner.get(key).onMessage(message);
					} catch (Exception e) {
						PlatformLogger.error(e);
					}
				}
			}
			public Runnable setMessageConsumer(MessageConsumer messageConsumer,String key){
				this.messageConsumer = messageConsumer;
				this.key = key;
				return this;
			}
		}.setMessageConsumer(messageConsumer,entity.getBrokerName()+","+theme));
		thread.setDaemon(true);
		thread.start();
	}

}
