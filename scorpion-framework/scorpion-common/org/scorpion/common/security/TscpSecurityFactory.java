package org.scorpion.common.security;

import org.scorpion.api.kernel.ABigDataDecoderPipeline;
import org.scorpion.api.kernel.SecurityEnum;
import org.scorpion.common.security.impl.DESDecoder;
import org.scorpion.common.security.impl.DSADecoder;
import org.scorpion.common.security.impl.RSADecoder;

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
public class TscpSecurityFactory {

	/**
	 * @param algorithm
	 * @return
	 * @throws Exception
	 */
	public static ABigDataDecoderPipeline getDataDecoder(String algorithm)throws Exception {

		if (SecurityEnum.DecoderAlgorithmType.DES.name().equals(algorithm.toUpperCase())) {
			return DESDecoder.getInstance();
		} else if (SecurityEnum.DecoderAlgorithmType.RSA.name().equals(algorithm.toUpperCase())) {
			return RSADecoder.getInstance();
		} else if (SecurityEnum.DecoderAlgorithmType.DSA.name().equals(algorithm.toUpperCase())) {
			return DSADecoder.getInstance();
		} else
			throw new Exception("Can't init unknown decoder type!");
	}

}
