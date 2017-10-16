package org.scorpion.kernel.container.guice;

import java.lang.reflect.Method;

import org.scorpion.api.kernel.AbsScorpionClassAndMethodFilter;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.annotation.Service;

import com.google.inject.matcher.Matcher;

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
public class ScorpionMethodsMatcher extends AbsScorpionClassAndMethodFilter<Method> {

	@Override
	public Matcher<Method> and(Matcher<? super Method> arg0) {
		return null;
	}

	@Override
	public boolean matches(Method method) {

		this.checkPointcutInfo(this.getAoppointcutinfo());
		Service service = method.getAnnotation(Service.class);

		if (service != null)

			if (this.getAoppointcutinfo().isServiceValidate()&& (SystemUtils.regularExpressionValidate("".equals(service.name()) ? method.getName() : service.name(), this.getAoppointcutinfo().getServiceName())))
				return true;

		for (String methodRegex : this.getAoppointcutinfo().getMethodRegex()) {

			/*
			 * if(SystemUtils.regularExpressionValidate(method.getName(),
			 * methodRegex)&&
			 * (this.getAoppointcutinfo().getArgumentNum()==-1?true
			 * :method.getParameterTypes
			 * ().length==this.getAoppointcutinfo().getArgumentNum())&&
			 * (this.getAoppointcutinfo().getReturnType() ==
			 * null?true:this.getAoppointcutinfo
			 * ().getReturnType().getName().equals(method.getReturnType())))
			 */
			if (SystemUtils.regularExpressionValidate(method.getName(),methodRegex))
				return true;
		}
		return false;
	}

	@Override
	public Matcher<Method> or(Matcher<? super Method> arg0) {
		return null;
	}

}
