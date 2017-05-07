package org.scorpion.scheduler.server;

import java.util.Date;
import java.util.Map;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.common.util.TscpSchedulerPolicy;

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
public class TscpSchedulerExpressionGenerator {
	
	
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
	 * @throws TscpBaseException 
	 */
	public static CronExpressEntity constructConnExpression(String timeExpression,Integer times,Date vaildTimeStart,Date vaildTimeEnd,int policy,String jobName,
			Map<Object,Object> args,Object job,boolean isMemoryScheduler,boolean isAllowSameNameRegister) throws TscpBaseException{
		
		switch(policy){
	
		case TscpSchedulerPolicy.SECOND_FREQUENCY:{
			return registerCommonCronExpress(timeExpression,times,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isMemoryScheduler,isAllowSameNameRegister);
		}
		
		case TscpSchedulerPolicy.MINUTES_FREQUENCY:{
			return registerCommonCronExpress(timeExpression,times,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isMemoryScheduler,isAllowSameNameRegister);
		}
		
		case TscpSchedulerPolicy.HOUR_FREQUENCY:{
			return registerCommonCronExpress(timeExpression,times,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isMemoryScheduler,isAllowSameNameRegister);
		}
		
		case TscpSchedulerPolicy.HOUR_DAY:{
			return parseHourOfDayCronExpress(timeExpression,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
		}
		
		case TscpSchedulerPolicy.HOUR_DAY_MONTH:{
			return parseHourOfDayMonthCronExpress(timeExpression,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
		}
		
		case TscpSchedulerPolicy.HOUR_DAY_MONTH_YEAR:{
			return parseHourOfDayMonthYearCronExpress(timeExpression,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
		}
		
		case TscpSchedulerPolicy.DAY_WEEK:{
			return parseWeekCronExpress(timeExpression,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
		}
		
		default:{
			throw new TscpBaseException("TSCP-4087:Invalid frequency type ["+policy+"]");
		}
	
		}
	}
	
	
	private static void validateTimerExpress(String timeExpression,String regex) throws TscpBaseException{
	
		//if(!SystemUtils.regularExpressionValidate(timeExpression,""))
				//throw new TscpBaseException("The express ["+timeExpression+"] is invalid !");
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
	 * @throws TscpBaseException 
	 */
	private static CronExpressEntity parseHourOfDayCronExpress(String timeExpression,Date vaildTimeStart,Date vaildTimeEnd,int policy,String jobName,Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws TscpBaseException{

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
	 * @throws TscpBaseException 
	 */
	private static CronExpressEntity parseHourOfDayMonthCronExpress(String timeExpression,Date vaildTimeStart,Date vaildTimeEnd,int policy,String jobName,Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws TscpBaseException{

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
	 * @throws TscpBaseException 
	 */
	private static CronExpressEntity parseHourOfDayMonthYearCronExpress(String timeExpression,Date vaildTimeStart,Date vaildTimeEnd,int policy,String jobName,Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws TscpBaseException{

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
	 * @throws TscpBaseException 
	 */
	private static CronExpressEntity parseWeekCronExpress(String timeExpression,Date validTimeStart,Date validTimeEnd,int policy,String jobName,Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws TscpBaseException{

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
	 * @throws TscpBaseException 
	 */
	private static CronExpressEntity registerCommonCronExpress(String timeExpression,Integer times,
			Date validTimeStart,Date vaildTimeEnd,int policy,String jobName,Map<Object,Object> args,Object job,boolean isMemoryScheduler,boolean isAllowSameNameRegister) throws TscpBaseException{
		
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
