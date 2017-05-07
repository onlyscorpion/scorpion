package org.scorpion.cipher.security.asymmetry;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import org.scorpion.cipher.security.configuration.Constant;


public class CertificateInfo {
	
	public static String getSigAlgName(Certificate cert){
		if(Constant.CertificateType.X509.equals(cert.getType())){
			X509Certificate x509 = (X509Certificate)cert;
			return x509.getSigAlgName();
		}
		return null;
	}

}
