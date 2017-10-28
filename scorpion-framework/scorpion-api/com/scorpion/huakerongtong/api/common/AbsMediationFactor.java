package com.scorpion.huakerongtong.api.common;

import java.io.Serializable;
import java.util.Date;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IScorpionAppliactionSession;
import com.scorpion.huakerongtong.api.kernel.IScorpionReqMedia;
import com.scorpion.huakerongtong.api.kernel.IScorpionRespMedia;
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
	
	protected IScorpionAppliactionSession session;
	
	
	public abstract void initialize()throws ScorpionBaseException;
	
	/**
	 * @param req
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public abstract IScorpionRespMedia messageSenderHandler(IScorpionReqMedia req)throws ScorpionBaseException;
	
	
	/**
	 * @throws ScorpionBaseException
	 */
	public abstract void close() throws ScorpionBaseException;
	
	
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
	 * @throws ScorpionBaseException
	 */
	public IScorpionRespMedia sendResMediaAgain(IScorpionReqMedia req,Throwable e) throws ScorpionBaseException{
		
		try{
			if(session != null&&session.getTmpData("CallNum")==null){
				session.setTmpData("CallNum", 1);
				try {
					return tryAgain(req);
				} catch (InterruptedException e1) {
					throw new ScorpionBaseException(e1);
				}
			}else{
				if(e != null&&e.getMessage() != null&&e.getMessage().indexOf("java.lang.ClassNotFoundException") >= 0)
					PlatformLogger.error("scorpion-9458：Remote object can't find . ");
				throw new ScorpionBaseException(e);
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
	 * @throws ScorpionBaseException
	 * 
	 * @throws InterruptedException
	 */
	protected abstract IScorpionRespMedia tryAgain(IScorpionReqMedia req) throws ScorpionBaseException, InterruptedException;

	
	
}
