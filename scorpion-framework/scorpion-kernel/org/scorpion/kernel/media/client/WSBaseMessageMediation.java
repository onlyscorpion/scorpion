package org.scorpion.kernel.media.client;

import java.net.URL;
import org.codehaus.xfire.client.Client;
import org.scorpion.api.common.AbsMediationFactor;
import org.scorpion.api.common.ITscpProtocal.ProtocolType;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpReqMedia;
import org.scorpion.api.kernel.ITscpRespMedia;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.common.annotation.Sender;
import org.scorpion.common.enums.SenderType;
import org.scorpion.kernel.media.DefaultTscpRespMedia;

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
@Sender(sendertype = SenderType.STANDARD_SENDER, protocolType = ProtocolType.WEBSERVICE)
public class WSBaseMessageMediation extends AbsMediationFactor {

	private static final long serialVersionUID = -4032626339999375992L;
	
	private String method;
	
	private Client sender;
	
	@Override
	public ITscpRespMedia messageSenderHandler(ITscpReqMedia req)throws TscpBaseException {
		
		if(sender == null)
			throw new TscpBaseException("TSCP-4213:The WEBSERVICE CLIENT don't initialize or initialization failure ! The WEBSERVICE ADDRESS IS ["+this.provideURL+"]");
		
		try {
			
			//Object retnObj = sender.invoke(this.method, new Object[]{(String)req.getServiceArgument()[0]})[0];
			Object retnObj = sender.invoke(this.method, req.getServiceArgument())[0];
			
			DefaultTscpRespMedia resp = new DefaultTscpRespMedia();
			
			resp.setStatus(Constant.SUCCESS);
			
			resp.setResponseValue(retnObj);
			
			return resp;
			
		} catch (Throwable e) {
			throw new TscpBaseException(e);
		}
	}

	@Override
	public void close() throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() throws TscpBaseException {
		try {
			this.sender = new Client(new URL(this.getProvideURL()));
		}catch (Throwable e) {
			PlatformLogger.error(e);
		}
	}

	@Override
	protected ITscpRespMedia tryAgain(ITscpReqMedia req)throws TscpBaseException, InterruptedException {
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
