package org.scorpion.kernel.container.customization;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpSystemIocManager;

public class TscpSystemIocManager implements ITscpSystemIocManager {

	@Override
	public <T> T getBeanByClassType(Class<T> clazz) throws TscpBaseException {
		return null;
	}

	@Override
	public <T> T getBeanByClassName(String name) throws TscpBaseException {
		return null;
	}

	@Override
	public <T> T getBeanByClassAndName(String name, Class<T> clazz)throws TscpBaseException {
		return null;
	}

	@Override
	public void getBeanByServiceName(String serviceName)throws TscpBaseException {

	}

	@Override
	public <T> T getBeanByServiceName(String serviceName, Class<T> clazz)throws TscpBaseException {
		return null;
	}

}
