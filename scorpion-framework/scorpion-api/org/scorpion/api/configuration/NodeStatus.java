package org.scorpion.api.configuration;

import java.util.Date;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class NodeStatus {
	
	private  String nodeName;
	
	private String ip;
	
	private boolean isRunning;
	
	private Date systemStartTime;
	
	private Long systemstartcost;

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Date getSystemStartTime() {
		return systemStartTime;
	}

	public void setSystemStartTime(Date systemStartTime) {
		this.systemStartTime = systemStartTime;
	}

	public Long getSystemstartcost() {
		return systemstartcost;
	}

	public void setSystemstartcost(Long systemstartcost) {
		this.systemstartcost = systemstartcost;
	}
	
}
