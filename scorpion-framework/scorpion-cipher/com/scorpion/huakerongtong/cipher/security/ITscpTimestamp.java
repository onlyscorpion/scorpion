package com.scorpion.huakerongtong.cipher.security;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

public interface IScorpionTimestamp {
	
	public byte[] timestamp(byte []data) throws ScorpionBaseException;
	
	public boolean verifyTimestamp(byte[] data, byte[] timestamp) throws ScorpionBaseException;

}
