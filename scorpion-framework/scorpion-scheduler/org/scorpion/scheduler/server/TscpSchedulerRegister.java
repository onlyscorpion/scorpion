package org.scorpion.scheduler.server;

import java.util.Date;
import java.util.Map;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.scorpion.api.common.AbsTscpSchedulerJob;
import org.scorpion.api.common.ITscpScheduler;
import org.scorpion.api.common.ITscpSchedulerJobGenerator;
import org.scorpion.api.common.ITscpSchedulerRegister;
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
public class TscpSchedulerRegister implements ITscpSchedulerRegister{
	
	
	private static ITscpSchedulerRegister register;
	
	private ITscpSchedulerJobGenerator generator;
	
	private ITscpScheduler scheduler;

	
	

	@Override
	public boolean registerSchedulerByServiceName(String serviceName,String timerExpression,Integer times,
			Date startAt,Date endAt,int policy,String jobName,Map<Object,Object> args) throws TscpBaseException, SchedulerException {
	
		if(scheduler == null)
			throw new TscpBaseException("TSCP-0374:The tscp scheduler component haven bean not started , can't register scheduler job !");
		
		return generator.generateTscpScheduler(scheduler,TscpSchedulerExpressionGenerator.
				constructConnExpression(timerExpression, times, startAt, endAt, policy,jobName,args,serviceName,false,true));
	}

	
	@Override
	public boolean registerSchedulerBySchedulerJob(AbsTscpSchedulerJob job,String timerExpression,Integer times,
			Date startAt,Date endAt,int policy,String jobName,Map<Object,Object> args) throws TscpBaseException, SchedulerException {
		
		if(scheduler == null)
			throw new TscpBaseException("TSCP-0375:The tscp scheduler component haven been not started , can't register scheduler job !");
		
		return generator.generateTscpScheduler(scheduler,TscpSchedulerExpressionGenerator.
				constructConnExpression(timerExpression, times, startAt, endAt, policy,jobName,args,job,false,true));
	}
	

	/**
	 * @return
	 */
	public synchronized static ITscpSchedulerRegister getDefaultInstance(){
		
		if(register == null)
			register = new TscpSchedulerRegister();
		
		return register;
	}
	

	@Override
	public boolean registerSchedulerByServiceName(String serviceName,String timerExpression,Integer times,
			Date startAt,Date endAt,int policy,String jobName,Map<Object,Object> args,boolean isMemoryScheduler,boolean isAllowSameNameRegister) throws TscpBaseException, SchedulerException {
	
		if(scheduler == null)
			throw new TscpBaseException("TSCP-0374:The tscp scheduler component haven bean not started , can't register scheduler job !");
		
		return generator.generateTscpScheduler(scheduler,TscpSchedulerExpressionGenerator.
				constructConnExpression(timerExpression, times, startAt, endAt, policy,jobName,args,serviceName,isMemoryScheduler,isAllowSameNameRegister));
	}

	
	
	/**
	 * @description register application context information ....
	 * 
	 * @param scheduler
	 * 
	 * @param generator
	 */
	void registerContext(ITscpScheduler scheduler,ITscpSchedulerJobGenerator generator){
		this.scheduler = scheduler;
		this.generator = generator;
	}


	@Override
	public void pauseJob(String jobName) throws TscpBaseException {
		
		if(scheduler == null)
			throw new TscpBaseException("TSCP-0375:The tscp scheduler component haven't started , can't register scheduler job !");
	
		try {
			scheduler.pauseJob(new JobKey(jobName));
		} catch (SchedulerException e) {
			throw new TscpBaseException("TSCP-4278:PauseJob scheduler job failure !",e);
		}
	}


	@Override
	public void removeJob(String jobName) throws TscpBaseException {
		
		try {
			scheduler.deleteJob(new JobKey(jobName));
		} catch (SchedulerException e) {
			throw new TscpBaseException("TSCP-4276:Remove scheduler job failure !",e);
		}
	}
	

}
