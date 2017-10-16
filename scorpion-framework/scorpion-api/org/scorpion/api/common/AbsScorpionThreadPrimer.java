package org.scorpion.api.common;

import java.util.Date;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.log.PlatformLogger;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this factory class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsScorpionThreadPrimer implements Runnable, Lifecycle {
	
	protected IConcurrentProcessor processor;
	
	private boolean isDead;
	
	private long timeout;
	
	private String threadPrefixName;
	
	private boolean isDaemon;
	
	private boolean iscycle = false;

	
	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
	public String getThreadPrefixName() {
		return threadPrefixName;
	}

	public void setThreadPrefixName(String threadPrefixName) {
		this.threadPrefixName = threadPrefixName;
	}

	public boolean isDaemon() {
		return isDaemon;
	}

	public void setDaemon(boolean isDaemon) {
		this.isDaemon = isDaemon;
	}
	

	public boolean isIscycle() {
		return iscycle;
	}

	public AbsScorpionThreadPrimer setIscycle(boolean iscycle) {
		this.iscycle = iscycle;
		return this;
	}

	
	/**
	 * @description handler the business of the user define
	 * @throws ScorpionBaseException
	 */
	public void handler()throws ScorpionBaseException{
	 
		if(processor == null)
			throw new ScorpionBaseException("TSC-9989:引擎处理单元不能为空!");
		
		if(iscycle)
			cycleTaskExecute();
		else
			singleTackExecute();
	}
	
	
	private void cycleTaskExecute(){
		while(true){
			AbsScorpionConcurrentTask task = null;
			try{
				task = ScorpionTaskDataBus.getDefaultInstance().get(timeout);
				if(task != null){
					task.setStartTime(new Date());
					processor.processor(task);
					task.setEndTime(new Date());
				}else{
					this.isDead = true;
					this.destroy();
					break;
				}
			}catch(Exception e){
				if(task != null){
					task.setErrorinfo(task.new TaskException().setMessage(e.getMessage()).setStackTraceElements(e.getStackTrace()));
					task.setEndTime(new Date());
				}
				PlatformLogger.error("scorpion-7005:线程执行异常",e);
			}finally{
				if(task != null&& task.isSyn())
					task.getLatch().countDown();
			}
		}
	}
	
	
	public void singleTackExecute() throws ScorpionBaseException{
		processor.processor();
	}
	

	public AbsScorpionThreadPrimer setProcessor(IConcurrentProcessor processor) {
		this.processor = processor;
		return this;
	}
	

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	@Override
	public void run() {
		try{
			handler();
		}catch(ScorpionBaseException e){
			PlatformLogger.error(e);
		}
	}
	
}
