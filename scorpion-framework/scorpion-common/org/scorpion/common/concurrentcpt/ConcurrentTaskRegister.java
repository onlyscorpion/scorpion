package org.scorpion.common.concurrentcpt;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.scorpion.api.common.AbsTscpConcurrentTask;
import org.scorpion.api.common.AbsTscpThreadGroup;
import org.scorpion.api.common.AbsTscpThreadPrimer;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.common.ITscpTaskGenerator;
import org.scorpion.api.common.TscpTaskDataBus;
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
	 * @throws TscpBaseException
	 */
	public final static void registerTask(ITscpTaskGenerator taskGenerator,IConcurrentProcessor processor,int threadNum,ThreadPropertiesEntity entity,String engineType,boolean isNeedSyn) throws TscpBaseException{
		
		AbsTscpThreadGroup engine = ThreadEngineFactory.getInstace().produceInstance(engineType,threadNum,entity,processor);
		List<AbsTscpConcurrentTask> tasks = taskGenerator.generateTask();
		CountDownLatch latch = configTaskProperty(tasks,isNeedSyn);
		TscpLifecycleManager.registerLifecycleSingle(engine);
		TscpTaskDataBus.getDefaultInstance().put(tasks);
		
		if(isNeedSyn){
			try {
				latch.await();
			} catch (InterruptedException e) {
				throw new TscpBaseException("TSC-8009：System thread synchronize failure !",e);
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
	 * @throws TscpBaseException
	 * 
	 * @throws InterruptedException
	 */
	public final static void registerTask(AbsTscpThreadPrimer primer,IConcurrentProcessor processor,boolean iscycle) throws TscpBaseException, InterruptedException{
		
		ExecutorService executor = Executors.newCachedThreadPool();
		primer.setDaemon(true);
		executor.submit(primer.setIscycle(iscycle).setProcessor(processor));
		executor.shutdown();
	}
	
	/**
	 * @param tasks
	 * @param isNeedSyn
	 */
	private static CountDownLatch configTaskProperty(List<AbsTscpConcurrentTask> tasks,boolean isNeedSyn){
	
		CountDownLatch latch = null;
		
		if(isNeedSyn){
			latch= new CountDownLatch(tasks.size());
			
			for(AbsTscpConcurrentTask task:tasks){
				task.setLatch(latch);
				task.setSyn(isNeedSyn);
			}
		}
		return latch;
	}
}
