package org.scorpion.common.command;

import org.scorpion.api.common.AbsCommandExecutor;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.util.Constant;

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
public class ScanServiceCommand extends AbsCommandExecutor{

	@Override
	protected boolean analyseCommand() throws ScorpionBaseException{
	
		if(!Constant.SSI.equals(command.toUpperCase()))
			return false;
		
		return super.analyseCommand();
	}
	
	
	@Override
	protected void processor() {
		// TODO Auto-generated method stub
		
	}

}
