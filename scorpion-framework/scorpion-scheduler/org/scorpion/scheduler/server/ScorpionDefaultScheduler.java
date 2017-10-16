package org.scorpion.scheduler.server;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;
import org.scorpion.api.common.IScorpionScheduler;

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
public class ScorpionDefaultScheduler implements IScorpionScheduler{
	
	
	private static IScorpionScheduler ScorpionScheduler ;
	
	private Scheduler scheduler;
	
	private ScorpionDefaultScheduler() throws SchedulerException {
		super();
		scheduler = StdSchedulerFactory.getDefaultScheduler();
	}

	@Override
	public String getSchedulerName() throws SchedulerException {
		return scheduler.getSchedulerName();
	}

	@Override
	public String getSchedulerInstanceId() throws SchedulerException {
		return scheduler.getSchedulerInstanceId();
	}

	@Override
	public SchedulerContext getContext() throws SchedulerException {
		return scheduler.getContext();
	}

	@Override
	public void start() throws SchedulerException {
		scheduler.start();
	}

	@Override
	public void startDelayed(int seconds) throws SchedulerException {
		scheduler.startDelayed(seconds);
	}

	@Override
	public boolean isStarted() throws SchedulerException {
		return scheduler.isStarted();
	}

	@Override
	public void standby() throws SchedulerException {
		scheduler.standby();
	}

	@Override
	public boolean isInStandbyMode() throws SchedulerException {
		return scheduler.isInStandbyMode();
	}

	@Override
	public void shutdown() throws SchedulerException {
		scheduler.shutdown();
	}

	@Override
	public void shutdown(boolean waitForJobsToComplete)throws SchedulerException {
		scheduler.shutdown(waitForJobsToComplete);
	}

	@Override
	public boolean isShutdown() throws SchedulerException {
		return scheduler.isShutdown();
	}

	@Override
	public SchedulerMetaData getMetaData() throws SchedulerException {
		return scheduler.getMetaData();
	}

	@Override
	public List<JobExecutionContext> getCurrentlyExecutingJobs()throws SchedulerException {
		return scheduler.getCurrentlyExecutingJobs();
	}

	@Override
	public void setJobFactory(JobFactory factory) throws SchedulerException {
		scheduler.setJobFactory(factory);
	}

	@Override
	public ListenerManager getListenerManager() throws SchedulerException {
		return scheduler.getListenerManager();
	}

	@Override
	public Date scheduleJob(JobDetail jobDetail, Trigger trigger)throws SchedulerException {
		return scheduler.scheduleJob(jobDetail, trigger);
	}

	@Override
	public Date scheduleJob(Trigger trigger) throws SchedulerException {
		return scheduler.scheduleJob(trigger);
	}

	@Override
	public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs,boolean replace) throws SchedulerException {
		scheduler.scheduleJobs(triggersAndJobs, replace);
	}

	@Override
	public void scheduleJob(JobDetail jobDetail,Set<? extends Trigger> triggersForJob, boolean replace)
			throws SchedulerException {
		scheduler.scheduleJob(jobDetail, triggersForJob, replace);
	}

	@Override
	public boolean unscheduleJob(TriggerKey triggerKey)throws SchedulerException {
		return scheduler.unscheduleJob(triggerKey);
	}

	@Override
	public boolean unscheduleJobs(List<TriggerKey> triggerKeys)throws SchedulerException {
		return scheduler.unscheduleJobs(triggerKeys);
	}

	@Override
	public Date rescheduleJob(TriggerKey triggerKey, Trigger newTrigger)throws SchedulerException {
		return scheduler.rescheduleJob(triggerKey, newTrigger);
	}

	@Override
	public void addJob(JobDetail jobDetail, boolean replace)throws SchedulerException {
		scheduler.addJob(jobDetail, replace);
	}

	@Override
	public void addJob(JobDetail jobDetail, boolean replace,boolean storeNonDurableWhileAwaitingScheduling)
			throws SchedulerException {
		scheduler.addJob(jobDetail, replace, storeNonDurableWhileAwaitingScheduling);
	}

	@Override
	public boolean deleteJob(JobKey jobKey) throws SchedulerException {
		return scheduler.deleteJob(jobKey);
	}

	@Override
	public boolean deleteJobs(List<JobKey> jobKeys) throws SchedulerException {
		return scheduler.deleteJobs(jobKeys);
	}

	@Override
	public void triggerJob(JobKey jobKey) throws SchedulerException {
		scheduler.triggerJob(jobKey);
	}

	@Override
	public void triggerJob(JobKey jobKey, JobDataMap data)throws SchedulerException {
		scheduler.triggerJob(jobKey, data);
	}

	@Override
	public void pauseJob(JobKey jobKey) throws SchedulerException {
		scheduler.pauseJob(jobKey);
	}

	@Override
	public void pauseJobs(GroupMatcher<JobKey> matcher)throws SchedulerException {
		scheduler.pauseJobs(matcher);
	}

	@Override
	public void pauseTrigger(TriggerKey triggerKey) throws SchedulerException {
		scheduler.resumeTrigger(triggerKey);
	}

	@Override
	public void pauseTriggers(GroupMatcher<TriggerKey> matcher)throws SchedulerException {
		scheduler.resumeTriggers(matcher);
	}

	@Override
	public void resumeJob(JobKey jobKey) throws SchedulerException {
		scheduler.resumeJob(jobKey);
	}

	@Override
	public void resumeJobs(GroupMatcher<JobKey> matcher)throws SchedulerException {
		scheduler.resumeJobs(matcher);
	}

	@Override
	public void resumeTrigger(TriggerKey triggerKey) throws SchedulerException {
		scheduler.resumeTrigger(triggerKey);
	}

	@Override
	public void resumeTriggers(GroupMatcher<TriggerKey> matcher)throws SchedulerException {
		scheduler.resumeTriggers(matcher);
	}

	@Override
	public void pauseAll() throws SchedulerException {
		scheduler.pauseAll();
	}

	@Override
	public void resumeAll() throws SchedulerException {
		scheduler.resumeAll();
	}

	@Override
	public List<String> getJobGroupNames() throws SchedulerException {
		return scheduler.getJobGroupNames();
	}

	@Override
	public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher)throws SchedulerException {
		return scheduler.getJobKeys(matcher);
	}

	@Override
	public List<? extends Trigger> getTriggersOfJob(JobKey jobKey)throws SchedulerException {
		return scheduler.getTriggersOfJob(jobKey);
	}

	@Override
	public List<String> getTriggerGroupNames() throws SchedulerException {
		return scheduler.getTriggerGroupNames();
	}

	@Override
	public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher)throws SchedulerException {
		return scheduler.getTriggerKeys(matcher);
	}

	@Override
	public Set<String> getPausedTriggerGroups() throws SchedulerException {
		return scheduler.getPausedTriggerGroups();
	}

	@Override
	public JobDetail getJobDetail(JobKey jobKey) throws SchedulerException {
		return scheduler.getJobDetail(jobKey);
	}

	@Override
	public Trigger getTrigger(TriggerKey triggerKey) throws SchedulerException {
		return scheduler.getTrigger(triggerKey);
	}

	@Override
	public TriggerState getTriggerState(TriggerKey triggerKey)throws SchedulerException {
		return scheduler.getTriggerState(triggerKey);
	}

	@Override
	public void addCalendar(String calName, Calendar calendar, boolean replace,boolean updateTriggers) throws SchedulerException {
		scheduler.addCalendar(calName, calendar, replace, updateTriggers);
	}

	@Override
	public boolean deleteCalendar(String calName) throws SchedulerException {
		return scheduler.deleteCalendar(calName);
	}

	@Override
	public Calendar getCalendar(String calName) throws SchedulerException {
		return scheduler.getCalendar(calName);
	}

	@Override
	public List<String> getCalendarNames() throws SchedulerException {
		return scheduler.getCalendarNames();
	}

	@Override
	public boolean interrupt(JobKey jobKey)throws UnableToInterruptJobException {
		return scheduler.interrupt(jobKey);
	}

	@Override
	public boolean interrupt(String fireInstanceId)throws UnableToInterruptJobException {
		return scheduler.interrupt(fireInstanceId);
	}

	@Override
	public boolean checkExists(JobKey jobKey) throws SchedulerException {
		return scheduler.checkExists(jobKey);
	}

	@Override
	public boolean checkExists(TriggerKey triggerKey) throws SchedulerException {
		return scheduler.checkExists(triggerKey);
	}

	@Override
	public void clear() throws SchedulerException {
		scheduler.clear();
	}

	@Override
	public void destroyAllServices() {
		
	}

	@Override
	public void printAllRegisterInfo() {
		
	}
	
	
	/**
	 * @description Generate default scheduler instance ...
	 * 
	 * @return
	 * 
	 * @throws SchedulerException 
	 */
	public synchronized static IScorpionScheduler getDefaultInstance() throws SchedulerException{
		
		if(ScorpionScheduler == null)
			ScorpionScheduler = new ScorpionDefaultScheduler();
		
		return ScorpionScheduler;
	}

}
