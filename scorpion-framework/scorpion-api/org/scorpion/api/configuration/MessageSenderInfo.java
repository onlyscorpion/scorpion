package org.scorpion.api.configuration;

import java.io.Serializable;

import org.scorpion.api.kernel.IBaseMessageSender;

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
public class MessageSenderInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Class<?> clazz;
	
	private String protocolType;
	
	private IBaseMessageSender sender;
	
	private boolean isStandardSender;

	

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClassName(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public boolean isStandardSender() {
		return isStandardSender;
	}

	public void setStandardSender(boolean isStandardSender) {
		this.isStandardSender = isStandardSender;
	}

	public IBaseMessageSender getSender() {
		return sender;
	}

	public void setSender(IBaseMessageSender sender) {
		this.sender = sender;
	}
	
	
}
