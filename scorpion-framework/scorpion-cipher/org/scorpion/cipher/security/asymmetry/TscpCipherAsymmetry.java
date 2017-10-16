package org.scorpion.cipher.security.asymmetry;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.cipher.security.AbsScorpionAsymmetry;
import org.scorpion.cipher.security.configuration.ScorpionCipherInfo;

public class ScorpionCipherAsymmetry extends AbsScorpionAsymmetry{

	public ScorpionCipherAsymmetry(ScorpionCipherInfo info) throws ScorpionBaseException {
		initParam(info);
	}
	
	private void initParam(ScorpionCipherInfo info) throws ScorpionBaseException {
		try{
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(info.getAlgorithm());
			keyPairGen.initialize(info.getKeyLength());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			this.privateKey = keyPair.getPrivate();
			this.publickKey = keyPair.getPublic();
			this.signAlgorithm = info.getSignAlgorithm();
		}catch(Exception e){
			throw new ScorpionBaseException("scorpion-6052:生成密钥对失败!", e);
		}
	}
	
	@Override
	public boolean verifySign(byte[] data, byte[] sign) throws ScorpionBaseException {
		try{
			Signature signature = Signature.getInstance(this.signAlgorithm);
			signature.initVerify(this.publickKey);
			signature.update(data);
			return signature.verify(sign);
//			return true;
		}catch(Exception e){
			throw new ScorpionBaseException("scorpion-6031:sign verify exception!", e);
		}
	}
}
