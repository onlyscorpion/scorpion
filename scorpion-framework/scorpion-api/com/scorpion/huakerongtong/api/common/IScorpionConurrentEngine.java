package com.scorpion.huakerongtong.api.common;

import java.util.List;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: IScorpionconcurrentEngine.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: concurrent computing engine</p>
 * <p>Description: generate the concurrent computing engine</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: concurrent computing</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface IScorpionConurrentEngine {
	
	
	/**
	 * @description Trigger task to start work ...
	 * 
	 * @param taskGenerator
	 * 
	 * @param processor
	 * 
	 * @throws ScorpionBaseException
	 */
	public void fire(IScorpionTaskGenerator taskGenerator,IConcurrentProcessor processor,boolean isneedsyn)throws ScorpionBaseException;
	
	
	/**
	 * @description 获取线程因子
	 * 
	 * @throws ScorpionBaseException
	 */
	public void getCurrentTaskProgress()throws ScorpionBaseException;
	
	/**
	 * @description 获取执行时异常
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public List<ScorpionBaseException> getRuntimeException()throws ScorpionBaseException;
	
	
	/**
	 * @param primer
	 * 
	 * @param processor
	 * 
	 * @throws ScorpionBaseException 
	 */
	public void fire(AbsScorpionThreadPrimer primer,IConcurrentProcessor processor) throws ScorpionBaseException;
	
	

}
