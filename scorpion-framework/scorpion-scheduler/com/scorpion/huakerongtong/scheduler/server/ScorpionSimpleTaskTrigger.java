package com.scorpion.huakerongtong.scheduler.server;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.quartz.JobDataMap;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import com.scorpion.huakerongtong.api.common.IScorpionJobTrigger;

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
public class ScorpionSimpleTaskTrigger extends SimpleTriggerImpl implements IScorpionJobTrigger{
	

	private static final long serialVersionUID = -8291453685827390565L;

	
	public IScorpionJobTrigger withSchedule(String timerExpression,TimeUnit timeunit,Integer repeatCount) {
	        this.setRepeatInterval(timeunit.toMillis(Integer.parseInt(timerExpression)));
	        if(repeatCount == null)
	        	this.setRepeatCount(REPEAT_INDEFINITELY);
	        else 
	        	this.setRepeatCount(repeatCount);
	        
	        this.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);
	        return this;
	}

	@Override
	public IScorpionJobTrigger startAt(Date startTime) {
		this.setStartTime(startTime);
		return this;
	}

	@Override
	public IScorpionJobTrigger endAt(Date endTime) {
		this.setEndTime(endTime);
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
	public IScorpionJobTrigger setJobKey(String name, String group) {
		super.setKey(new TriggerKey(name,group));
		return this;
	}

}
