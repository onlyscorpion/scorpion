package com.scorpion.huakerongtong.cipher.security;

import javax.crypto.SecretKey;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

public interface IScorpionSymmetryKey {
	
	public SecretKey byteToSecretKey(byte[] secretKey) throws ScorpionBaseException;
	
	public SecretKey generateSecretKey() throws ScorpionBaseException;
	
	public byte[] generateSecretKeyEncoded() throws ScorpionBaseException;

}
