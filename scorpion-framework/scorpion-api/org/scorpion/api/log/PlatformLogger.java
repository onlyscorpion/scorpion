package org.scorpion.api.log;


import org.scorpion.api.common.AbsTscpBaseException;
import org.scorpion.api.common.TscpLogger;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ApplicationContext;

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
public class PlatformLogger{
	
	private static TscpLogger logger = TscpLogFactory.getLogger(PlatformLogger.class);
	
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
		if(throwable instanceof AbsTscpBaseException){
			((AbsTscpBaseException) throwable).addNode(context.getSystemCoreConfig().getNodeName());
			((AbsTscpBaseException) throwable).setSessionId(sessionId);
			((AbsTscpBaseException) throwable).setCurrentNode(context.getSystemCoreConfig().getNodeName());
			th = throwable;
		}else{
			AbsTscpBaseException exception = new TscpBaseException(throwable);
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
		logger.error("tscp.xml the name of component can't be null !");
		for(int i=thread.getStackTrace().length-1;i>=0;i--){
			logger.error(thread.getStackTrace()[i]);
		}
	}
	
}
