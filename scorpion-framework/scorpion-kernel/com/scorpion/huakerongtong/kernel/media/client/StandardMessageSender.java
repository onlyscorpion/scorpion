package com.scorpion.huakerongtong.kernel.media.client;

import java.util.Map;

import com.scorpion.huakerongtong.api.common.IScorpionProtocal.ProtocolType;
import com.scorpion.huakerongtong.api.configuration.ScorpionRouteInfo;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IBaseMessageSender;
import com.scorpion.huakerongtong.api.kernel.IScorpionAppliactionSession;
import com.scorpion.huakerongtong.api.kernel.IScorpionReqMedia;
import com.scorpion.huakerongtong.api.kernel.IScorpionRespMedia;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.common.context.SystemContext;
import com.scorpion.huakerongtong.common.util.ScorpionSystemSessionUtils;
import com.scorpion.huakerongtong.kernel.media.DefaultScorpionRespMedia;
import com.scorpion.huakerongtong.kernel.route.ProtocolConf.EJBProtocolConf;
import com.scorpion.huakerongtong.kernel.route.ProtocolConf.RouteConf;
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
public class StandardMessageSender implements IBaseMessageSender {

	private String serviceName;

	private RouteConf routeConf;

	private EJBProtocolConf protocolConf;

	/**
	 * @param req
	 * 
	 * @param protocolType
	 * 
	 * @param parameter
	 * 
	 * @param isLocalService
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	final protected IScorpionRespMedia send(final IScorpionReqMedia req,final ProtocolType protocolType,final Map<String, Object> parameter, final boolean isLocalService)throws ScorpionBaseException {

		if (!(SystemContext.getApplicationContext().getSystemProfile().getApplicationState() == Constant.RUNNING)) {
			throw new ScorpionBaseException("scorpion-8759:SYSTEM DON'T START COMPLETE ，TRY AGAIN LATER !");
		}

		final IScorpionAppliactionSession session = ScorpionSystemSessionUtils.clear();
		final String serviceName = req.getServiceName();
		Object value = null;

		try {
			if (isLocalService) {
				value = ScorpionServiceUtil.callLocalServiceByServiceContainer(null, serviceName, req.getServiceArgument());
			} else {
				value = ScorpionServiceUtil.callService(req.getServiceName(),req.getServiceArgument());
			}

			final DefaultScorpionRespMedia response = new DefaultScorpionRespMedia();
			response.setSessionID(req.getSessionId());
			response.setResponseValue(value);
			response.setCallLevel(session.getServiceCalledLevel().get());
			response.setTempData(session.getTmpData());
			return response;
		} catch (Throwable e) {
			PlatformLogger.error(e);
			throw new ScorpionBaseException(e);
		}

	}

	@Override
	public IScorpionRespMedia send(IScorpionReqMedia req) throws ScorpionBaseException {
		return null;
	}

	@Override
	public IScorpionRespMedia send(String protocol, IScorpionReqMedia req)throws ScorpionBaseException {
		return null;
	}

	@Override
	public IScorpionRespMedia send(ScorpionRouteInfo routeInfo, IScorpionReqMedia req)throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public RouteConf getRouteConf() {
		return routeConf;
	}

	public void setRouteConf(RouteConf routeConf) {
		this.routeConf = routeConf;
	}

	public EJBProtocolConf getProtocolConf() {
		return protocolConf;
	}

	public void setProtocolConf(EJBProtocolConf protocolConf) {
		this.protocolConf = protocolConf;
	}

}
