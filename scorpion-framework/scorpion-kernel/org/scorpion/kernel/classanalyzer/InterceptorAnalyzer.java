package org.scorpion.kernel.classanalyzer;

import org.scorpion.api.configuration.AopPointcutInfo;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IAnnotationAnalyzerListener;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.common.annotation.Interceptor;
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
public class InterceptorAnalyzer implements IAnnotationAnalyzerListener {

	@Override
	public void analyse(Class<?> clazz, String jarName)
			throws ScorpionBaseException {

		Interceptor interceptor = clazz.getAnnotation(Interceptor.class);

		if (interceptor == null)
			return;

		AopPointcutInfo aopPointcutInfo = new AopPointcutInfo();
		aopPointcutInfo.setClassRegex(interceptor.classRegex().split(","));
		aopPointcutInfo.setMethodRegex(interceptor.methodRegex().split(","));
		aopPointcutInfo.setServiceName(interceptor.serviceName());
		aopPointcutInfo.setServiceValidate("".equals(interceptor.serviceName()) ? false: true);
		aopPointcutInfo.setReturnType(interceptor.returnType());
		aopPointcutInfo.setArgumentNum(interceptor.paramNum());
		aopPointcutInfo.setName(interceptor.name());
		aopPointcutInfo.setInterceptorClass(clazz);
		aopPointcutInfo.setJarName(jarName);

		SystemContext.getApplicationContext().getScanInfo().getPointcutInfos().put(aopPointcutInfo.getName(), aopPointcutInfo);
		PlatformLogger.info("system find the interceptor["+ aopPointcutInfo.getName() + "]in the class["+ clazz.getName() + "]");
	}

}
