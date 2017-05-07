package org.scorpion.cipher.security.asymmetry;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.cipher.security.AbsTscpAsymmetry;
import org.scorpion.cipher.security.ITscpAsymmetry;
import org.scorpion.cipher.security.configuration.TscpCipherInfo;

public class TscpCipherCertificate extends AbsTscpAsymmetry implements ITscpAsymmetry{
	
	private Certificate cert;

	public TscpCipherCertificate(TscpCipherInfo info) throws TscpBaseException {
		initParam(info);
	}
	
	private void initParam(TscpCipherInfo info) throws TscpBaseException {
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
			throw new TscpBaseException("TSCP-6053:证书获取密钥对失败!", e);
		}finally{
			try{
				if(certIn != null){
					certIn.close();
				}
			}catch(Exception e){
				throw new TscpBaseException("TSCP-6053:证书获取密钥对失败!", e);
			}
			
			try{
				if(KeyStoreIn != null){
					KeyStoreIn.close();
				}
			}catch(Exception e){
				throw new TscpBaseException("TSCP-6053:证书获取密钥对失败!", e);
			}
		}
	}
	
	@Override
	public boolean verifySign(byte[] data, byte[] sign) throws TscpBaseException {
		try{
			Signature signature = Signature.getInstance(this.signAlgorithm);
			signature.initVerify(cert);
			signature.update(data);
			return signature.verify(sign);
//			return true;
		}catch(Exception e){
			throw new TscpBaseException("TSCP-6031:sign verify exception!", e);
		}
	}
}
