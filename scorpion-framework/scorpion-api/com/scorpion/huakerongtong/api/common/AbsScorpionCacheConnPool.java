package com.scorpion.huakerongtong.api.common;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsScorpionCacheConnPool<T,E> implements IScorpionConnectionPool<T>{

	protected LinkedBlockingQueue<T> freeQueue;
	
	protected LinkedBlockingQueue<T> busyQueue;
	
	private int connNum;
	
	private Class<E> clazz;
	
	private String url;
	
	private int port;
	
	
	/**
	 * @param deep
	 */
	protected synchronized void initQueue(){
		
		if(connNum == 0){
			connNum = 15;
			freeQueue = new LinkedBlockingQueue<T>(connNum);
			busyQueue = new LinkedBlockingQueue<T>(connNum);
		}else{
			freeQueue = new LinkedBlockingQueue<T>(connNum);
			busyQueue = new LinkedBlockingQueue<T>(connNum);
		}
	}


	public int getConnNum() {
		return connNum;
	}

	public void setConnNum(int connNum) {
		this.connNum = connNum;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setConnInfo(String url,int port,Class<E> clazz){
		this.url = url;
		this.port = port;
		this.setClazz(clazz);
	}

	public Class<E> getClazz() {
		return clazz;
	}

	public void setClazz(Class<E> clazz) {
		this.clazz = clazz;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
	
}
