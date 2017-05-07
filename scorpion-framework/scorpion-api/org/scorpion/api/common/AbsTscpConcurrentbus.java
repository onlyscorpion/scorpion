package org.scorpion.api.common;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.scorpion.api.exception.TscpBaseException;

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

public abstract class AbsTscpConcurrentbus<T> {
	
	/** deep of the queue **/
	private int queueDeep = 100;
	
	private LinkedBlockingQueue<T> bus = new LinkedBlockingQueue<T>(queueDeep);
	
	
	/**
	 * @description Developer can get task by this method
	 * 
	 * @param timeout timeout mean that if the queue is empty the thread need wait to get the data
	 * 
	 * @return
	 * 
	 * @throws InterruptedException
	 */
	public T get(long timeout) throws InterruptedException{
	
		return this.bus.poll(timeout, TimeUnit.SECONDS);
	
	}
	
	/**
	 * @Description The thread will get data from queue.  if the queue is empty the thread will be block
	 * @return
	 * @throws InterruptedException
	 */
	public T get() throws InterruptedException{
	
		return this.bus.take();
	
	}	
	
	/**
	 * @Description Put data into the queue
	 * @param t
	 * @throws TscpBaseException
	 */
	public void put(T t) throws TscpBaseException{
	
		try{
	
			this.bus.put(t);
	
		}catch(InterruptedException e){
	
			throw new TscpBaseException(e);
	
		}
	}
	
	/**
	 * @param ts
	 * @throws TscpBaseException
	 */
	public void put(List<T> ts) throws TscpBaseException{
	
		for(T t: ts)
		
			put(t);
	
	}


	/**
	 * @description Configure the queue deep
	 * 
	 * @param queueDeep
	 */
	public void setQueueDeep(int queueDeep) {
	
		this.queueDeep = queueDeep;
	
	}
	
	/**
	 * @description The task which stored queue have a valid time. When it timeout then need developer handler those task or the queue will be blocked
	 * 
	 * @throws TscpBaseException
	 */
	public abstract void validCheck()throws TscpBaseException;
	
	
	
	
    
	
	
	
	

}
