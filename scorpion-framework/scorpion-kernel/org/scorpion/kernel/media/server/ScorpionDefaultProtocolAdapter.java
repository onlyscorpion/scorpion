package org.scorpion.kernel.media.server;

import java.io.Serializable;
import java.rmi.RemoteException;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsBaseMessageReceiver;
import org.scorpion.api.kernel.IScorpionBaseInternalReceiver;
import org.scorpion.api.kernel.IScorpionBaseMessageReceiver;
import org.scorpion.api.kernel.IScorpionReqMedia;
import org.scorpion.api.kernel.IScorpionRespMedia;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;
import org.scorpion.common.util.ScorpionSystemSessionUtils;
import org.scorpion.kernel.media.DefaultScorpionRespMedia;
import org.scorpion.kernel.util.ScorpionServiceUtil;

/**
 * 天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.Scorpion.common
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
