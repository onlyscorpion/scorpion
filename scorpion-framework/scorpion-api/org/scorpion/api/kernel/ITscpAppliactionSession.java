package org.scorpion.api.kernel;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;








import javax.servlet.http.HttpServletRequest;

import org.scorpion.api.common.ITscpProtocal.ProtocolType;
import org.scorpion.api.exception.TscpBaseException;

/**
 * 
 * @author zcl
 *
 */
public interface ITscpAppliactionSession extends Serializable{
	
	
	/**
	 * @description 获取用户ID
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public String getUserId()throws TscpBaseException;
	
	
	/**
	 * @description 设置用户ID
	 * 
	 * @throws TscpBaseException
	 */
	public void setUserId(String userId)throws TscpBaseException;

	/**
	 * @description 获取系统回话
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 * 
	 */
    public String getSessionId();
    
    
    /**
     * @description 获取属性参数
     * 
     * @return
     * 
     * @throws TscpBaseException
     * 
     */
    public Object getAttributes(String key)throws TscpBaseException;
    
    
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
     * @throws TscpBaseException
     */
    public void addServers(LinkedList<String> servers)throws TscpBaseException;
    
    
    /**
     * 
     * @throws TscpBaseException
     */
    public void addCurrentServer()throws TscpBaseException;
    
    
    /**
     * @throws TscpBaseException
     */
    public void setSessionId(String sessionId)throws TscpBaseException;
    
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
     * @throws TscpBaseException
     */
    public AtomicInteger getServiceCalledLevel()throws TscpBaseException;


    /**
     * 
     * @param name
     * 
     * @param value
     * 
     * @throws TscpBaseException
     */
	public void setAttribute(String name, Object value) throws TscpBaseException;
	
	
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
	ITscpAppliactionSession clear();
	
	
	/**
	 * @description Create or instead of session
	 * 
	 * @param signal
	 * 
	 * @param req
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	ITscpAppliactionSession createOrInsteadOfSession(String signal,HttpServletRequest req)throws TscpBaseException;

	

	/**
	 * @param session
	 * @return
	 */
	ITscpAppliactionSession setSession(ITscpAppliactionSession session);
	
	
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
