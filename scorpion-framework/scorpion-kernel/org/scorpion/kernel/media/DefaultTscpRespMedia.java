package org.scorpion.kernel.media;

import java.io.Serializable;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpRespMedia;

/**
 * 自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.tscp.common
 * <p>
 * File: AbsTscpFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 taiji.com.cn
 * </p>
 * <p>
 * Company: taiji.com.cn
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class DefaultTscpRespMedia implements ITscpRespMedia {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> tempData;

	private Object responseValue;

	private String sessionID;
	
	private int status;

	private int callLevel;
	
	private StackInfo stackInfo;
	

	@Override
	public Object getRespTempData(String key) throws TscpBaseException {

		if (tempData == null)
			return null;

		return tempData.get(key);
	}

	@Override
	public void setRespTempData(String key, Object value)
			throws TscpBaseException {

		if (tempData == null)
			tempData = new ConcurrentHashMap<String, Object>();

		tempData.put(key, value);
	}
	
	@Override
	public void setRespTempData(Map<String,Object> map)throws TscpBaseException {
		
		if (tempData == null)
			tempData = new ConcurrentHashMap<String, Object>();
		
		if(map == null)
			return ;
		
		tempData.putAll(map);
	}
	

	public StackInfo getStackInfo() {
		if(stackInfo == null)
			stackInfo = new StackInfo();
		return stackInfo;
	}

	public void setStackInfo(StackInfo stackInfo) {
		this.stackInfo = stackInfo;
	}

	public Map<String, Object> getTempData() {
		return tempData;
	}

	public void setTempData(Map<String, Object> tempData) {
		this.tempData = tempData;
	}

	public Object getResponseValue() {
		return responseValue;
	}

	public void setResponseValue(Object responseValue) {
		this.responseValue = responseValue;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public int getCallLevel() {
		return callLevel;
	}

	public void setCallLevel(int callLevel) {
		this.callLevel = callLevel;
	}

	@Override
	public ConcurrentHashMap<Object, Object> getSystemContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConcurrentHashMap<Object, Object> getTmpData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConcurrentHashMap<Object, Object> getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	/**
	 * 
	 * @author zcl
	 *
	 */
	public class StackInfo implements Serializable{
		
		private static final long serialVersionUID = 6101226512417275869L;
		
		private Stack<String> throughNode;
		
		private Stack<InternalStack> internalStack;
		
		
		
	    public Stack<InternalStack> getInternalStack() {
			return internalStack;
		}

		public void setInternalStack(Stack<InternalStack> internalStack) {
			this.internalStack = internalStack;
		}

		public Stack<String> getThroughNode() {
			return throughNode;
		}
		
		public void addServerNode(String nodeName){
		
			if(throughNode == null)
				throughNode = new Stack<String>();
			throughNode.push(nodeName);
		}

		public void setThroughNode(Stack<String> throughNode) {
			this.throughNode = throughNode;
		}

		/**
		 * 
		 * @author zcl
		 *
		 */
		public class InternalStack implements Serializable{
			
			private static final long serialVersionUID = 7625425697225692871L;

			private String nodeName;
			
			private Throwable throwable;

			public String getNodeName() {
				return nodeName;
			}

			public void setNodeName(String nodeName) {
				this.nodeName = nodeName;
			}

			public Throwable getThrowable() {
				return throwable;
			}

			public void setThrowable(Throwable throwable) {
				this.throwable = throwable;
			}

	    }
		
	}

}
