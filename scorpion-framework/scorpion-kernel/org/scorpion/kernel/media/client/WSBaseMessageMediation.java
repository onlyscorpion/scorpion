package org.scorpion.kernel.media.client;

import java.net.URL;
import org.codehaus.xfire.client.Client;
import org.scorpion.api.common.AbsMediationFactor;
import org.scorpion.api.common.IScorpionProtocal.ProtocolType;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IScorpionReqMedia;
import org.scorpion.api.kernel.IScorpionRespMedia;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.common.annotation.Sender;
import org.scorpion.common.enums.SenderType;
import org.scorpion.kernel.media.DefaultScorpionRespMedia;

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
@Sender(sendertype = SenderType.STANDARD_SENDER, protocolType = ProtocolType.WEBSERVICE)
public class WSBaseMessageMediation extends AbsMediationFactor {

	private static final long serialVersionUID = -4032626339999375992L;
	
	private String method;
	
	private Client sender;
	
	@Override
	public IScorpionRespMedia messageSenderHandler(IScorpionReqMedia req)throws ScorpionBaseException {
		
		if(sender == null)
			throw new ScorpionBaseException("scorpion-4213:The WEBSERVICE CLIENT don't initialize or initialization failure ! The WEBSERVICE ADDRESS IS ["+this.provideURL+"]");
		
		try {
			
			//Object retnObj = sender.invoke(this.method, new Object[]{(String)req.getServiceArgument()[0]})[0];
			Object retnObj = sender.invoke(this.method, req.getServiceArgument())[0];
			
			DefaultScorpionRespMedia resp = new DefaultScorpionRespMedia();
			
			resp.setStatus(Constant.SUCCESS);
			
			resp.setResponseValue(retnObj);
			
			return resp;
			
		} catch (Throwable e) {
			throw new ScorpionBaseException(e);
		}
	}

	@Override
	public void close() throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() throws ScorpionBaseException {
		try {
			this.sender = new Client(new URL(this.getProvideURL()));
		}catch (Throwable e) {
			PlatformLogger.error(e);
		}
	}

	@Override
	protected IScorpionRespMedia tryAgain(IScorpionReqMedia req)throws ScorpionBaseException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
