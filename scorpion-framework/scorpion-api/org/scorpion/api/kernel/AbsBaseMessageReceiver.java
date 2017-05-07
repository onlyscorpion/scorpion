package org.scorpion.api.kernel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.log.PlatformLogger;

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

	public String recevieXml(String argument) throws TscpBaseException {
		
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
	 * @throws TscpBaseException
	 */
	public ITscpRespMedia internalInvoke(ITscpReqMedia req)throws TscpBaseException{
	
		try{
			return internalCall(req);
		}catch(Throwable throwable){
			throw (TscpBaseException)PlatformLogger.detailStack(throwable,context,req.getSessionId());
		}
		
	}
	
	/*
	public DefaultTscpRespMedia getExceptionResp(Throwable throwable){
	
		DefaultTscpRespMedia resp = new DefaultTscpRespMedia();
	
		DefaultTscpRespMedia.StackInfo.InternalStack internal = resp.getStackInfo().new InternalStack();

		try {
			resp.getStackInfo().addServerNode(context.getServerName());
			internal.setNodeName(context.getServerName());
		} catch (TscpBaseException e) {
			resp.getStackInfo().addServerNode("Unknown Node Name");
			internal.setNodeName("Unknown Node Name");
			PlatformLogger.error("TSCP-8049:Generate exception instance failure !",e);
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
	 * @throws TscpBaseException
	 */
	public abstract ITscpRespMedia internalCall(ITscpReqMedia req)throws TscpBaseException;
	
	
	/**
	 * @description 
	 * 
	 * @param arguemnt
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public abstract String receiveXmlMessage (String arguemnt) throws TscpBaseException;
	
	protected void internalStackPackage(Throwable throwable){
		
		StackTraceElement[] stackElements = throwable.getStackTrace();
		stackElements = Arrays.copyOf(stackElements, stackElements.length+1);
		//Stack<Object> stack = new Stack<Object>();
		
/*		for(StackTraceElement elements:stackElements){
			//elements.
			
		}*/
		
		
	}
	
	

}
