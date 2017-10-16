package org.scorpion.scheduler.server;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.scorpion.api.common.AbsScorpionSchedulerJob;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.util.Constant;
import org.scorpion.scheduler.server.ScorpionDefaultSchedulerJob;

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
public class CronExpressEntity implements Serializable{

	private static final long serialVersionUID = -4898185866251004276L;
	
	private int policyType;
	
	private boolean isSimpleType;
	
	private String executeExpres;
	
	private Date validTimeStart; 
	
	private Date validTimeEnd;
	
	private Integer executeTimes;
	
	private String jobName;
	
	private Map<Object,Object> jobDataMap;
	
	private boolean isExecuteForever;
	
	private String serviceName;
	
	private Class<? extends AbsScorpionSchedulerJob> job;
	
	private boolean isDefaultJob;
	
	private boolean isMemoryScheduler;
	
	private boolean allowRegisterSameService;
	
	
	/**
	 * 
	 * @param job
	 * 
	 * @throws ScorpionBaseException
	 */
	@SuppressWarnings("unchecked")
	public void setJob(Object job) throws ScorpionBaseException{
		if(job instanceof Class){
			this.job =  (Class<? extends AbsScorpionSchedulerJob>) job;
			isDefaultJob = true;
		}else if(job instanceof String){
			if(jobDataMap == null)
				jobDataMap = new HashMap<Object,Object>();
			jobDataMap.put(Constant.SERVICE_NAME, job);
			this.serviceName = (String) job;
			this.job = ScorpionDefaultSchedulerJob.class;
			isDefaultJob = false;
		}else{
			throw new ScorpionBaseException("scorpion-4907:Unknown Scorpion scheduler job type !");
		}
		
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public Class<? extends AbsScorpionSchedulerJob> getJob() {
		return job;
	}

	public int getPolicyType() {
		return policyType;
	}

	public void setPolicyType(int policyType) {
		this.policyType = policyType;
	}

	public boolean isSimpleType() {
		return isSimpleType;
	}

	public void setSimpleType(boolean isSimpleType) {
		this.isSimpleType = isSimpleType;
	}

	public String getExecuteExpres() {
		return executeExpres;
	}

	public void setExecuteExpres(String executeExpres) {
		this.executeExpres = executeExpres;
	}

	public Date getValidTimeStart() {
		return validTimeStart;
	}

	public boolean isAllowRegisterSameService() {
		return allowRegisterSameService;
	}

	public void setAllowRegisterSameService(boolean allowRegisterSameService) {
		this.allowRegisterSameService = allowRegisterSameService;
	}

	public void setValidTimeStart(Date validTimeStart) {
		this.validTimeStart = validTimeStart;
	}

	public Date getValidTimeEnd() {
		return validTimeEnd;
	}

	public void setValidTimeEnd(Date validTimeEnd) {
		this.validTimeEnd = validTimeEnd;
	}

	public Integer getExecuteTimes() {
		return executeTimes;
	}

	public void setExecuteTimes(Integer executeTimes) {
		this.executeTimes = executeTimes;
	}
	
	public boolean isMemoryScheduler() {
		return isMemoryScheduler;
	}

	public void setMemoryScheduler(boolean isMemoryScheduler) {
		this.isMemoryScheduler = isMemoryScheduler;
	}

	public boolean isExecuteForever() {
		return isExecuteForever;
	}

	public void setExecuteForever(boolean isExecuteForever) {
		this.isExecuteForever = isExecuteForever;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Map<?, ?> getJobDataMap() {
		if(jobDataMap == null)
			jobDataMap = new HashMap<Object,Object>();
		return jobDataMap;
	}

	public void setJobDataMap(Map<Object, Object> jobDataMap) {
		
		if(this.jobDataMap == null)
			this.jobDataMap = new HashMap<Object,Object>();
		
		this.jobDataMap.put(Constant.CUSTOM_JOB_DATA, jobDataMap);
	}

	public boolean isDefaultJob() {
		return isDefaultJob;
	}
}
