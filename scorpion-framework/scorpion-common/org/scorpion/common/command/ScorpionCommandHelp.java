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
public class ScorpionCommandHelp extends AbsCommandExecutor{
	
	
	@Override
	protected boolean analyseCommand() throws ScorpionBaseException{
	
		if(!Constant.HELP.equals(command.toUpperCase()))
			return false;
		
		return super.analyseCommand();
	}

	
	

	@Override
	protected void processor() throws ScorpionBaseException {
		System.out.println("*************************************************");
		System.out.println("* 命令名称              选项                            描述                                                                *");
		System.out.println("* rls                   重新加载SQL配置                                       *");
		System.out.println("* cpo                   创建PO信息                                                  *");
		System.out.println("* rsp                   重新启动持久层                                            *");
		System.out.println("* ls                    重新加载服务                                                *");
		System.out.println("* la                    重新加载ACTION             *");
		System.out.println("* psi                   打印系统信息                                                *");
		System.out.println("* ssi                   打印服务加载信息                                        *");
		System.out.println("* sai                   打印ACTION加载信息                              *");
		System.out.println("* help                  打印HELP信息                                            *");
		System.out.println("*************************************************");

	}

}
