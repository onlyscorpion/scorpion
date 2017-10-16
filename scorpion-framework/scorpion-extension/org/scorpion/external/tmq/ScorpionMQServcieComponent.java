package org.scorpion.external.tmq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionComponent;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.ScorpionFileStreamHandler;
import org.scorpion.common.annotation.Component;

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

@Component(name="ScorpionMQServcieComponent",params={"brokerConfig=scorpion-mq-conf.xml"}, sequence = 46)
public class ScorpionMQServcieComponent extends AbsScorpionComponent{
	
	private String configFile ;
	
	private List<MQConfEntity> mqconfigs;

	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {
		try {
		
			this.configFile = arguments.get("brokerConfig");
			
			parseDocument(configFile);
			
			if(mqconfigs == null)
				return;
			
			initBrokerHandler();
			
			new MessageConsumerRegister().registerConsumer(mqconfigs);
			
		} catch (Exception e) {
			throw new ScorpionBaseException(e);
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void parseDocument(String configFile) throws DocumentException, IOException{
		
		File conf = new File(this.getClass().getResource("/"+configFile).getPath());
		
		if(!conf.exists())
			throw new FileNotFoundException("scorpion-4098:File ["+conf.getPath()+"] no found !");
		
		Document document = DocumentHelper.parseText(ScorpionFileStreamHandler.getFileContent(conf.getPath()));
	
		List<Element> elements = document.getRootElement().elements();
		
		mqconfigs = new ArrayList<MQConfEntity>();
		
		for(Element el:elements){
			MQConfEntity mqconf= new MQConfEntity();
			mqconf.setBrokerName(el.attributeValue(Constant.NAME));
			mqconf.setLoad(Boolean.parseBoolean(el.attributeValue(Constant.IS_LOCAL)));
			mqconf.setMarshal(el.attributeValue(Constant.MARSHAL));
			mqconf.setUseJmx(el.attributeValue(Constant.USER_JMX));
			mqconf.setPersistent(el.attributeValue(Constant.PERSISTENT));
			mqconf.setUrl(el.attributeValue(Constant.URL));
			mqconf.setEnable(Boolean.parseBoolean(el.attributeValue(Constant.ENABLE)));
			mqconf.setQueues(new ArrayList<String>());
			List<Element> childrens = el.elements();
			for(Element chs:childrens){
				mqconf.getQueues().add(chs.attributeValue(Constant.NAME));
			}
			mqconfigs.add(mqconf);
		}
		
	}
	
	
	
	
	/**
	 * 
	 */
	private void initBrokerHandler(){
		
		for(MQConfEntity entity:mqconfigs){
			try {
				if(!entity.isEnable())
					continue;
				if(!entity.isLoad()){
					String BROKER_URL ="broker:tcp://"+entity.getUrl()+"?marshal="+entity.getMarshal()+"&broker.persistent="+entity.getPersistent()+"&broker.useJmx="+(entity.getUseJmx()==null?false:entity.getUseJmx());
					final BrokerService broker = BrokerFactory.createBroker(new URI(BROKER_URL));
					broker.setBrokerName(entity.getBrokerName());
					broker.setUseJmx(false);
					broker.setPersistent(false);
					broker.start();
				}else{
					String BROKER_URL ="broker:mv://"+entity.getBrokerName()+"?marshal="+entity.getMarshal()+"&broker.persistent="+entity.getPersistent()+"&broker.useJmx="+entity.getUseJmx();
					final BrokerService broker = BrokerFactory.createBroker(new URI(BROKER_URL));
					broker.setBrokerName(entity.getBrokerName());
					broker.setUseJmx(false);
					broker.setPersistent(false);
					broker.start();	
				}
			} catch (Exception e) {
				PlatformLogger.error("Starting internal MQ failure ! ",e);
			}
		}
	}
}
