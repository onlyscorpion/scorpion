package org.scorpion.scheduler.server;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.scorpion.api.common.IScorpionJobTrigger;


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
public class ScorpionCronTaskTrigger extends CronTriggerImpl implements IScorpionJobTrigger{
	
	
	private static final long serialVersionUID = 7463592202094767149L;

	
	public IScorpionJobTrigger startAt(Date startTime) {
		this.setStartTime(startTime);
		return this;
		
	}

	@Override
	public IScorpionJobTrigger endAt(Date endTime) {
		this.setEndTime(endTime);
		return this;
	}
	
	public IScorpionJobTrigger setJobKey(String name,String group){
		super.setKey(new TriggerKey(name,group));
		return this;
	}

	@Override
	public IScorpionJobTrigger bind() {
		
		return this;
	}

	@Override
	public void registerGroupAndName(String name, String group) {
		TriggerKey key = new TriggerKey(name, group);
		this.setKey(key);
	}
	

	@Override
	public IScorpionJobTrigger withPriority(int priority) {
		this.withPriority(priority);
		return this;
	}
	

	@Override
	public IScorpionJobTrigger addDescription(String description) {
		this.setDescription(description);
		return this;
	}
	

	@Override
	public void addPropertiesValue(String key, Object value) {
		this.getJobDataMap().put(key, value);
	}

	
	@Override
	public void addPropertiesMap(Map<String, Object> argMap) {
		if(argMap != null)
			this.setJobDataMap(new JobDataMap(argMap));
	}

	
	@Override
	public IScorpionJobTrigger withSchedule(String timerExpression,TimeUnit timeunit,Integer repeatCount) throws ParseException {
		
		CronExpression expression = new CronExpression(timerExpression);
		this.setCronExpression(expression);
		this.setTimeZone(expression.getTimeZone());
		this.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);
		return this;
	
	}

	

}
