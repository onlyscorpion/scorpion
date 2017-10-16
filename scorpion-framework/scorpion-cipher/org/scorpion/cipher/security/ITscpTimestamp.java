package org.scorpion.cipher.security;

import org.scorpion.api.exception.ScorpionBaseException;

public interface IScorpionTimestamp {
	
	public byte[] timestamp(byte []data) throws ScorpionBaseException;
	
	public boolean verifyTimestamp(byte[] data, byte[] timestamp) throws ScorpionBaseException;

}
