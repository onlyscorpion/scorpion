package org.scorpion.api.common;


import java.util.List;
import java.util.Stack;

import org.scorpion.api.configuration.ExceptionInfo;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.configuration.SystemResourcePool;
import org.scorpion.api.util.Constant;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this factory class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsTscpBaseException extends Exception{

	private static final long serialVersionUID = 1658863687602518657L;
	
	private Stack<String> nodeChain;
	
	private String currentNode = Constant.SERVER_NODE;
	
	private String exceptionPointNode;
	
	protected String sessionId;
	
	
	public AbsTscpBaseException() {
		super();
	}

	/**
	 * @param message
	 * @param isGetMessageByCode
	 * @param cause
	 */
	public AbsTscpBaseException(String message, boolean isGetMessageByCode, Throwable cause) {
			super(isGetMessageByCode?getExceptionMessage(message):message, cause);
	}

	/**
	 * @param message
	 * @param isGetMessageByCode
	 */
	public AbsTscpBaseException(String message, boolean isGetMessageByCode) {
		
		super(isGetMessageByCode?getExceptionMessage(message):message);
	}

	/**
	 * @param cause
	 */
	public AbsTscpBaseException(Throwable cause) {
	
		super(cause);
	}
	
	
	
	public Stack<String> getNodeChain() {
		if(nodeChain == null)
			nodeChain = new Stack<String>();
		return nodeChain;
	}

	public void addNode(String serverNode) {
	
		if(nodeChain == null)
			nodeChain = new Stack<String>();
		this.nodeChain.push(serverNode);
		
	}

	public String getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}

	public String getExceptionPointNode() {
		return exceptionPointNode;
	}

	public void setExceptionPointNode(String exceptionPointNode) {
		this.exceptionPointNode = exceptionPointNode;
	}

	/**
	 * 
	 * @param code
	 * 
	 * @return
	 */
	protected static String getExceptionMessage(String code){
		
		for(Object exceptions:SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.systemexceptionconfigresource.getValue(),List.class)){
			if(((ExceptionInfo)exceptions).getExceptionmap().containsKey(code))
				return code+":"+((ExceptionInfo)exceptions).getContentByCode(code);
		}
		
		return code;
	}

	@Override
	public void printStackTrace() {
		
    	System.err.println("Session ID:["+sessionId+"]");
	    this.exceptionPointNode = dumpexceptionPoint(this);
	    if(exceptionPointNode != this.currentNode){
	    	System.err.println("CurrentNode: "+this.currentNode);
	    	System.err.println("The service have gone through those following node ：");
	    	if(this.nodeChain != null)
	    		for(String node:this.nodeChain)
	    			System.err.println(node);
	    	System.err.println("Exception point occur in node ["+this.exceptionPointNode+"]");
	    	System.err.println("=======================================");
	    }
	    if(this.getStackTrace() == null || this.getStackTrace().length == 0)
	    	return;
	    dumpStack(this);
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
	private void dumpStack(Throwable throwable){
		
		if(throwable != null){
			if(throwable.getCause() == null){
				if(throwable != null&&throwable.getStackTrace() != null){
					System.err.println("TSCP exception is caused by :"+throwable.getMessage());
					for(int i=0;i<throwable.getStackTrace().length;i++)
						System.err.println("     "+throwable.getStackTrace()[i]);
				}
				return;
			}else{
				dumpStack(throwable.getCause());
				System.err.println("TSCP exception is caused by :"+throwable.getMessage());
				for(int i=0;i<throwable.getStackTrace().length;i++)
					System.err.println("     "+throwable.getStackTrace()[i]);
			}
			return;
		}
	}
	
	/**
	 * 
	 * @param throwable
	 * 
	 * @return
	 */
	public String dumpexceptionPoint(Throwable throwable){
		
		if(throwable == null){
			return null;
		}
		if(throwable instanceof AbsTscpBaseException){
			if(((AbsTscpBaseException)throwable).nodeChain != null&&((AbsTscpBaseException)throwable).nodeChain.size()>0){
			
				if(((AbsTscpBaseException)throwable).sessionId != null&&!"".equals(((AbsTscpBaseException)throwable).sessionId))
				this.sessionId = ((AbsTscpBaseException)throwable).sessionId;
				for(int i=0;i<((AbsTscpBaseException)throwable).nodeChain.size();i++){
					if(this.nodeChain == null)
						this.nodeChain = new Stack<String>();
					this.nodeChain.push(((AbsTscpBaseException)throwable).nodeChain.pop());
				}
			}
		}
		if(throwable instanceof AbsTscpBaseException&&(throwable.getCause()==null||!(throwable.getCause() instanceof AbsTscpBaseException)))
			return ((AbsTscpBaseException)throwable).getCurrentNode();
		return dumpexceptionPoint(throwable.getCause());
	}
	
	
}
