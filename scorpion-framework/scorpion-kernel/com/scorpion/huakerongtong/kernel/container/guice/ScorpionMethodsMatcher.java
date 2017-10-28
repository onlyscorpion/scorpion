package com.scorpion.huakerongtong.kernel.container.guice;

import java.lang.reflect.Method;

import com.scorpion.huakerongtong.api.kernel.AbsScorpionClassAndMethodFilter;
import com.scorpion.huakerongtong.api.util.SystemUtils;
import com.scorpion.huakerongtong.common.annotation.Service;

import com.google.inject.matcher.Matcher;

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
