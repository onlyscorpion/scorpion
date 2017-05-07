package org.scorpion.external.tmq.connection;

import java.io.Serializable;

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
public class MQDataSource implements Serializable{
	
	private static final long serialVersionUID = 714335882870459875L;
	
	private String identity;

	private String connUrl;
	
	private int maxActive;
	
	private int maxIdle;
	
	private long maxWait = 10;
	
	private boolean isTryAgain;
	
	private int nextNum;
	
	private int initSize;
	
	private long validTime;
	
	private boolean isTempDataSource;
	
	private boolean isCacheConn;
	
	
	
	

	public MQDataSource() {
		super();
	}

	public int getInitSize() {
		return initSize;
	}

	public void setInitSize(int initSize) {
		this.initSize = initSize;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getConnUrl() {
		return connUrl;
	}

	public void setConnUrl(String connUrl) {
		this.connUrl = connUrl;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public long getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}

	public boolean isTryAgain() {
		return isTryAgain;
	}

	public void setTryAgain(boolean isTryAgain) {
		this.isTryAgain = isTryAgain;
	}

	public int getNextNum() {
		return nextNum;
	}


	public void setNextNum(int nextNum) {
		this.nextNum = nextNum;
	}

	public long getValidTime() {
		return validTime;
	}

	public void setValidTime(long validTime) {
		this.validTime = validTime;
	}

	public boolean isTempDataSource() {
		return isTempDataSource;
	}

	public void setTempDataSource(boolean isTempDataSource) {
		this.isTempDataSource = isTempDataSource;
	}

	public boolean isCacheConn() {
		return isCacheConn;
	}

	public void setCacheConn(boolean isCacheConn) {
		this.isCacheConn = isCacheConn;
	}
	
}
