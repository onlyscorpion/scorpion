package org.scorpion.common.concurrentcpt;

import java.util.List;

import org.scorpion.api.common.AbsScorpionThreadPrimer;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.common.IScorpionConurrentEngine;
import org.scorpion.api.common.IScorpionTaskGenerator;
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
public class ConcurrentEngineUtil {
	
	
	
	  /**
	   * 
	   * @param taskname
	   * 
	   * @param threadnum
	   * 
	   * @param enginemode
	   * 
	   * @return
	   */
	  public final static IScorpionConurrentEngine createConcurrentEngine(String taskname,int threadnum,String enginemode){
		  return getEngineInstace(taskname,threadnum,enginemode,null);
	  }
	
	  
	   /**
	    * @param taskname
	    * 
	    * @param threadnum
	    * 
	    * @param enginemode
	    * 
	    * @return
	    * 
	    * @throws ScorpionBaseException
	    */
	   public final static IScorpionConurrentEngine createConcurrentEngine(String taskname,int threadnum,String enginemode,ThreadPropertiesEntity threadPropertiesEntity) throws ScorpionBaseException{
		   return getEngineInstace(taskname,threadnum,enginemode,threadPropertiesEntity);
	   }
	   
	   
	   /**
	    * @param taskname
	    * 
	    * @param threadnum
	    * 
	    * @param enginemode
	    * 
	    * @param threadPropertiesEntity
	    * 
	    * @return
	    */
	   private static IScorpionConurrentEngine getEngineInstace(String taskname,final int threadnum,final String enginemode,final ThreadPropertiesEntity tpe){
		   
		    return new IScorpionConurrentEngine() {
		    	
		    @Override
			public List<ScorpionBaseException> getRuntimeException()throws ScorpionBaseException {
				return null;
			}
			
			@Override
			public void getCurrentTaskProgress() throws ScorpionBaseException {
			}
			
			@Override
			public void fire(IScorpionTaskGenerator taskGenerator, IConcurrentProcessor processor,boolean isneedsyn) throws ScorpionBaseException {
			    if(taskGenerator == null) throw new ScorpionBaseException("scorpion-1006：任务产生器不能为空");
			    if(processor == null) throw new ScorpionBaseException("scorpion-1007：核心处理业务实现");
			    ConcurrentTaskRegister.registerTask(taskGenerator,processor,threadnum,tpe,enginemode,isneedsyn);
			}

			@Override
			public void fire(AbsScorpionThreadPrimer primer,IConcurrentProcessor processor) throws ScorpionBaseException {
				if(primer == null) throw new ScorpionBaseException("scorpion-1006：任务产生器不能为空");
				if(processor == null) throw new ScorpionBaseException("scorpion-1007：核心处理业务实现");
				try {
					ConcurrentTaskRegister.registerTask(primer,processor,false);
				} catch (InterruptedException e) {
					throw new ScorpionBaseException(e);
				}
			}
		    };
	   }

}
