package com.scorpion.huakerongtong.common.security;

import com.scorpion.huakerongtong.api.kernel.ABigDataDecoderPipeline;
import com.scorpion.huakerongtong.api.kernel.SecurityEnum;
import com.scorpion.huakerongtong.common.security.impl.DESDecoder;
import com.scorpion.huakerongtong.common.security.impl.DSADecoder;
import com.scorpion.huakerongtong.common.security.impl.RSADecoder;

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
public class ScorpionSecurityFactory {

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
