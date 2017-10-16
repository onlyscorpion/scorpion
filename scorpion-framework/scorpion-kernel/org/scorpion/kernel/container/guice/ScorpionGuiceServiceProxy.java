package org.scorpion.kernel.container.guice;

import java.lang.reflect.InvocationTargetException;

import org.scorpion.api.configuration.ScorpionSystemScanInfo.ServiceInfo;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionServiceProxy;
import org.scorpion.api.kernel.IReqData;
import org.scorpion.api.kernel.IScorpionSystemIocManager;
import org.scorpion.common.context.SystemContext;

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
public class ScorpionGuiceServiceProxy extends AbsScorpionServiceProxy {

	/**
	 * 
	 * @param iocManager
	 */
	public ScorpionGuiceServiceProxy(IScorpionSystemIocManager iocManager) {
		super();
		super.iocManager = iocManager;
		super.context = SystemContext.getApplicationContext();
	}

	
	/**
	 * 
	 * @param serviceName
	 * 
	 * @param args
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	private Object[] coverntParam(String serviceName, Object... args)
			throws ScorpionBaseException {

		Object[] parameters;

		ServiceInfo serviceInfo = SystemContext.getApplicationContext().getScanInfo().getService(serviceName);

		if (serviceInfo == null)
			throw new ScorpionBaseException("scorpion-9754:调用服务信息不存在");

		if ((args == null && serviceInfo.getParameterTypes().length == 1)|| (serviceInfo.getParameterTypes().length == 1 && serviceInfo.getParameterTypes()[0].isAssignableFrom(args.getClass()))) {
			parameters = new Object[] { args };

		} else if (args != null && args.length == 0&& serviceInfo.getParameterTypes() == null) {
			parameters = null;

		} else if ((args == null && serviceInfo.getParameterTypes().length > 1)|| (args.length != serviceInfo.getParameterTypes().length)) {
			throw new ScorpionBaseException("scorpion-9075:调用服务[" + serviceName+ "]传入参数和方法参数不匹配!");

		} else {
			parameters = args;
		}

		return parameters;

	}

	/**
	 * @description call service handler
	 * 
	 * @param serviceName
	 * 
	 * @param params
	 * 
	 * @time 2015-06-04
	 */
	public final Object invokeService(String serviceName, Object... params)throws ScorpionBaseException {

		if (iocManager == null)
			throw new ScorpionBaseException("TSC-9008:Service container don't initialize ! ");

		params = coverntParam(serviceName, params);

		try {
			return SystemContext.getApplicationContext().getScanInfo().getService(serviceName).getMethod().invoke(iocManager.getBeanByClassType(SystemContext.getApplicationContext().getScanInfo().getService(serviceName).getClazz()), params);
		} catch (java.lang.IllegalArgumentException e) {
			throw new ScorpionBaseException("scorpion-9087:调用服务[" + serviceName+ "]的参数不匹配", e);
		} catch (IllegalAccessException e) {
			throw new ScorpionBaseException("scorpion-9088:访问受保护服务[" + serviceName+ "]", e);
		} catch (InvocationTargetException e) {
			throw new ScorpionBaseException(e);
		}

	}

	@Override
	public Object invokeAction(String actionName, IReqData req)throws ScorpionBaseException {

		if (actionName == null || actionName.indexOf("_") < 0)
			throw new ScorpionBaseException("TSC-6004:调用Action的名称不正确,正确的名称为空AcionName_methodName");

		try {
			if (req != null)
				return SystemContext.getApplicationContext().getScanInfo().getActionMethod(actionName.split("_")[0], actionName).invoke(iocManager.getBeanByClassType(SystemContext
						.getApplicationContext().getScanInfo().getActionByName(actionName.split("_")[0]).getClazz()), req);

			else
				return SystemContext.getApplicationContext().getScanInfo().getActionMethod(actionName.split("_")[0], actionName).invoke(iocManager.getBeanByClassType(SystemContext.getApplicationContext().getScanInfo().getActionByName(actionName.split("_")[0]).getClazz()));

		} catch (Exception e) {
			throw new ScorpionBaseException("TSC-9976:ACTION调用异常！", e);

		}
	}

}
