package com.scorpion.huakerongtong.kernel.media;

import com.scorpion.huakerongtong.api.common.AbsMedaitionPool;
import com.scorpion.huakerongtong.api.common.AbsProtocol;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.kernel.media.client.JMSBaseMessageMediation;
import com.scorpion.huakerongtong.kernel.route.ProtocolConf;

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
public class ScorpionJMSSenderMediationPool extends AbsMedaitionPool<JMSBaseMessageMediation> {

	@Override
	public void init() throws ScorpionBaseException {
		// TODO Auto-generated method stub

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
	public void rebuildByProtocolId(String key, String protocolType) throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	public JMSBaseMessageMediation getMediation(String protocolId) throws InterruptedException, ScorpionBaseException {

		return super.getMediation(protocolId, ProtocolConf.getInstance().getJmsProtocolConf().get(protocolId));
	}

	@Override
	public void increaseByProtocolInfo(String key, String protocolType) throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public JMSBaseMessageMediation getMediation(AbsProtocol protocol) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dynamicAddProtocol(AbsProtocol protocol) throws ScorpionBaseException, InterruptedException {
		createQueue(protocol.getProtocolId(), protocol);
	}

}
