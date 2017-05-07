package org.scorpion.common.logfw.messagequeue;

import org.scorpion.api.common.AbsTscpConcurrentbus;
import org.scorpion.api.common.ILogMessage;
import org.scorpion.api.exception.TscpBaseException;

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
public class LogMessageQueue extends AbsTscpConcurrentbus<ILogMessage>{
	
	
	private static AbsTscpConcurrentbus<ILogMessage> instance;
	

	@Override
	public void validCheck() throws TscpBaseException {
		
	}
	
	
	/**
	 * @description generate queue instance
	 * 
	 * @return
	 */
	public synchronized static AbsTscpConcurrentbus<ILogMessage> getInstance(){
		
		if(instance == null)
			instance = new LogMessageQueue();
	
		return instance;
	}

}
