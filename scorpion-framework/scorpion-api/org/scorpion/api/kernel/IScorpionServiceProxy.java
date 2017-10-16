package org.scorpion.api.kernel;

import java.io.Serializable;

import org.scorpion.api.configuration.ScorpionSystemScanInfo.ServiceInfo;
import org.scorpion.api.exception.ScorpionBaseException;

public interface IScorpionServiceProxy extends Serializable{
	
	
	/**
	 * call proxy service
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public Object invokeService()throws ScorpionBaseException;
	
	/**
	 * @param serviceInfo
	 * @param args
	 * @return
	 */
	public Object callService(ServiceInfo serviceInfo,Object[] args);

	
	

}
