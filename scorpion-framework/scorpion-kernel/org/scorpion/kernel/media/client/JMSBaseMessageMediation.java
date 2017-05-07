package org.scorpion.kernel.media.client;

import org.scorpion.api.common.AbsMediationFactor;
import org.scorpion.api.common.ITscpProtocal.ProtocolType;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpReqMedia;
import org.scorpion.api.kernel.ITscpRespMedia;
import org.scorpion.common.annotation.Sender;
import org.scorpion.common.enums.SenderType;

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
@Sender(sendertype = SenderType.STANDARD_SENDER, protocolType = ProtocolType.JMS)
public class JMSBaseMessageMediation extends AbsMediationFactor {

	private static final long serialVersionUID = 8689859605363543325L;

	@Override
	public ITscpRespMedia messageSenderHandler(ITscpReqMedia req)throws TscpBaseException {
		return null;
	}

	@Override
	public void close() throws TscpBaseException {

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected ITscpRespMedia tryAgain(ITscpReqMedia req)throws TscpBaseException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

}
