package org.scorpion.api.kernel;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;








import javax.servlet.http.HttpServletRequest;

import org.scorpion.api.common.IScorpionProtocal.ProtocolType;
import org.scorpion.api.exception.ScorpionBaseException;

/**
 * 
 * @author zcl
 *
 */
public interface IScorpionAppliactionSession extends Serializable{
	
	
	/**
	 * @description 获取用户ID
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public String getUserId()throws ScorpionBaseException;
	
	
	/**
	 * @description 设置用户ID
	 * 
	 * @throws ScorpionBaseException
	 */
	public void setUserId(String userId)throws ScorpionBaseException;

	/**
	 * @description 获取系统回话
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * 
	 */
    public String getSessionId();
    
    
    /**
     * @description 获取属性参数
     * 
     * @return
     * 
     * @throws ScorpionBaseException
     * 
     */
    public Object getAttributes(String key)throws ScorpionBaseException;
    
    
    /**
     * @description Clone object information
     * 
     * @return
     * 
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException;
    
    
    
    /**
     * 
     * @param servers
     * 
     * @throws ScorpionBaseException
     */
    public void addServers(LinkedList<String> servers)throws ScorpionBaseException;
    
    
    /**
     * 
     * @throws ScorpionBaseException
     */
    public void addCurrentServer()throws ScorpionBaseException;
    
    
    /**
     * @throws ScorpionBaseException
     */
    public void setSessionId(String sessionId)throws ScorpionBaseException;
    
    /**
     * @param key
     * @param value
     */
    public void setTmpData(String key,Object value);
    
    /**
     * @param tempData
     */
    public void setTmpData(Map<String,Object> tempData);
    
    /**
     * 
     * @param userId
     */
    public void setUserID(String userId);

    /**
     * 
     * @param protocolType
     */
	public void setProtocolType(ProtocolType protocolType);
	
	/**
	 * 
	 * @return
	 */
	public ProtocolType getProtocolType();
	
	/**
	 * 
	 * @return
	 */
	public Map<String, Object> getTmpData();
	
	/**
	 * @param key
	 * @return
	 */
	public Object getTmpData(String key);
	
	/**
	 * 
	 * @return
	 */
	public String getWebReqId();
	
    /**
     * @return
     * @throws ScorpionBaseException
     */
    public AtomicInteger getServiceCalledLevel()throws ScorpionBaseException;


    /**
     * 
     * @param name
     * 
     * @param value
     * 
     * @throws ScorpionBaseException
     */
	public void setAttribute(String name, Object value) throws ScorpionBaseException;
	
	
	/**
	 * @description remove attribute information ...
	 * 
	 * @param name
	 */
	public Object removeAttribute(String name);
	
	
	/**
	 * 
	 */
	public void openTransactionManager();
	
	/**
	 * 
	 */
	public void closeTransactionManager();
	
	/**
	 * 
	 * @param isBegin
	 */
	public void fresh(boolean isBeginning);
	
	
	/**
	 * 
	 * @return
	 */
	Map<String,Object> getReqTempData();
	
	
	/**
	 * @description Clear session information ...
	 * 
	 * @return
	 */
	IScorpionAppliactionSession clear();
	
	
	/**
	 * @description Create or instead of session
	 * 
	 * @param signal
	 * 
	 * @param req
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	IScorpionAppliactionSession createOrInsteadOfSession(String signal,HttpServletRequest req)throws ScorpionBaseException;

	

	/**
	 * @param session
	 * @return
	 */
	IScorpionAppliactionSession setSession(IScorpionAppliactionSession session);
	
	
	/**
	 * 
	 * @return
	 */
	Map<String,Object> sesstionDataConvertMap();
	
	/**
	 * 
	 * @param arguments
	 */
	void tempMapDataConvertSession(Map<String,Object> arguments);
	

}
