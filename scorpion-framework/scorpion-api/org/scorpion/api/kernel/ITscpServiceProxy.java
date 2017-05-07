package org.scorpion.api.kernel;

import java.io.Serializable;

import org.scorpion.api.configuration.TscpSystemScanInfo.ServiceInfo;
import org.scorpion.api.exception.TscpBaseException;

public interface ITscpServiceProxy extends Serializable{
	
	
	/**
	 * call proxy service
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public Object invokeService()throws TscpBaseException;
	
	/**
	 * @param serviceInfo
	 * @param args
	 * @return
	 */
	public Object callService(ServiceInfo serviceInfo,Object[] args);

	
	

}
