package org.scorpion.kernel.container.guice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.scorpion.api.kernel.ITscpAopBeforeAdvice;

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
public class TscpAopDoBeforeInterceptor implements MethodInterceptor {

	private ITscpAopBeforeAdvice beforeAdvice;

	@Override
	public Object invoke(MethodInvocation invoke) throws Throwable {

		if (beforeAdvice != null)

			beforeAdvice.doBeforeAdvice();

		return invoke.proceed();

	}

	/**
	 * 
	 * @param beforeAdvice
	 * 
	 * @return
	 */
	public MethodInterceptor setBeforeAdvice(ITscpAopBeforeAdvice beforeAdvice) {

		this.beforeAdvice = beforeAdvice;

		return this;

	}

}
