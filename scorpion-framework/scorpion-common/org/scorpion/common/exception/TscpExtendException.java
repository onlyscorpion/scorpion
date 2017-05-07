package org.scorpion.common.exception;

import java.util.Map;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.common.util.TscpSystemSessionUtils;

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
public class TscpExtendException extends TscpBaseException{

	private static final long serialVersionUID = 238732163072225953L;
	
	public TscpExtendException() throws TscpBaseException {
		super();
	}

	public TscpExtendException(String code, Map<String, Object> map, Exception e) {
		super(code, map, e);
		// TODO Auto-generated constructor stub
	}

	public TscpExtendException(String code, Map<String, Object> parameters,
			Throwable ex) {
		super(code, parameters, ex);
		// TODO Auto-generated constructor stub
	}

	public TscpExtendException(String message, Map<String, Object> errorMap) {
		super(message, errorMap);
		// TODO Auto-generated constructor stub
	}

	public TscpExtendException(String code, String message,
			Map<String, Object> errorMap, Throwable cause) {
		super(code, message, errorMap, cause);
		// TODO Auto-generated constructor stub
	}

	public TscpExtendException(String code, String message,
			Map<String, Object> errorMap) {
		super(code, message, errorMap);
		// TODO Auto-generated constructor stub
	}

	public TscpExtendException(String code, String message, Throwable cause) {
		super(code, message, cause);
		// TODO Auto-generated constructor stub
	}

	public TscpExtendException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public TscpExtendException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public TscpExtendException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	{
		this.sessionId = TscpSystemSessionUtils.getSession().getSessionId();
	}

	

}
