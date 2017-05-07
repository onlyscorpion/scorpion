package org.scorpion.api.common;

import java.util.Date;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.log.PlatformLogger;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this factory class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsTscpThreadPrimer implements Runnable, Lifecycle {
	
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

	public AbsTscpThreadPrimer setIscycle(boolean iscycle) {
		this.iscycle = iscycle;
		return this;
	}

	
	/**
	 * @description handler the business of the user define
	 * @throws TscpBaseException
	 */
	public void handler()throws TscpBaseException{
	 
		if(processor == null)
			throw new TscpBaseException("TSC-9989:引擎处理单元不能为空!");
		
		if(iscycle)
			cycleTaskExecute();
		else
			singleTackExecute();
	}
	
	
	private void cycleTaskExecute(){
		while(true){
			AbsTscpConcurrentTask task = null;
			try{
				task = TscpTaskDataBus.getDefaultInstance().get(timeout);
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
				PlatformLogger.error("TSCP-7005:线程执行异常",e);
			}finally{
				if(task != null&& task.isSyn())
					task.getLatch().countDown();
			}
		}
	}
	
	
	public void singleTackExecute() throws TscpBaseException{
		processor.processor();
	}
	

	public AbsTscpThreadPrimer setProcessor(IConcurrentProcessor processor) {
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
		}catch(TscpBaseException e){
			PlatformLogger.error(e);
		}
	}
	
}
