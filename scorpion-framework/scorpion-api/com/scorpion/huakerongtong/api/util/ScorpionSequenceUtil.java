package com.scorpion.huakerongtong.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionSequenceUtil {
	
	
	public static String encryptionType = "MD5";
	

	public final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	
	/**
	 * 
	 * @param bByte
	 * 
	 * @return
	 */
	public static String byteToNumber(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		return String.valueOf(iRet);
	}
	

	/**
	 * @param _b
	 * 
	 * @return
	 */
	public static String byteToHexString(byte _b){
		
		return hexDigits[_b >>> 4 & 0xf]+hexDigits[_b & 0xf];
	}
	  
	
	/**
	 * @description generate string type MD5 code
	 * 
	 * @param encryptionData 需要生成离散数列的数据
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public static String generateMd5EncryptString(String encryptionData) throws ScorpionBaseException{
		
		try {
			byte[] md5data = generateMd5EncryptByte(encryptionData.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < md5data.length; i++) {
				sb.append(byteToHexString(md5data[i]));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new ScorpionBaseException("scorpion-7864:Generate MD5 code failure !",e);
		}
	}
	
	
	/**
	 * @param btInput
	 * @return
	 * @throws ScorpionBaseException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] generateMd5EncryptByte(byte[] btInput) throws ScorpionBaseException, NoSuchAlgorithmException{
	
		MessageDigest md5Digest = MessageDigest.getInstance(encryptionType);
		md5Digest.update(btInput);
		return md5Digest.digest();
	}
	
	
	/**
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] generateMd5EncryptForFile(File file) throws IOException, NoSuchAlgorithmException{
		
		if(!file.exists())
			throw new FileNotFoundException("scorpion-4907:The file["+file.getPath()+"] not fonud ....");
		
		InputStream in = null;
		try{
			MessageDigest md5Digest = MessageDigest.getInstance(encryptionType);
			in = new FileInputStream(file);
			int len;
			byte[] b = new byte[1024];
			while((len = in.read(b))>0){
				md5Digest.update(b, 0, len);
			}
			return md5Digest.digest();
		}finally{
			if(in != null)
				in.close();
		}
	}
	


	
	/**
	 * @description generate number type MD5码
	 * 
	 * @param encryptionData
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public static String generateMd5EncryptNumber(String encryptionData) throws ScorpionBaseException{

		try {
			byte[] btInput = encryptionData.getBytes();
			MessageDigest md5Digest = MessageDigest.getInstance(encryptionType);
			md5Digest.update(btInput);
			byte[] md5data = md5Digest.digest();
			StringBuilder sBuffer = new StringBuilder();
	
			for (int i = 0; i < md5data.length; i++) {
				sBuffer.append(byteToNumber(md5data[i]));
			}
			return sBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new ScorpionBaseException("scorpion-7864:Generate MD5 code failure",e);
		}
	}
	

	
	/**
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public static String generateSequeueString(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) throws ScorpionBaseException {
		
		System.out.println(generateMd5EncryptString("afafasfafas"));
		
	}

}
