package com.scorpion.huakerongtong.external.tmq;

import java.io.Serializable;
import java.util.List;

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
public class MQConfEntity implements Serializable{
	

	private static final long serialVersionUID = -7425510782755184018L;

	private String brokerName;
	
	private String url;
	
	private boolean isLoad;
	
	private String marshal;
	
	private String persistent;
	
	private String useJmx;
	
	private boolean enable;
	
	private List<String> queues;
	
	

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isLoad() {
		return isLoad;
	}

	public void setLoad(boolean isLoad) {
		this.isLoad = isLoad;
	}

	public String getMarshal() {
		return marshal;
	}

	public void setMarshal(String marshal) {
		this.marshal = marshal;
	}

	public String getPersistent() {
		return persistent;
	}

	public void setPersistent(String persistent) {
		this.persistent = persistent;
	}

	public String getUseJmx() {
		return useJmx;
	}

	public void setUseJmx(String useJmx) {
		this.useJmx = useJmx;
	}

	public List<String> getQueues() {
		return queues;
	}

	public void setQueues(List<String> queues) {
		this.queues = queues;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public boolean isThemeExist(String theme){
		
		if(queues == null||theme == null)
			return false;
		
		for(String queue:queues)
			if(theme.equals(queue))
				return true;
		
		return false;
	}

}
