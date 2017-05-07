package org.scorpion.api.exception;

import java.util.Map;

import org.scorpion.api.common.AbsTscpBaseException;


/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: task unit </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpBaseException extends AbsTscpBaseException{

	private static final long serialVersionUID = 1L;
	
	public TscpBaseException()throws TscpBaseException{
		super();
	}
	
	public String getExceptionCode()throws TscpBaseException{
		return null;
	}
	
	
	public String getExceptionStackName(){
		return null;
	}
	
	
	public TscpBaseException(String message,Map<String,Object> errorMap){
		super(message,true);
	}
	
	public TscpBaseException(String code,String message,Throwable cause){
		super(code+":"+message,false,cause);
	}
	
	
	public TscpBaseException(String code,String message,Map<String,Object> errorMap,Throwable cause){
		
		super(code+":"+message+"/r/n"+(errorMap == null?"":errorMap.toString()),false,cause);
	}
	
	public TscpBaseException(String code,String message,Map<String,Object> errorMap){
	
		super(code+":"+message+"/r/n"+(errorMap == null?"":errorMap.toString()),false);
	}
	
	public TscpBaseException(String code, Map<String, Object> parameters, Throwable ex){
		
		super(getExceptionMessage(code)+":"+(parameters == null?"":parameters.toString()),false,ex);
	}
	

	public TscpBaseException(String message, Throwable cause) {
		super(message,true, cause);
	}

	public TscpBaseException(String message) {
		super(message,true);
	}

	public TscpBaseException(Throwable cause) {
		super(cause);
	}
	
	
	public TscpBaseException(String code ,Map<String,Object> map,Exception e){
	
	}
	
	public void setMessage(String message) {
		if(message == null||"".equals(message))
			return ;
		String me = getExceptionMessage(message);
		Exception ex = new Exception(me);
		this.setStackTrace(ex.getStackTrace());
	}
}
