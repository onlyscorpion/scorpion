package org.scorpion.kernel.media;

import org.scorpion.api.common.AbsMedaitionPool;
import org.scorpion.api.common.AbsProtocol;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.kernel.media.client.JMSBaseMessageMediation;
import org.scorpion.kernel.route.ProtocolConf;

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
public class TscpJMSSenderMediationPool extends AbsMedaitionPool<JMSBaseMessageMediation> {

	@Override
	public void init() throws TscpBaseException {
		// TODO Auto-generated method stub

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
	public void rebuildByProtocolId(String key, String protocolType) throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	public JMSBaseMessageMediation getMediation(String protocolId) throws InterruptedException, TscpBaseException {

		return super.getMediation(protocolId, ProtocolConf.getInstance().getJmsProtocolConf().get(protocolId));
	}

	@Override
	public void increaseByProtocolInfo(String key, String protocolType) throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public JMSBaseMessageMediation getMediation(AbsProtocol protocol) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dynamicAddProtocol(AbsProtocol protocol) throws TscpBaseException, InterruptedException {
		createQueue(protocol.getProtocolId(), protocol);
	}

}
