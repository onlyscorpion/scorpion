package org.scorpion.common.command;

import java.lang.reflect.InvocationTargetException;

import org.scorpion.api.common.AbsCommandExecutor;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;

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
public class POGeneratorCommand extends AbsCommandExecutor{
	
	

	@Override
	protected boolean analyseCommand() throws ScorpionBaseException {
		
		if(!Constant.CPO.equals(this.command))
			return false;
		
		
		return super.analyseCommand();
	}

	@Override
	protected void processor() throws ScorpionBaseException {
		
		PlatformLogger.info("starting po generator ......");
		try {
			System.setProperty("user.web.env", this.getClass().getResource("/").getPath());
			Object obj = ((SystemContext)SystemContext.getApplicationContext()).getSystemClassLoader().loadClass("com.taiji.Scorpion.persistence.meta.PoGenerator").newInstance();
			try {
				((SystemContext)SystemContext.getApplicationContext()).getSystemClassLoader().loadClass("com.taiji.Scorpion.persistence.meta.PoGenerator").getMethod("generatorPo").invoke(obj);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
