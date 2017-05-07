package org.scorpion.common.exception;

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
public class TscpConnectTimeoutException extends AbsTscpBaseException{

	private static final long serialVersionUID = 9050384635655005877L;

	public TscpConnectTimeoutException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TscpConnectTimeoutException(String message, Throwable cause) {
		super(message,true, cause);
		// TODO Auto-generated constructor stub
	}

	public TscpConnectTimeoutException(String message) {
		super(message,true);
		// TODO Auto-generated constructor stub
	}

	public TscpConnectTimeoutException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	

}
