package org.scorpion.kernel.classanalyzer;

import java.lang.reflect.Method;

import org.scorpion.api.configuration.ScorpionSystemScanInfo.ServiceInfo;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IAnnotationAnalyzerListener;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.common.annotation.BeanContainer;
import org.scorpion.common.annotation.Service;
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
 * Description: if developer want to create a component. the developer must
 * extends the abstract
 * </p>
 * <p>
 * class AScorpionComponet. the AScorpionComponent exist life cycle. developer can
 * override
 * </p>
 * <p>
 * the initialization method or service method or destroy method to handle
 * themselves business
 * </p>
 * <p>
 * but we don't suggest the developer do that
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
public class BeanAnalyzer implements IAnnotationAnalyzerListener {

	/**
	 * @param serviceInfo
	 * 
	 * @param service
	 * 
	 * @param clazz
	 * 
	 * @param method
	 */
	private void serviceGenerator(ServiceInfo serviceInfo, Service service,Class<?> clazz, Method method) {

		serviceInfo.setServiceName("".equals(service.name()) ? method.getName(): service.name());
		serviceInfo.setSingleton(service.isSynService());
		serviceInfo.setParameterTypes(method.getParameterTypes());
		serviceInfo.setReturnType(method.getReturnType());
		serviceInfo.setMethod(method);
		serviceInfo.setDescription(service.describe());
		serviceInfo.setClazz(clazz);

	}

	@Override
	public void analyse(Class<?> clazz, String jarName)throws ScorpionBaseException {

		if (clazz.getAnnotation(BeanContainer.class) == null)
			return;

		for (Method method : clazz.getMethods()) {
			Service service = method.getAnnotation(Service.class);

			if (service == null)
				continue;
			ServiceInfo serviceInfo = SystemContext.getApplicationContext().getScanInfo().new ServiceInfo();
			serviceGenerator(serviceInfo, service, clazz, method);
			serviceInfo.setJarName(jarName);

			if (SystemContext.getApplicationContext().getScanInfo().getServicePool().containsKey(serviceInfo.getServiceName())) {
				PlatformLogger.error("TSC-9006:服务冲突",new ScorpionBaseException("服务["+ serviceInfo.getServiceName()+ "]已注册，冲突服务在类["+ clazz.getName()+
						"]和类["+ SystemContext.getApplicationContext().getScanInfo().getServicePool().get(serviceInfo.getServiceName()).getClazz().getName() + "]中"));
				continue;
			}

			SystemContext.getApplicationContext().getScanInfo().getServicePool().put(serviceInfo.getServiceName(), serviceInfo);
			SystemContext.getApplicationContext().getScanInfo().getServiceNum().incrementAndGet();
			PlatformLogger.info("在类[" + clazz.getName() + "]中扫描到服务["+ serviceInfo.getServiceName() + "]控制属性single["+ serviceInfo.isSingleton() + "]");

		}
	}
}
