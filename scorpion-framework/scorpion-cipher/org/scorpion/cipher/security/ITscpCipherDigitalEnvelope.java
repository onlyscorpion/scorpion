package org.scorpion.cipher.security;

import org.scorpion.api.exception.TscpBaseException;

public interface ITscpCipherDigitalEnvelope {

	public byte[] encrypt(byte[] data) throws TscpBaseException;
	
	public byte[] decrypt(byte[] data) throws TscpBaseException;
	
}
