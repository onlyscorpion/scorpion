package com.scorpion.huakerongtong.kernel.media.client;

import java.rmi.Naming;
import java.rmi.RemoteException;

import com.scorpion.huakerongtong.api.common.AbsMediationFactor;
import com.scorpion.huakerongtong.api.common.IScorpionProtocal.ProtocolType;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IScorpionBaseInternalReceiver;
import com.scorpion.huakerongtong.api.kernel.IScorpionReqMedia;
import com.scorpion.huakerongtong.api.kernel.IScorpionRespMedia;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.common.annotation.Sender;
import com.scorpion.huakerongtong.common.enums.SenderType;
import com.scorpion.huakerongtong.common.util.ScorpionSystemSessionUtils;
import com.scorpion.huakerongtong.kernel.media.MediationPoolFactory;
import com.scorpion.huakerongtong.kernel.media.ScorpionRMSenderMediationPool;

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
@Sender(sendertype = SenderType.STANDARD_SENDER, protocolType = ProtocolType.TMI)
public class TMIBaseMessagMediation extends AbsMediationFactor {

	private static final long serialVersionUID = -8495689765153933048L;

	private IScorpionBaseInternalReceiver sender;

	@Override
	public IScorpionRespMedia messageSenderHandler(IScorpionReqMedia req)throws ScorpionBaseException {

		this.session = ScorpionSystemSessionUtils.getSession();
		
		try {
			if(sender == null)
				throw new ScorpionBaseException("scorpion-4398:消息发送器未初始化");
			IScorpionRespMedia resp = sender.internalInvoke(req);
			ScorpionSystemSessionUtils.getSession().tempMapDataConvertSession(resp.getTempData());
			return resp;
		} catch (RemoteException e) {
			return sendResMediaAgain(req,e);
		}catch(Exception e){
			if(e.getMessage() != null&&e.getMessage().indexOf("java.lang.ClassNotFoundException") >= 0)
				PlatformLogger.error("scorpion-9458：Remote object can't find . ");
			throw new ScorpionBaseException(e);
		} finally {
			this.close();
		}
	}

	@Override
	public void close() throws ScorpionBaseException {

		try {
			MediationPoolFactory.getMessageMediation().produceInstance(ScorpionRMSenderMediationPool.class).getBusyQueue().get(this.getProtocolId()).remove(this);
			MediationPoolFactory.getMessageMediation().produceInstance(ScorpionRMSenderMediationPool.class).getFreeQueue().get(this.getProtocolId()).put(this);
		} catch (InterruptedException e) {
			throw new ScorpionBaseException("scorpion-9056:CLOSE RESOURCE EXCEPTION !",e);
		}

	}
	
	
	/**
	 * @description Send message try again...
	 * @param req
	 * @return
	 * @throws ScorpionBaseException
	 * @throws InterruptedException
	 */
	protected IScorpionRespMedia tryAgain(IScorpionReqMedia req) throws ScorpionBaseException, InterruptedException{
		
		MediationPoolFactory.getMessageMediation().produceInstance(ScorpionRMSenderMediationPool.class).clear(this.getProtocolId());
		
		return MediationPoolFactory.getMessageMediation().produceInstance(ScorpionRMSenderMediationPool.class).getMediation(this.getProtocolId()).messageSenderHandler(req);
		
	}

	
	

	@Override
	public void initialize() throws ScorpionBaseException {

		try {
			this.sender = (IScorpionBaseInternalReceiver) Naming.lookup("//"+ this.provideURL + "/ScorpionDefaultProtocolAdapter");
		} catch (Exception e) {
			PlatformLogger.error("scorpion-6945:Connection refused["+ this.provideURL + "]", e);
		}
	}

}
