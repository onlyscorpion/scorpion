package org.scorpion.kernel.media.server;

import java.io.Serializable;
import java.rmi.RemoteException;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsBaseMessageReceiver;
import org.scorpion.api.kernel.ITscpBaseInternalReceiver;
import org.scorpion.api.kernel.ITscpBaseMessageReceiver;
import org.scorpion.api.kernel.ITscpReqMedia;
import org.scorpion.api.kernel.ITscpRespMedia;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;
import org.scorpion.common.util.TscpSystemSessionUtils;
import org.scorpion.kernel.media.DefaultTscpRespMedia;
import org.scorpion.kernel.util.TscpServiceUtil;

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
public class TscpDefaultProtocolAdapter extends AbsBaseMessageReceiver implements ITscpBaseMessageReceiver, ITscpBaseInternalReceiver, Serializable {

	public TscpDefaultProtocolAdapter() throws RemoteException, TscpBaseException {
		super();
		init();
	}
	
	
	/**
	 * @Description Initialize application configuration ... 
	 * 
	 * @Time 2015-08-15
	 * 
	 * @author zcl
	 * @throws TscpBaseException 
	 */
	private void init() throws TscpBaseException{
		super.context = SystemContext.getApplicationContext();
	}

	private static final long serialVersionUID = 5132745554268873283L;


	@Override
	public ITscpRespMedia receiveBean(ITscpReqMedia argument)throws TscpBaseException {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public ITscpRespMedia internalCall(ITscpReqMedia req)throws TscpBaseException {
			
		TscpSystemSessionUtils.clear();
		
		TscpSystemSessionUtils.getSession().setSessionId(req.getSessionId());
		
		TscpSystemSessionUtils.getSession().tempMapDataConvertSession(req.getReqTempData());

		Object returnObj = TscpServiceUtil.callService(req.getServiceName(),req.getServiceArgument());
			
		DefaultTscpRespMedia resp = new DefaultTscpRespMedia();
		
		resp.setStatus(Constant.SUCCESS);
		
		resp.setResponseValue(returnObj);
		
		resp.setRespTempData(TscpSystemSessionUtils.getSession().sesstionDataConvertMap());
		
		return resp;
			
	}


	@Override
	public String receiveXmlMessage(String arguemnt) throws TscpBaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
