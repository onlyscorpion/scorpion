package org.scorpion.common.concurrentcpt;

import org.scorpion.api.common.AbsTscpFactory;
import org.scorpion.api.common.AbsTscpThreadGroup;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.configuration.SystemEnumType;
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
public class ThreadEngineFactory extends AbsTscpFactory<AbsTscpThreadGroup>{
	
	private static AbsTscpFactory<AbsTscpThreadGroup> engineFactory = getInstace();
	
    private void  argumentVaildCheck(Object... arg) throws TscpBaseException{
      
    	if(arg == null||arg.length<1) throw new TscpBaseException("TSC-9006:引擎构造参数缺失!");
      
    	if(!(arg[1] instanceof Integer)) throw new TscpBaseException("TSC-6009:引擎构造参数类型不匹配！");
      
    	if(!(arg[2] instanceof ThreadPropertiesEntity)) throw new TscpBaseException("TSC-6010:引擎构造参数类型不匹配！");
   
    }	

	@Override
	public AbsTscpThreadGroup produceInstance(Object... arg) throws TscpBaseException {
	
		argumentVaildCheck(arg);
	
		if(SystemEnumType.threadgroup.getValue().equals(arg[0])){
			return TscpThreadGroup.getNewInstace().setGroupProperty((Integer)arg[1],(ThreadPropertiesEntity)arg[2],(IConcurrentProcessor)arg[3]);	   
		
		}else if(SystemEnumType.threadpool.getValue().equals(arg[0])){
			return null;
		
		}else{
			throw new TscpBaseException("TSCP-9757:Construct thread group failure !");
		}
	}

	@Override
	public AbsTscpThreadGroup produceInstance() throws TscpBaseException {
		return null;
	}
	
	
	/**
	 * return thread engine factory 
	 * 
	 * @return AbsTscpFactory
	 */
	public synchronized static AbsTscpFactory<AbsTscpThreadGroup> getInstace(){
		
		if(engineFactory == null)
			engineFactory = new ThreadEngineFactory();
	
		return engineFactory;
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
