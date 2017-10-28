package com.scorpion.huakerongtong.common.security.impl;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import com.scorpion.huakerongtong.api.kernel.ABigDataDecoderPipeline;

import sun.misc.BASE64Encoder;

/**
 * 天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>
 * com.SCORPION.Scorpion.common
 * <p>
 * File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 SCORPION.COM.CN
 * </p>
 * <p>
 * Company: SCORPION.COM.CN
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class DSADecoder extends ABigDataDecoderPipeline {

	/**
	 * @param key_size
	 * @param seed
	 * @param deciry
	 * @throws Exception
	 */
	private DSADecoder() throws Exception {

	}

	@Override
	public String signMessage(String data, String key) throws Exception {
		
		PKCS8EncodedKeySpec pk = new PKCS8EncodedKeySpec(this.deCode(key));
		KeyFactory kf = KeyFactory.getInstance(this.getAlgorithm());
		PrivateKey k = kf.generatePrivate(pk);
		Signature s = Signature.getInstance(this.getAlgorithm());
		s.initSign(k);
		s.update(data.getBytes());
		return new BASE64Encoder().encode(s.sign());
	}

	@Override
	public boolean validate(String data, String key, String sign)throws Exception {
	
		KeyFactory keyFactory = KeyFactory.getInstance(this.getAlgorithm());
		PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(this.deCode(key)));
		Signature s = Signature.getInstance(this.getAlgorithm());
		s.initVerify(publicKey);
		s.update(data.getBytes());
		return s.verify(this.deCode(sign));
	}

	/**
	 * @param key_size
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public synchronized static ABigDataDecoderPipeline getInstance()throws Exception {
	
		if (dsaDecoder == null)
			dsaDecoder = new DSADecoder();
		return dsaDecoder;
	}

	private static DSADecoder dsaDecoder;

	@Override
	public byte[] encrypt(byte[] data) throws Exception {
		return null;
	}

	@Override
	public byte[] decrypt(byte[] data) throws Exception {
		return null;
	}

	@Override
	public Object getCipherKey(String algorithm) throws Exception {
		
		Map<String, String> mapkey = new HashMap<String, String>();
		mapkey.put("privatekey", this.getPrivateKey());
		mapkey.put("publickey", this.getPublicKey());
		return mapkey;
	}

	@Override
	public byte[] encrypt(byte[] data, int off, int len) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] decrypt(byte[] data, int off, int len) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
