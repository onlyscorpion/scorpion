package org.scorpion.api.kernel;

import java.io.Serializable;
import java.util.Map;
import java.util.Stack;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.persistence.IScorpionPersistenceSession;

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
public interface IScorpionGlobalSystemSession extends IScorpionAppliactionSession, Serializable,Cloneable{

	
	public final String USER_ID = "ScorpionUserId";
	
	
	/**
	 * 
	 * @param tmpMap
	 */
	public void currentHandlerData(String key,Stack<Map<String,Object>> stack);
    
    
    /**
     * @return
     * @throws ScorpionBaseException
     */
    public IScorpionPersistenceSession getDefaultPersistenceSession()throws ScorpionBaseException;
    
    /**
     * @return
     * @throws ScorpionBaseException
     */
    public Map<String,IScorpionPersistenceSession>getOtherPersistenceSession()throws ScorpionBaseException;
    
	
}
