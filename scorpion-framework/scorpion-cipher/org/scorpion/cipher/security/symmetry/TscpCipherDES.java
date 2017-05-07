package org.scorpion.cipher.security.symmetry;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.cipher.security.AbsTscpSymmetry;
import org.scorpion.cipher.security.configuration.Constant;
import org.scorpion.cipher.security.configuration.TscpCipherInfo;

public class TscpCipherDES extends AbsTscpSymmetry {
	
	public TscpCipherDES(TscpCipherInfo info) {
		algorithm = Constant.SymmetryAlgorithm.DES;
		keyLength = info.getKeyLength();
	}

	@Override
	public SecretKey byteToSecretKey(byte[] secretKey) throws TscpBaseException {
		try {
			DESKeySpec keySpec = new DESKeySpec(secretKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
			return keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			throw new TscpBaseException("TSCP-6051:byte密钥转换SecretKey密钥错误", e);
		}
	}
}
