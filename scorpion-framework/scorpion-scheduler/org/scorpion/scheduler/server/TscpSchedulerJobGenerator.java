package org.scorpion.scheduler.server;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.scorpion.api.common.ITscpScheduler;
import org.scorpion.api.common.ITscpSchedulerJobGenerator;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.TscpSequenceUtil;
import org.scorpion.common.util.TscpSchedulerPolicy;
import org.scorpion.kernel.util.TscpServiceUtil;

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
public class TscpSchedulerJobGenerator implements ITscpSchedulerJobGenerator{

	@Override
	public boolean generateTscpScheduler(ITscpScheduler scheduler,Object express) throws TscpBaseException {
		try{
			CronExpressEntity expression =  (CronExpressEntity) express;
			if(expression.isMemoryScheduler()){
				registerLocalMemoryScheduler(expression);
				return true;
			}
			JobKey jobkey = null;
			if(expression.getJobName() != null){
				jobkey = new JobKey(expression.getJobName(),expression.getJobName());
		
				if(scheduler.checkExists(jobkey)&&!expression.isAllowRegisterSameService()){
				
					PlatformLogger.warn("TSCP-4078:The jobName ["+expression.getJobName()+"] already exist , "
							+ "Don't allow to register same jobName in the same scheduler instance !");
					return false;
				}
			}else{
				expression.setJobName(TscpSequenceUtil.generateSequeueString());
			}
			JobDetail jobDetail = null;
			if(expression.getJobDataMap() == null)
				jobDetail = JobBuilder.newJob(expression.getJob()).withIdentity(expression.getJobName(),expression.getServiceName()==null?
						TscpSequenceUtil.generateSequeueString():expression.getServiceName()).build();
			else
				jobDetail = JobBuilder.newJob(expression.getJob()).setJobData(new JobDataMap(expression.getJobDataMap())).withIdentity(expression.getJobName(),
						expression.isAllowRegisterSameService()?TscpSequenceUtil.generateSequeueString():expression.getJobName()).build();
			TimeUnit timeUnit = getTimeUnit(expression);
			scheduler.scheduleJob(jobDetail, TscpSchedulerTriggerFactory.getTscpJobTrigger().produceInstance(expression.isSimpleType()).
					startAt(expression.getValidTimeStart()).endAt(expression.getValidTimeEnd()).setJobKey(expression.getJobName(),
							expression.isAllowRegisterSameService()?TscpSequenceUtil.generateSequeueString():expression.getJobName()).
							withSchedule(expression.getExecuteExpres(), timeUnit, expression.getExecuteTimes()));
			return true;
		}catch(Throwable ex){
			throw new TscpBaseException("TSCP-4960:Register tscp scheduler job failure !",ex);
		}
	}
	
	
	/**
	 * @DESCRIPTION GENEREATE MEMORY SCHEDULER ....
	 * 
	 * @param expression
	 * 
	 * @throws TscpBaseException
	 */
	public void registerLocalMemoryScheduler(final CronExpressEntity expression) throws TscpBaseException{
		
		int period;
		
		switch(expression.getPolicyType()){
		
		case TscpSchedulerPolicy.SECOND_FREQUENCY : period = Integer.parseInt(expression.getExecuteExpres())*1000; break;
		case TscpSchedulerPolicy.MINUTES_FREQUENCY : period = Integer.parseInt(expression.getExecuteExpres())*60000; break;
		case TscpSchedulerPolicy.HOUR_FREQUENCY : period = Integer.parseInt(expression.getExecuteExpres())*3600000; break;
		default: throw new TscpBaseException("TSCP-9687：Unknow period type !");
		}
		
        new Timer().schedule(new TimerTask() {
        	int num = 0;
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				try {
					if(expression.getExecuteTimes()!=null&&(++num) > expression.getExecuteTimes())
						Thread.currentThread().stop();
					Object map = expression.getJobDataMap().get(Constant.CUSTOM_JOB_DATA);
					if(map == null)
						TscpServiceUtil.callService(expression.getServiceName());
					else
						TscpServiceUtil.callService(expression.getServiceName(), expression.getJobDataMap().get(Constant.CUSTOM_JOB_DATA));
				} catch (TscpBaseException e) {
					PlatformLogger.error("TSCP-1024：The scheduler ["+expression.getServiceName()+"] executed failure !",e);
				}
			}
			
		}, 0, period);
		
	}
	
	
	/**
	 * @param expression
	 * 
	 * @return
	 */
	private TimeUnit getTimeUnit(CronExpressEntity expression){
		
		switch(expression.getPolicyType()){
		case TscpSchedulerPolicy.SECOND_FREQUENCY :{
			return TimeUnit.SECONDS;
		}
		case TscpSchedulerPolicy.MINUTES_FREQUENCY :{
			return TimeUnit.MINUTES;
		}
		case TscpSchedulerPolicy.HOUR_FREQUENCY :{
			return TimeUnit.HOURS;
		}
		default:{
			return null;
		}
		}
	}
	

}
