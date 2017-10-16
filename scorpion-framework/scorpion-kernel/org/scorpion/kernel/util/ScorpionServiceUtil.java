package org.scorpion.kernel.util;

import java.io.NotSerializableException;
import java.util.Map;

import org.scorpion.api.common.AbsMedaitionPool;
import org.scorpion.api.common.AbsScorpionConcurrentTask;
import org.scorpion.api.common.AbsScorpionThreadPrimer;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionServiceProxy;
import org.scorpion.api.kernel.IScorpionGlobalSystemSession;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.concurrentcpt.ConcurrentEngineUtil;
import org.scorpion.common.concurrentcpt.ThreadPropertiesEntity;
import org.scorpion.common.context.SystemContext;
import org.scorpion.common.enums.SenderType;
import org.scorpion.common.exception.ScorpionExtendException;
import org.scorpion.common.util.ScorpionSystemSessionUtils;
import org.scorpion.kernel.media.DefaultScorpionReqMedia;
import org.scorpion.kernel.media.MediationPoolFactory;
import org.scorpion.kernel.media.ScorpionDynamicMedia;
import org.scorpion.kernel.route.CoreRouterCache;

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
public class ScorpionServiceUtil {

	public static AbsScorpionServiceProxy serviceProxy;
	
	private static String ENGINE_NAME = "ASYCALL";

	/**
	 * @param serviceName
	 * 
	 * @param params
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public final static void callAsynService(final String serviceName, final Object... params)throws ScorpionBaseException {

		ConcurrentEngineUtil.createConcurrentEngine(ENGINE_NAME, 5, SystemEnumType.threadgroup.getValue(),
				new ThreadPropertiesEntity(ENGINE_NAME,60,true)).fire(new AbsScorpionThreadPrimer() {
					
					@Override
					public void initialization() throws ScorpionBaseException {
					}
					
					@Override
					public void destroy() throws ScorpionBaseException {
					}
				}, new IConcurrentProcessor() {
					@Override
					public void processor(AbsScorpionConcurrentTask task) throws ScorpionBaseException {
					}

					@Override
					public void processor() throws ScorpionBaseException {
						ScorpionServiceUtil.callService(serviceName, params);
					}
				});
		
	}
	
	
	
	/**
	 * @param serviceName
	 * 
	 * @param arguments
	 * 
	 * @return
	 */
	public final Object callServiceByDestinationId(String serviceName,String destinationId, Object... arguments) {

		return null;
	}

	
	

	/**
	 * @description Call remote service by ip address and port....
	 * 
	 * @param serviceName
	 * 
	 * @param ip
	 * 
	 * @param port
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public final static Object callRemoteServiceByIp(String serviceName,String protocolType, String ip, int port, Object... arguments)throws ScorpionBaseException {

		try {
			
			if (!SystemUtils.regularExpressionValidate(ip, Constant.IP_PATTERN))
				throw new ScorpionBaseException("Scorpion:6904:Invalid IP address[" + ip+ "], check IP configuration and try again !");
			
			return ScorpionDynamicMedia.getDynamicMediation(protocolType, ip, port)
					.messageSenderHandler(generateDefaultMediaReq(serviceName, arguments)).getResponseValue();
	
		} catch (InterruptedException e) {
			throw new ScorpionExtendException(e);
		} catch (InstantiationException e) {
			throw new ScorpionExtendException(e);
		} catch (IllegalAccessException e) {
			throw new ScorpionExtendException(e);
		} catch (NotSerializableException e) {
			throw new ScorpionExtendException(e);
		}
	
	}


	/**
	 * @description Service invoke... current service is local service or remote service...
	 * 
	 * @param serviceName
	 * 
	 * @param params
	 * 
	 * @return object
	 * 
	 * @throws ScorpionBaseException
	 */
	public final static Object callService(String serviceName,Object... arguments) throws ScorpionBaseException {

		PlatformLogger.info("Is beginning to call service ["+serviceName+"] !");
		
		try {
			if (CoreRouterCache.protocolResource.containsKey(serviceName)) {
			
				DefaultScorpionReqMedia req = generateDefaultMediaReq(serviceName,arguments);
				return ((AbsMedaitionPool<?>) MediationPoolFactory.getMessageMediation().produceInstance(CoreRouterCache.protocolResource.getProtocolByServiceName(serviceName))).
						getMediation(CoreRouterCache.protocolResource.getServiceConf().get(serviceName).getProtocolId()).messageSenderHandler(req).getResponseValue();
		
			} else if (SystemContext.getApplicationContext().getMessageSenders().get(SenderType.STANDARD_SENDER.name()) != null) {
			
				DefaultScorpionReqMedia req = generateDefaultMediaReq(serviceName,arguments);
				return SystemContext.getApplicationContext().getMessageSenders().get(SenderType.STANDARD_SENDER.name()).getSender().send(req).getResponseValue();
			
			} else
			
				return callLoaclService(serviceName, arguments);
		
		} catch (Throwable ex) {
			
			throw new ScorpionExtendException("scorpion-9124:Call service ["+serviceName+"] exception ! ",ex);
		}
	}

	/**
	 * @Description Construct request media
	 * 
	 * @param serviceName
	 * 
	 * @param arguments
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws NotSerializableException 
	 */
	protected static DefaultScorpionReqMedia generateDefaultMediaReq(String serviceName, Object... arguments) throws ScorpionBaseException, NotSerializableException {

		DefaultScorpionReqMedia req = new DefaultScorpionReqMedia();

		req.setServiceName(serviceName);

		req.setServiceParam(arguments);
		
		Map<String,Object> temp = ScorpionSystemSessionUtils.getSession().sesstionDataConvertMap();
		
		if(temp != null)
			req.getReqTempData().putAll(temp);
		
		req.setSessionId(ScorpionSystemSessionUtils.getSession().getSessionId());

		req.addCurrentServer();
		
		SystemUtils.argumentSerializableValidate(req);

		return req;
	}
	
	
	

	/**
	 * @Description Call service by protocol...
	 * 
	 * @param protocolId
	 * 
	 * @param serviceName
	 * 
	 * @param params
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public final static Object callServiceByProtocol(String protocolId,String serviceName, Object... arguments) throws ScorpionBaseException {

		return null;
	}

	/**
	 * @Description Call local container service ...
	 * 
	 * @param serviceName
	 * 
	 * @param params
	 * 
	 * @return
	 * @throws Throwable 
	 */
	public final static Object callLoaclService(String serviceName,Object... arguments) throws Throwable {

		IScorpionGlobalSystemSession session = (IScorpionGlobalSystemSession) ScorpionSystemSessionUtils.getSession();

		if (session == null)
			session = (IScorpionGlobalSystemSession) ScorpionSystemSessionUtils.createSession();

		session.fresh(true);

		try {

			return serviceProxy.callServiceProxy(session, serviceName,arguments);

		} finally {

			session.fresh(false);

		}

	}

	/**
	 * @Description Call local container service ...
	 * 
	 * @param object
	 * 
	 * @param serviceName
	 * 
	 * @param argument
	 * 
	 * @return
	 */
	public static Object callLocalServiceByServiceContainer(Object object,String serviceName, Object[] argument) {
		return null;
	}

}
