package org.scorpion.api.persistence;
/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface IScorpionConnectionPoolMonitor {
	
	/**
	 * @description return current system is using connection number
	 * 
	 * @return
	 */
	public int getCurrentActiveConnectionNum();
	
	/**
	 * @description return system can get max connection number ...
	 * 
	 * @return
	 */
	public int getMaxConnectionNum();
	
	/**
	 * @description get connection max idle time ....
	 * 
	 * @return
	 */
	public int getMaxIdle();
	
	/**
	 * @description get database type ...
	 * 
	 * @return
	 */
	public int getDBType();
	
	
	
	

}
