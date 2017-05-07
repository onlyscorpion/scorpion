package org.scorpion.scheduler.server;

import org.scorpion.api.common.AbsTscpFactory;
import org.scorpion.api.common.ITscpJobTrigger;
import org.scorpion.api.exception.TscpBaseException;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpSchedulerTriggerFactory extends AbsTscpFactory<ITscpJobTrigger>{

	@Override
	public ITscpJobTrigger produceInstance(Object... arg)throws TscpBaseException {
		if(Boolean.parseBoolean(arg[0].toString()))
			return new TscpSimpleTaskTrigger();
		else
			return new TscpCronTaskTrigger();
	}

	@Override
	public <P> P produceInstance(Class<P> clazz) throws TscpBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITscpJobTrigger produceInstance() throws TscpBaseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @description Create sender
	 * 
	 * @return
	 */
	public static AbsTscpFactory<ITscpJobTrigger> getTscpJobTrigger() {

		if (factory == null)
			factory = new TscpSchedulerTriggerFactory();

		return factory;
	}

	private static AbsTscpFactory<ITscpJobTrigger> factory;


}
