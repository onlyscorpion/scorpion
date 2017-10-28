package com.scorpion.huakerongtong.cipher.security;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

public interface IScorpionCipherDigitalEnvelope {

	public byte[] encrypt(byte[] data) throws ScorpionBaseException;
	
	public byte[] decrypt(byte[] data) throws ScorpionBaseException;
	
}
