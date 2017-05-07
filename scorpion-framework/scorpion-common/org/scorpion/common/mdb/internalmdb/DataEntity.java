package org.scorpion.common.mdb.internalmdb;

import java.io.Serializable;
import java.util.Date;

/**
 * 自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.tscp.common
 * <p>
 * File: AbsTscpFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 taiji.com.cn
 * </p>
 * <p>
 * Company: taiji.com.cn
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class DataEntity implements Serializable {

	private static final long serialVersionUID = 8840192901198662818L;

	private Object cacheData;

	private Date inputDate;

	private long validTime;

	private boolean persistent;

	private boolean needValidverify;

	public DataEntity() {
		inputDate = new Date();
		needValidverify = false;
		persistent = false;
	}

	public Object getCacheData() {
		return cacheData;
	}

	public void setCacheData(Object cacheData) {
		this.cacheData = cacheData;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public long getValidTime() {
		return validTime;
	}

	public void setValidTime(long validTime) {
		this.validTime = validTime;
	}

	public boolean isPersistent() {
		return persistent;
	}

	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}

	public boolean isNeedValidverify() {
		return needValidverify;
	}

	public void setNeedValidverify(boolean needValidverify) {
		this.needValidverify = needValidverify;
	}

}
