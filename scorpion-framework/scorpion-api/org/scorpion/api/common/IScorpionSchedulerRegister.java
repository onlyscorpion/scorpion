package org.scorpion.api.common;

import java.util.Date;
import java.util.Map;

import org.quartz.SchedulerException;
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
public interface IScorpionSchedulerRegister {
	
	
	/**
	 * @param serviceName 调用服务名称
	 * 
	 * @param timerExpression 定时任务表达式
	 * 
	 * @param times 执行次数
	 * 
	 * @param startAt 任务有效期起
	 * 
	 * @param endAt 任务有效期至
	 * 
	 * @param policy 策略
	 * 
	 * @param jobName 定时任务名称
	 * 
	 * @param args 定时任务参数
	 * 
	 * @throws ScorpionBaseException
	 */
	boolean registerSchedulerByServiceName(String serviceName,String timerExpression,Integer times,
			Date startAt,Date endAt,int policy,String jobName,Map<Object,Object> args) throws ScorpionBaseException, SchedulerException;
	
	
	/**
	 * @param job 定时任务处理类 需要实现粗象类 AbsScorpionSchedulerJob
	 * 
	 * @param timerExpression 定时任务表达式
	 * 
	 * @param times 定时任务执行次数
	 * 
	 * @param startAt 定时任务有效期起
	 * 
	 * @param endAt 定时任务有效期至
	 * 
	 * @param policy 定时任务执行策略
	 * 
	 * @param jobName 定时任务名称
	 * 
	 * @param args 定时任务执行参数
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws SchedulerException
	 */
	boolean registerSchedulerBySchedulerJob(AbsScorpionSchedulerJob job,String timerExpression,Integer times,
			Date startAt,Date endAt,int policy,String jobName,Map<Object,Object> args)throws ScorpionBaseException,SchedulerException;
	
	
	/**
	 * @param jobName
	 * 
	 * @throws ScorpionBaseException
	 */
	void pauseJob(String jobName)throws ScorpionBaseException;
	
	
	/**
	 * 
	 * @param jobName
	 * 
	 * @throws ScorpionBaseException
	 */
	void removeJob(String jobName)throws ScorpionBaseException;
	

	/**
	 * @param serviceName 定时任务调用服务名称
	 * 
	 * @param timerExpression 定时任务执行时间表达式
	 * 
	 * @param times 定时任务执行次数
	 * 
	 * @param startAt 定时任务有效期起
	 * 
	 * @param endAt 定时任务有效期至
	 * 
	 * @param policy 定时任务执行策略
	 * 
	 * @param jobName 定时任务名称
	 * 
	 * @param args 定时任务执行参数
	 * 
	 * @param isMemoryScheduler 是否是内存及定时任务
	 * 
	 * @param isAllowSameNameRegister 是否允许注册同名服务
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws SchedulerException
	 */
	public boolean registerSchedulerByServiceName(String serviceName,String timerExpression,Integer times,
			Date startAt,Date endAt,int policy,String jobName,Map<Object,Object> args,boolean isMemoryScheduler,boolean isAllowSameNameRegister) throws ScorpionBaseException, SchedulerException;
	
	
	

}
