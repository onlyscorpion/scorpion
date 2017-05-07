package org.scorpion.api.common;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;

import org.quartz.Trigger;

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
public interface ITscpJobTrigger extends Trigger{
	
	/**
	 * @param timerExpress
	 */
	ITscpJobTrigger withSchedule(String timerExpression,TimeUnit timeunit,Integer repeatCount)throws ParseException;
	
	/*
	 * 
	 */
	//ITscpJobTrigger withSchedule(String timerExpression)throws ParseException;
	
	/**
	 * @param date
	 */
	ITscpJobTrigger startAt(Date date);
	
	/**
	 * @param date
	 */
	ITscpJobTrigger endAt(Date date);
	
	/**
	 * @param job
	 */
	ITscpJobTrigger bind();
    
    /**
     * @param name
     * @param group
     */
    void registerGroupAndName(String name,String group);
    
    /**
     * @param priority
     */
    ITscpJobTrigger withPriority(int priority);
    
    /**
     * @param description
     */
    ITscpJobTrigger addDescription(String description);
    
    /**
     * @param key
     * @param value
     */
    void addPropertiesValue(String key,Object value);
    
    /**
     * @param argMap
     */
    void addPropertiesMap(Map<String,Object> argMap);
    
    /**
     * @param name
     * @param group
     * @return
     */
	public ITscpJobTrigger setJobKey(String name,String group);
	
	
}
