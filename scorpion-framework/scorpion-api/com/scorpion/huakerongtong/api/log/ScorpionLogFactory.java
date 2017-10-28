package com.scorpion.huakerongtong.api.log;

import com.scorpion.huakerongtong.api.common.AbsScorpionFactory;
import com.scorpion.huakerongtong.api.common.ScorpionLogger;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;


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
public class ScorpionLogFactory extends AbsScorpionFactory<ScorpionLogger> {
	
	private static ScorpionLogFactory factory = new ScorpionLogFactory();

	
	@Override
	public ScorpionLogger produceInstance(Object... arg) throws ScorpionBaseException {
	
		if(arg == null||arg.length<1)
			throw new ScorpionBaseException("scorpion-9007:入参类型不能为空!");
	
		if(arg[0] == java.lang.String.class)
			return new ScorpionLoggerImpl((String)arg[0]);
		
		else if(arg[0] instanceof Class)
			return new ScorpionLoggerImpl((Class<?>)arg[0]);
		
		else
			throw new ScorpionBaseException("scorpion-8976:入参类型不正确！");
	
	}

	@Override
	public ScorpionLogger produceInstance() throws ScorpionBaseException {
		return null;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 * @throws ScorpionBaseException
	 */
	public static ScorpionLogger getLogger(Class<?> clazz){
	
		try{
		
			return factory.produceInstance(clazz,null);
		
		}catch(Throwable e){
		
			e.printStackTrace();
	     
			return null;
	
		}
	}
	
	/**
	 * 
	 * @param className
	 * @return
	 * @throws ScorpionBaseException
	 */
	public static ScorpionLogger getLogger(String className){
	
		try{
	
			return factory.produceInstance(className);
	
		}catch(ScorpionBaseException e){
		
			e.printStackTrace();
		
			return null;
		
		}
	}

	@Override
	public <P> P produceInstance(Class<P> clazz) throws ScorpionBaseException {
		try {
			return clazz.newInstance();
		} catch (Throwable ex) {
			throw new ScorpionBaseException(ex);
		}
	}
	
	
}
