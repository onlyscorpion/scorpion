package org.scorpion.api.common;

import org.scorpion.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface Lifecycle {
	
	

	/**
	 * @description Be use to initialization configuration information
	 * 
	 * @throws ScorpionBaseException
	 * 
	 */
	public void initialization() throws ScorpionBaseException;

	/**
	 * @description Developer handle the core business by this method.
	 * 
	 * @throws ScorpionBaseException
	 * 
	 */
	public void handler()throws ScorpionBaseException;
	
	/**
	 * 
	 * @description When this method is called it means the end of the life cycle. the developer can logout some system 
	 * 
	 *  information through this method
	 *  
	 * @throws ScorpionBaseException
	 * 
	 */
	public void destroy()throws ScorpionBaseException;
	
	
	

}
