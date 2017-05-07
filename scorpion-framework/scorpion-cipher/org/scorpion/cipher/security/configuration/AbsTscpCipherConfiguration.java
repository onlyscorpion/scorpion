package org.scorpion.cipher.security.configuration;

import org.scorpion.api.exception.TscpBaseException;

public abstract class AbsTscpCipherConfiguration {
	
	private final static TscpCipherConfigurationImpl tci = new TscpCipherConfigurationImpl();
	
	public static AbsTscpCipherConfiguration getInstance(){
		return tci;
	}
	
	public abstract void loadCipherConfiguration()  throws TscpBaseException;
	
	public abstract void reloadCipherConfiguration()  throws TscpBaseException;
	
	public abstract TscpCipherInfo getType(String name);

}
