package org.scorpion.cipher.security;

import org.scorpion.api.exception.TscpBaseException;

public interface ITscpSignature {
	
	public byte[] sign(byte[] data) throws TscpBaseException;
	
	public boolean verifySign(byte[] data, byte[] sign) throws TscpBaseException;

}
