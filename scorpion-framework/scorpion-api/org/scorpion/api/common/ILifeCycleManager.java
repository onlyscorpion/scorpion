package org.scorpion.api.common;

import java.util.List;

import org.scorpion.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java createtime:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this fatory class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface ILifeCycleManager {
	

	/**
	 * @description Register group life cycle implement
	 * 
	 * @param lifecycles
	 * 
	 * @throws ScorpionBaseException
	 */
	public void registerLifecycleImplGroup(List<Lifecycle>lifecycles)throws ScorpionBaseException;
	
	
	/**
	 * @description Register life cycle implement
	 * 
	 * @param lifecycle
	 * 
	 * @throws ScorpionBaseException
	 */
	public void registerLifecycleSingle(Lifecycle lifecycle)throws ScorpionBaseException;

}
