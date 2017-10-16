package org.scorpion.api.common;

import org.quartz.Scheduler;

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
public interface IScorpionScheduler extends Scheduler{
	

	/**
	 * @Description destroy all services
	 * 
	 * @Time 2015
	 * 
	 * @author zcl
	 */
	public void destroyAllServices();
	
	
	/**
	 * @Description destroy all services
	 * 
	 * @Time 2015
	 * 
	 * @author zcl
	 */
	public void printAllRegisterInfo();

}
