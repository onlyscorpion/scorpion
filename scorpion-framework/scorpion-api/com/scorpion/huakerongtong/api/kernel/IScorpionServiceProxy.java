package com.scorpion.huakerongtong.api.kernel;

import java.io.Serializable;

import com.scorpion.huakerongtong.api.configuration.ScorpionSystemScanInfo.ServiceInfo;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

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
