package org.scorpion.scheduler.server;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.quartz.JobDataMap;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.scorpion.api.common.ITscpJobTrigger;

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
public class TscpSimpleTaskTrigger extends SimpleTriggerImpl implements ITscpJobTrigger{
	

	private static final long serialVersionUID = -8291453685827390565L;

	
	public ITscpJobTrigger withSchedule(String timerExpression,TimeUnit timeunit,Integer repeatCount) {
	        this.setRepeatInterval(timeunit.toMillis(Integer.parseInt(timerExpression)));
	        if(repeatCount == null)
	        	this.setRepeatCount(REPEAT_INDEFINITELY);
	        else 
	        	this.setRepeatCount(repeatCount);
	        
	        this.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);
	        return this;
	}

	@Override
	public ITscpJobTrigger startAt(Date startTime) {
		this.setStartTime(startTime);
		return this;
	}

	@Override
	public ITscpJobTrigger endAt(Date endTime) {
		this.setEndTime(endTime);
		return this;
	}

	@Override
	public ITscpJobTrigger bind() {
		return this;
	}
	

	@Override
	public void registerGroupAndName(String name, String group) {
		TriggerKey key = new TriggerKey(name, group);
		this.setKey(key);
	}

	@Override
	public ITscpJobTrigger withPriority(int priority) {
		this.withPriority(priority);
		return this;
	}

	@Override
	public ITscpJobTrigger addDescription(String description) {
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
	public ITscpJobTrigger setJobKey(String name, String group) {
		super.setKey(new TriggerKey(name,group));
		return this;
	}

}
