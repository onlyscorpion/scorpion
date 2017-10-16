package org.scorpion.api.exception;

import java.util.Map;

import org.scorpion.api.common.AbsScorpionBaseException;


/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: task unit </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionBaseException extends AbsScorpionBaseException{

	private static final long serialVersionUID = 1L;
	
	public ScorpionBaseException()throws ScorpionBaseException{
		super();
	}
	
	public String getExceptionCode()throws ScorpionBaseException{
		return null;
	}
	
	
	public String getExceptionStackName(){
		return null;
	}
	
	
	public ScorpionBaseException(String message,Map<String,Object> errorMap){
		super(message,true);
	}
	
	public ScorpionBaseException(String code,String message,Throwable cause){
		super(code+":"+message,false,cause);
	}
	
	
	public ScorpionBaseException(String code,String message,Map<String,Object> errorMap,Throwable cause){
		
		super(code+":"+message+"/r/n"+(errorMap == null?"":errorMap.toString()),false,cause);
	}
	
	public ScorpionBaseException(String code,String message,Map<String,Object> errorMap){
	
		super(code+":"+message+"/r/n"+(errorMap == null?"":errorMap.toString()),false);
	}
	
	public ScorpionBaseException(String code, Map<String, Object> parameters, Throwable ex){
		
		super(getExceptionMessage(code)+":"+(parameters == null?"":parameters.toString()),false,ex);
	}
	

	public ScorpionBaseException(String message, Throwable cause) {
		super(message,true, cause);
	}

	public ScorpionBaseException(String message) {
		super(message,true);
	}

	public ScorpionBaseException(Throwable cause) {
		super(cause);
	}
	
	
	public ScorpionBaseException(String code ,Map<String,Object> map,Exception e){
	
	}
	
	public void setMessage(String message) {
		if(message == null||"".equals(message))
			return ;
		String me = getExceptionMessage(message);
		Exception ex = new Exception(me);
		this.setStackTrace(ex.getStackTrace());
	}
}
