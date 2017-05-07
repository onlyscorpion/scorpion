package org.scorpion.api.common;

import java.io.Serializable;
import java.util.Date;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpAppliactionSession;
import org.scorpion.api.kernel.ITscpReqMedia;
import org.scorpion.api.kernel.ITscpRespMedia;
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
public abstract class AbsMediationFactor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String protocolId;

	private Date initDate;
	
	private int initConnNum;
	
	private int nextIncreConnNum;
	
	private int maxConnNum;
	
	private long dataValidData;
	
	protected String provideURL;
	
	private Date lastActivity;
	
	private boolean isActivity;
	
	private long connTimeOut;
	
	private long callTimeOut;
	
	protected ITscpAppliactionSession session;
	
	
	public abstract void initialize()throws TscpBaseException;
	
	/**
	 * @param req
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public abstract ITscpRespMedia messageSenderHandler(ITscpReqMedia req)throws TscpBaseException;
	
	
	/**
	 * @throws TscpBaseException
	 */
	public abstract void close() throws TscpBaseException;
	
	
	public String getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

	/**
	 * 
	 */
	public void interrupt() {
	
	   try{
		   if(Thread.currentThread().isAlive())
			   Thread.currentThread().interrupt();
	   }catch(Exception e){
		   PlatformLogger.error(e);
	   }
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}

	public boolean isActivity() {
		return isActivity;
	}

	public void setActivity(boolean isActivity) {
		this.isActivity = isActivity;
	}

	public String getProvideURL() {
		return provideURL;
	}

	public void setProvideURL(String provideURL) {
		this.provideURL = provideURL;
	}

	public long getConnTimeOut() {
		return connTimeOut;
	}

	public void setConnTimeOut(long connTimeOut) {
		this.connTimeOut = connTimeOut;
	}

	public long getCallTimeOut() {
		return callTimeOut;
	}

	public void setCallTimeOut(long callTimeOut) {
		this.callTimeOut = callTimeOut;
	}

	public long getDataValidData() {
		return dataValidData;
	}

	public void setDataValidData(long dataValidData) {
		this.dataValidData = dataValidData;
	}


	public int getInitConnNum() {
		return initConnNum;
	}


	public void setInitConnNum(int initConnNum) {
		this.initConnNum = initConnNum;
	}


	public int getNextIncreConnNum() {
		return nextIncreConnNum;
	}

	public void setNextIncreConnNum(int nextIncreConnNum) {
		this.nextIncreConnNum = nextIncreConnNum;
	}

	public int getMaxConnNum() {
		return maxConnNum;
	}

	public void setMaxConnNum(int maxConnNum) {
		this.maxConnNum = maxConnNum;
	}
	
	

	/**
	 * @param req
	 * @param e
	 * @return
	 * @throws TscpBaseException
	 */
	public ITscpRespMedia sendResMediaAgain(ITscpReqMedia req,Throwable e) throws TscpBaseException{
		
		try{
			if(session != null&&session.getTmpData("CallNum")==null){
				session.setTmpData("CallNum", 1);
				try {
					return tryAgain(req);
				} catch (InterruptedException e1) {
					throw new TscpBaseException(e1);
				}
			}else{
				if(e != null&&e.getMessage() != null&&e.getMessage().indexOf("java.lang.ClassNotFoundException") >= 0)
					PlatformLogger.error("TSCP-9458：Remote object can't find . ");
				throw new TscpBaseException(e);
			}
		}finally{
			if(session != null&&session.getTmpData("CallNum")!=null)
				session.setTmpData("CallNum",null);
		}
	}
	
	/**
	 * @param req
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 * 
	 * @throws InterruptedException
	 */
	protected abstract ITscpRespMedia tryAgain(ITscpReqMedia req) throws TscpBaseException, InterruptedException;

	
	
}
