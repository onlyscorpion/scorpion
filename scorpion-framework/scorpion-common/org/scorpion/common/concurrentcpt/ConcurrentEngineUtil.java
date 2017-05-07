package org.scorpion.common.concurrentcpt;

import java.util.List;

import org.scorpion.api.common.AbsTscpThreadPrimer;
import org.scorpion.api.common.IConcurrentProcessor;
import org.scorpion.api.common.ITscpConurrentEngine;
import org.scorpion.api.common.ITscpTaskGenerator;
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
	  public final static ITscpConurrentEngine createConcurrentEngine(String taskname,int threadnum,String enginemode){
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
	    * @throws TscpBaseException
	    */
	   public final static ITscpConurrentEngine createConcurrentEngine(String taskname,int threadnum,String enginemode,ThreadPropertiesEntity threadPropertiesEntity) throws TscpBaseException{
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
	   private static ITscpConurrentEngine getEngineInstace(String taskname,final int threadnum,final String enginemode,final ThreadPropertiesEntity tpe){
		   
		    return new ITscpConurrentEngine() {
		    	
		    @Override
			public List<TscpBaseException> getRuntimeException()throws TscpBaseException {
				return null;
			}
			
			@Override
			public void getCurrentTaskProgress() throws TscpBaseException {
			}
			
			@Override
			public void fire(ITscpTaskGenerator taskGenerator, IConcurrentProcessor processor,boolean isneedsyn) throws TscpBaseException {
			    if(taskGenerator == null) throw new TscpBaseException("TSCP-1006：任务产生器不能为空");
			    if(processor == null) throw new TscpBaseException("TSCP-1007：核心处理业务实现");
			    ConcurrentTaskRegister.registerTask(taskGenerator,processor,threadnum,tpe,enginemode,isneedsyn);
			}

			@Override
			public void fire(AbsTscpThreadPrimer primer,IConcurrentProcessor processor) throws TscpBaseException {
				if(primer == null) throw new TscpBaseException("TSCP-1006：任务产生器不能为空");
				if(processor == null) throw new TscpBaseException("TSCP-1007：核心处理业务实现");
				try {
					ConcurrentTaskRegister.registerTask(primer,processor,false);
				} catch (InterruptedException e) {
					throw new TscpBaseException(e);
				}
			}
		    };
	   }

}
