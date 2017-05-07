package org.scorpion.scheduler.server;

import java.util.Map;

import org.quartz.SchedulerException;
import org.scorpion.api.common.ITscpScheduler;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpComponent;
import org.scorpion.api.log.PlatformLogger;

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
//@Component(name="TscpSchedulerComponent",iscorecomponent = false,sequence=49)
public class TscpSchedulerComponent extends AbsTscpComponent{

	
	@Override
	public void start(Map<String, String> arguments) throws TscpBaseException {
		
		try {
			PlatformLogger.info("Is beginning to load tscp scheduler component !");
			
			ITscpScheduler scheduler = TscpDefaultScheduler.getDefaultInstance();
			
			((TscpSchedulerRegister)TscpSchedulerRegister.getDefaultInstance()).registerContext(scheduler, new TscpSchedulerJobGenerator());
			
			scheduler.start();
			
			PlatformLogger.info("Loading tscp scheduler completed !");
			
		} catch (SchedulerException e) {
		     throw new TscpBaseException("TSCP-4869：Starting tscp scheduler failure !",e);
		}
		
	}

}
