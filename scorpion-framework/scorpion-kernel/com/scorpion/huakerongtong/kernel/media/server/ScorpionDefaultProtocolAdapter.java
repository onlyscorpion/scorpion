package com.scorpion.huakerongtong.kernel.media.server;

import java.io.Serializable;
import java.rmi.RemoteException;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.AbsBaseMessageReceiver;
import com.scorpion.huakerongtong.api.kernel.IScorpionBaseInternalReceiver;
import com.scorpion.huakerongtong.api.kernel.IScorpionBaseMessageReceiver;
import com.scorpion.huakerongtong.api.kernel.IScorpionReqMedia;
import com.scorpion.huakerongtong.api.kernel.IScorpionRespMedia;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.common.context.SystemContext;
import com.scorpion.huakerongtong.common.util.ScorpionSystemSessionUtils;
import com.scorpion.huakerongtong.kernel.media.DefaultScorpionRespMedia;
import com.scorpion.huakerongtong.kernel.util.ScorpionServiceUtil;

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
public class ScorpionDefaultProtocolAdapter extends AbsBaseMessageReceiver implements IScorpionBaseMessageReceiver, IScorpionBaseInternalReceiver, Serializable {

	public ScorpionDefaultProtocolAdapter() throws RemoteException, ScorpionBaseException {
		super();
		init();
	}
	
	
	/**
	 * @Description Initialize application configuration ... 
	 * 
	 * @Time 2015-08-15
	 * 
	 * @author zcl
	 * @throws ScorpionBaseException 
	 */
	private void init() throws ScorpionBaseException{
		super.context = SystemContext.getApplicationContext();
	}

	private static final long serialVersionUID = 5132745554268873283L;


	@Override
	public IScorpionRespMedia receiveBean(IScorpionReqMedia argument)throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public IScorpionRespMedia internalCall(IScorpionReqMedia req)throws ScorpionBaseException {
			
		ScorpionSystemSessionUtils.clear();
		
		ScorpionSystemSessionUtils.getSession().setSessionId(req.getSessionId());
		
		ScorpionSystemSessionUtils.getSession().tempMapDataConvertSession(req.getReqTempData());

		Object returnObj = ScorpionServiceUtil.callService(req.getServiceName(),req.getServiceArgument());
			
		DefaultScorpionRespMedia resp = new DefaultScorpionRespMedia();
		
		resp.setStatus(Constant.SUCCESS);
		
		resp.setResponseValue(returnObj);
		
		resp.setRespTempData(ScorpionSystemSessionUtils.getSession().sesstionDataConvertMap());
		
		return resp;
			
	}


	@Override
	public String receiveXmlMessage(String arguemnt) throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
