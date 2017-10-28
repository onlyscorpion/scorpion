package com.scorpion.huakerongtong.api.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;


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
public abstract class AbsMedaitionPool<T extends AbsMediationFactor> implements IMediationPool<T>{
	
	
	private long timeout;
	
	private long checkCycleTime;
	
	private boolean enableCheck; 
	
	protected Map<String,LinkedBlockingQueue<T>> freeQueue = new HashMap<String,LinkedBlockingQueue<T>>();
	
	protected Map<String,LinkedBlockingQueue<T>> busyQueue = new HashMap<String,LinkedBlockingQueue<T>>();
	
	
	/**
	 * 
	 * @param protocol
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws InterruptedException
	 */
	public abstract void dynamicAddProtocol(AbsProtocol protocol) throws ScorpionBaseException, InterruptedException;
	
	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getCheckCycleTime() {
		return checkCycleTime;
	}

	public void setCheckCycleTime(long checkCycleTime) {
		this.checkCycleTime = checkCycleTime;
	}

	public boolean isEnableCheck() {
		return enableCheck;
	}

	public void setEnableCheck(boolean enableCheck) {
		this.enableCheck = enableCheck;
	}



	/**
	 * 
	 * @throws ScorpionBaseException
	 */
	public void statusCheck() throws ScorpionBaseException{
		
		if(!enableCheck)
			return;
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
			  synchronized (freeQueue) {
				  for(LinkedBlockingQueue<T> queue:freeQueue.values()){
					  Collection<AbsMediationFactor> factors = new ArrayList<AbsMediationFactor>();
				  for(AbsMediationFactor factor:queue){
						if(factor.isActivity())
							continue;
						if((System.currentTimeMillis()-factor.getLastActivity().getTime())>=factor.getDataValidData()){
							factors.add(factor);
						}
					}
				  queue.removeAll(factors);
			  }
			  }
			}
		}, checkCycleTime);
	}

	
	
	/**
	 * @description Create queue information
	 * 
	 * @param ejbProtocolConf
	 * 
	 * @throws InterruptedException
	 * @throws ScorpionBaseException 
	 */
	protected void createQueue(String key,AbsProtocol protocolConf) throws InterruptedException, ScorpionBaseException{
		
		LinkedBlockingQueue<T> fqueue = new LinkedBlockingQueue<T>(protocolConf.getMaxConnNum());
		LinkedBlockingQueue<T> bqueue = new LinkedBlockingQueue<T>(protocolConf.getMaxConnNum());
	    
		for(int i = 0;i<protocolConf.getMaxConnNum();i++){
			fqueue.put(getMediation(protocolConf));
		}
	
		this.freeQueue.put(protocolConf.getProtocolId(), fqueue);
		this.busyQueue.put(protocolConf.getProtocolId(), bqueue);
	}
	
	
	public boolean isInit(String key){
		return (this.freeQueue.containsKey(key)||this.busyQueue.containsKey(key));
	}
	


	@Override
	public void clear() {
		//TODO
	}


	@Override
	public synchronized T getMediation(String protocolId,AbsProtocol absProtocol) throws InterruptedException, ScorpionBaseException {
		
		if(this.freeQueue.get(protocolId) == null&&absProtocol == null)
			throw new ScorpionBaseException("scorpion-4067:The protocol id ["+protocolId+"] mapped protocol configuration not exist, please check route.xml !");
		
		if((this.freeQueue.get(protocolId) == null||this.freeQueue.get(protocolId).size()==0)&&(absProtocol != null)){
			this.freeQueue.put(protocolId, new LinkedBlockingQueue<T>());
		}
		
		if(this.freeQueue.get(protocolId).size()<=0&&(this.freeQueue.get(protocolId).size()+this.busyQueue.size()) <= absProtocol.getMaxConnNum()){
			this.freeQueue.get(protocolId).put(getMediation(absProtocol));
		}
		
		T factor = this.freeQueue.get(protocolId).poll(30, TimeUnit.SECONDS);
		
		if(factor == null){
			
			if(this.freeQueue.get(protocolId).size()+ this.freeQueue.get(protocolId).size()==0)
				throw new ScorpionBaseException("scorpion-6043:Connection don't initialize,current connection num["+(this.freeQueue.get(protocolId).size()+ this.freeQueue.get(protocolId).size())+"]"
						+ "maxsize ["+absProtocol.getMaxConnNum()+"] initsize ["+absProtocol.getInitConnNum()+"] nextSize ["+absProtocol.getNextConnNum()+"]");
		
			throw new ScorpionBaseException("scorpion-8097：CAN'T GET CONNECTION ，PROTOCOL ID["+protocolId+"] !");
		}

		if(this.busyQueue.get(protocolId) == null)
			this.busyQueue.put(protocolId, new LinkedBlockingQueue<T>(freeQueue.size()));
	
		this.busyQueue.get(protocolId).put(factor);
		return factor;
	}

	public Map<String, LinkedBlockingQueue<T>> getFreeQueue() {
		return freeQueue;
	}

	public void setFreeQueue(Map<String, LinkedBlockingQueue<T>> freeQueue) {
		this.freeQueue = freeQueue;
	}

	public Map<String, LinkedBlockingQueue<T>> getBusyQueue() {
		return busyQueue;
	}

	public void setBusyQueue(Map<String, LinkedBlockingQueue<T>> busyQueue) {
		this.busyQueue = busyQueue;
	}
	
	/**
	 * @param protocol
	 * 
	 * @return
	 */
	public abstract T getMediation(AbsProtocol protocol)throws ScorpionBaseException;
	
	
	@Override
	public void clear(String key) {
	
		if(this.freeQueue.containsKey(key))
			this.freeQueue.get(key).clear();
	
		if(this.busyQueue.containsKey(key))
			this.busyQueue.get(key).clear();
	}

	
}
