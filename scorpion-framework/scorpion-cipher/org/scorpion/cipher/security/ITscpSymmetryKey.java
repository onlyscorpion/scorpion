package org.scorpion.cipher.security;

import javax.crypto.SecretKey;

import org.scorpion.api.exception.TscpBaseException;

public interface ITscpSymmetryKey {
	
	public SecretKey byteToSecretKey(byte[] secretKey) throws TscpBaseException;
	
	public SecretKey generateSecretKey() throws TscpBaseException;
	
	public byte[] generateSecretKeyEncoded() throws TscpBaseException;

}
