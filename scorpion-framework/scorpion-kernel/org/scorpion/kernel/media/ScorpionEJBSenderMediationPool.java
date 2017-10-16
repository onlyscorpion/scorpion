package org.scorpion.kernel.media;

import java.util.Date;
import java.util.Map.Entry;

import org.scorpion.api.common.AbsMedaitionPool;
import org.scorpion.api.common.AbsProtocol;
import org.scorpion.api.common.IScorpionProtocal.ProtocolType;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.kernel.media.client.EJBBaseMessageMediation;
import org.scorpion.kernel.route.ProtocolConf;
import org.scorpion.kernel.route.ProtocolConf.EJBProtocolConf;

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
public class ScorpionEJBSenderMediationPool extends AbsMedaitionPool<EJBBaseMessageMediation> {

	@Override
	public void init() throws ScorpionBaseException {

		try {
			for (Entry<String, ProtocolConf.EJBProtocolConf> protocol : ProtocolConf.getInstance().getEjbProtocolConf().entrySet()) {
				createQueue(protocol.getKey(), protocol.getValue());
			}
			((MediationPoolFactory) MediationPoolFactory.getMessageMediation()).addFatorToPool(EJBBaseMessageMediation.class.getName(),this);
		} catch (InterruptedException e) {
			throw new ScorpionBaseException("scorpion-9045:Unknown interrupt", e);
		}
	}

	@Override
	public void rebuild() throws ScorpionBaseException {
		init();
	}

	@Override
	public void increase() throws ScorpionBaseException {

		// TODO Auto-generated method stub

	}

	@Override
	public void reduce() throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rebuildByProtocolId(String key, String protocolType)throws ScorpionBaseException {

		if (ProtocolType.EJB.name().equals(protocolType)) {

			if (ProtocolConf.getInstance().getEjbProtocolConf().containsKey(key))
				throw new ScorpionBaseException("scorpion-6894:protocol not exist , Protocol ID is [" + key + "]");

			try {
				createQueue(key, ProtocolConf.getInstance().getEjbProtocolConf().get(key));
			} catch (InterruptedException e) {
				throw new ScorpionBaseException("scorpion-9786:rebulid EJB protocol failure ,Protocol ID is["+ key + "]");
			}

		} else if (ProtocolType.JMS.name().equals(protocolType)) {

		} else if (ProtocolType.WEBSERVICE.name().equals(protocolType)) {

		} else if (ProtocolType.MQ.name().equals(protocolType)) {

		} else if (ProtocolType.TMI.name().equals(protocolType)) {

		} else {

		}

	}

	/**
	 * @param protocol
	 * @return
	 */
	/*
	 * public EJBBaseMessageMediation getMediation(ProtocolConf.EJBProtocolConf
	 * protocol){
	 * 
	 * EJBBaseMessageMediation ejbMediation = new EJBBaseMessageMediation();
	 * ejbMediation.setInitDate(new Date());
	 * ejbMediation.setProvideURL(protocol.getProvideURL());
	 * ejbMediation.setUsername(protocol.getUsername());
	 * ejbMediation.setPassword(protocol.getPassword());
	 * ejbMediation.setConnTimeOut(protocol.getConnTimeOut());
	 * ejbMediation.setCallTimeOut(protocol.getCallTimeOut());
	 * ejbMediation.setContextFactory(protocol.getContextFactory());
	 * ejbMediation.setEjbName(protocol.getEjbName()); return ejbMediation; }
	 */

	public EJBBaseMessageMediation getMediation(String protocolId)throws InterruptedException, ScorpionBaseException {

		return super.getMediation(protocolId, ProtocolConf.getInstance().getEjbProtocolConf().get(protocolId));
	}

	@Override
	public void increaseByProtocolInfo(String key, String protocolType)throws ScorpionBaseException {

		if (ProtocolType.EJB.name().equals(protocolType)) {
			
			if (!ProtocolConf.getInstance().getEjbProtocolConf().containsKey(key))
				return;

			EJBProtocolConf ejbconf = ProtocolConf.getInstance().getEjbProtocolConf().get(key);
			if (ejbconf.getCurrentConnNum().get() >= ejbconf.getMaxConnNum()) {
			
				if ((ejbconf.getMaxConnNum() - ejbconf.getCurrentConnNum().get()) > ejbconf.getNextConnNum()) {
					
					for (int i = 0; i < ejbconf.getNextConnNum(); i++) {
						try {
							this.getFreeQueue().get(ejbconf.getProtocolId()).put(getMediation(ejbconf));
						} catch (InterruptedException e) {
							throw new ScorpionBaseException("scorpion-9876:increase resource exception ！",e);
						}
					}
				}
			}
		} else if (ProtocolType.JMS.name().equals(protocolType)) {

		} else if (ProtocolType.TMI.name().equals(protocolType)) {

		} else if (ProtocolType.WEBSERVICE.name().equals(protocolType)) {

		}

	}

	@Override
	public EJBBaseMessageMediation getMediation(AbsProtocol protocol) {

		EJBProtocolConf ejbProtocol = (EJBProtocolConf) protocol;
		EJBBaseMessageMediation ejbMediation = new EJBBaseMessageMediation();
		ejbMediation.setInitDate(new Date());
		ejbMediation.setProvideURL(ejbProtocol.getProvideURL());
		ejbMediation.setUsername(ejbProtocol.getUsername());
		ejbMediation.setPassword(ejbProtocol.getPassword());
		ejbMediation.setConnTimeOut(ejbProtocol.getConnTimeOut());
		ejbMediation.setCallTimeOut(ejbProtocol.getCallTimeOut());
		ejbMediation.setContextFactory(ejbProtocol.getContextFactory());
		ejbMediation.setEjbName(ejbProtocol.getEjbName());
		return ejbMediation;
	}

	@Override
	public void dynamicAddProtocol(AbsProtocol protocol)throws ScorpionBaseException, InterruptedException {
		createQueue(protocol.getProtocolId(), protocol);
	}

}
