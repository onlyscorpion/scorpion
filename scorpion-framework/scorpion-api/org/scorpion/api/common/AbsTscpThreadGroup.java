package org.scorpion.api.common;

import java.util.ArrayList;
import java.util.List;

import org.scorpion.api.exception.TscpBaseException;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java createtime:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this fatory class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsTscpThreadGroup implements Lifecycle{
	
	
	protected final int queueDeep = 100;
	
	protected final List<Lifecycle> workthreadgroup = new ArrayList<Lifecycle>(queueDeep);
	
	/**
	 * @description Initialize thread pool. cause the concurrent computing have two model. so there are two implement.
	 * 
	 * @throws TscpBaseException
	 */
	public abstract void threadColletionInitialization()throws TscpBaseException;


	@Override
	public void initialization() throws TscpBaseException {
	
		threadColletionInitialization();
	
	}

	@Override
	public void destroy() throws TscpBaseException {
	
		for(Lifecycle lifecycle:workthreadgroup)
			lifecycle.destroy();
	
	}

}
