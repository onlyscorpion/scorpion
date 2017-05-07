package org.scorpion.kernel.media;

import java.util.Map.Entry;

import org.scorpion.api.common.AbsMedaitionPool;
import org.scorpion.api.common.AbsProtocol;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.util.Constant;
import org.scorpion.kernel.media.client.WSBaseMessageMediation;
import org.scorpion.kernel.route.ProtocolConf;
import org.scorpion.kernel.route.ProtocolConf.WSProtocolConf;

/**
 * 自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.tscp.common
 * <p>
 * File: AbsTscpFactory.java create time:2015-5-8下午07:57:37
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
public class TscpWSSenderMediationPool extends AbsMedaitionPool<WSBaseMessageMediation> {

	
	private static TscpWSSenderMediationPool wspool;
	
	@Override
	public void init() throws TscpBaseException {
		try {
			for (Entry<String, ProtocolConf.WSProtocolConf> protocol : ProtocolConf.getInstance().getWsProtocolConf().entrySet()) 
				if (!Constant.LAZY.equals(protocol.getValue().getInitType()))
					createQueue(protocol.getKey(), protocol.getValue());
			((MediationPoolFactory) MediationPoolFactory.getMessageMediation()).addFatorToPool(TscpWSSenderMediationPool.class.getName(),this);
		} catch (Throwable e) {
			throw new TscpBaseException(e);
		}
	}

	@Override
	public void rebuild() throws TscpBaseException {
		// TODO Auto-generated method stub
	}

	@Override
	public void increase() throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void reduce() throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rebuildByProtocolId(String key, String protocolType)throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	public WSBaseMessageMediation getMediation(String protocolId)throws InterruptedException, TscpBaseException {

		return super.getMediation(protocolId, ProtocolConf.getInstance().getWsProtocolConf().get(protocolId));
	}

	@Override
	public void increaseByProtocolInfo(String key, String protocolType)throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public WSBaseMessageMediation getMediation(AbsProtocol protocol)throws TscpBaseException {
		
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
			wspool = new TscpWSSenderMediationPool();
	
		return wspool;
	}

	@Override
	public void dynamicAddProtocol(AbsProtocol protocol)throws TscpBaseException, InterruptedException {
		createQueue(protocol.getProtocolId(), protocol);
	}

}
