package com.scorpion.huakerongtong.common.concurrentcpt;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.scorpion.huakerongtong.api.common.AbsScorpionConcurrentTask;
import com.scorpion.huakerongtong.api.common.AbsScorpionThreadGroup;
import com.scorpion.huakerongtong.api.common.AbsScorpionThreadPrimer;
import com.scorpion.huakerongtong.api.common.IConcurrentProcessor;
import com.scorpion.huakerongtong.api.common.IScorpionTaskGenerator;
import com.scorpion.huakerongtong.api.common.ScorpionTaskDataBus;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.common.lifecycle.ScorpionLifecycleManager;

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
public class ConcurrentTaskRegister {
	
	
	/**
	 * @param taskGenerator
	 * 
	 * @param processor
	 * 
	 * @param threadNum
	 * 
	 * @param entity
	 * 
	 * @param engineType
	 * 
	 * @param isNeedSyn
	 * 
	 * @throws ScorpionBaseException
	 */
	public final static void registerTask(IScorpionTaskGenerator taskGenerator,IConcurrentProcessor processor,int threadNum,ThreadPropertiesEntity entity,String engineType,boolean isNeedSyn) throws ScorpionBaseException{
		
		AbsScorpionThreadGroup engine = ThreadEngineFactory.getInstace().produceInstance(engineType,threadNum,entity,processor);
		List<AbsScorpionConcurrentTask> tasks = taskGenerator.generateTask();
		CountDownLatch latch = configTaskProperty(tasks,isNeedSyn);
		ScorpionLifecycleManager.registerLifecycleSingle(engine);
		ScorpionTaskDataBus.getDefaultInstance().put(tasks);
		
		if(isNeedSyn){
			try {
				latch.await();
			} catch (InterruptedException e) {
				throw new ScorpionBaseException("TSC-8009：System thread synchronize failure !",e);
			}
		}
	}
	
	
	
	/**
	 * @param primer
	 * 
	 * @param processor
	 * 
	 * @param iscycle
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws InterruptedException
	 */
	public final static void registerTask(AbsScorpionThreadPrimer primer,IConcurrentProcessor processor,boolean iscycle) throws ScorpionBaseException, InterruptedException{
		
		ExecutorService executor = Executors.newCachedThreadPool();
		primer.setDaemon(true);
		executor.submit(primer.setIscycle(iscycle).setProcessor(processor));
		executor.shutdown();
	}
	
	/**
	 * @param tasks
	 * @param isNeedSyn
	 */
	private static CountDownLatch configTaskProperty(List<AbsScorpionConcurrentTask> tasks,boolean isNeedSyn){
	
		CountDownLatch latch = null;
		
		if(isNeedSyn){
			latch= new CountDownLatch(tasks.size());
			
			for(AbsScorpionConcurrentTask task:tasks){
				task.setLatch(latch);
				task.setSyn(isNeedSyn);
			}
		}
		return latch;
	}
}
