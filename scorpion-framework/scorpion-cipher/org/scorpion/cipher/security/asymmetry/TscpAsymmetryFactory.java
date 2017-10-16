package org.scorpion.cipher.security.asymmetry;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.cipher.security.IScorpionAsymmetry;
import org.scorpion.cipher.security.configuration.AbsScorpionCipherConfiguration;
import org.scorpion.cipher.security.configuration.Constant;
import org.scorpion.cipher.security.configuration.ScorpionCipherInfo;
import org.scorpion.cipher.security.util.ScorpionCipherManagerUtil;

public class ScorpionAsymmetryFactory {
	
	public static IScorpionAsymmetry getCigher(String name) throws ScorpionBaseException{
		AbsScorpionCipherConfiguration ScorpionCipherConfiguration = AbsScorpionCipherConfiguration.getInstance();
		ScorpionCipherInfo info = ScorpionCipherConfiguration.getType(name);
		
		if(info == null || Constant.CipherType.SYMMETRY.equals(info.getType())){
//			return null;
			info = ScorpionCipherConfiguration.getType("c");
		}
		
		IScorpionAsymmetry ScorpionAsymmetry = null;
		if(Constant.CipherType.ASYMMETRY.equals(info.getType())){	//非对称
			ScorpionAsymmetry = new ScorpionCipherAsymmetry(info);
		}else{	//证书方式
			ScorpionAsymmetry = new ScorpionCipherCertificate(info);
		}
		
		return ScorpionAsymmetry;
	}
	
	public static void main(String[] args) throws Exception{
		AbsScorpionCipherConfiguration ScorpionCipherConfiguration = AbsScorpionCipherConfiguration.getInstance();
		ScorpionCipherConfiguration.loadCipherConfiguration();
		IScorpionAsymmetry ScorpionAsymmetry = ScorpionAsymmetryFactory.getCigher("c");
//		PublicKey pubk = ScorpionAsymmetry.getPublicKey();
//		PrivateKey prik = ScorpionAsymmetry.getPrivateKey();
//		byte[] byt = ScorpionAsymmetry.encrypt(prik, "测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据".getBytes("UTF-8"));
//		String str = new String(ScorpionAsymmetry.decrypt(pubk, byt), "UTF-8");
//		System.out.println(str);
		
		String str = new String("测试数据");
//		System.out.println(str.getBytes("UTF-8").length);
		String signStr = ScorpionCipherManagerUtil.signAsymmetry(ScorpionAsymmetry, str);
		String str2 = new String("测试数据");
		System.out.println(signStr.length());
		System.out.println(ScorpionCipherManagerUtil.verifyAsymmetry(ScorpionAsymmetry, signStr, str2));
//		byte[] sign = ScorpionAsymmetry.sign(str.getBytes("UTF-8"));
//		System.out.println(sign.length);
//		System.out.println(ScorpionAsymmetry.verify(str.getBytes("UTF-8"), sign));
	}

}
