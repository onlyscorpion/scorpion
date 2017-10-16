package org.scorpion.common.logfw.messagequeue;

import org.scorpion.api.common.AbsScorpionConcurrentbus;
import org.scorpion.api.common.ILogMessage;
import org.scorpion.api.exception.ScorpionBaseException;

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
public class LogMessageQueue extends AbsScorpionConcurrentbus<ILogMessage>{
	
	
	private static AbsScorpionConcurrentbus<ILogMessage> instance;
	

	@Override
	public void validCheck() throws ScorpionBaseException {
		
	}
	
	
	/**
	 * @description generate queue instance
	 * 
	 * @return
	 */
	public synchronized static AbsScorpionConcurrentbus<ILogMessage> getInstance(){
		
		if(instance == null)
			instance = new LogMessageQueue();
	
		return instance;
	}

}
