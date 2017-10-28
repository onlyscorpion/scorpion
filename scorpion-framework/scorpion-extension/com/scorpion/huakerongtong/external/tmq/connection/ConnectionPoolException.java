package com.scorpion.huakerongtong.external.tmq.connection;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ConnectionPoolException extends Exception{

	private static final long serialVersionUID = 1L;

	
	public ConnectionPoolException() {
		super();
	}
	
	/**
	 * 异常处理
	 * @param message
	 * @param cause
	 */
	public ConnectionPoolException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	/**
	 * 异常处理
	 * @param message
	 * @param cause
	 */
	public ConnectionPoolException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 异常处理
	 * @param message
	 * @param cause
	 */
	public ConnectionPoolException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
