package org.scorpion.api.common;

import java.util.List;

import org.scorpion.api.exception.TscpBaseException;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: ITscpconcurrentEngine.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: concurrent computing engine</p>
 * <p>Description: generate the concurrent computing engine</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: concurrent computing</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface ITscpConurrentEngine {
	
	
	/**
	 * @description Trigger task to start work ...
	 * 
	 * @param taskGenerator
	 * 
	 * @param processor
	 * 
	 * @throws TscpBaseException
	 */
	public void fire(ITscpTaskGenerator taskGenerator,IConcurrentProcessor processor,boolean isneedsyn)throws TscpBaseException;
	
	
	/**
	 * @description 获取线程因子
	 * 
	 * @throws TscpBaseException
	 */
	public void getCurrentTaskProgress()throws TscpBaseException;
	
	/**
	 * @description 获取执行时异常
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public List<TscpBaseException> getRuntimeException()throws TscpBaseException;
	
	
	/**
	 * @param primer
	 * 
	 * @param processor
	 * 
	 * @throws TscpBaseException 
	 */
	public void fire(AbsTscpThreadPrimer primer,IConcurrentProcessor processor) throws TscpBaseException;
	
	

}
