package org.scorpion.kernel.container.guice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.scorpion.api.kernel.IScorpionAopAroundAdvice;

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
public class ScorpionAopDoAroundInterceptor implements MethodInterceptor {

	private IScorpionAopAroundAdvice aroundAdvice;

	@Override
	public Object invoke(MethodInvocation invoke) throws Throwable {

		if (aroundAdvice == null)
			return invoke.proceed();

		aroundAdvice.doAroundAdvice();

		Object obj = invoke.proceed();

		aroundAdvice.doAroundAdvice();

		return obj;
	}

	/**
	 * @description set around advice
	 * 
	 * @param aroundAdvice
	 * 
	 * @return
	 */
	public MethodInterceptor setAroundAdvice(IScorpionAopAroundAdvice aroundAdvice) {

		this.aroundAdvice = aroundAdvice;

		return this;

	}

}
