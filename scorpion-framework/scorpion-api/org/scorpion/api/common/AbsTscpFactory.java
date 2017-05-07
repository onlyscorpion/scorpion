package org.scorpion.api.common;

import org.scorpion.api.exception.TscpBaseException;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this factory class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsTscpFactory<T> {
	
	   /**
	    * 
	    * @param arg
	    * @return
	    * @throws TscpBaseException
	    */
	   public abstract T produceInstance(Object... arg) throws TscpBaseException;
	   
	   /**
	    * @param clazz
	    * @return
	    * @throws TscpBaseException
	    */
	   public abstract <P> P produceInstance(Class<P> clazz) throws TscpBaseException;
	   
	   /**
	    * 
	    * @return
	    * @throws TscpBaseException
	    */
	   public abstract T produceInstance() throws TscpBaseException;
	   
}
