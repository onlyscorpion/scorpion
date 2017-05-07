package org.scorpion.cipher.security.asymmetry;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.cipher.security.AbsTscpAsymmetry;
import org.scorpion.cipher.security.configuration.TscpCipherInfo;

public class TscpCipherAsymmetry extends AbsTscpAsymmetry{

	public TscpCipherAsymmetry(TscpCipherInfo info) throws TscpBaseException {
		initParam(info);
	}
	
	private void initParam(TscpCipherInfo info) throws TscpBaseException {
		try{
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(info.getAlgorithm());
			keyPairGen.initialize(info.getKeyLength());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			this.privateKey = keyPair.getPrivate();
			this.publickKey = keyPair.getPublic();
			this.signAlgorithm = info.getSignAlgorithm();
		}catch(Exception e){
			throw new TscpBaseException("TSCP-6052:生成密钥对失败!", e);
		}
	}
	
	@Override
	public boolean verifySign(byte[] data, byte[] sign) throws TscpBaseException {
		try{
			Signature signature = Signature.getInstance(this.signAlgorithm);
			signature.initVerify(this.publickKey);
			signature.update(data);
			return signature.verify(sign);
//			return true;
		}catch(Exception e){
			throw new TscpBaseException("TSCP-6031:sign verify exception!", e);
		}
	}
}
