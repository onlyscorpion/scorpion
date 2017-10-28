package com.scorpion.huakerongtong.scheduler.server;

import java.util.Date;
import java.util.Map;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.common.util.ScorpionSchedulerPolicy;

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
public class ScorpionSchedulerExpressionGenerator {
	
	
	/**
	 * @param timeExpression
	 * 
	 * @param times
	 * 
	 * @param vaildTimeStart
	 * 
	 * @param vaildTimeEnd
	 * 
	 * @param policy
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException 
	 */
	public static CronExpressEntity constructConnExpression(String timeExpression,Integer times,Date vaildTimeStart,Date vaildTimeEnd,int policy,String jobName,
			Map<Object,Object> args,Object job,boolean isMemoryScheduler,boolean isAllowSameNameRegister) throws ScorpionBaseException{
		
		switch(policy){
	
		case ScorpionSchedulerPolicy.SECOND_FREQUENCY:{
			return registerCommonCronExpress(timeExpression,times,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isMemoryScheduler,isAllowSameNameRegister);
		}
		
		case ScorpionSchedulerPolicy.MINUTES_FREQUENCY:{
			return registerCommonCronExpress(timeExpression,times,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isMemoryScheduler,isAllowSameNameRegister);
		}
		
		case ScorpionSchedulerPolicy.HOUR_FREQUENCY:{
			return registerCommonCronExpress(timeExpression,times,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isMemoryScheduler,isAllowSameNameRegister);
		}
		
		case ScorpionSchedulerPolicy.HOUR_DAY:{
			return parseHourOfDayCronExpress(timeExpression,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
		}
		
		case ScorpionSchedulerPolicy.HOUR_DAY_MONTH:{
			return parseHourOfDayMonthCronExpress(timeExpression,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
		}
		
		case ScorpionSchedulerPolicy.HOUR_DAY_MONTH_YEAR:{
			return parseHourOfDayMonthYearCronExpress(timeExpression,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
		}
		
		case ScorpionSchedulerPolicy.DAY_WEEK:{
			return parseWeekCronExpress(timeExpression,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
		}
		
		default:{
			throw new ScorpionBaseException("scorpion-4087:Invalid frequency type ["+policy+"]");
		}
	
		}
	}
	
	
	private static void validateTimerExpress(String timeExpression,String regex) throws ScorpionBaseException{
	
		//if(!SystemUtils.regularExpressionValidate(timeExpression,""))
				//throw new ScorpionBaseException("The express ["+timeExpression+"] is invalid !");
			//System.out.println("invalid");
	
	}
	
	
	
	/**
	 * 
	 * @param timeExpression
	 * 
	 * @param times
	 * 
	 * @param vaildTimeStart
	 * 
	 * @param vaildTimeEnd
	 * 
	 * @param policy
	 * 
	 * @throws ScorpionBaseException 
	 */
	private static CronExpressEntity parseHourOfDayCronExpress(String timeExpression,Date vaildTimeStart,Date vaildTimeEnd,int policy,String jobName,Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws ScorpionBaseException{

		validateTimerExpress(timeExpression,"");
		CronExpressEntity express = new CronExpressEntity();
		express.setPolicyType(policy);
		express.setJob(job);
		express.setJobDataMap(args);
		express.setValidTimeStart(vaildTimeStart);
		express.setValidTimeEnd(vaildTimeEnd);
		express.setExecuteExpres(constructCommonCronExpress(timeExpression).append(" * * ?").substring(1));
		express.setAllowRegisterSameService(isAllowSameNameRegister);
		if(!isAllowSameNameRegister)
			express.setJobName(jobName);
		return express;
	}
	
	
	
	
	
	
	
	/**
	 * @param timeExpression
	 * 
	 * @param times
	 * 
	 * @param vaildTimeStart
	 * 
	 * @param vaildTimeEnd
	 * 
	 * @param policy
	 * 
	 * @throws ScorpionBaseException 
	 */
	private static CronExpressEntity parseHourOfDayMonthCronExpress(String timeExpression,Date vaildTimeStart,Date vaildTimeEnd,int policy,String jobName,Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws ScorpionBaseException{

		validateTimerExpress(timeExpression,"");
		CronExpressEntity express = new CronExpressEntity();
		express.setPolicyType(policy);
		express.setJob(job);
		express.setJobDataMap(args);
		express.setValidTimeStart(vaildTimeStart);
		express.setValidTimeEnd(vaildTimeEnd);
		express.setExecuteExpres(constructCommonCronExpress(timeExpression).append(" ? *").substring(1));
		express.setAllowRegisterSameService(isAllowSameNameRegister);
		if(!isAllowSameNameRegister)
			express.setJobName(jobName);
		return express;
	}
	
	
	
	/**
	 * @param timeExpression
	 * 
	 * @param times
	 * 
	 * @param vaildTimeStart
	 * 
	 * @param vaildTimeEnd
	 * 
	 * @param policy
	 * 
	 * @throws ScorpionBaseException 
	 */
	private static CronExpressEntity parseHourOfDayMonthYearCronExpress(String timeExpression,Date vaildTimeStart,Date vaildTimeEnd,int policy,String jobName,Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws ScorpionBaseException{

		validateTimerExpress(timeExpression,"");
		CronExpressEntity express = new CronExpressEntity();
		express.setPolicyType(policy);
		express.setJob(job);
		express.setJobDataMap(args);
		express.setValidTimeStart(vaildTimeStart);
		express.setValidTimeEnd(vaildTimeEnd);
		express.setExecuteExpres(constructCommonCronExpress(timeExpression).substring(1));
		express.setAllowRegisterSameService(isAllowSameNameRegister);
		if(!isAllowSameNameRegister)
			express.setJobName(jobName);
		return express;
	
	}
	
	
	/**
	 * 
	 * @param timerExpress
	 * @return
	 */
	private static StringBuilder constructCommonCronExpress(String timerExpression){
		
		String[] allRegex = timerExpression.split(" ");
		String[] time = allRegex[allRegex.length-1].split(":");
		StringBuilder sb = new StringBuilder();
		for(int j = time.length-1;j>=0;j--){
			sb.append(" "+time[j]);
		}
		if(allRegex.length>1){
			String[] month = allRegex[0].split("/");
			for(int j = month.length-1;j>=0;j--){
				if(month.length == 3&&j == 0){
					sb.append(" ?");
				}
				sb.append(" "+month[j]);
			}
		}
		return sb;
	}
	
	
	
	/**
	 * @param timeExpression
	 * 
	 * @param times
	 * 
	 * @param validTimeStart
	 * 
	 * @param validTimeEnd
	 * 
	 * @param policy
	 * @throws ScorpionBaseException 
	 */
	private static CronExpressEntity parseWeekCronExpress(String timeExpression,Date validTimeStart,Date validTimeEnd,int policy,String jobName,Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws ScorpionBaseException{

		validateTimerExpress(timeExpression,"");
		CronExpressEntity express = new CronExpressEntity();
		express.setPolicyType(policy);
		express.setValidTimeStart(validTimeStart);
		express.setValidTimeEnd(validTimeEnd);
		express.setJob(job);
		express.setJobDataMap(args);
		express.setAllowRegisterSameService(isAllowSameNameRegister);
		String[] time = timeExpression.split(":");
		StringBuilder sb = new StringBuilder();
			
		for(int j = time.length-1;j>=0;j--){
			sb.append(" "+time);
		}
		sb.append(" ? *");
		express.setExecuteExpres(sb.substring(1));
		return express;
	
	}
	
	
	/**
	 * 
	 * @param timeExpression
	 * 
	 * @param times
	 * 
	 * @param vaildTimeStart
	 * 
	 * @param vaildTimeEnd
	 * 
	 * @param policy
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException 
	 */
	private static CronExpressEntity registerCommonCronExpress(String timeExpression,Integer times,
			Date validTimeStart,Date vaildTimeEnd,int policy,String jobName,Map<Object,Object> args,Object job,boolean isMemoryScheduler,boolean isAllowSameNameRegister) throws ScorpionBaseException{
		
		if(timeExpression == null||"".equals(timeExpression)){
			PlatformLogger.warn("Don't find scheduler expression, System will ignore current timer register !");
			PlatformLogger.error("Register scheduler task failure !");
			return null;
		}
		
		CronExpressEntity express = new CronExpressEntity();
		express.setExecuteExpres(timeExpression);
		express.setPolicyType(policy);
		express.setSimpleType(true);
		express.setJob(job);
		express.setJobDataMap(args);
		express.setMemoryScheduler(isMemoryScheduler);
		express.setAllowRegisterSameService(isAllowSameNameRegister);
		if(!isAllowSameNameRegister)
			express.setJobName(jobName);
		
		if(validTimeStart == null)
			validTimeStart = new Date();
		express.setValidTimeStart(validTimeStart);
		if(vaildTimeEnd == null)
			express.setExecuteForever(false);
		else
			express.setValidTimeEnd(vaildTimeEnd);
		
		express.setExecuteTimes(times);
		return express;
		
	}
	
	

}
