package org.scorpion.kernel.route;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.scorpion.api.common.AbsScorpionConfigFileHandler;
import org.scorpion.api.common.AbsScorpionXmlParser;
import org.scorpion.api.exception.ScorpionBaseException;

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
public class TRouteParser extends AbsScorpionXmlParser<ProtocolConf> {

	@Override
	public String parseObject2Xml(ProtocolConf t,AbsScorpionConfigFileHandler<String, ProtocolConf> handler)throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProtocolConf parseXml2Object(String xml,AbsScorpionConfigFileHandler<ProtocolConf, String> processor)throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProtocolConf parseXml2Object(InputStream in,AbsScorpionConfigFileHandler<ProtocolConf, InputStream> processor)throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseXml2Object(List<File> configs,AbsScorpionConfigFileHandler<ProtocolConf, File> handler)throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public InputStream getConfigFileInputStream(String configPath)throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getConfigFileInputStream(File file)throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
