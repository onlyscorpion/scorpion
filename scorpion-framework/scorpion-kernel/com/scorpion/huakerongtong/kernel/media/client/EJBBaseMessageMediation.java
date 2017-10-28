package com.scorpion.huakerongtong.kernel.media.client;

import javax.naming.NamingException;

import com.scorpion.huakerongtong.api.common.AbsMediationFactor;
import com.scorpion.huakerongtong.api.common.IScorpionProtocal.ProtocolType;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IMessageReceiver;
import com.scorpion.huakerongtong.api.kernel.IScorpionReqMedia;
import com.scorpion.huakerongtong.api.kernel.IScorpionRespMedia;
import com.scorpion.huakerongtong.common.annotation.Sender;
import com.scorpion.huakerongtong.common.enums.SenderType;
import com.scorpion.huakerongtong.kernel.media.MediationPoolFactory;
import com.scorpion.huakerongtong.kernel.media.ScorpionEJBSenderMediationPool;
import com.scorpion.huakerongtong.kernel.route.EJBAdapter;

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
@Sender(sendertype = SenderType.STANDARD_SENDER, protocolType = ProtocolType.EJB)
public class EJBBaseMessageMediation extends AbsMediationFactor {

	private static final long serialVersionUID = 2689610828067476887L;

	private int containerType;

	private String username;

	private String password;

	private String contextFactory;

	private String ejbName;

	/**
	 * 
	 */
	public EJBBaseMessageMediation() {

	}

	@Override
	public IScorpionRespMedia messageSenderHandler(IScorpionReqMedia req)throws ScorpionBaseException {

		try {
			IMessageReceiver receiver = (IMessageReceiver) EJBAdapter.getProxyObject(provideURL, username, password);
			return receiver.receiver(req);
		} catch (NamingException e) {
			throw new ScorpionBaseException("scorpion-9067:CAN'T LOOK UP REMOTE OBJECT !", e);
		}

	}

	@Override
	public void close() throws ScorpionBaseException {

		try {
			MediationPoolFactory.getMessageMediation().produceInstance(ScorpionEJBSenderMediationPool.class).getFreeQueue().get(this.getProtocolId()).put(this);
		} catch (InterruptedException e) {
			throw new ScorpionBaseException("scorpion-9056:CLOSE RESOURCE EXCEPTION !",e);
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getContainerType() {
		return containerType;
	}

	public void setContainerType(int containerType) {
		this.containerType = containerType;
	}

	public String getContextFactory() {
		return contextFactory;
	}

	public void setContextFactory(String contextFactory) {
		this.contextFactory = contextFactory;
	}

	public String getEjbName() {
		return ejbName;
	}

	public void setEjbName(String ejbName) {
		this.ejbName = ejbName;
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
