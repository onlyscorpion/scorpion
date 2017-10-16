package org.scorpion.api.common;

import org.scorpion.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsCommandExecutor implements ICommandExecutor{

	
	protected String command;
	
	
	/**
	 * @description parser user input command information
	 * 
	 * @author 郑承磊
	 * 
	 * @Time 2015
	 * 
	 */
	protected boolean analyseCommand() throws ScorpionBaseException {
	
		this.processor();
		
		return true;
	}
	
	/**
	 * @description command execute method
	 * 
	 * @author 郑承磊
	 * 
	 * @Time 2015
	 */
	protected abstract void processor() throws ScorpionBaseException ;
	
	
	@Override
	public boolean execute(String command) throws ScorpionBaseException {
		
		if(command == null||"".equals(command))
			return true;
		
		this.command = command.toUpperCase().trim();
	
		return analyseCommand();
		
	}
	
	

}
