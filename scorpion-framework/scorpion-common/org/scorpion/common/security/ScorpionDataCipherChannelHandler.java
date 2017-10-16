package org.scorpion.common.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.scorpion.api.kernel.ABigDataDecoderPipeline;
import org.scorpion.api.kernel.SecurityEnum;

/**
 * 天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.Scorpion.common
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
public class ScorpionDataCipherChannelHandler {

	/**
	 * @param data
	 * @param off
	 * @param len
	 * @param key
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] cipherEncryptDataHandler(byte[] data, int off,int len, String key, String algorithm) throws Exception {
		if (key == null || "".equals(key))
			throw new Exception("Encrypt exception,secret key is null !");
		if (algorithm == null || "".equals(algorithm))
			throw new Exception("algorithm type is null !");
		byte[] b = new byte[len];
		if (data == null) {
			throw new NullPointerException();
		} else if ((off < 0) || (off > b.length) || (len < 0)|| ((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return null;
		}
		for (int i = 0; i < len; i++) {
			b[i] = (data[off + i]);
		}
		ABigDataDecoderPipeline cipherPrimer = ScorpionSecurityFactory.getDataDecoder(SecurityEnum.DecoderAlgorithmType.DES.name());
		return cipherPrimer.setKey(key).setAlgorithm(algorithm).encrypt(b);
	}

	/**
	 * @param data
	 * @param key
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] cipherEncryptDataHandler(byte[] data, String key,String algorithm) throws Exception {

		ABigDataDecoderPipeline cipherPrimer = ScorpionSecurityFactory.getDataDecoder(SecurityEnum.DecoderAlgorithmType.DES.name());
		return cipherPrimer.setKey(key).setAlgorithm(algorithm).encrypt(data);
	}

	/**
	 * @param data
	 * @param off
	 * @param len
	 * @param key
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] cipherDecryptDataHandler(byte[] data, int off,int len, String key, String algorithm) throws Exception {
		if (key == null || "".equals(key))
			throw new Exception("Decrypt exception,secret key is null !");
		if (algorithm == null || "".equals(algorithm))
			throw new Exception("algorithm type is null !");
		byte[] b = new byte[len];
		if (data == null) {
			throw new NullPointerException();
		} else if ((off < 0) || (off > b.length) || (len < 0)|| ((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return null;
		}
		for (int i = 0; i < len; i++) {
			b[i] = (data[off + i]);
		}

		ABigDataDecoderPipeline cipherPrimer = ScorpionSecurityFactory.getDataDecoder(SecurityEnum.DecoderAlgorithmType.DES.name());
		return cipherPrimer.setKey(key).setAlgorithm(algorithm).decrypt(b);
	}

	/**
	 * @param data
	 * @param key
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] cipherDecryptDataHandler(byte[] data, String key,String algorithm) throws Exception {
		if (key == null || "".equals(key))
			throw new Exception("Decrypt exception,secret key is null !");
		if (algorithm == null || "".equals(algorithm))
			throw new Exception("algorithm type is null !");
		ABigDataDecoderPipeline cipherPrimer = ScorpionSecurityFactory.getDataDecoder(SecurityEnum.DecoderAlgorithmType.DES.name());
		return cipherPrimer.setKey(key).setAlgorithm(algorithm).decrypt(data);
	}

	public static void main(String[] args) throws Exception {

		InputStream in = new FileInputStream("d:/11.txt");
		OutputStream out = new FileOutputStream("e:/11.txt");
		byte[] b = new byte[8];
		int l;
		while ((l = in.read(b)) > 0) {

			byte[] _b = cipherDecryptDataHandler(b, 0, l, "ut+dIEbTO5Q=", "DES");
			out.write(_b);
		}
		out.close();
		in.close();
	}

}
