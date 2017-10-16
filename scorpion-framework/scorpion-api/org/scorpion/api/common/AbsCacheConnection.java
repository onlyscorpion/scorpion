package org.scorpion.api.common;

import java.io.Serializable;
import java.util.Date;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsCacheConnection<T> implements IScorpionConnection<T>,Serializable{
	
	private static final long serialVersionUID = 1439563620230193230L;

	private String url;
	
	private int port;
	
	private Date createTime;
	
	private int refreshTime;
	
	protected IScorpionConnectionPool<IScorpionConnection<T>> pool;
	
	
	/**
	 * check connection status.....
	 */
	protected abstract void refreshConnection();

	
	public String getUrl() {
		return url;
	}

	public IScorpionConnection<T> setUrl(String url) {
		this.url = url;
		return this;
	}
	
	public IScorpionConnection<T> setConnInfo(String url,int port,IScorpionConnectionPool<IScorpionConnection<T>> pool){
		this.url = url;
		this.port = port;
		this.pool = pool;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(int refreshTime) {
		this.refreshTime = refreshTime;
	}

	public int getPort() {
		return port;
	}

	public IScorpionConnection<T> setPort(int port) {
		this.port = port;
		return this;
	}
	


}
