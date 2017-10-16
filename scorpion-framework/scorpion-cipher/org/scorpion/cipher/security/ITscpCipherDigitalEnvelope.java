package org.scorpion.cipher.security;

import org.scorpion.api.exception.ScorpionBaseException;

public interface IScorpionCipherDigitalEnvelope {

	public byte[] encrypt(byte[] data) throws ScorpionBaseException;
	
	public byte[] decrypt(byte[] data) throws ScorpionBaseException;
	
}
