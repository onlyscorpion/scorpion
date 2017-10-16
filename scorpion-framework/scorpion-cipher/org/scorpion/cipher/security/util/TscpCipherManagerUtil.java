package org.scorpion.cipher.security.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.cipher.security.AbsScorpionDigitalEnvelope;
import org.scorpion.cipher.security.IScorpionAsymmetry;
import org.scorpion.cipher.security.IScorpionSymmetry;
import org.scorpion.cipher.security.asymmetry.ScorpionAsymmetryFactory;
import org.scorpion.cipher.security.symmetry.ScorpionSymmetryFactory;

/**
 * 安全工具类
 * @author Administrator
 *
 */
public class ScorpionCipherManagerUtil {
	
	private static Map<String, IScorpionAsymmetry> asymmetryMap = new HashMap<String, IScorpionAsymmetry>();
	
	private static Map<String, IScorpionSymmetry> symmetryMap = new HashMap<String, IScorpionSymmetry>();
	
	/**
	 * 根据应用标识获取非对称加密类
	 * @param name
	 * @return
	 * @throws ScorpionBaseException
	 */
	public static IScorpionAsymmetry getInstanceAsymmetry(String name) throws ScorpionBaseException{
		if(!asymmetryMap.containsKey(name)){
			synchronized (ScorpionCipherManagerUtil.class) {
				if(!asymmetryMap.containsKey(name)){
					asymmetryMap.put(name, ScorpionAsymmetryFactory.getCigher(name));
				}
			}
		}
		
		return asymmetryMap.get(name);
	}
	
	/**
	 * 根据对称算法名称获取对称加密类
	 * @param name
	 * @return
	 * @throws ScorpionBaseException
	 */
	public static IScorpionSymmetry getInstanceSymmetry(String name) throws ScorpionBaseException{
		if(!symmetryMap.containsKey(name)){
			synchronized (ScorpionCipherManagerUtil.class) {
				if(!symmetryMap.containsKey(name)){
					symmetryMap.put(name, ScorpionSymmetryFactory.getCigher(name));
				}
			}
		}
		
		return symmetryMap.get(name);
	}
	
	public static AbsScorpionDigitalEnvelope getInstanceDigitalEnvelope() throws ScorpionBaseException{
		return AbsScorpionDigitalEnvelope.getInstance();
	}
	
	/**********************************************非对称加密*************************************************/
	
	/**
	 * 非对称加密方法
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ScorpionBaseException
	 */
	public static String encryptAsymmetry(IScorpionAsymmetry asymmetry, String data) throws UnsupportedEncodingException, ScorpionBaseException{
		byte[] byteData = asymmetry.encrypt(asymmetry.getPublicKey(), data.getBytes("UTF-8"));
		return Base64.encodeBase64String(byteData);
	}
	
	/**
	 * 非对称解密方法
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ScorpionBaseException
	 */
	public static String decryptAsymmetry(IScorpionAsymmetry asymmetry, String data) throws UnsupportedEncodingException, ScorpionBaseException{
		byte[] byteData = asymmetry.decrypt(asymmetry.getPrivateKey(), Base64.decodeBase64(data));
		return new String(byteData, "UTF-8");
	}
	
	/**
	 * 非对称签名
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ScorpionBaseException
	 */
	public static String signAsymmetry(IScorpionAsymmetry asymmetry, String data) throws UnsupportedEncodingException, ScorpionBaseException{
		byte[] sign = asymmetry.sign(data.getBytes("UTF-8"));
		return Base64.encodeBase64String(sign);
	}
	
	/**
	 * 非对称验证签名
	 * @param asymmetry
	 * @param sign
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ScorpionBaseException
	 */
	public static boolean verifyAsymmetry(IScorpionAsymmetry asymmetry, String sign, String data) throws UnsupportedEncodingException, ScorpionBaseException{
		byte[] byteData = data.getBytes("UTF-8");
		byte[] byteSign = Base64.decodeBase64(sign);
		return asymmetry.verifySign(byteData, byteSign);
	}
	
	/**********************************************非对称加密*************************************************/
	
	
	
	
	
	
	
	
	/**********************************************对称加密*************************************************/
	
	/**
	 * 对称加密方法
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ScorpionBaseException
	 */
	public static String encryptSymmetry(IScorpionSymmetry symmetry, Key key, String data) throws UnsupportedEncodingException, ScorpionBaseException{
		byte[] byteData = symmetry.encrypt(key, data.getBytes("UTF-8"));
		return Base64.encodeBase64String(byteData);
	}
	
	/**
	 * 对称解密方法
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ScorpionBaseException
	 */
	public static String decryptSymmetry(IScorpionSymmetry symmetry, Key key, String data) throws UnsupportedEncodingException, ScorpionBaseException{
		byte[] byteData = symmetry.decrypt(key, Base64.decodeBase64(data));
		return new String(byteData, "UTF-8");
	}
	
	/**********************************************数字信封加密*************************************************/

	/**
	 * 数字信封加密方法
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ScorpionBaseException
	 */
	public static String encryptDigitalEnvelope(AbsScorpionDigitalEnvelope de, String data) throws UnsupportedEncodingException, ScorpionBaseException{
		byte[] byteData = de.encrypt(data.getBytes("UTF-8"));
		return Base64.encodeBase64String(byteData);
	}
	
	/**
	 * 数字信封解密方法
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ScorpionBaseException
	 */
	public static String decryptDigitalEnvelope(AbsScorpionDigitalEnvelope de, String data) throws UnsupportedEncodingException, ScorpionBaseException{
		byte[] byteData = de.decrypt(Base64.decodeBase64(data));
		return new String(byteData, "UTF-8");
	}
	
	/**
	 * 数字信封签名
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ScorpionBaseException
	 */
	public static String signDigitalEnvelope(AbsScorpionDigitalEnvelope de, String data) throws UnsupportedEncodingException, ScorpionBaseException{
		byte[] sign = de.sign(data.getBytes("UTF-8"));
		return Base64.encodeBase64String(sign);
	}
	
	/**
	 * 数字信封验证签名
	 * @param asymmetry
	 * @param sign
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ScorpionBaseException
	 */
	public static boolean verifySignDigitalEnvelope(AbsScorpionDigitalEnvelope de, String sign, String data) throws UnsupportedEncodingException, ScorpionBaseException{
		byte[] byteData = data.getBytes("UTF-8");
		byte[] byteSign = Base64.decodeBase64(sign);
		return de.verifySign(byteData, byteSign);
	}
	
	/**
	 * 时间戳
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ScorpionBaseException
	 */
	public static String timestampDigitalEnvelope(AbsScorpionDigitalEnvelope de, String data) throws UnsupportedEncodingException, ScorpionBaseException{
		byte[] timestamp = de.timestamp(data.getBytes("UTF-8"));
		return Base64.encodeBase64String(timestamp);
	}
	
	/**
	 * 验证时间戳
	 * @param asymmetry
	 * @param sign
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ScorpionBaseException
	 */
	public static boolean verifyTimestampDigitalEnvelope(AbsScorpionDigitalEnvelope de, String timestamp, String data) throws UnsupportedEncodingException, ScorpionBaseException{
		byte[] byteData = data.getBytes("UTF-8");
		byte[] byteTimestamp = Base64.decodeBase64(timestamp);
		return de.verifyTimestamp(byteData, byteTimestamp);
	}
	
	/**********************************************数字信封加密*************************************************/
	
	
	
}
