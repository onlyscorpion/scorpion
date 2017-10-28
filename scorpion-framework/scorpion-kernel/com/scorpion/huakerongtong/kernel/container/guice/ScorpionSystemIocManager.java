package com.scorpion.huakerongtong.kernel.container.guice;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IScorpionSystemIocManager;

import com.google.inject.Injector;

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
public class ScorpionSystemIocManager implements IScorpionSystemIocManager {

	private Injector injector;

	@Override
	public <T> T getBeanByClassType(Class<T> clazz) throws ScorpionBaseException {
		return injector.getInstance(clazz);
	}

	@Override
	public <T> T getBeanByClassName(String name) throws ScorpionBaseException {
		return null;
	}

	@Override
	public <T> T getBeanByClassAndName(String name, Class<T> clazz)throws ScorpionBaseException {
		return null;
	}

	public void setInjector(Injector injector) {
		this.injector = injector;
	}

	public Injector getInjector() {
		return injector;
	}

	@Override
	public void getBeanByServiceName(String serviceName)throws ScorpionBaseException {

	}

	@Override
	public <T> T getBeanByServiceName(String serviceName, Class<T> clazz)throws ScorpionBaseException {
		return null;
	}

}
