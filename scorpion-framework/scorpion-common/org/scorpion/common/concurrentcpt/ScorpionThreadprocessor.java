package org.scorpion.common.concurrentcpt;

import org.scorpion.api.common.AbsScorpionThreadPrimer;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.common.Lifecycle;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.log.PlatformLogger;

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

public class ScorpionThreadprocessor extends AbsScorpionThreadPrimer{
	
	private Thread thread;
	
	
	/**
	 * @param processor
	 * 
	 * @param threadPropertiesEntity
	 */
	public ScorpionThreadprocessor(IConcurrentProcessor processor,ThreadPropertiesEntity threadPropertiesEntity) {
	
		this.processor = processor;
		this.setThreadPrefixName(threadPropertiesEntity.getPrefixname());
		this.setDaemon(threadPropertiesEntity.isDaemon());
		this.setTimeout(threadPropertiesEntity.getTimeout());
		this.setIscycle(true);
	}
	
	
	/**
	 * @description System default configuration is that interrupt current running thread.
	 *
	 *  so suggest developer to define implements the destroy of yourself
	 */
	@Override
	public void destroy() throws ScorpionBaseException {
	
		Thread.currentThread().interrupt();
	
	}
	
	@Override
	public void handler() throws ScorpionBaseException {
	
		if(thread == null)
			throw new ScorpionBaseException("scorpion-0897:Initialize thread failure");
	
		thread.start();
	}

	@Override
	public void run() {
	
		try{
			super.handler();
		}catch(ScorpionBaseException e){
			PlatformLogger.error(e);
		}
	}

	
	/**
	 * @description 构造线程实例
	 * 
	 * @return
	 */
	public Lifecycle newThread() {
	
		thread = new Thread(this);
		thread.setName(this.getThreadPrefixName()+"线程["+thread.getName()+"]");
		thread.setDaemon(this.isDaemon());
		return this;
	}

	@Override
	public void initialization() throws ScorpionBaseException {
	}

}
