package org.scorpion.api.common;

import java.util.Date;
import java.util.Map;

import org.quartz.SchedulerException;
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
public interface ITscpSchedulerRegister {
	
	
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
	 * @throws TscpBaseException
	 */
	boolean registerSchedulerByServiceName(String serviceName,String timerExpression,Integer times,
			Date startAt,Date endAt,int policy,String jobName,Map<Object,Object> args) throws TscpBaseException, SchedulerException;
	
	
	/**
	 * @param job 定时任务处理类 需要实现粗象类 AbsTscpSchedulerJob
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
	 * @throws TscpBaseException
	 * 
	 * @throws SchedulerException
	 */
	boolean registerSchedulerBySchedulerJob(AbsTscpSchedulerJob job,String timerExpression,Integer times,
			Date startAt,Date endAt,int policy,String jobName,Map<Object,Object> args)throws TscpBaseException,SchedulerException;
	
	
	/**
	 * @param jobName
	 * 
	 * @throws TscpBaseException
	 */
	void pauseJob(String jobName)throws TscpBaseException;
	
	
	/**
	 * 
	 * @param jobName
	 * 
	 * @throws TscpBaseException
	 */
	void removeJob(String jobName)throws TscpBaseException;
	

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
	 * @throws TscpBaseException
	 * 
	 * @throws SchedulerException
	 */
	public boolean registerSchedulerByServiceName(String serviceName,String timerExpression,Integer times,
			Date startAt,Date endAt,int policy,String jobName,Map<Object,Object> args,boolean isMemoryScheduler,boolean isAllowSameNameRegister) throws TscpBaseException, SchedulerException;
	
	
	

}
