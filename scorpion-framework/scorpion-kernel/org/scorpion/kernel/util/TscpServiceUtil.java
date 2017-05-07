package org.scorpion.kernel.util;

import java.io.NotSerializableException;
import java.util.Map;

import org.scorpion.api.common.AbsMedaitionPool;
import org.scorpion.api.common.AbsTscpConcurrentTask;
import org.scorpion.api.common.AbsTscpThreadPrimer;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpServiceProxy;
import org.scorpion.api.kernel.ITscpGlobalSystemSession;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.concurrentcpt.ConcurrentEngineUtil;
import org.scorpion.common.concurrentcpt.ThreadPropertiesEntity;
import org.scorpion.common.context.SystemContext;
import org.scorpion.common.enums.SenderType;
import org.scorpion.common.exception.TscpExtendException;
import org.scorpion.common.util.TscpSystemSessionUtils;
import org.scorpion.kernel.media.DefaultTscpReqMedia;
import org.scorpion.kernel.media.MediationPoolFactory;
import org.scorpion.kernel.media.TscpDynamicMedia;
import org.scorpion.kernel.route.CoreRouterCache;

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
public class TscpServiceUtil {

	public static AbsTscpServiceProxy serviceProxy;
	
	private static String ENGINE_NAME = "ASYCALL";

	/**
	 * @param serviceName
	 * 
	 * @param params
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public final static void callAsynService(final String serviceName, final Object... params)throws TscpBaseException {

		ConcurrentEngineUtil.createConcurrentEngine(ENGINE_NAME, 5, SystemEnumType.threadgroup.getValue(),
				new ThreadPropertiesEntity(ENGINE_NAME,60,true)).fire(new AbsTscpThreadPrimer() {
					
					@Override
					public void initialization() throws TscpBaseException {
					}
					
					@Override
					public void destroy() throws TscpBaseException {
					}
				}, new IConcurrentProcessor() {
					@Override
					public void processor(AbsTscpConcurrentTask task) throws TscpBaseException {
					}

					@Override
					public void processor() throws TscpBaseException {
						TscpServiceUtil.callService(serviceName, params);
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
	 * @throws TscpBaseException
	 */
	public final static Object callRemoteServiceByIp(String serviceName,String protocolType, String ip, int port, Object... arguments)throws TscpBaseException {

		try {
			
			if (!SystemUtils.regularExpressionValidate(ip, Constant.IP_PATTERN))
				throw new TscpBaseException("TSCP:6904:Invalid IP address[" + ip+ "], check IP configuration and try again !");
			
			return TscpDynamicMedia.getDynamicMediation(protocolType, ip, port)
					.messageSenderHandler(generateDefaultMediaReq(serviceName, arguments)).getResponseValue();
	
		} catch (InterruptedException e) {
			throw new TscpExtendException(e);
		} catch (InstantiationException e) {
			throw new TscpExtendException(e);
		} catch (IllegalAccessException e) {
			throw new TscpExtendException(e);
		} catch (NotSerializableException e) {
			throw new TscpExtendException(e);
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
	 * @throws TscpBaseException
	 */
	public final static Object callService(String serviceName,Object... arguments) throws TscpBaseException {

		PlatformLogger.info("Is beginning to call service ["+serviceName+"] !");
		
		try {
			if (CoreRouterCache.protocolResource.containsKey(serviceName)) {
			
				DefaultTscpReqMedia req = generateDefaultMediaReq(serviceName,arguments);
				return ((AbsMedaitionPool<?>) MediationPoolFactory.getMessageMediation().produceInstance(CoreRouterCache.protocolResource.getProtocolByServiceName(serviceName))).
						getMediation(CoreRouterCache.protocolResource.getServiceConf().get(serviceName).getProtocolId()).messageSenderHandler(req).getResponseValue();
		
			} else if (SystemContext.getApplicationContext().getMessageSenders().get(SenderType.STANDARD_SENDER.name()) != null) {
			
				DefaultTscpReqMedia req = generateDefaultMediaReq(serviceName,arguments);
				return SystemContext.getApplicationContext().getMessageSenders().get(SenderType.STANDARD_SENDER.name()).getSender().send(req).getResponseValue();
			
			} else
			
				return callLoaclService(serviceName, arguments);
		
		} catch (Throwable ex) {
			
			throw new TscpExtendException("TSCP-9124:Call service ["+serviceName+"] exception ! ",ex);
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
	 * @throws TscpBaseException
	 * 
	 * @throws NotSerializableException 
	 */
	protected static DefaultTscpReqMedia generateDefaultMediaReq(String serviceName, Object... arguments) throws TscpBaseException, NotSerializableException {

		DefaultTscpReqMedia req = new DefaultTscpReqMedia();

		req.setServiceName(serviceName);

		req.setServiceParam(arguments);
		
		Map<String,Object> temp = TscpSystemSessionUtils.getSession().sesstionDataConvertMap();
		
		if(temp != null)
			req.getReqTempData().putAll(temp);
		
		req.setSessionId(TscpSystemSessionUtils.getSession().getSessionId());

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
	 * @throws TscpBaseException
	 */
	public final static Object callServiceByProtocol(String protocolId,String serviceName, Object... arguments) throws TscpBaseException {

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

		ITscpGlobalSystemSession session = (ITscpGlobalSystemSession) TscpSystemSessionUtils.getSession();

		if (session == null)
			session = (ITscpGlobalSystemSession) TscpSystemSessionUtils.createSession();

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
