package org.scorpion.kernel.media;

import org.scorpion.api.common.AbsMedaitionPool;
import org.scorpion.api.common.AbsMediationFactor;
import org.scorpion.api.common.AbsProtocol;
import org.scorpion.api.common.IScorpionProtocal.ProtocolType;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.kernel.route.ProtocolConf;
import org.scorpion.kernel.route.ProtocolConf.EJBProtocolConf;
import org.scorpion.kernel.route.ProtocolConf.JMSProtocolConf;
import org.scorpion.kernel.route.ProtocolConf.TMIConf;
import org.scorpion.kernel.route.ProtocolConf.WSProtocolConf;

/**
 * 天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.Scorpion.common
 * <p>
 * File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 taiji.com.cn
 * </p>
 * <p>
 * Company: taiji.com.cn
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionDynamicMedia {

	/**
	 * @description generate dynamic mediation
	 * 
	 * @param protocolType
	 * 
	 * @param ip
	 * 
	 * @param port
	 * 
	 * @return
	 * 
	 * @throws InterruptedException
	 * 
	 * @throws ScorpionBaseException
	 */
	private static AbsMediationFactor constructDynamicMediation(Class<?> clazz,AbsProtocol protocolConf, String protocolType, String ip, int port)throws InstantiationException, IllegalAccessException,ScorpionBaseException, InterruptedException {

		AbsMedaitionPool<?> metiationPool = ((MediationPoolFactory) MediationPoolFactory.getMessageMediation()).addFatorToPool(clazz.getName(),(AbsMedaitionPool<?>) clazz.newInstance());

		metiationPool.dynamicAddProtocol(protocolConf);

		return metiationPool.getMediation(protocolConf.getProtocolId());
	}

	
	/**
	 * @description get dynamic sender mediation ....
	 * 
	 * @param protocolType
	 * 
	 * @param ip
	 * 
	 * @param port
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws InterruptedException
	 * 
	 * @throws InstantiationException
	 * 
	 * @throws IllegalAccessException
	 * 
	 */
	public static AbsMediationFactor getDynamicMediation(String protocolType,String ip, int port) throws ScorpionBaseException,InterruptedException, InstantiationException,IllegalAccessException {

		if (ProtocolType.EJB.equals(protocolType)) {
			synchronized (ScorpionDynamicMedia.class) {
				if (!MediationPoolFactory.getMessageMediation().produceInstance(ScorpionEJBSenderMediationPool.class).isInit(ip +":"+ port))
					return constructDynamicMediation(ScorpionEJBSenderMediationPool.class,generateEJBConf(ip, port), protocolType, ip, port);
			}	
			return MediationPoolFactory.getMessageMediation().produceInstance(ScorpionEJBSenderMediationPool.class).getMediation(ip + port);
		} else if (ProtocolType.TMI.name().equals(protocolType)|| protocolType == null) {
			synchronized (ScorpionDynamicMedia.class) {
				if (!MediationPoolFactory.getMessageMediation().produceInstance(ScorpionRMSenderMediationPool.class).isInit(ip +":"+ port))
					return constructDynamicMediation(ScorpionRMSenderMediationPool.class,generateTMIConf(ip, port), protocolType, ip, port);
			}
			return MediationPoolFactory.getMessageMediation().produceInstance(ScorpionRMSenderMediationPool.class).getMediation(ip +":"+ port);
		} else if (ProtocolType.JMS.name().equals(protocolType)) {
			synchronized (ScorpionDynamicMedia.class) {
				if (!MediationPoolFactory.getMessageMediation().produceInstance(ScorpionJMSSenderMediationPool.class).isInit(ip +":"+ port))
					return constructDynamicMediation(ScorpionJMSSenderMediationPool.class,generateJMSConf(ip, port), protocolType, ip, port);
			}
			return MediationPoolFactory.getMessageMediation().produceInstance(ScorpionJMSSenderMediationPool.class).getMediation(ip +":"+ port);
		} else if (ProtocolType.WEBSERVICE.name().equals(protocolType)) {
			synchronized (ScorpionDynamicMedia.class) {
				if (!MediationPoolFactory.getMessageMediation().produceInstance(ScorpionWSSenderMediationPool.class).isInit(ip +":"+ port))
					return constructDynamicMediation(ScorpionWSSenderMediationPool.class,generateWSConf(ip, port), protocolType, ip, port);
			}
			return MediationPoolFactory.getMessageMediation().produceInstance(ScorpionWSSenderMediationPool.class).getMediation(ip +":"+ port);
		} else {
			throw new ScorpionBaseException("scorpion-7095:Unknown define protocol type [" + protocolType+ "] !");
		}
	}

	
	/**
	 * 
	 * @param ip
	 * 
	 * @param port
	 * 
	 * @return
	 */
	private static EJBProtocolConf generateEJBConf(String ip, int port) {
		EJBProtocolConf conf = ProtocolConf.getInstance().new EJBProtocolConf();
		conf.setProtocolId(ip + ":" + port);
		conf.setProvideURL(ip + ":" + port);
		return conf;
	}

	
	/**
	 * 
	 * @param ip
	 * @param port
	 * @return
	 */
	private static TMIConf generateTMIConf(String ip, int port) {
		TMIConf conf = ProtocolConf.getInstance().new TMIConf();
		conf.setProtocolId(ip + ":" + port);
		conf.setProvideURL(ip + ":" + port);
		ProtocolConf.getInstance().getTmiConf().put(conf.getProtocolId(), conf);
		return conf;
	}
	

	/**
	 * @param ip
	 * @param port
	 * @return
	 */
	private static JMSProtocolConf generateJMSConf(String ip, int port) {
		JMSProtocolConf conf = ProtocolConf.getInstance().new JMSProtocolConf();
		conf.setProvideURL(ip + ":" + port);
		return conf;
	}
	

	/**
	 * @param ip
	 * @param port
	 * @return
	 */
	private static WSProtocolConf generateWSConf(String ip, int port) {
		WSProtocolConf conf = ProtocolConf.getInstance().new WSProtocolConf();
		conf.setWsdlAddress(ip + ":" + port);
		return conf;
	}

}
