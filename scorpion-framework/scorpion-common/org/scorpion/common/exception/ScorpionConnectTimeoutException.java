package org.scorpion.common.exception;

import org.scorpion.api.common.AbsScorpionBaseException;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: task unit </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionConnectTimeoutException extends AbsScorpionBaseException{

	private static final long serialVersionUID = 9050384635655005877L;

	public ScorpionConnectTimeoutException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScorpionConnectTimeoutException(String message, Throwable cause) {
		super(message,true, cause);
		// TODO Auto-generated constructor stub
	}

	public ScorpionConnectTimeoutException(String message) {
		super(message,true);
		// TODO Auto-generated constructor stub
	}

	public ScorpionConnectTimeoutException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	

}
