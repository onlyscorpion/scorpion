package org.scorpion.api.kernel;

import org.scorpion.api.configuration.TscpSystemScanInfo.ServiceInfo;

public interface ITscpProxyService {
	
	public Object callService(ServiceInfo serviceInfo,Object[] args);

}
