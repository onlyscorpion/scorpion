package org.scorpion.kernel.container.spring;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IScorpionSystemIocManager;

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
public class ScorpionSystemIocManager implements IScorpionSystemIocManager {

	@Override
	public <T> T getBeanByClassType(Class<T> clazz) throws ScorpionBaseException {
		return null;
	}

	@Override
	public <T> T getBeanByClassName(String name) throws ScorpionBaseException {
		return null;
	}

	@Override
	public <T> T getBeanByClassAndName(String name, Class<T> clazz)
			throws ScorpionBaseException {
		return null;
	}

	@Override
	public void getBeanByServiceName(String serviceName)
			throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T getBeanByServiceName(String serviceName, Class<T> clazz)
			throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
