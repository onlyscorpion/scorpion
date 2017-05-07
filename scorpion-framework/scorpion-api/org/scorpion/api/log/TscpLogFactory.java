package org.scorpion.api.log;

import org.scorpion.api.common.AbsTscpFactory;
import org.scorpion.api.common.TscpLogger;
import org.scorpion.api.exception.TscpBaseException;


/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: system constant information </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpLogFactory extends AbsTscpFactory<TscpLogger> {
	
	private static TscpLogFactory factory = new TscpLogFactory();

	
	@Override
	public TscpLogger produceInstance(Object... arg) throws TscpBaseException {
	
		if(arg == null||arg.length<1)
			throw new TscpBaseException("TSCP-9007:入参类型不能为空!");
	
		if(arg[0] == java.lang.String.class)
			return new TscpLoggerImpl((String)arg[0]);
		
		else if(arg[0] instanceof Class)
			return new TscpLoggerImpl((Class<?>)arg[0]);
		
		else
			throw new TscpBaseException("TSCP-8976:入参类型不正确！");
	
	}

	@Override
	public TscpLogger produceInstance() throws TscpBaseException {
		return null;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 * @throws TscpBaseException
	 */
	public static TscpLogger getLogger(Class<?> clazz){
	
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
	 * @throws TscpBaseException
	 */
	public static TscpLogger getLogger(String className){
	
		try{
	
			return factory.produceInstance(className);
	
		}catch(TscpBaseException e){
		
			e.printStackTrace();
		
			return null;
		
		}
	}

	@Override
	public <P> P produceInstance(Class<P> clazz) throws TscpBaseException {
		try {
			return clazz.newInstance();
		} catch (Throwable ex) {
			throw new TscpBaseException(ex);
		}
	}
	
	
}
