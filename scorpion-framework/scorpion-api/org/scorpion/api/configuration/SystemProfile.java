package org.scorpion.api.configuration;

import java.io.Serializable;

import org.scorpion.api.util.Constant;

import sun.misc.VM;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: store jvm information. Developer can get jvm of the system </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class SystemProfile implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String hostName;
	private String user;
	private String ip;
	private String pid;
	private int applicationState = Constant.STOP;
	private String jdkversion;
	private String jdkname;
	private long preGemSize;
	private long preGemMaxSize;
	private long unHeapMinSize;
	private long unHeadMaxSize;
	private long unHeadCommited;
	private long XmxSize;
	private long XmxMaxSize;
	private String systemDefaultClassPath;
	private String userDefaultClassPath;

	public String getJdkversion() {
		return jdkversion;
	}

	public void setJdkversion(String jdkversion) {
		this.jdkversion = jdkversion;
	}

	public long getPreGemSize() {
		return preGemSize;
	}

	public void setPreGemSize(long preGemSize) {
		this.preGemSize = preGemSize;
	}

	public long getPreGemMaxSize() {
		return preGemMaxSize;
	}

	public void setPreGemMaxSize(long preGemMaxSize) {
		this.preGemMaxSize = preGemMaxSize;
	}

	public long getXmxSize() {
		return XmxSize;
	}

	public void setXmxSize(long xmxSize) {
		XmxSize = xmxSize;
	}

	public long getXmxMaxSize() {
		return XmxMaxSize;
	}

	public void setXmxMaxSize(long xmxMaxSize) {
		XmxMaxSize = xmxMaxSize;
	}

	public String getSystemDefaultClassPath() {
		return systemDefaultClassPath;
	}

	public void setSystemDefaultClassPath(String systemDefaultClassPath) {
		this.systemDefaultClassPath = systemDefaultClassPath;
	}

	public String getUserDefaultClassPath() {
		return userDefaultClassPath;
	}

	public void setUserDefaultClassPath(String userDefaultClassPath) {
		this.userDefaultClassPath = userDefaultClassPath;
	}

	public String getJdkname() {
		return jdkname;
	}

	public void setJdkname(String jdkname) {
		this.jdkname = jdkname;
	}

	public long getUnHeapMinSize() {
		return unHeapMinSize;
	}

	public void setUnHeapMinSize(long unHeapMinSize) {
		this.unHeapMinSize = unHeapMinSize;
	}

	public long getUnHeadMaxSize() {
		return unHeadMaxSize;
	}

	public void setUnHeadMaxSize(long unHeadMaxSize) {
		this.unHeadMaxSize = unHeadMaxSize;
	}

	public long getUnHeadCommited() {
		return unHeadCommited;
	}

	public void setUnHeadCommited(long unHeadCommited) {
		this.unHeadCommited = unHeadCommited;
	}

	public int getAvailableProcessors() {
		return Runtime.getRuntime().availableProcessors();
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOperatingSystem() {
		   return System.getProperty("os.name");
		}
	
	public String getOperatingSystemPatch(){
			return System.getProperty("sun.os.patch.level");
	}
	
	public String getOSArch() {
		return System.getProperty("os.arch");
	}
	
	public String getOperatingSystemVersion(){
		return System.getProperty("os.version");
	}
	
	public long getMaxDirectMemory() {
		return VM.maxDirectMemory();
	}

	public int getApplicationState() {
		return applicationState;
	}

	public void setApplicationState(int applicationState) {
		this.applicationState = applicationState;
	}
	
}
