package org.scorpion.scheduler.server;

import java.util.Map;

import org.scorpion.api.common.AbsTscpSchedulerJob;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.kernel.util.TscpServiceUtil;

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
public class TscpDefaultSchedulerJob extends AbsTscpSchedulerJob{

	@Override
	public void processor(Map<?, ?> args) {
		
		if(args.get(Constant.SERVICE_NAME) == null||"".equals(args.get(Constant.SERVICE_NAME))){
			PlatformLogger.warn("Nothing to do !");
			return ;
		}
	
		try {
			
			PlatformLogger.info("The tscp scheduler is beginning to execute scheduler job. The job NAME is["+args.get(Constant.JOB_NAME)+"]"
					+ " and job group is["+args.get(Constant.JOB_GROUP)+"]");
			
			if(args.get(Constant.CUSTOM_JOB_DATA) != null){
				TscpServiceUtil.callService((String)args.get(Constant.SERVICE_NAME), args.get(Constant.CUSTOM_JOB_DATA));
			}else{
				TscpServiceUtil.callService((String)args.get(Constant.SERVICE_NAME));
			}
			PlatformLogger.info("The job ["+args.get(Constant.JOB_NAME)+"]  group ["+args.get(Constant.JOB_GROUP)+"] execute completed !");
		} catch (TscpBaseException e) {
				PlatformLogger.error("TSCP-4490:Tscp scheduler call service ["+args.get(Constant.SERVICE_NAME)+"] exception !",e);
		}
	}

}
