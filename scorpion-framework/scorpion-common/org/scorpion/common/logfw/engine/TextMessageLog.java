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
public class TextMessageLog implements ILogMessage{
	
	private static final long serialVersionUID = 1822972075945996056L;

	private String logMessage;
	
	private String themeName;

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}


	@Override
	public void putTextMessageLog(String message) {
		this.logMessage = message;
		
	}

	@Override
	public void putObjectMessageLog(Object obj) {
		
		// nothing to do
		
	}

	@Override
	public String getTextMessageLog() {
		return this.logMessage;
	}

	@Override
	public Object getObjectMessageLog() {
		return null;
	}

	@Override
	public String getCurrentQueueName() {
		return themeName;
	}

	@Override
	public String setQueueName(String qName) {
		this.themeName = qName;
		return themeName;
	}

	@Override
	public Object getMessage() {
		return this.logMessage;
	}

}
