package org.scorpion.kernel.media.client;

import java.rmi.Naming;
import java.rmi.RemoteException;

import org.scorpion.api.common.AbsMediationFactor;
import org.scorpion.api.common.ITscpProtocal.ProtocolType;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpBaseInternalReceiver;
import org.scorpion.api.kernel.ITscpReqMedia;
import org.scorpion.api.kernel.ITscpRespMedia;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.common.annotation.Sender;
import org.scorpion.common.enums.SenderType;
import org.scorpion.common.util.TscpSystemSessionUtils;
import org.scorpion.kernel.media.MediationPoolFactory;
import org.scorpion.kernel.media.TscpRMSenderMediationPool;

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
@Sender(sendertype = SenderType.STANDARD_SENDER, protocolType = ProtocolType.TMI)
public class TMIBaseMessagMediation extends AbsMediationFactor {

	private static final long serialVersionUID = -8495689765153933048L;

	private ITscpBaseInternalReceiver sender;

	@Override
	public ITscpRespMedia messageSenderHandler(ITscpReqMedia req)throws TscpBaseException {

		this.session = TscpSystemSessionUtils.getSession();
		
		try {
			if(sender == null)
				throw new TscpBaseException("TSCP-4398:消息发送器未初始化");
			ITscpRespMedia resp = sender.internalInvoke(req);
			TscpSystemSessionUtils.getSession().tempMapDataConvertSession(resp.getTempData());
			return resp;
		} catch (RemoteException e) {
			return sendResMediaAgain(req,e);
		}catch(Exception e){
			if(e.getMessage() != null&&e.getMessage().indexOf("java.lang.ClassNotFoundException") >= 0)
				PlatformLogger.error("TSCP-9458：Remote object can't find . ");
			throw new TscpBaseException(e);
		} finally {
			this.close();
		}
	}

	@Override
	public void close() throws TscpBaseException {

		try {
			MediationPoolFactory.getMessageMediation().produceInstance(TscpRMSenderMediationPool.class).getBusyQueue().get(this.getProtocolId()).remove(this);
			MediationPoolFactory.getMessageMediation().produceInstance(TscpRMSenderMediationPool.class).getFreeQueue().get(this.getProtocolId()).put(this);
		} catch (InterruptedException e) {
			throw new TscpBaseException("TSCP-9056:CLOSE RESOURCE EXCEPTION !",e);
		}

	}
	
	
	/**
	 * @description Send message try again...
	 * @param req
	 * @return
	 * @throws TscpBaseException
	 * @throws InterruptedException
	 */
	protected ITscpRespMedia tryAgain(ITscpReqMedia req) throws TscpBaseException, InterruptedException{
		
		MediationPoolFactory.getMessageMediation().produceInstance(TscpRMSenderMediationPool.class).clear(this.getProtocolId());
		
		return MediationPoolFactory.getMessageMediation().produceInstance(TscpRMSenderMediationPool.class).getMediation(this.getProtocolId()).messageSenderHandler(req);
		
	}

	
	

	@Override
	public void initialize() throws TscpBaseException {

		try {
			this.sender = (ITscpBaseInternalReceiver) Naming.lookup("//"+ this.provideURL + "/TscpDefaultProtocolAdapter");
		} catch (Exception e) {
			PlatformLogger.error("TSCP-6945:Connection refused["+ this.provideURL + "]", e);
		}
	}

}
