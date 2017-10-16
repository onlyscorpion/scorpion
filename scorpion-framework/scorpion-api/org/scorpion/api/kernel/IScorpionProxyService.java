package org.scorpion.api.kernel;

import org.scorpion.api.configuration.ScorpionSystemScanInfo.ServiceInfo;

public interface IScorpionProxyService {
	
	public Object callService(ServiceInfo serviceInfo,Object[] args);

}
