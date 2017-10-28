package com.scorpion.huakerongtong.cipher.security;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

public interface IScorpionSignature {
	
	public byte[] sign(byte[] data) throws ScorpionBaseException;
	
	public boolean verifySign(byte[] data, byte[] sign) throws ScorpionBaseException;

}
