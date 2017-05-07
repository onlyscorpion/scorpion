package org.scorpion.common.security.impl;

import java.security.Key;

import javax.crypto.Cipher;

import org.scorpion.api.kernel.ABigDataDecoderPipeline;

/**
 * 自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.tscp.common
 * <p>
 * File: AbsTscpFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 taiji.com.cn
 * </p>
 * <p>
 * Company: taiji.com.cn
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class DESDecoder extends ABigDataDecoderPipeline {

	private DESDecoder() {

	}

	@Override
	public String signMessage(String data, String key) throws Exception {
		return null;
	}

	@Override
	public boolean validate(String data, String key, String sign)throws Exception {
		return false;
	}

	@Override
	public byte[] encrypt(byte[] data) throws Exception {
		Key k = turnKey(deCode(this.getKey()));
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	@Override
	public byte[] decrypt(byte[] data) throws Exception {
		Key k = turnKey(deCode(this.getKey()));
		Cipher cipher = Cipher.getInstance(this.getAlgorithm());
		 //Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	/**
	 * @return
	 */
	public synchronized static ABigDataDecoderPipeline getInstance() {

		if (decodePipeline == null)
			decodePipeline = new DESDecoder();
		return decodePipeline;
	}

	public static ABigDataDecoderPipeline decodePipeline;

	@Override
	public Object getCipherKey(String algorithm) throws Exception {
		return this.getKey();
	}

	@Override
	public byte[] encrypt(byte[] data, int off, int len) throws Exception {
		Key k = turnKey(deCode(this.getKey()));
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data,off,len);
	}

	@Override
	public byte[] decrypt(byte[] data, int off, int len) throws Exception {
		Key k = turnKey(deCode(this.getKey()));
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data,off,len);
	}

}
