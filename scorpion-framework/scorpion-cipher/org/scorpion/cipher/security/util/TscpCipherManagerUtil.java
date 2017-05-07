package org.scorpion.cipher.security.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.cipher.security.AbsTscpDigitalEnvelope;
import org.scorpion.cipher.security.ITscpAsymmetry;
import org.scorpion.cipher.security.ITscpSymmetry;
import org.scorpion.cipher.security.asymmetry.TscpAsymmetryFactory;
import org.scorpion.cipher.security.symmetry.TscpSymmetryFactory;

/**
 * 安全工具类
 * @author Administrator
 *
 */
public class TscpCipherManagerUtil {
	
	private static Map<String, ITscpAsymmetry> asymmetryMap = new HashMap<String, ITscpAsymmetry>();
	
	private static Map<String, ITscpSymmetry> symmetryMap = new HashMap<String, ITscpSymmetry>();
	
	/**
	 * 根据应用标识获取非对称加密类
	 * @param name
	 * @return
	 * @throws TscpBaseException
	 */
	public static ITscpAsymmetry getInstanceAsymmetry(String name) throws TscpBaseException{
		if(!asymmetryMap.containsKey(name)){
			synchronized (TscpCipherManagerUtil.class) {
				if(!asymmetryMap.containsKey(name)){
					asymmetryMap.put(name, TscpAsymmetryFactory.getCigher(name));
				}
			}
		}
		
		return asymmetryMap.get(name);
	}
	
	/**
	 * 根据对称算法名称获取对称加密类
	 * @param name
	 * @return
	 * @throws TscpBaseException
	 */
	public static ITscpSymmetry getInstanceSymmetry(String name) throws TscpBaseException{
		if(!symmetryMap.containsKey(name)){
			synchronized (TscpCipherManagerUtil.class) {
				if(!symmetryMap.containsKey(name)){
					symmetryMap.put(name, TscpSymmetryFactory.getCigher(name));
				}
			}
		}
		
		return symmetryMap.get(name);
	}
	
	public static AbsTscpDigitalEnvelope getInstanceDigitalEnvelope() throws TscpBaseException{
		return AbsTscpDigitalEnvelope.getInstance();
	}
	
	/**********************************************非对称加密*************************************************/
	
	/**
	 * 非对称加密方法
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws TscpBaseException
	 */
	public static String encryptAsymmetry(ITscpAsymmetry asymmetry, String data) throws UnsupportedEncodingException, TscpBaseException{
		byte[] byteData = asymmetry.encrypt(asymmetry.getPublicKey(), data.getBytes("UTF-8"));
		return Base64.encodeBase64String(byteData);
	}
	
	/**
	 * 非对称解密方法
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws TscpBaseException
	 */
	public static String decryptAsymmetry(ITscpAsymmetry asymmetry, String data) throws UnsupportedEncodingException, TscpBaseException{
		byte[] byteData = asymmetry.decrypt(asymmetry.getPrivateKey(), Base64.decodeBase64(data));
		return new String(byteData, "UTF-8");
	}
	
	/**
	 * 非对称签名
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws TscpBaseException
	 */
	public static String signAsymmetry(ITscpAsymmetry asymmetry, String data) throws UnsupportedEncodingException, TscpBaseException{
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
	 * @throws TscpBaseException
	 */
	public static boolean verifyAsymmetry(ITscpAsymmetry asymmetry, String sign, String data) throws UnsupportedEncodingException, TscpBaseException{
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
	 * @throws TscpBaseException
	 */
	public static String encryptSymmetry(ITscpSymmetry symmetry, Key key, String data) throws UnsupportedEncodingException, TscpBaseException{
		byte[] byteData = symmetry.encrypt(key, data.getBytes("UTF-8"));
		return Base64.encodeBase64String(byteData);
	}
	
	/**
	 * 对称解密方法
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws TscpBaseException
	 */
	public static String decryptSymmetry(ITscpSymmetry symmetry, Key key, String data) throws UnsupportedEncodingException, TscpBaseException{
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
	 * @throws TscpBaseException
	 */
	public static String encryptDigitalEnvelope(AbsTscpDigitalEnvelope de, String data) throws UnsupportedEncodingException, TscpBaseException{
		byte[] byteData = de.encrypt(data.getBytes("UTF-8"));
		return Base64.encodeBase64String(byteData);
	}
	
	/**
	 * 数字信封解密方法
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws TscpBaseException
	 */
	public static String decryptDigitalEnvelope(AbsTscpDigitalEnvelope de, String data) throws UnsupportedEncodingException, TscpBaseException{
		byte[] byteData = de.decrypt(Base64.decodeBase64(data));
		return new String(byteData, "UTF-8");
	}
	
	/**
	 * 数字信封签名
	 * @param asymmetry
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws TscpBaseException
	 */
	public static String signDigitalEnvelope(AbsTscpDigitalEnvelope de, String data) throws UnsupportedEncodingException, TscpBaseException{
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
	 * @throws TscpBaseException
	 */
	public static boolean verifySignDigitalEnvelope(AbsTscpDigitalEnvelope de, String sign, String data) throws UnsupportedEncodingException, TscpBaseException{
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
	 * @throws TscpBaseException
	 */
	public static String timestampDigitalEnvelope(AbsTscpDigitalEnvelope de, String data) throws UnsupportedEncodingException, TscpBaseException{
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
	 * @throws TscpBaseException
	 */
	public static boolean verifyTimestampDigitalEnvelope(AbsTscpDigitalEnvelope de, String timestamp, String data) throws UnsupportedEncodingException, TscpBaseException{
		byte[] byteData = data.getBytes("UTF-8");
		byte[] byteTimestamp = Base64.decodeBase64(timestamp);
		return de.verifyTimestamp(byteData, byteTimestamp);
	}
	
	/**********************************************数字信封加密*************************************************/
	
	
	
}
