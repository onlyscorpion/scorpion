package com.scorpion.huakerongtong.api.common;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this factory class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsScorpionFactory<T> {
	
	   /**
	    * 
	    * @param arg
	    * @return
	    * @throws ScorpionBaseException
	    */
	   public abstract T produceInstance(Object... arg) throws ScorpionBaseException;
	   
	   /**
	    * @param clazz
	    * @return
	    * @throws ScorpionBaseException
	    */
	   public abstract <P> P produceInstance(Class<P> clazz) throws ScorpionBaseException;
	   
	   /**
	    * 
	    * @return
	    * @throws ScorpionBaseException
	    */
	   public abstract T produceInstance() throws ScorpionBaseException;
	   
}
