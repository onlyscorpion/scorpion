package com.scorpion.huakerongtong.kernel.container.customization;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IScorpionSystemIocManager;

public class ScorpionSystemIocManager implements IScorpionSystemIocManager {

	@Override
	public <T> T getBeanByClassType(Class<T> clazz) throws ScorpionBaseException {
		return null;
	}

	@Override
	public <T> T getBeanByClassName(String name) throws ScorpionBaseException {
		return null;
	}

	@Override
	public <T> T getBeanByClassAndName(String name, Class<T> clazz)throws ScorpionBaseException {
		return null;
	}

	@Override
	public void getBeanByServiceName(String serviceName)throws ScorpionBaseException {

	}

	@Override
	public <T> T getBeanByServiceName(String serviceName, Class<T> clazz)throws ScorpionBaseException {
		return null;
	}

}
