package org.scorpion.common.concurrentcpt;

import org.scorpion.api.common.AbsScorpionThreadGroup;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.common.lifecycle.ScorpionLifecycleManager;

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
public class ScorpionThreadGroup extends AbsScorpionThreadGroup{
	
	private IConcurrentProcessor processor;
	
	private int threadnum;
	
	private ThreadPropertiesEntity tpe;
	
	private ScorpionThreadGroup(){}

	@Override
	public void threadColletionInitialization() throws ScorpionBaseException {
		
		for(int i=0;i<threadnum;i++)
		
			this.workthreadgroup.add(new ScorpionThreadprocessor(processor,tpe).newThread());
	
	}

	@Override
	public void handler() throws ScorpionBaseException {
		
		ScorpionLifecycleManager.registerLifecycleImplGroup(workthreadgroup);;
	
	}
	
	public static ScorpionThreadGroup getNewInstace(){
	
		return new ScorpionThreadGroup();
	
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
	public ScorpionThreadGroup setGroupProperty(int threadnum,ThreadPropertiesEntity tpe,IConcurrentProcessor processor) {
	
		this.threadnum = threadnum;
	
		this.processor = processor;
	
		this.tpe = tpe;
	
		return this;
	
	}
}
