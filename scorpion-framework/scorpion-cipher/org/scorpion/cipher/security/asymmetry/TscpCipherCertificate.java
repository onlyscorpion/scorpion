package org.scorpion.cipher.security.asymmetry;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.cipher.security.AbsScorpionAsymmetry;
import org.scorpion.cipher.security.IScorpionAsymmetry;
import org.scorpion.cipher.security.configuration.ScorpionCipherInfo;

public class ScorpionCipherCertificate extends AbsScorpionAsymmetry implements IScorpionAsymmetry{
	
	private Certificate cert;

	public ScorpionCipherCertificate(ScorpionCipherInfo info) throws ScorpionBaseException {
		initParam(info);
	}
	
	private void initParam(ScorpionCipherInfo info) throws ScorpionBaseException {
		FileInputStream certIn = null;
		FileInputStream KeyStoreIn = null;
		try{
			//获取公钥
			CertificateFactory certFactory = CertificateFactory.getInstance(info.getCerType());
			certIn = new FileInputStream(info.getCerPath());
			this.cert = certFactory.generateCertificate(certIn);
			this.publickKey = this.cert.getPublicKey();
			
			//获取私钥
			KeyStore keyStore = KeyStore.getInstance(info.getKeyStoreType());
			KeyStoreIn = new FileInputStream(info.getKeyStorePath());
			keyStore.load(KeyStoreIn, info.getPassword().toCharArray());
			this.privateKey = (PrivateKey) keyStore.getKey(info.getAlias(), info.getPassword().toCharArray());
			
			this.signAlgorithm = CertificateInfo.getSigAlgName(this.cert);
		}catch(Exception e){
			throw new ScorpionBaseException("scorpion-6053:证书获取密钥对失败!", e);
		}finally{
			try{
				if(certIn != null){
					certIn.close();
				}
			}catch(Exception e){
				throw new ScorpionBaseException("scorpion-6053:证书获取密钥对失败!", e);
			}
			
			try{
				if(KeyStoreIn != null){
					KeyStoreIn.close();
				}
			}catch(Exception e){
				throw new ScorpionBaseException("scorpion-6053:证书获取密钥对失败!", e);
			}
		}
	}
	
	@Override
	public boolean verifySign(byte[] data, byte[] sign) throws ScorpionBaseException {
		try{
			Signature signature = Signature.getInstance(this.signAlgorithm);
			signature.initVerify(cert);
			signature.update(data);
			return signature.verify(sign);
//			return true;
		}catch(Exception e){
			throw new ScorpionBaseException("scorpion-6031:sign verify exception!", e);
		}
	}
}
