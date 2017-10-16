package org.scorpion.scheduler.server;

import org.scorpion.api.common.AbsScorpionFactory;
import org.scorpion.api.common.IScorpionJobTrigger;
import org.scorpion.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionSchedulerTriggerFactory extends AbsScorpionFactory<IScorpionJobTrigger>{

	@Override
	public IScorpionJobTrigger produceInstance(Object... arg)throws ScorpionBaseException {
		if(Boolean.parseBoolean(arg[0].toString()))
			return new ScorpionSimpleTaskTrigger();
		else
			return new ScorpionCronTaskTrigger();
	}

	@Override
	public <P> P produceInstance(Class<P> clazz) throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IScorpionJobTrigger produceInstance() throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @description Create sender
	 * 
	 * @return
	 */
	public static AbsScorpionFactory<IScorpionJobTrigger> getScorpionJobTrigger() {

		if (factory == null)
			factory = new ScorpionSchedulerTriggerFactory();

		return factory;
	}

	private static AbsScorpionFactory<IScorpionJobTrigger> factory;


}
