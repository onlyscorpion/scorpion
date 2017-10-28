package com.scorpion.huakerongtong.kernel.media.client;

import java.net.URL;
import org.codehaus.xfire.client.Client;
import com.scorpion.huakerongtong.api.common.AbsMediationFactor;
import com.scorpion.huakerongtong.api.common.IScorpionProtocal.ProtocolType;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IScorpionReqMedia;
import com.scorpion.huakerongtong.api.kernel.IScorpionRespMedia;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.common.annotation.Sender;
import com.scorpion.huakerongtong.common.enums.SenderType;
import com.scorpion.huakerongtong.kernel.media.DefaultScorpionRespMedia;

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
