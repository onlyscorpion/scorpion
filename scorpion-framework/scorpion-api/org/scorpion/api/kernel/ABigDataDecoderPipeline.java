package org.scorpion.api.kernel;

import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Decoder;
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
public abstract class ABigDataDecoderPipeline implements IBigDataDecoderPipeline {

	private String seed;
	private int key_size = 1024;
	protected String algorithm;
	private String privateKey;
	private String publicKey;
	private String key;
	protected String RSAWITHMD5 = "Md5withRSA";

	/**
	 * @param key
	 * @return
	 */
	public String getkey(Key key) {
		return enCode(key.getEncoded());
	}

	/**
	 * @param _b
	 * @return
	 */
	public String enCode(byte[] _b) {
		return new BASE64Encoder().encode(_b);
	}

	/**
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public byte[] deCode(String code) throws IOException {
		return new BASE64Decoder().decodeBuffer(code);
	}

	/**
	 * @throws Exception
	 */
	public void initDecoderKey() throws Exception {

		SecureRandom random = getSeed() == null ? new SecureRandom(): new SecureRandom(this.deCode(getSeed()));
		if (SecurityEnum.DecoderAlgorithmType.RSA.name().equals(algorithm.toUpperCase())|| SecurityEnum.DecoderAlgorithmType.DES.equals(algorithm.toUpperCase())) {
			KeyPairGenerator kg = KeyPairGenerator.getInstance(algorithm);
			kg.initialize(this.getKey_size(), random);
			KeyPair kp = kg.genKeyPair();
			PrivateKey privateKey = kp.getPrivate();
			PublicKey publicKey = kp.getPublic();
			this.setPrivateKey(enCode(privateKey.getEncoded()));
			this.setPublicKey(enCode(publicKey.getEncoded()));
		} else if (SecurityEnum.DecoderAlgorithmType.DES.name().equals(algorithm.toUpperCase())) {
			SecureRandom secureRandom = null;
			KeyGenerator kg = KeyGenerator.getInstance(algorithm);
			kg.init(secureRandom);
			SecretKey secretKey = kg.generateKey();
			setKey(enCode(secretKey.getEncoded()));
		} else
			throw new Exception("there is not type to match !");
	}

	public Key turnKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(this.algorithm);
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}

	@Override
	public byte[] encrypt(String data) throws Exception {
	
		if (data == null)
			throw new Exception("error ! encrypting data is null,please check input data .");
	
		return encrypt(data.getBytes());
	}

	@Override
	public byte[] decrypt(String data) throws Exception {
		
		if (data == null)
			throw new Exception("error ! decrypt data is null,please check input data .");
	
		return decrypt(data.getBytes());
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public int getKey_size() {
		return key_size;
	}

	public void setKey_size(int key_size) {
		this.key_size = key_size;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public ABigDataDecoderPipeline setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
		return this;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getKey() {
		return key;
	}

	public ABigDataDecoderPipeline setKey(String key) {
		this.key = key;
		return this;
	}

}
