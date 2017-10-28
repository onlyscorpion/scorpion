package com.scorpion.huakerongtong.api.kernel;

import com.scorpion.huakerongtong.api.configuration.ScorpionSystemScanInfo.ServiceInfo;

public interface IScorpionProxyService {
	
	public Object callService(ServiceInfo serviceInfo,Object[] args);

}
