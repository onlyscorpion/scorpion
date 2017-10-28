package com.scorpion.huakerongtong.kernel.media.client;

import com.scorpion.huakerongtong.api.common.AbsMediationFactor;
import com.scorpion.huakerongtong.api.common.IScorpionProtocal.ProtocolType;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IScorpionReqMedia;
import com.scorpion.huakerongtong.api.kernel.IScorpionRespMedia;
import com.scorpion.huakerongtong.common.annotation.Sender;
import com.scorpion.huakerongtong.common.enums.SenderType;

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
@Sender(sendertype = SenderType.STANDARD_SENDER, protocolType = ProtocolType.JMS)
public class JMSBaseMessageMediation extends AbsMediationFactor {

	private static final long serialVersionUID = 8689859605363543325L;

	@Override
	public IScorpionRespMedia messageSenderHandler(IScorpionReqMedia req)throws ScorpionBaseException {
		return null;
	}

	@Override
	public void close() throws ScorpionBaseException {

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected IScorpionRespMedia tryAgain(IScorpionReqMedia req)throws ScorpionBaseException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

}
