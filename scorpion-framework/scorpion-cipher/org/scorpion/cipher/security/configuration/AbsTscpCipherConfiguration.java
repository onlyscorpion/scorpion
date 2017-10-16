package org.scorpion.cipher.security.configuration;

import org.scorpion.api.exception.ScorpionBaseException;

public abstract class AbsScorpionCipherConfiguration {
	
	private final static ScorpionCipherConfigurationImpl tci = new ScorpionCipherConfigurationImpl();
	
	public static AbsScorpionCipherConfiguration getInstance(){
		return tci;
	}
	
	public abstract void loadCipherConfiguration()  throws ScorpionBaseException;
	
	public abstract void reloadCipherConfiguration()  throws ScorpionBaseException;
	
	public abstract ScorpionCipherInfo getType(String name);

}
