package org.scorpion.kernel.media;

import java.util.Map.Entry;

import org.scorpion.api.common.AbsMedaitionPool;
import org.scorpion.api.common.AbsProtocol;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.util.Constant;
import org.scorpion.kernel.media.client.WSBaseMessageMediation;
import org.scorpion.kernel.route.ProtocolConf;
import org.scorpion.kernel.route.ProtocolConf.WSProtocolConf;

/**
 * 天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>
 * com.SCORPION.Scorpion.common
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
 * Copyright: Copyright (c) 2015 SCORPION.COM.CN
 * </p>
 * <p>
 * Company: SCORPION.COM.CN
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionWSSenderMediationPool extends AbsMedaitionPool<WSBaseMessageMediation> {

	
	private static ScorpionWSSenderMediationPool wspool;
	
	@Override
	public void init() throws ScorpionBaseException {
		try {
			for (Entry<String, ProtocolConf.WSProtocolConf> protocol : ProtocolConf.getInstance().getWsProtocolConf().entrySet()) 
				if (!Constant.LAZY.equals(protocol.getValue().getInitType()))
					createQueue(protocol.getKey(), protocol.getValue());
			((MediationPoolFactory) MediationPoolFactory.getMessageMediation()).addFatorToPool(ScorpionWSSenderMediationPool.class.getName(),this);
		} catch (Throwable e) {
			throw new ScorpionBaseException(e);
		}
	}

	@Override
	public void rebuild() throws ScorpionBaseException {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}

	public WSBaseMessageMediation getMediation(String protocolId)throws InterruptedException, ScorpionBaseException {

		return super.getMediation(protocolId, ProtocolConf.getInstance().getWsProtocolConf().get(protocolId));
	}

	@Override
	public void increaseByProtocolInfo(String key, String protocolType)throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public WSBaseMessageMediation getMediation(AbsProtocol protocol)throws ScorpionBaseException {
		
		WSProtocolConf wsprotocol = (WSProtocolConf) protocol;
		WSBaseMessageMediation wsMediation = new WSBaseMessageMediation();
		wsMediation.setProtocolId(wsprotocol.getProtocolId());
		wsMediation.setProvideURL(wsprotocol.getWsdlAddress());
		wsMediation.setMethod(wsprotocol.getMethod());
		wsMediation.initialize();
		return wsMediation;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static synchronized AbsMedaitionPool<WSBaseMessageMediation> getInstance() {
		
		if (wspool == null)
			wspool = new ScorpionWSSenderMediationPool();
	
		return wspool;
	}

	@Override
	public void dynamicAddProtocol(AbsProtocol protocol)throws ScorpionBaseException, InterruptedException {
		createQueue(protocol.getProtocolId(), protocol);
	}

}
