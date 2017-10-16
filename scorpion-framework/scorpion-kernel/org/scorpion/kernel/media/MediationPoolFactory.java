package org.scorpion.kernel.media;

import java.util.HashMap;
import java.util.Map;

import org.scorpion.api.common.AbsMedaitionPool;
import org.scorpion.api.common.AbsMediationFactor;
import org.scorpion.api.common.AbsScorpionFactory;
import org.scorpion.api.exception.ScorpionBaseException;

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
public class MediationPoolFactory extends AbsScorpionFactory<AbsMedaitionPool<AbsMediationFactor>> {

	private Map<String, AbsMedaitionPool<?>> poolmap = new HashMap<String, AbsMedaitionPool<?>>();

	/**
	 * 
	 * @param key
	 * 
	 * @param factor
	 */
	public AbsMedaitionPool<?> addFatorToPool(String key,AbsMedaitionPool<?> factor) {

		if(poolmap.containsKey(key))
			return poolmap.get(key);
		
		poolmap.put(key, factor);
		
		return factor;
	}

	@Override
	public AbsMedaitionPool<AbsMediationFactor> produceInstance(Object... arg)throws ScorpionBaseException {

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <P> P produceInstance(Class<P> clazz) throws ScorpionBaseException {

		if(!poolmap.containsKey(clazz.getName()))
			throw new ScorpionBaseException("scorpion-9423:Application route component don't start, so can't call service by dynamic ip address");

		/*
		 * if(ScorpionEJBSenderMediationPool.class.isAssignableFrom(clazz)){ return
		 * (P) poolmap.get(clazz.getName()); }else
		 * if(ScorpionRMSenderMediationPool.class.isAssignableFrom(clazz)){ return
		 * (P) poolmap.get(clazz.getName()); }
		 */
		
		return (P) poolmap.get(clazz.getName());
	}

	@Override
	public AbsMedaitionPool<AbsMediationFactor> produceInstance()throws ScorpionBaseException {

		// return new EJBBaseMessageMediation();
		return null;
	}

	/**
	 * @description Create sender
	 * 
	 * @return
	 */
	public static AbsScorpionFactory<AbsMedaitionPool<AbsMediationFactor>> getMessageMediation() {

		if (factory == null)
			factory = new MediationPoolFactory();

		return factory;
	}

	private static AbsScorpionFactory<AbsMedaitionPool<AbsMediationFactor>> factory;

}
