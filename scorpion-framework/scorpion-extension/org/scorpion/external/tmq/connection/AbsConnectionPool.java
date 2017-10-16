package org.scorpion.external.tmq.connection;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

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
public abstract class AbsConnectionPool<K> {
	
	protected Map<String,LinkedBlockingQueue<K>> freePool;
	
	protected List<MQDataSource> datasoruces;
	
	protected int INIT_NUM = 5;

	protected int NEXT_INCREMENT = 1;
	
	protected int MAX_NUM = 10;
	
	protected final int TIME_OUT = 30000;
	
	protected final int FREE_TIME = 300000;
	
	protected Map<String,AtomicInteger> CURRENT_NUM = new ConcurrentHashMap<String, AtomicInteger>();
	
	public Map<String,Long> version;
	
	//链接回收模式 
	protected String CONNECTION_RESOURCE_MODEL = "0";

	/**
	 * @throws ConnectionPoolException
	 */
	public abstract void initPool() throws ConnectionPoolException;

	/**
	 * @throws ConnectionPoolException
	 */
	public abstract void reloadPool() throws ConnectionPoolException;

	/**
	 * @throws ConnectionPoolException
	 */
	public abstract void distoryPool() throws ConnectionPoolException ;
	/**
	 * @param key
	 * @param k
	 * @throws ConnectionPoolException
	 */
	public abstract void distoryConnection(String key,K k) throws ConnectionPoolException; 
	
	/**
	 * @return
	 * @throws ConnectionPoolException
	 */
	public abstract K getConnection(String key) throws ConnectionPoolException;
	
	/**
	 * @return
	 */
	public int getFreeDeep(String key) {
		return freePool.get(key).size();
	}
	
	
	/**
	 * 重建连接
	 * @return
	 * @throws ConnectionPoolException
	 */
	public abstract K rebuildConntion(K k,String connName,Exception e,int waitTime,int tryNum,long version) throws ConnectionPoolException;

	
	/**
	 * @param deep
	 * @throws Exception 
	 * @throws  
	 */
	public synchronized void initConnQueue(String key,Integer maxSize) throws Exception {
		if(freePool == null)
			freePool = new ConcurrentHashMap<String, LinkedBlockingQueue<K>>();
		freePool.put(key, new LinkedBlockingQueue<K>(maxSize));
		CURRENT_NUM.put(key, new AtomicInteger(0));
	}
	
    /**
     * @param k
     * @throws ConnectionPoolException
     */
	public abstract void close(String key,K k) throws ConnectionPoolException;
	
	/**
	 * @throws ConnectionPoolException
	 */
	public void resource()throws ConnectionPoolException{/*
		 new Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				if(freePool == null||freePool.size()<1)
					return;
				for(Entry<String,LinkedBlockingQueue<K>> entry:freePool.entrySet()){
					logger.info("["+entry.getKey()+"]当前空闲连接数为["+entry.getValue().size()+"]总池化数["+CURRENT_NUM.get(entry.getKey())+"]");
					for(K conn:entry.getValue()){
					if((((IESBConncetion)conn).isFree())&&(System.currentTimeMillis()-((IESBConncetion)conn).getInitDate().getTime())>TIME_OUT){
						try {
							distoryConnection(entry.getKey(),conn);
						} catch (ConnectionPoolException e) {
							 e.printStackTrace();
						}
					  }
					}
				}
			}
		}, 0, 30000);
	
	*/};
	
	/**
	 * @throws ConnectionPoolException
	 */
	public abstract void extendPool(String key)throws ConnectionPoolException;

	public List<MQDataSource> getDatasoruces() {
		return datasoruces;
	}

	public void setDatasoruces(List<MQDataSource> datasoruces) {
		this.datasoruces = datasoruces;
	}
	
}
