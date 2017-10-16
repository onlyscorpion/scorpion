package org.scorpion.scheduler.server;

import java.util.Map;

import org.scorpion.api.common.AbsScorpionSchedulerJob;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.kernel.util.ScorpionServiceUtil;

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
public class ScorpionDefaultSchedulerJob extends AbsScorpionSchedulerJob{

	@Override
	public void processor(Map<?, ?> args) {
		
		if(args.get(Constant.SERVICE_NAME) == null||"".equals(args.get(Constant.SERVICE_NAME))){
			PlatformLogger.warn("Nothing to do !");
			return ;
		}
	
		try {
			
			PlatformLogger.info("The Scorpion scheduler is beginning to execute scheduler job. The job NAME is["+args.get(Constant.JOB_NAME)+"]"
					+ " and job group is["+args.get(Constant.JOB_GROUP)+"]");
			
			if(args.get(Constant.CUSTOM_JOB_DATA) != null){
				ScorpionServiceUtil.callService((String)args.get(Constant.SERVICE_NAME), args.get(Constant.CUSTOM_JOB_DATA));
			}else{
				ScorpionServiceUtil.callService((String)args.get(Constant.SERVICE_NAME));
			}
			PlatformLogger.info("The job ["+args.get(Constant.JOB_NAME)+"]  group ["+args.get(Constant.JOB_GROUP)+"] execute completed !");
		} catch (ScorpionBaseException e) {
				PlatformLogger.error("scorpion-4490:Scorpion scheduler call service ["+args.get(Constant.SERVICE_NAME)+"] exception !",e);
		}
	}

}
