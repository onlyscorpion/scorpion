package org.scorpion.common.logfw.engine;

import org.scorpion.api.common.ILogMessage;
import org.scorpion.api.common.IMessageProducer;
import org.scorpion.api.exception.TscpBaseException;

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
public class TscpLogEngineUtil {
	
	
	
	public static IMessageProducer producer; 
	
	
	
	/**
	 * 
	 * @param message
	 * 
	 * @throws TscpBaseException
	 */
	public static void putTextLog(String message)throws TscpBaseException{
		
		initializeValidate(producer);
		
		ILogMessage log = new TextMessageLog();
		
		log.putTextMessageLog(message);
		
		producer.init();
		
		try{
			producer.produce(log);
		}finally{
			producer.destroy();
		}
	}
	
	
	
	/**
	 * 
	 * @param message
	 * 
	 * @param theme
	 * 
	 * @throws TscpBaseException
	 */
	public static void putTextLog(String message,String theme)throws TscpBaseException{
		
		initializeValidate(producer);
		
		ILogMessage log = new TextMessageLog();
		
		log.putTextMessageLog(message);
	
		log.setQueueName(theme);
		
		producer.init();
		
		try{
			producer.produce(log);
		}finally{
			producer.destroy();
		}
		
	}
	

	/**
	 * 
	 * @param obj
	 * 
	 * @throws TscpBaseException
	 */
	public static void putObjectLog(Object objMessage)throws TscpBaseException{
		
		initializeValidate(producer);
		
		ILogMessage log = new ObjectMessageLog();
		
		log.putObjectMessageLog(objMessage);
		
		producer.init();
		
		try{
			producer.produce(log);
		}finally{
			//producer.destroy();
		}
		
	}
	
	
	
	/**
	 * 
	 * @param obj
	 * 
	 * @param queueName
	 * 
	 * @throws TscpBaseException
	 */
	public static void putObjectLog(Object objMessage,String theme)throws TscpBaseException{
		
		initializeValidate(producer);
		
		ILogMessage log = new ObjectMessageLog();
		
		log.putObjectMessageLog(objMessage);
	
		log.setQueueName(theme);
		
		producer.init();
		
		try{
			producer.produce(log);
		}finally{
			producer.destroy();
		}
		
	}
	
	
	/**
	 * 
	 * @param producer
	 * 
	 * @throws TscpBaseException
	 */
	private static void initializeValidate(IMessageProducer producer) throws TscpBaseException{
		
		if(producer == null)
			throw new TscpBaseException("TSCP-8946:Log framework don't initialize , Please check log framework configuration in tscp.xml !");
		
	}
	
	

}
