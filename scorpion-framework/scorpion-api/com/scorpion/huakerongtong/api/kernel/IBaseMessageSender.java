package com.scorpion.huakerongtong.api.kernel;

import com.scorpion.huakerongtong.api.configuration.ScorpionRouteInfo;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

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
public interface IBaseMessageSender {
	
	public static final String USER_TRANSACTION = "Scorpion_user_transaction";	
	
	/**
	 * @param req
	 * @return
	 * @throws ScorpionBaseException
	 */
	public IScorpionRespMedia send(IScorpionReqMedia req)throws ScorpionBaseException;
	
	
	/**
	 * @param protocol
	 * @param req
	 * @return
	 * @throws ScorpionBaseException
	 */
	public IScorpionRespMedia send(String protocol,IScorpionReqMedia req)throws ScorpionBaseException;
	
	/**
	 * @param routeInfo
	 * @param req
	 * @return
	 * @throws ScorpionBaseException
	 */
	public IScorpionRespMedia send(ScorpionRouteInfo routeInfo, IScorpionReqMedia req) throws ScorpionBaseException;
	
	

}
