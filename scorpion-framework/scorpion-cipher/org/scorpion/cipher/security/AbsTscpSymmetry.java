package org.scorpion.cipher.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.scorpion.api.exception.TscpBaseException;

public abstract class AbsTscpSymmetry extends TscpCipherKeyImpl implements ITscpSymmetry {
	
	protected String algorithm;
	
	protected int keyLength;
	
	@Override
	public SecretKey generateSecretKey() throws TscpBaseException {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
			keyGenerator.init(keyLength);
			return keyGenerator.generateKey();
		} catch (Exception e) {
			throw new TscpBaseException("TSCP-6050:生成密钥错误", e);
		}
	}

	@Override
	public byte[] generateSecretKeyEncoded() throws TscpBaseException {
		return this.generateSecretKey().getEncoded();
	}

}
