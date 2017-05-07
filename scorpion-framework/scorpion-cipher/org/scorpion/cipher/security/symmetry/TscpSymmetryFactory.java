package org.scorpion.cipher.security.symmetry;

import java.security.Key;

import org.scorpion.cipher.security.ITscpAsymmetry;
import org.scorpion.cipher.security.ITscpSymmetry;
import org.scorpion.cipher.security.configuration.AbsTscpCipherConfiguration;
import org.scorpion.cipher.security.configuration.Constant;
import org.scorpion.cipher.security.configuration.TscpCipherInfo;
import org.scorpion.cipher.security.util.TscpCipherManagerUtil;

public class TscpSymmetryFactory {
	
	public static ITscpSymmetry getCigher(String name){
		AbsTscpCipherConfiguration tscpCipherConfiguration = AbsTscpCipherConfiguration.getInstance();
		TscpCipherInfo info = tscpCipherConfiguration.getType(name);
		
		if(info == null || !Constant.CipherType.SYMMETRY.equals(info.getType())){
			return null;
		}
		
		ITscpSymmetry tscpSymmetry = null;
		if(Constant.SymmetryAlgorithm.DES.equals(info.getAlgorithm())){
			tscpSymmetry = new TscpCipherDES(info);
		}
		
		return tscpSymmetry;
	}
	
	public static void main(String[] args) throws Exception{
//		AbsTscpCipherConfiguration tscpCipherConfiguration = AbsTscpCipherConfiguration.getInstance();
//		tscpCipherConfiguration.loadCipherConfiguration();
//		ITscpSymmetry tscpSymmetry = TscpSymmetryFactory.getCigher("DES");
//		Key key = tscpSymmetry.generateSecretKey();
//		System.out.println(Base64.encodeBase64String(tscpSymmetry.generateSecretKeyEncoded()).getBytes("UTF-8").length);
//		System.out.println(Base64.encodeBase64String(tscpSymmetry.generateSecretKeyEncoded()).getBytes("UTF-8").length);
//		System.out.println(Base64.encodeBase64String(tscpSymmetry.generateSecretKeyEncoded()).getBytes("UTF-8").length);
//		byte[] byt = tscpSymmetry.encrypt(key, "测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据".getBytes("UTF-8"));
////		String str = new String(tscpSymmetry.decrypt(key, byt), "UTF-8");
//		byt = "测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据测试加密数据".getBytes("UTF-8");
//		System.out.println(Base64.encodeBase64String(byt));
//		System.out.println(TscpCipherUtil.bytesToHexString(TscpCipherUtil.MD5Encoding(byt)));
//		System.out.println(TscpCipherUtil.bytesToHexString(TscpCipherUtil.MD5Encoding(byt)));
		AbsTscpCipherConfiguration tscpCipherConfiguration = AbsTscpCipherConfiguration.getInstance();
		tscpCipherConfiguration.loadCipherConfiguration();
		ITscpSymmetry tscpSymmetry = TscpSymmetryFactory.getCigher("DES");
		String key1 = "cVs14FPWEk7DFJeLkRe3pcMzU54akXG1/Yl7qZ7anWWt90hvXuPJEx3WtpiiVjrHtcux9La/+4QWIfvLq0khDXujCKP8NKUvmBz5Jeu1tDvxoUW9SfRK59LnGucdlrVHIYsT7ByhrOQn4j0TIYs6KASYp1cwzCFX5v9BgHZlo4IjMLhkj93BX1w096nOhEsqsIt8Tig+mKdpx+WofnI2jrv9KcGm30cg5m8OzBK5LAyo0FYg17o3xBUSVuFHRzbQVC8RJ3Jcf/maYyx9DHJ1Ubo1mOq4y3STWq5f/Y+WKnIdPtPeg7BRDiTmah/CoLjcLFuH4ATKjDR6sbsbwRmgOQ==";
		String key2 = "rGxITAOtosUsV4mpz+U6/A2FQOUHNK+nEhJF61a5S/hrA0n/ewTOjlJLCZp46oql53AvmrKjy/UT1AookmPiIDqvZLGMTxlpFm7Hy1CH9EF9OGQc80Nb1/9tgTwrpPcMYuiBankiqH/9Jgg3DrFbfsvQC4Wgj+gGMig+Jin6AzF6Ts3FQ8AVkvqYjPBvzqwFmYUKklErA8JOtpRFlqOMxAw4wrDPOadISgXQUo0qit4eIhFkIjz/iJDPTKLIMuJhnppFRdjE36ZY6wVtWlc1sDoqPXa7/9CPLrGxKLkHvXjlyaecuSAiCqD7m+PrBiCAaaK7aN3p9QBg13vVXMKvvw==";
		Key key = tscpSymmetry.generateSecretKey();
		ITscpAsymmetry asymmetryCurrent = TscpCipherManagerUtil.getInstanceAsymmetry(null);
		key1 = TscpCipherManagerUtil.decryptAsymmetry(asymmetryCurrent, key1);
		key1 = TscpCipherManagerUtil.decryptAsymmetry(asymmetryCurrent, key2);
		
//		Key key = tscpSymmetry.byteToSecretKey(Base64.decodeBase64(key1));
		String data = TscpCipherManagerUtil.encryptSymmetry(tscpSymmetry, key, 
				"123456789");
		System.out.println(data);
	}

}
