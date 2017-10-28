package com.scorpion.huakerongtong.kernel.classanalyzer;

import com.scorpion.huakerongtong.api.configuration.AopPointcutInfo;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IAnnotationAnalyzerListener;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.common.annotation.Interceptor;
import com.scorpion.huakerongtong.common.context.SystemContext;

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
