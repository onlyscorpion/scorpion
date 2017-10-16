package org.scorpion.common.logfw.engine;

import org.scorpion.api.common.ILogMessage;

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
public class ObjectMessageLog implements ILogMessage{
	
	private static final long serialVersionUID = -7887131943782070083L;

	private Object logMessage;
	
	private String queueName;

	@Override
	public void putTextMessageLog(String message) {
		// NOTHING TO DO
	}

	@Override
	public void putObjectMessageLog(Object obj) {
		this.logMessage = obj;
	}

	@Override
	public String getTextMessageLog() {
		return null;
	}

	@Override
	public Object getObjectMessageLog() {
		return logMessage;
	}

	@Override
	public String getCurrentQueueName() {
		return queueName;
	}

	@Override
	public String setQueueName(String qName) {
		this.queueName = qName;
		return queueName;
	}

	@Override
	public Object getMessage() {
		return this.logMessage;
	}

}
