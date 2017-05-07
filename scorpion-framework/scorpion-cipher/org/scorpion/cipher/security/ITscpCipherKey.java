package org.scorpion.cipher.security;

import java.security.Key;

import org.scorpion.api.exception.TscpBaseException;

public interface ITscpCipherKey {
	
	public byte[] encrypt(Key key, byte[] data) throws TscpBaseException;
	
	public byte[] decrypt(Key key, byte[] data) throws TscpBaseException;

}
