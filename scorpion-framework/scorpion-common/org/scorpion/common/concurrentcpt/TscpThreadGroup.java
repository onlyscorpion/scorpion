package org.scorpion.common.concurrentcpt;

import org.scorpion.api.common.AbsTscpThreadGroup;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.common.lifecycle.TscpLifecycleManager;

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
public class TscpThreadGroup extends AbsTscpThreadGroup{
	
	private IConcurrentProcessor processor;
	
	private int threadnum;
	
	private ThreadPropertiesEntity tpe;
	
	private TscpThreadGroup(){}

	@Override
	public void threadColletionInitialization() throws TscpBaseException {
		
		for(int i=0;i<threadnum;i++)
		
			this.workthreadgroup.add(new TscpThreadprocessor(processor,tpe).newThread());
	
	}

	@Override
	public void handler() throws TscpBaseException {
		
		TscpLifecycleManager.registerLifecycleImplGroup(workthreadgroup);;
	
	}
	
	public static TscpThreadGroup getNewInstace(){
	
		return new TscpThreadGroup();
	
	}
	
	
	/**
	 * 
	 * @param threadnum
	 * 
	 * @param tpe
	 * 
	 * @param processor
	 * 
	 * @return
	 */
	public TscpThreadGroup setGroupProperty(int threadnum,ThreadPropertiesEntity tpe,IConcurrentProcessor processor) {
	
		this.threadnum = threadnum;
	
		this.processor = processor;
	
		this.tpe = tpe;
	
		return this;
	
	}
}
