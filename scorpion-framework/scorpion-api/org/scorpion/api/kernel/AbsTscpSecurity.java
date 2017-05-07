package org.scorpion.api.kernel;

import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: if developer want to create a component. the developer must extends the abstract </p>
 * <p>class ATscpComponet. the ATscpComponent exist life cycle. developer can override</p>
 * <p>the initialization method or service method or destroy method to handle themselves business</p>
 * <p>but we don't suggest the developer do that </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsTscpSecurity {
   
	private String seed;
	
	private int key_size = 1024;
	
	protected String algorithm;

	private String privateKey;
	
	private String publicKey;
	
	protected String RSAWITHMD5 = "Md5withRSA";

	
	
	/**
	 * 获取密钥
	 * 
	 * @param key
	 * 
	 * @return
	 * 
	 */
	public String getkey(Key key) {
	
		return enCode(key.getEncoded());
	
	}

	/**
	 * 
	 * @param _b
	 * @return
	 */
	public String enCode(byte[] _b) {
	
		return new BASE64Encoder().encode(_b);
	
	}

	/**
	 * BASE64 DECODE转换
	 * 
	 * @param code
	 * 
	 * @return
	 * 
	 * @throws IOException
	 * 
	 */
	public byte[] deCode(String code) throws IOException {
	
		return new BASE64Decoder().decodeBuffer(code);
	
	}

	/**
	 * 初始化秘钥
	 * 
	 * @throws Exception
	 * 
	 */
	public void initSecurityKey() throws Exception {
			
		KeyPairGenerator kg = KeyPairGenerator.getInstance(algorithm);
		SecureRandom random = new SecureRandom();
		random.setSeed(this.getSeed().getBytes());
		kg.initialize(this.getKey_size(), random);
		KeyPair kp = kg.genKeyPair();
		PrivateKey privateKey =  kp.getPrivate();
		PublicKey publicKey =  kp.getPublic();
		this.setPrivateKey(enCode(privateKey.getEncoded()));
		this.setPublicKey(enCode(publicKey.getEncoded()));
	
	}
    
	/**
	 * @return
	 */
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

	
	public void setAlgorithm(String algorithm) {
	
		this.algorithm = algorithm;
	
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

}
