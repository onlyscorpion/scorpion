package org.scorpion.cipher.security;

import java.security.Key;

import org.scorpion.api.exception.ScorpionBaseException;

public interface IScorpionCipherKey {
	
	public byte[] encrypt(Key key, byte[] data) throws ScorpionBaseException;
	
	public byte[] decrypt(Key key, byte[] data) throws ScorpionBaseException;

}
