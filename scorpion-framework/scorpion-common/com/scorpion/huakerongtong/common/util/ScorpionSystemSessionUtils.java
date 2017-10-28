package com.scorpion.huakerongtong.common.util;

import javax.servlet.http.HttpServletRequest;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IScorpionAppliactionSession;
import com.scorpion.huakerongtong.api.kernel.IScorpionGlobalSystemSession;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.common.session.ApplicationSession;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: system constant information </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionSystemSessionUtils {

	
	
	/**
	 * 获取session信息
	 * 
	 * @return
	 * 
	 */
	public static IScorpionAppliactionSession getSession(){
		
		return ApplicationSession.getSession();
		
	}

	
	/**
	 * 设置session信息
	 * 
	 * @param key
	 * 
	 * @param obj
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void put(String key,Object obj) throws ScorpionBaseException{
	
		ApplicationSession.getSession().setAttribute(key, obj);

	}
	
	
	
	/**
	 * 
	 * @param dataKey
	 * 
	 * @throws ScorpionBaseException
	 */
	public static Object getTempDataFromSession(String dataKey)throws ScorpionBaseException{
		
		return	ApplicationSession.getSession().getTmpData(dataKey);
	}
	
	
	/**
	 * @param key
	 * 
	 * @param obj
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void putTempDataInSession(String key,Object value)throws ScorpionBaseException{
	
		ApplicationSession.getSession().setTmpData(key, value);
	}
	
	
	/**
	 * @param key
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void removeTempDataFromSession(String key)throws ScorpionBaseException{
		
		ApplicationSession.getSession().getTmpData().remove(key);
	}

	
	/**
	 * 
	 * @param sessionId
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void setSessionID(String sessionId)throws ScorpionBaseException{
	
		((ApplicationSession)ApplicationSession.getSession()).setSessionId(sessionId);
	
	}
	
	
	
	
	/**
	 * clear user session information
	 * 
	 * @throws ScorpionBaseException
	 */
	public static IScorpionAppliactionSession clear(){
	
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
	 * @throws ScorpionBaseException
	 */
	public static IScorpionGlobalSystemSession cloneSession()throws ScorpionBaseException{
	
		try{
	
			return (IScorpionGlobalSystemSession)ApplicationSession.getSession().clone();
	
		}catch(Exception e){
		
			throw new ScorpionBaseException("TSC-8769:Clone session failure ",e);
		
		}
	}
	
	/**
	 * @return
	 */
    public static IScorpionAppliactionSession createSession(){
    	
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
	 * @throws ScorpionBaseException
	 */
	public static IScorpionAppliactionSession createOrinsteadOfSession(HttpServletRequest req)throws ScorpionBaseException{
	
		return ApplicationSession.getSession().createOrInsteadOfSession(Constant.Scorpion_SESSION, req);
	}
	
	/**
	 * 创建session信息
	 * 
	 * @param session
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public static IScorpionAppliactionSession createOrinsteadOfSession(IScorpionGlobalSystemSession session)throws ScorpionBaseException{

		return ApplicationSession.createOrInsteadOfSession(session);

	}
	
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	public static IScorpionAppliactionSession setSession(IScorpionAppliactionSession session){
		
		return ApplicationSession.getSession().setSession(session);
	}
	
		
}
