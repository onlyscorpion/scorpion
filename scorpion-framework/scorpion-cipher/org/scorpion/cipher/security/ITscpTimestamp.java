package org.scorpion.cipher.security;

import org.scorpion.api.exception.TscpBaseException;

public interface ITscpTimestamp {
	
	public byte[] timestamp(byte []data) throws TscpBaseException;
	
	public boolean verifyTimestamp(byte[] data, byte[] timestamp) throws TscpBaseException;

}
