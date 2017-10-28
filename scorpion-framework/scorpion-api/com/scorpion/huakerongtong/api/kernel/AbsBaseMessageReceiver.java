package com.scorpion.huakerongtong.api.kernel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.log.PlatformLogger;

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
public abstract class AbsBaseMessageReceiver extends UnicastRemoteObject{
	
	
	protected ApplicationContext context;
	
	private static final long serialVersionUID = -1103549996723933233L;

	/**
	 * 
	 * @throws RemoteException
	 */
	protected AbsBaseMessageReceiver() throws RemoteException {
		super();
	}

	public String recevieXml(String argument) throws ScorpionBaseException {
		
		try{
			receiveXmlMessage("");
		}catch(Throwable throwable){
			PlatformLogger.error(throwable);
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws ScorpionBaseException
	 */
	public IScorpionRespMedia internalInvoke(IScorpionReqMedia req)throws ScorpionBaseException{
	
		try{
			return internalCall(req);
		}catch(Throwable throwable){
			throw (ScorpionBaseException)PlatformLogger.detailStack(throwable,context,req.getSessionId());
		}
		
	}
	
	/*
	public DefaultScorpionRespMedia getExceptionResp(Throwable throwable){
	
		DefaultScorpionRespMedia resp = new DefaultScorpionRespMedia();
	
		DefaultScorpionRespMedia.StackInfo.InternalStack internal = resp.getStackInfo().new InternalStack();

		try {
			resp.getStackInfo().addServerNode(context.getServerName());
			internal.setNodeName(context.getServerName());
		} catch (ScorpionBaseException e) {
			resp.getStackInfo().addServerNode("Unknown Node Name");
			internal.setNodeName("Unknown Node Name");
			PlatformLogger.error("scorpion-8049:Generate exception instance failure !",e);
		}
		internal.setThrowable(throwable);
		resp.getStackInfo().getInternalStack().push(internal);
		resp.setStatus(Constant.FAILURE);
		return null;
	}
	*/
	
	/**
	 * @description Service internal call ...
	 * 
	 * @param req
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public abstract IScorpionRespMedia internalCall(IScorpionReqMedia req)throws ScorpionBaseException;
	
	
	/**
	 * @description 
	 * 
	 * @param arguemnt
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public abstract String receiveXmlMessage (String arguemnt) throws ScorpionBaseException;
	
	protected void internalStackPackage(Throwable throwable){
		
		StackTraceElement[] stackElements = throwable.getStackTrace();
		stackElements = Arrays.copyOf(stackElements, stackElements.length+1);
		//Stack<Object> stack = new Stack<Object>();
		
/*		for(StackTraceElement elements:stackElements){
			//elements.
			
		}*/
		
		
	}
	
	

}
