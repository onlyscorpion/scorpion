package org.scorpion.cipher.security;

import org.scorpion.api.exception.ScorpionBaseException;

public interface IScorpionSignature {
	
	public byte[] sign(byte[] data) throws ScorpionBaseException;
	
	public boolean verifySign(byte[] data, byte[] sign) throws ScorpionBaseException;

}
