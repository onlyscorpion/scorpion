package org.scorpion.kernel.container.guice;

import org.scorpion.api.kernel.AbsTscpClassAndMethodFilter;
import org.scorpion.api.util.SystemUtils;

import com.google.inject.matcher.Matcher;

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
public class TscpClassesMatcher extends AbsTscpClassAndMethodFilter<Class<?>> {

	@Override
	public Matcher<Class<?>> and(Matcher<? super Class<?>> org0) {
		return null;
	}

	@Override
	public boolean matches(Class<?> argument) {

		if (this.getAoppointcutinfo() == null|| this.getAoppointcutinfo().isServiceValidate())
			return true;

		for (String regex : this.getAoppointcutinfo().getClassRegex())

			if (SystemUtils.regularExpressionValidate(argument.getName(), regex))
				return true;

		return false;

	}

	@Override
	public Matcher<Class<?>> or(Matcher<? super Class<?>> arg0) {

		return null;

	}

}
