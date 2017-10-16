package org.scorpion.api.common;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsProtocol {
	

	private long connTimeOut;
	
	private long callTimeOut;
	
	private AtomicInteger currentConnNum = new AtomicInteger(0);
	
	private int initConnNum =3;
	
	private int maxConnNum = 6;
	
	private int nextConnNum =2;
	
	private String initType;
	
	public abstract String getProtocolId();
	
	
	public String getInitType() {
		return initType;
	}


	public void setInitType(String initType) {
		this.initType = initType;
	}


	public AtomicInteger getCurrentConnNum() {
		return currentConnNum;
	}

	public void setCurrentConnNum(AtomicInteger currentConnNum) {
		this.currentConnNum = currentConnNum;
	}

	public long getConnTimeOut() {
		return connTimeOut;
	}

	public void setConnTimeOut(long connTimeOut) {
		this.connTimeOut = connTimeOut;
	}

	public long getCallTimeOut() {
		return callTimeOut;
	}

	public void setCallTimeOut(long callTimeOut) {
		this.callTimeOut = callTimeOut;
	}

	public int getInitConnNum() {
		return initConnNum;
	}

	public void setInitConnNum(int initConnNum) {
		this.initConnNum = initConnNum;
	}

	public int getMaxConnNum() {
		return maxConnNum;
	}

	public void setMaxConnNum(int maxConnNum) {
		this.maxConnNum = maxConnNum;
	}

	public int getNextConnNum() {
		return nextConnNum;
	}

	public void setNextConnNum(int nextConnNum) {
		this.nextConnNum = nextConnNum;
	}

}
