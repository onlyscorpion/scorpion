package com.scorpion.huakerongtong.cipher.security.configuration;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

public abstract class AbsScorpionCipherConfiguration {
	
	private final static ScorpionCipherConfigurationImpl tci = new ScorpionCipherConfigurationImpl();
	
	public static AbsScorpionCipherConfiguration getInstance(){
		return tci;
	}
	
	public abstract void loadCipherConfiguration()  throws ScorpionBaseException;
	
	public abstract void reloadCipherConfiguration()  throws ScorpionBaseException;
	
	public abstract ScorpionCipherInfo getType(String name);

}
