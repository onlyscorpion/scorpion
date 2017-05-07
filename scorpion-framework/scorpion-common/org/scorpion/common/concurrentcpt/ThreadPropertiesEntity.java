package org.scorpion.common.concurrentcpt;

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
public class ThreadPropertiesEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String prefixname;
	
	private long timeout;
	
	private boolean isDaemon;
	
	private Thread thread;
	
	
	/**
	 * 
	 * @param prefixname
	 * 
	 * @param timeout
	 * 
	 * @param isDaemon
	 */
	public ThreadPropertiesEntity(String prefixname, long timeout,boolean isDaemon) {
		
		super();
		
		this.prefixname = prefixname;
		this.timeout = timeout;
		this.isDaemon = isDaemon;
	
	}
	
	/**
	 * 系统构造
	 * 
	 * @Time 2015-06-06
	 */
	public ThreadPropertiesEntity() {
	
		super();
	
	}

	public String getPrefixname() {
	
		return prefixname;
	
	}

	public void setPrefixname(String prefixname) {
	
		this.prefixname = prefixname;
	
	}

	public long getTimeout() {
	
		return timeout;
	
	}

	public void setTimeout(long timeout) {
	
		this.timeout = timeout;
	
	}

	public boolean isDaemon() {
	
		return isDaemon;
	
	}

	public void setDaemon(boolean isDaemon) {
	
		this.isDaemon = isDaemon;
	
	}

	public Thread getThread() {
	
		return thread;
	
	}

	
	public void setThread(Thread thread) {
	
		this.thread = thread;
	
	}
}
