package org.scorpion.kernel.media;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpReqMedia;
import org.scorpion.common.context.SystemContext;

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
public class DefaultTscpReqMedia implements ITscpReqMedia {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> tempData;

	private String sessionId;

	private String serviceName;

	private Object[] serviceParam;
	
	private String userId;

	private ConcurrentHashMap<Object, Object> tmpData;

	private LinkedList<String> servers;

	private int callLevel;

	public Map<String, Object> getTempData() {
		
		if(tempData == null)
			tempData = new ConcurrentHashMap<String, Object>();

		return tempData;
	}

	public void setTempData(Map<String, Object> tempData) {

		this.tempData = tempData;
	}

	public Object[] getServiceParam() {

		return serviceParam;
	}

	public void setServiceParam(Object[] serviceParam) {

		this.serviceParam = serviceParam;
	}

	public ConcurrentHashMap<Object, Object> getTmpData() {

		return tmpData;
	}

	public void setTmpData(ConcurrentHashMap<Object, Object> tmpData) {

		this.tmpData = tmpData;
	}

	public int getCallLevel() {

		return callLevel;
	}

	public void setCallLevel(int callLevel) {

		this.callLevel = callLevel;
	}

	public void setServiceName(String serviceName) {

		this.serviceName = serviceName;
	}

	public void setServers(LinkedList<String> servers) {

		this.servers = servers;
	}

	@Override
	public String getServiceName() {

		return serviceName;
	}
	

	@Override
	public String getSessionId() {

		return sessionId;
	}
	
	

	@Override
	public Object getReqTempData(String key) throws TscpBaseException {

		if (tempData == null)
			return null;

		return tempData.get(key);
	}

	@Override
	public void setReqTempData(String key, Object value)
			throws TscpBaseException {

		if (tempData == null)
			tempData = new ConcurrentHashMap<String, Object>();

		tempData.put(key, value);
	}

	@Override
	public void setReqTempData(Map<String, Object> tempData)
			throws TscpBaseException {

		this.tempData = tempData;
	}

	@Override
	public Map<String, Object> getReqTempData() throws TscpBaseException {
	
		if(tempData == null)
			tempData = new ConcurrentHashMap<String, Object>();
		return tempData;
	}

	@Override
	public LinkedList<String> getServers() {

		return servers;
	}

	@Override
	public void setSessionId(String sessionId) {

		this.sessionId = sessionId;
	}

	@Override
	public void setUserId(String userId) {

		this.userId = userId;
	}

	/**
	 * @return
	 */
	public String getUserId() {

		return this.userId;
	}

	@Override
	public Object[] getServiceArgument() {
		return serviceParam;
	}

	@Override
	public void addCurrentServer() throws TscpBaseException {

		if (this.servers == null)
			this.servers = new LinkedList<String>();

		this.servers.add(SystemContext.getApplicationContext().getSystemCoreConfig().getNodeName());
	}

	@Override
	public void addServiceParameter(Object obj, int index)throws TscpBaseException {

		if (serviceParam.length <= index)
			throw new IndexOutOfBoundsException("TSCP-8076:argument outofbound !");

		this.serviceParam[index] = obj;
	}

}
