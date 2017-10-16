package org.scorpion.cipher.security.configuration;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: Constant.java create time:2015-8-25下午03:00:00</p> 
 * <p>Title: Constant class </p>
 * <p>Description: system constant information </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: Constant</p>
 * @author  于洪斌
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class Constant {
	
	/**
	 * 配置文件名称
	 */
	public final static String CIPHER_CONFIG_FILE = "scorpion-cipher.xml";
	
	public final static String ENCODING = "UTF-8";
	
	/**
	 * 配置文件标签
	 * @author yhb
	 *
	 */
	public final static class Cipher{
		public final static String CIPHERS = "ciphers";
		public final static String CIPHER = "cipher";
		public final static String NAME = "name";
		public final static String TYPE = "type";
		public final static String ALGORITHM = "algorithm";
		public final static String SIGNALGORITHM = "signAlgorithm";
		public final static String CERPATH = "cerPath";
		public final static String CERTYPE = "cerType";
		public final static String KEYLENGTH = "keyLength";
		public final static String KEYSTOREPATH = "keyStorePath";
		public final static String KEYSTORETYPE = "keyStoreType";
		public final static String ALIAS = "alias";
		public final static String PASSWORD = "password";
	}
	
	/**
	 * 对称加密算法
	 * @author yhb
	 *
	 */
	public final static class SymmetryAlgorithm{
		public final static String DES = "DES";
		public final static String AES = "AES";
	}
	
	public final static class CipherType{
		public final static String SYMMETRY = "symmetry";
		public final static String ASYMMETRY = "asymmetry";
		public final static String CERTIFICATE = "certificate";
	}
	
	/**
	 * 非对称加密算法
	 * @author yhb
	 *
	 */
	public final static class AsymmetryAlgorithm{
		public final static String AES = "AES";
	}
	
	/**
	 * 支持的整数类型
	 * @author yhb
	 *
	 */
	public final static class CertificateType{
		public final static String X509 = "X.509";
	}
	
	/**
	 * 证书和密钥文件位置 %classpath%/cert以及 %classpath%/keyStore
	 * @author yhb
	 *
	 */
	public final static class CipherFilePath{
		public final static String CERTIFICATEPATH = "cert";
		public final static String KEYSTOREPATH = "keyStore";
	}
	

}
