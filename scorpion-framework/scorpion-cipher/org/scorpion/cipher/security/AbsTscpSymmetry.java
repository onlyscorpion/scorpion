package org.scorpion.cipher.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.scorpion.api.exception.ScorpionBaseException;

public abstract class AbsScorpionSymmetry extends ScorpionCipherKeyImpl implements IScorpionSymmetry {
	
	protected String algorithm;
	
	protected int keyLength;
	
	@Override
	public SecretKey generateSecretKey() throws ScorpionBaseException {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
			keyGenerator.init(keyLength);
			return keyGenerator.generateKey();
		} catch (Exception e) {
			throw new ScorpionBaseException("scorpion-6050:生成密钥错误", e);
		}
	}

	@Override
	public byte[] generateSecretKeyEncoded() throws ScorpionBaseException {
		return this.generateSecretKey().getEncoded();
	}

}
