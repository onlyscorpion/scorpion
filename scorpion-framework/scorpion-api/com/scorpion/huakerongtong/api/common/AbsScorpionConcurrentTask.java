package com.scorpion.huakerongtong.api.common;


import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

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
public abstract class AbsScorpionConcurrentTask implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private String taskId;
	
	private String taskName;
	
	private Date startTime;
	
	private Date endTime;
	
	private boolean isSyn = false;
	
	private CountDownLatch latch;
	
	private TaskException errorinfo;
	
	public abstract Object getParameter() throws ScorpionBaseException;
	
	public String getTaskId() {
		return taskId;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	public TaskException getErrorinfo() {
		return errorinfo;
	}

	public void setErrorinfo(TaskException errorinfo) {
		this.errorinfo = errorinfo;
	}
	
    public boolean isSyn() {
		return isSyn;
	}

	public void setSyn(boolean isSyn) {
		this.isSyn = isSyn;
	}



	public class TaskException{
		
		private String message;
		
		private StackTraceElement[] stackTraceElements;

		public String getMessage() {
			return message;
		}

		public TaskException setMessage(String message) {
			this.message = message;
			return this;
		}

		public StackTraceElement[] getStackTraceElements() {
			return stackTraceElements;
		}

		public TaskException setStackTraceElements(StackTraceElement[] stackTraceElements) {
			this.stackTraceElements = stackTraceElements;
			return this;
		}
	}
	
}
