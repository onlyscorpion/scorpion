package org.scorpion.scheduler.server;

import java.util.Date;
import java.util.Map;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.scorpion.api.common.AbsScorpionSchedulerJob;
import org.scorpion.api.common.IScorpionScheduler;
import org.scorpion.api.common.IScorpionSchedulerJobGenerator;
import org.scorpion.api.common.IScorpionSchedulerRegister;
import org.scorpion.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionSchedulerRegister implements IScorpionSchedulerRegister{
	
	
	private static IScorpionSchedulerRegister register;
	
	private IScorpionSchedulerJobGenerator generator;
	
	private IScorpionScheduler scheduler;

	
	

	@Override
	public boolean registerSchedulerByServiceName(String serviceName,String timerExpression,Integer times,
			Date startAt,Date endAt,int policy,String jobName,Map<Object,Object> args) throws ScorpionBaseException, SchedulerException {
	
		if(scheduler == null)
			throw new ScorpionBaseException("scorpion-0374:The Scorpion scheduler component haven bean not started , can't register scheduler job !");
		
		return generator.generateScorpionScheduler(scheduler,ScorpionSchedulerExpressionGenerator.
				constructConnExpression(timerExpression, times, startAt, endAt, policy,jobName,args,serviceName,false,true));
	}

	
	@Override
	public boolean registerSchedulerBySchedulerJob(AbsScorpionSchedulerJob job,String timerExpression,Integer times,
			Date startAt,Date endAt,int policy,String jobName,Map<Object,Object> args) throws ScorpionBaseException, SchedulerException {
		
		if(scheduler == null)
			throw new ScorpionBaseException("scorpion-0375:The Scorpion scheduler component haven been not started , can't register scheduler job !");
		
		return generator.generateScorpionScheduler(scheduler,ScorpionSchedulerExpressionGenerator.
				constructConnExpression(timerExpression, times, startAt, endAt, policy,jobName,args,job,false,true));
	}
	

	/**
	 * @return
	 */
	public synchronized static IScorpionSchedulerRegister getDefaultInstance(){
		
		if(register == null)
			register = new ScorpionSchedulerRegister();
		
		return register;
	}
	

	@Override
	public boolean registerSchedulerByServiceName(String serviceName,String timerExpression,Integer times,
			Date startAt,Date endAt,int policy,String jobName,Map<Object,Object> args,boolean isMemoryScheduler,boolean isAllowSameNameRegister) throws ScorpionBaseException, SchedulerException {
	
		if(scheduler == null)
			throw new ScorpionBaseException("scorpion-0374:The Scorpion scheduler component haven bean not started , can't register scheduler job !");
		
		return generator.generateScorpionScheduler(scheduler,ScorpionSchedulerExpressionGenerator.
				constructConnExpression(timerExpression, times, startAt, endAt, policy,jobName,args,serviceName,isMemoryScheduler,isAllowSameNameRegister));
	}

	
	
	/**
	 * @description register application context information ....
	 * 
	 * @param scheduler
	 * 
	 * @param generator
	 */
	void registerContext(IScorpionScheduler scheduler,IScorpionSchedulerJobGenerator generator){
		this.scheduler = scheduler;
		this.generator = generator;
	}


	@Override
	public void pauseJob(String jobName) throws ScorpionBaseException {
		
		if(scheduler == null)
			throw new ScorpionBaseException("scorpion-0375:The Scorpion scheduler component haven't started , can't register scheduler job !");
	
		try {
			scheduler.pauseJob(new JobKey(jobName));
		} catch (SchedulerException e) {
			throw new ScorpionBaseException("scorpion-4278:PauseJob scheduler job failure !",e);
		}
	}


	@Override
	public void removeJob(String jobName) throws ScorpionBaseException {
		
		try {
			scheduler.deleteJob(new JobKey(jobName));
		} catch (SchedulerException e) {
			throw new ScorpionBaseException("scorpion-4276:Remove scheduler job failure !",e);
		}
	}
	

}
