package org.scorpion.scheduler.server;

import java.util.Map;

import org.quartz.SchedulerException;
import org.scorpion.api.common.IScorpionScheduler;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionComponent;
import org.scorpion.api.log.PlatformLogger;

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
//@Component(name="ScorpionSchedulerComponent",iscorecomponent = false,sequence=49)
public class ScorpionSchedulerComponent extends AbsScorpionComponent{

	
	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {
		
		try {
			PlatformLogger.info("Is beginning to load Scorpion scheduler component !");
			
			IScorpionScheduler scheduler = ScorpionDefaultScheduler.getDefaultInstance();
			
			((ScorpionSchedulerRegister)ScorpionSchedulerRegister.getDefaultInstance()).registerContext(scheduler, new ScorpionSchedulerJobGenerator());
			
			scheduler.start();
			
			PlatformLogger.info("Loading Scorpion scheduler completed !");
			
		} catch (SchedulerException e) {
		     throw new ScorpionBaseException("scorpion-4869：Starting Scorpion scheduler failure !",e);
		}
		
	}

}
