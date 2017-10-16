package org.scorpion.common.exception;

import java.util.Map;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.common.util.ScorpionSystemSessionUtils;

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
public class ScorpionExtendException extends ScorpionBaseException{

	private static final long serialVersionUID = 238732163072225953L;
	
	public ScorpionExtendException() throws ScorpionBaseException {
		super();
	}

	public ScorpionExtendException(String code, Map<String, Object> map, Exception e) {
		super(code, map, e);
		// TODO Auto-generated constructor stub
	}

	public ScorpionExtendException(String code, Map<String, Object> parameters,
			Throwable ex) {
		super(code, parameters, ex);
		// TODO Auto-generated constructor stub
	}

	public ScorpionExtendException(String message, Map<String, Object> errorMap) {
		super(message, errorMap);
		// TODO Auto-generated constructor stub
	}

	public ScorpionExtendException(String code, String message,
			Map<String, Object> errorMap, Throwable cause) {
		super(code, message, errorMap, cause);
		// TODO Auto-generated constructor stub
	}

	public ScorpionExtendException(String code, String message,
			Map<String, Object> errorMap) {
		super(code, message, errorMap);
		// TODO Auto-generated constructor stub
	}

	public ScorpionExtendException(String code, String message, Throwable cause) {
		super(code, message, cause);
		// TODO Auto-generated constructor stub
	}

	public ScorpionExtendException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ScorpionExtendException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ScorpionExtendException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	{
		this.sessionId = ScorpionSystemSessionUtils.getSession().getSessionId();
	}

	

}
