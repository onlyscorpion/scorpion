package org.scorpion.kernel.media.server;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpBaseInternalReceiver;
import org.scorpion.api.kernel.ITscpBaseMessageReceiver;
import org.scorpion.api.kernel.ITscpReqMedia;
import org.scorpion.api.kernel.ITscpRespMedia;

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
@Stateless(mappedName = "EJBProtocolAdapter")
@TransactionManagement(TransactionManagementType.BEAN)
@Remote(value = { ITscpBaseMessageReceiver.class,ITscpBaseInternalReceiver.class })
public class EJBProtocolAdapter implements ITscpBaseMessageReceiver,ITscpBaseInternalReceiver {

	private static final long serialVersionUID = 1651513324412399396L;

	@Override
	public ITscpRespMedia internalInvoke(ITscpReqMedia req)throws TscpBaseException {
		return null;
	}

	@Override
	public String recevieXml(String argument) throws TscpBaseException {
		return null;
	}

	@Override
	public ITscpRespMedia receiveBean(ITscpReqMedia argument)throws TscpBaseException {
		return null;
	}

}
