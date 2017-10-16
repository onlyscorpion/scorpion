package org.scorpion.cipher.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import org.scorpion.api.exception.ScorpionBaseException;

public abstract class AbsScorpionAsymmetry extends ScorpionCipherKeyImpl implements IScorpionAsymmetry{
	
	protected String signAlgorithm;
	
	protected PrivateKey privateKey;
	
	protected PublicKey publickKey;

	@Override
	public byte[] sign(byte[] data) throws ScorpionBaseException {
		try{
			Signature signature = Signature.getInstance(this.signAlgorithm);
			signature.initSign(this.privateKey);
			signature.update(data);
			return signature.sign();
		}catch(Exception e){
			throw new ScorpionBaseException("scorpion-6030:sign exception!", e);
		}
	}
	
	@Override
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	@Override
	public PublicKey getPublicKey() {
		return this.publickKey;
	}
}
