package org.scorpion.cipher.security;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import org.scorpion.api.exception.TscpBaseException;

public abstract class AbsTscpAsymmetry extends TscpCipherKeyImpl implements ITscpAsymmetry{
	
	protected String signAlgorithm;
	
	protected PrivateKey privateKey;
	
	protected PublicKey publickKey;

	@Override
	public byte[] sign(byte[] data) throws TscpBaseException {
		try{
			Signature signature = Signature.getInstance(this.signAlgorithm);
			signature.initSign(this.privateKey);
			signature.update(data);
			return signature.sign();
		}catch(Exception e){
			throw new TscpBaseException("TSCP-6030:sign exception!", e);
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
