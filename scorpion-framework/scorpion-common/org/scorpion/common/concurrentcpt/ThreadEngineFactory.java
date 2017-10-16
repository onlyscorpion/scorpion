package org.scorpion.common.concurrentcpt;

import org.scorpion.api.common.AbsScorpionFactory;
import org.scorpion.api.common.AbsScorpionThreadGroup;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.configuration.SystemEnumType;
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
public class ThreadEngineFactory extends AbsScorpionFactory<AbsScorpionThreadGroup>{
	
	private static AbsScorpionFactory<AbsScorpionThreadGroup> engineFactory = getInstace();
	
    private void  argumentVaildCheck(Object... arg) throws ScorpionBaseException{
      
    	if(arg == null||arg.length<1) throw new ScorpionBaseException("TSC-9006:引擎构造参数缺失!");
      
    	if(!(arg[1] instanceof Integer)) throw new ScorpionBaseException("TSC-6009:引擎构造参数类型不匹配！");
      
    	if(!(arg[2] instanceof ThreadPropertiesEntity)) throw new ScorpionBaseException("TSC-6010:引擎构造参数类型不匹配！");
   
    }	

	@Override
	public AbsScorpionThreadGroup produceInstance(Object... arg) throws ScorpionBaseException {
	
		argumentVaildCheck(arg);
	
		if(SystemEnumType.threadgroup.getValue().equals(arg[0])){
			return ScorpionThreadGroup.getNewInstace().setGroupProperty((Integer)arg[1],(ThreadPropertiesEntity)arg[2],(IConcurrentProcessor)arg[3]);	   
		
		}else if(SystemEnumType.threadpool.getValue().equals(arg[0])){
			return null;
		
		}else{
			throw new ScorpionBaseException("scorpion-9757:Construct thread group failure !");
		}
	}

	@Override
	public AbsScorpionThreadGroup produceInstance() throws ScorpionBaseException {
		return null;
	}
	
	
	/**
	 * return thread engine factory 
	 * 
	 * @return AbsScorpionFactory
	 */
	public synchronized static AbsScorpionFactory<AbsScorpionThreadGroup> getInstace(){
		
		if(engineFactory == null)
			engineFactory = new ThreadEngineFactory();
	
		return engineFactory;
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
