package org.scorpion.common.lifecycle;

import java.util.List;

import org.scorpion.api.common.Lifecycle;
import org.scorpion.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this factory class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionLifecycleManager {
	
	/**
	 * @param lifecycles
	 * @throws ScorpionBaseException
	 */
	public final static void registerLifecycleImplGroup(List<Lifecycle>lifecycles)throws ScorpionBaseException{
		
		for(Lifecycle lifecycle:lifecycles){
		
			registerLifecycleSingle(lifecycle);
		   
		}
	}
	
	/**
	 * @param lifecycle
	 * @throws ScorpionBaseException
	 */
	public final static void registerLifecycleSingle(Lifecycle lifecycle)throws ScorpionBaseException{
		
		lifecycle.initialization();
		
		lifecycle.handler(); 
		
	}
	
}
