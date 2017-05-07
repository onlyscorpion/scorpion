package org.scorpion.api.kernel;

import java.io.Serializable;
import java.util.Map;
import java.util.Stack;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.persistence.ITscpPersistenceSession;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface ITscpGlobalSystemSession extends ITscpAppliactionSession, Serializable,Cloneable{

	
	public final String USER_ID = "tscpUserId";
	
	
	/**
	 * 
	 * @param tmpMap
	 */
	public void currentHandlerData(String key,Stack<Map<String,Object>> stack);
    
    
    /**
     * @return
     * @throws TscpBaseException
     */
    public ITscpPersistenceSession getDefaultPersistenceSession()throws TscpBaseException;
    
    /**
     * @return
     * @throws TscpBaseException
     */
    public Map<String,ITscpPersistenceSession>getOtherPersistenceSession()throws TscpBaseException;
    
	
}
