package org.scorpion.kernel.media.client;

import java.util.Map;

import org.scorpion.api.common.IScorpionProtocal.ProtocolType;
import org.scorpion.api.configuration.ScorpionRouteInfo;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IBaseMessageSender;
import org.scorpion.api.kernel.IScorpionAppliactionSession;
import org.scorpion.api.kernel.IScorpionReqMedia;
import org.scorpion.api.kernel.IScorpionRespMedia;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;
import org.scorpion.common.util.ScorpionSystemSessionUtils;
import org.scorpion.kernel.media.DefaultScorpionRespMedia;
import org.scorpion.kernel.route.ProtocolConf.EJBProtocolConf;
import org.scorpion.kernel.route.ProtocolConf.RouteConf;
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
