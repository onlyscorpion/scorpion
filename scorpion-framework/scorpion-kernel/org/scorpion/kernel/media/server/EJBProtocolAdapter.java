package org.scorpion.kernel.media.server;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IScorpionBaseInternalReceiver;
import org.scorpion.api.kernel.IScorpionBaseMessageReceiver;
import org.scorpion.api.kernel.IScorpionReqMedia;
import org.scorpion.api.kernel.IScorpionRespMedia;

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
