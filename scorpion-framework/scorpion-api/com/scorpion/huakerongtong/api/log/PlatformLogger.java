package com.scorpion.huakerongtong.api.log;


import com.scorpion.huakerongtong.api.common.AbsScorpionBaseException;
import com.scorpion.huakerongtong.api.common.ScorpionLogger;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.ApplicationContext;

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
public class PlatformLogger{
	
	private static ScorpionLogger logger = ScorpionLogFactory.getLogger(PlatformLogger.class);
	
	public static boolean isShowServiceCall = true;
	
	
	/**
	 * @param message
	 * 
	 * @param throwable
	 */
	public static void debug(String message,Throwable throwable){
		
		logger.debug(message,throwable);
	}
	
	
	/**
	 * 
	 * @param message
	 */
	public static void debug(Throwable throwable){
		
		logger.debug(throwable);
	}
	
	/**
	 * 
	 * @param message
	 */
	public static void debug(String message){
		
		logger.debug(message);
	}
	
	
	public static void warn(String message,Throwable throwable){
		
		logger.warn(message, throwable);
	}
	
	public static void warn(String message){
		logger.warn("###################################################");
		logger.warn(message);
		logger.warn("###################################################");

	}
	
	public static void warn(Throwable throwable){
		logger.warn("###################################################");
		logger.warn(throwable);
		logger.warn("###################################################");
	}
	
	/**
	 * 
	 * @param message
	 * 
	 * @param throwable
	 */
	public static void info(String message,Throwable throwable){
	
		logger.info(message, throwable);
	}
	
	
	/**
	 * 
	 * @param message
	 */
	public static void info(String message){
		
		logger.info(message);
	}
	
	
	/**
	 * 
	 * @param message
	 */
	public static void info(Throwable throwable){
		
		logger.info(throwable);
	}
	
	
	/**
	 * @param message
	 * @param throwable
	 */
	public static void error(String message,Throwable throwable){
		logger.error("###################################################");
		logger.error(message,throwable);
		logger.error("###################################################");

	}
	
	
	/**
	 * 
	 * @param message
	 */
	public static void error(String message){
		logger.error("###################################################");
		logger.error(message);
		logger.error("###################################################");
	}
	
	/**
	 * 
	 * @param message
	 */
	public static void error(Throwable throwable){
		logger.error("###################################################");
		logger.error(throwable);
		logger.error("###################################################");
	}
	
	
	/**
	 * @description print detail stack information 
	 * 
	 * @param throwable
	 */
	public static Throwable detailStack(Throwable throwable,ApplicationContext context,String sessionId){
		
		Throwable th ;
		if(throwable instanceof AbsScorpionBaseException){
			((AbsScorpionBaseException) throwable).addNode(context.getSystemCoreConfig().getNodeName());
			((AbsScorpionBaseException) throwable).setSessionId(sessionId);
			((AbsScorpionBaseException) throwable).setCurrentNode(context.getSystemCoreConfig().getNodeName());
			th = throwable;
		}else{
			AbsScorpionBaseException exception = new ScorpionBaseException(throwable);
			exception.setExceptionPointNode(context.getSystemCoreConfig().getNodeName());
			exception.setCurrentNode(context.getSystemCoreConfig().getNodeName());
			exception.addNode(context.getSystemCoreConfig().getNodeName());
			exception.setSessionId(sessionId);
			th = exception;
		}
		throwable.printStackTrace();
		return th;
		
	}
	
	public static void dumpStack(String message,Thread thread){
		logger.error("Scorpion.xml the name of component can't be null !");
		for(int i=thread.getStackTrace().length-1;i>=0;i--){
			logger.error(thread.getStackTrace()[i]);
		}
	}
	
}
