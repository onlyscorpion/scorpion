package org.scorpion.common.util;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.scorpion.api.common.ICommandExecutor;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.common.command.ScorpionCommandHelp;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionCommand implements Runnable{
	
	
	public static Collection<ICommandExecutor> executors;

	
	public void run() {
	
		Scanner sc = new Scanner(System.in);
	
		try{
			while(true){
				PlatformLogger.info("请输入命令:");
				String command = sc.nextLine();
				boolean flag = true;
				for(ICommandExecutor executor:executors){
					try {
						if(executor.execute(command)){
							flag = false;
							break;
						}
					} catch (ScorpionBaseException e) {
						PlatformLogger.error(e);
					}
				}
				if(flag){
					try {
						PlatformLogger.error("输入命令有误，请查看一下命令参数：");
						ScorpionCommandHelp.class.newInstance().execute(Constant.HELP);
					}catch (Exception e) {
						PlatformLogger.error(e);
					} 
				}
			}
		}catch(NoSuchElementException e){
		}finally{
			sc.close();
		}
	}
	
}
