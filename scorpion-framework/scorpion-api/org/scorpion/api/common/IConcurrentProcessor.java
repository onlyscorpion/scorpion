package org.scorpion.api.common;

import org.scorpion.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java createtime:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this fatory class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface IConcurrentProcessor {
	
	
	/**
	 * @description 处理因子
	 * 
	 * @param task
	 * 
	 * @Time 2015-06-01
	 * 
	 * @throws ScorpionBaseException
	 */
	public void processor(AbsScorpionConcurrentTask task)throws ScorpionBaseException;
	
	
	/**
	 * @description 处理因子
	 * 
	 * @throws ScorpionBaseException
	 */
	public void processor()throws ScorpionBaseException;
	

}
