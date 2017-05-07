package org.scorpion.cipher.security.asymmetry;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.cipher.security.ITscpAsymmetry;
import org.scorpion.cipher.security.configuration.AbsTscpCipherConfiguration;
import org.scorpion.cipher.security.configuration.Constant;
import org.scorpion.cipher.security.configuration.TscpCipherInfo;
import org.scorpion.cipher.security.util.TscpCipherManagerUtil;

public class TscpAsymmetryFactory {
	
	public static ITscpAsymmetry getCigher(String name) throws TscpBaseException{
		AbsTscpCipherConfiguration tscpCipherConfiguration = AbsTscpCipherConfiguration.getInstance();
		TscpCipherInfo info = tscpCipherConfiguration.getType(name);
		
		if(info == null || Constant.CipherType.SYMMETRY.equals(info.getType())){
//			return null;
			info = tscpCipherConfiguration.getType("c");
		}
		
		ITscpAsymmetry tscpAsymmetry = null;
		if(Constant.CipherType.ASYMMETRY.equals(info.getType())){	//非对称
			tscpAsymmetry = new TscpCipherAsymmetry(info);
		}else{	//证书方式
			tscpAsymmetry = new TscpCipherCertificate(info);
		}
		
		return tscpAsymmetry;
	}
	
	public static void main(String[] args) throws Exception{
		AbsTscpCipherConfiguration tscpCipherConfiguration = AbsTscpCipherConfiguration.getInstance();
		tscpCipherConfiguration.loadCipherConfiguration();
		ITscpAsymmetry tscpAsymmetry = TscpAsymmetryFactory.getCigher("c");
//		PublicKey pubk = tscpAsymmetry.getPublicKey();
//		PrivateKey prik = tscpAsymmetry.getPrivateKey();
//		byte[] byt = tscpAsymmetry.encrypt(prik, "测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据".getBytes("UTF-8"));
//		String str = new String(tscpAsymmetry.decrypt(pubk, byt), "UTF-8");
//		System.out.println(str);
		
		String str = new String("测试数据");
//		System.out.println(str.getBytes("UTF-8").length);
		String signStr = TscpCipherManagerUtil.signAsymmetry(tscpAsymmetry, str);
		String str2 = new String("测试数据");
		System.out.println(signStr.length());
		System.out.println(TscpCipherManagerUtil.verifyAsymmetry(tscpAsymmetry, signStr, str2));
//		byte[] sign = tscpAsymmetry.sign(str.getBytes("UTF-8"));
//		System.out.println(sign.length);
//		System.out.println(tscpAsymmetry.verify(str.getBytes("UTF-8"), sign));
	}

}
