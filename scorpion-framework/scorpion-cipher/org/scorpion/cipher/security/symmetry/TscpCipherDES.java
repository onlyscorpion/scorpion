package org.scorpion.cipher.security.symmetry;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.cipher.security.AbsScorpionSymmetry;
import org.scorpion.cipher.security.configuration.Constant;
import org.scorpion.cipher.security.configuration.ScorpionCipherInfo;

public class ScorpionCipherDES extends AbsScorpionSymmetry {
	
	public ScorpionCipherDES(ScorpionCipherInfo info) {
		algorithm = Constant.SymmetryAlgorithm.DES;
		keyLength = info.getKeyLength();
	}

	@Override
	public SecretKey byteToSecretKey(byte[] secretKey) throws ScorpionBaseException {
		try {
			DESKeySpec keySpec = new DESKeySpec(secretKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
			return keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			throw new ScorpionBaseException("scorpion-6051:byte密钥转换SecretKey密钥错误", e);
		}
	}
}
