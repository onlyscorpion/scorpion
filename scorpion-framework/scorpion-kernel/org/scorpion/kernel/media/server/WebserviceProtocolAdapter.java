package org.scorpion.kernel.media.server;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IScorpionBaseInternalReceiver;
import org.scorpion.api.kernel.IScorpionBaseMessageReceiver;
import org.scorpion.api.kernel.IScorpionReqMedia;
import org.scorpion.api.kernel.IScorpionRespMedia;

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
public class WebserviceProtocolAdapter implements IScorpionBaseMessageReceiver,IScorpionBaseInternalReceiver {

	private static final long serialVersionUID = 1142387424804815704L;

	@Override
	public IScorpionRespMedia internalInvoke(IScorpionReqMedia req)throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String recevieXml(String argument) throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IScorpionRespMedia receiveBean(IScorpionReqMedia argument)throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
