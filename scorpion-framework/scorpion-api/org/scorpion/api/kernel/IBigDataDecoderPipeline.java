package org.scorpion.api.kernel;

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
public interface IBigDataDecoderPipeline {

	/**
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String signMessage(String data, String key) throws Exception;

	/**
	 * @param data
	 * @param key
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public boolean validate(String data, String key, String sign)throws Exception;

	/**
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(String data) throws Exception;

	/**
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] data) throws Exception;
	
	/**
	 * @param data
	 * @param off
	 * @param len
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] data,int off,int len) throws Exception;


	/**
	 * @param data
	 * @return
	 * @throws Exception
	 */

	public byte[] decrypt(String data) throws Exception;

	/**
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] data) throws Exception;
	
	
	/**
	 * @param data
	 * @param off
	 * @param len
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] data,int off,int len) throws Exception;

	/**
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public Object getCipherKey(String algorithm) throws Exception;

}
