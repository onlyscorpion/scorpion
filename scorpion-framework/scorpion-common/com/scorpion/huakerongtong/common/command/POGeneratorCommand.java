package com.scorpion.huakerongtong.common.command;

import java.lang.reflect.InvocationTargetException;

import com.scorpion.huakerongtong.api.common.AbsCommandExecutor;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.common.context.SystemContext;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
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
			Object obj = ((SystemContext)SystemContext.getApplicationContext()).getSystemClassLoader().loadClass("com.SCORPION.Scorpion.persistence.meta.PoGenerator").newInstance();
			try {
				((SystemContext)SystemContext.getApplicationContext()).getSystemClassLoader().loadClass("com.SCORPION.Scorpion.persistence.meta.PoGenerator").getMethod("generatorPo").invoke(obj);
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
