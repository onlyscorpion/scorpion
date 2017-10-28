package com.scorpion.huakerongtong.kernel.media.server;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IScorpionBaseInternalReceiver;
import com.scorpion.huakerongtong.api.kernel.IScorpionBaseMessageReceiver;
import com.scorpion.huakerongtong.api.kernel.IScorpionReqMedia;
import com.scorpion.huakerongtong.api.kernel.IScorpionRespMedia;

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
@Stateless(mappedName = "EJBProtocolAdapter")
@TransactionManagement(TransactionManagementType.BEAN)
@Remote(value = { IScorpionBaseMessageReceiver.class,IScorpionBaseInternalReceiver.class })
public class EJBProtocolAdapter implements IScorpionBaseMessageReceiver,IScorpionBaseInternalReceiver {

	private static final long serialVersionUID = 1651513324412399396L;

	@Override
	public IScorpionRespMedia internalInvoke(IScorpionReqMedia req)throws ScorpionBaseException {
		return null;
	}

	@Override
	public String recevieXml(String argument) throws ScorpionBaseException {
		return null;
	}

	@Override
	public IScorpionRespMedia receiveBean(IScorpionReqMedia argument)throws ScorpionBaseException {
		return null;
	}

}
