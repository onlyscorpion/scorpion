package org.scorpion.common.concurrentcpt;

import org.scorpion.api.common.AbsTscpThreadPrimer;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.common.Lifecycle;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.log.PlatformLogger;

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

public class TscpThreadprocessor extends AbsTscpThreadPrimer{
	
	private Thread thread;
	
	
	/**
	 * @param processor
	 * 
	 * @param threadPropertiesEntity
	 */
	public TscpThreadprocessor(IConcurrentProcessor processor,ThreadPropertiesEntity threadPropertiesEntity) {
	
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
	public void destroy() throws TscpBaseException {
	
		Thread.currentThread().interrupt();
	
	}
	
	@Override
	public void handler() throws TscpBaseException {
	
		if(thread == null)
			throw new TscpBaseException("TSCP-0897:Initialize thread failure");
	
		thread.start();
	}

	@Override
	public void run() {
	
		try{
			super.handler();
		}catch(TscpBaseException e){
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
	public void initialization() throws TscpBaseException {
	}

}
