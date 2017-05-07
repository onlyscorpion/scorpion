package org.scorpion.common.util;

import javax.servlet.http.HttpServletRequest;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpAppliactionSession;
import org.scorpion.api.kernel.ITscpGlobalSystemSession;
import org.scorpion.api.util.Constant;
import org.scorpion.common.session.ApplicationSession;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: system constant information </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpSystemSessionUtils {

	
	
	/**
	 * 获取session信息
	 * 
	 * @return
	 * 
	 */
	public static ITscpAppliactionSession getSession(){
		
		return ApplicationSession.getSession();
		
	}

	
	/**
	 * 设置session信息
	 * 
	 * @param key
	 * 
	 * @param obj
	 * 
	 * @throws TscpBaseException
	 */
	public static void put(String key,Object obj) throws TscpBaseException{
	
		ApplicationSession.getSession().setAttribute(key, obj);

	}
	
	
	
	/**
	 * 
	 * @param dataKey
	 * 
	 * @throws TscpBaseException
	 */
	public static Object getTempDataFromSession(String dataKey)throws TscpBaseException{
		
		return	ApplicationSession.getSession().getTmpData(dataKey);
	}
	
	
	/**
	 * @param key
	 * 
	 * @param obj
	 * 
	 * @throws TscpBaseException
	 */
	public static void putTempDataInSession(String key,Object value)throws TscpBaseException{
	
		ApplicationSession.getSession().setTmpData(key, value);
	}
	
	
	/**
	 * @param key
	 * 
	 * @throws TscpBaseException
	 */
	public static void removeTempDataFromSession(String key)throws TscpBaseException{
		
		ApplicationSession.getSession().getTmpData().remove(key);
	}

	
	/**
	 * 
	 * @param sessionId
	 * 
	 * @throws TscpBaseException
	 */
	public static void setSessionID(String sessionId)throws TscpBaseException{
	
		((ApplicationSession)ApplicationSession.getSession()).setSessionId(sessionId);
	
	}
	
	
	
	
	/**
	 * clear user session information
	 * 
	 * @throws TscpBaseException
	 */
	public static ITscpAppliactionSession clear(){
	
		return ApplicationSession.getSession().clear();
	
	}
	
	
	public static void dropSession(){
		 ((ApplicationSession)ApplicationSession.getSession()).dropSession();
	}
	
	

	/**
	 * @Description Clone session 
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public static ITscpGlobalSystemSession cloneSession()throws TscpBaseException{
	
		try{
	
			return (ITscpGlobalSystemSession)ApplicationSession.getSession().clone();
	
		}catch(Exception e){
		
			throw new TscpBaseException("TSC-8769:Clone session failure ",e);
		
		}
	}
	
	/**
	 * @return
	 */
    public static ITscpAppliactionSession createSession(){
    	
    	  return ApplicationSession.createSession();
    }
	

	/**
	 * 创建session信息
	 * 
	 * @param signal
	 * 
	 * @param req
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public static ITscpAppliactionSession createOrinsteadOfSession(HttpServletRequest req)throws TscpBaseException{
	
		return ApplicationSession.getSession().createOrInsteadOfSession(Constant.TSCP_SESSION, req);
	}
	
	/**
	 * 创建session信息
	 * 
	 * @param session
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public static ITscpAppliactionSession createOrinsteadOfSession(ITscpGlobalSystemSession session)throws TscpBaseException{

		return ApplicationSession.createOrInsteadOfSession(session);

	}
	
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	public static ITscpAppliactionSession setSession(ITscpAppliactionSession session){
		
		return ApplicationSession.getSession().setSession(session);
	}
	
		
}
