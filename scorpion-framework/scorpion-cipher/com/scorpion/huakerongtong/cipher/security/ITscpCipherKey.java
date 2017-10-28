package com.scorpion.huakerongtong.cipher.security;

import java.security.Key;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

public interface IScorpionCipherKey {
	
	public byte[] encrypt(Key key, byte[] data) throws ScorpionBaseException;
	
	public byte[] decrypt(Key key, byte[] data) throws ScorpionBaseException;

}
