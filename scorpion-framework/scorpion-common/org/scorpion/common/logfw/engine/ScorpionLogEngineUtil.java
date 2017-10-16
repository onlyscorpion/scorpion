package org.scorpion.common.logfw.engine;

import org.scorpion.api.common.ILogMessage;
import org.scorpion.api.common.IMessageProducer;
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
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionLogEngineUtil {
	
	
	
	public static IMessageProducer producer; 
	
	
	
	/**
	 * 
	 * @param message
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void putTextLog(String message)throws ScorpionBaseException{
		
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
	 * @throws ScorpionBaseException
	 */
	public static void putTextLog(String message,String theme)throws ScorpionBaseException{
		
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
	 * @throws ScorpionBaseException
	 */
	public static void putObjectLog(Object objMessage)throws ScorpionBaseException{
		
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
	 * @throws ScorpionBaseException
	 */
	public static void putObjectLog(Object objMessage,String theme)throws ScorpionBaseException{
		
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
	 * @throws ScorpionBaseException
	 */
	private static void initializeValidate(IMessageProducer producer) throws ScorpionBaseException{
		
		if(producer == null)
			throw new ScorpionBaseException("scorpion-8946:Log framework don't initialize , Please check log framework configuration in Scorpion.xml !");
		
	}
	
	

}
