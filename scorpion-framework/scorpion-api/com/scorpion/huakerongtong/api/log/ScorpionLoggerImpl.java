package com.scorpion.huakerongtong.api.log;

import org.apache.log4j.Logger;
import com.scorpion.huakerongtong.api.common.ScorpionLogger;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: system constant information </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionLoggerImpl implements ScorpionLogger{
	
	private final Logger logger;
	
    ScorpionLoggerImpl(Class<?> clazz){
		logger = org.apache.log4j.Logger.getLogger(clazz);
	}
	
    ScorpionLoggerImpl(String className){
		logger = org.apache.log4j.Logger.getLogger(className);
	}

	@Override
	public void debug(Object obj) {
		logger.debug(obj);
	}

	@Override
	public void debug(Object obj, Throwable throwable) {
		logger.debug(obj,throwable);
	}

	@Override
	public void info(Object obj) {
		logger.info(obj);
		//logger.info(defaultLogProcessor(obj,null));
	}

	@Override
	public void info(Object obj, Throwable throwable) {
		logger.info(obj,throwable);
	}

	@Override
	public void warn(Object obj) {
		//logger.warn(defaultLogProcessor(obj,null));
		logger.warn(obj);
	}

	@Override
	public void warn(Object obj, Throwable throwable) {
		//logger.warn(defaultLogProcessor(obj,throwable),throwable);
		logger.warn(obj,throwable);
	}

	@Override
	public void error(Object obj) {
		logger.error(obj);
		//logger.error(defaultLogProcessor(obj,null));
	}

	@Override
	public void error(Object obj, Throwable throwable) {
	/*	StackTraceElement[] stackTraces = throwable.getStackTrace();
		for(StackTraceElement stack:stackTraces)
			System.out.println(stack);*/
		logger.error(obj,throwable);
		//logger.error(defaultLogProcessor(obj,throwable),throwable);
	}

	@Override
	public void setLogLevel(String level) {
		
	}
	
	
/*	private Object defaultLogProcessor(Object obj,Throwable throwable){
		 return null;
	}*/

	@Override
	public void debug(Object... obj) {
		logger.debug(obj);
	}

	@Override
	public void info(Object... obj) {
		// TODO Auto-generated method stub
		logger.info(obj);
	}

	@Override
	public void warn(Object... ojb) {
		// TODO Auto-generated method stub
		logger.warn(ojb);
	}

	@Override
	public void error(Object... obj) {
		// TODO Auto-generated method stub
		logger.error(obj);
	}
	

}
