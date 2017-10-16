package org.scorpion.cipher.security;

import javax.crypto.SecretKey;

import org.scorpion.api.exception.ScorpionBaseException;

public interface IScorpionSymmetryKey {
	
	public SecretKey byteToSecretKey(byte[] secretKey) throws ScorpionBaseException;
	
	public SecretKey generateSecretKey() throws ScorpionBaseException;
	
	public byte[] generateSecretKeyEncoded() throws ScorpionBaseException;

}
