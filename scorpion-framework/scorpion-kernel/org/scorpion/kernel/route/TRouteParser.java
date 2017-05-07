package org.scorpion.kernel.route;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.scorpion.api.common.AbsTscpConfigFileHandler;
import org.scorpion.api.common.AbsTscpXmlParser;
import org.scorpion.api.exception.TscpBaseException;

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
public class TRouteParser extends AbsTscpXmlParser<ProtocolConf> {

	@Override
	public String parseObject2Xml(ProtocolConf t,AbsTscpConfigFileHandler<String, ProtocolConf> handler)throws TscpBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProtocolConf parseXml2Object(String xml,AbsTscpConfigFileHandler<ProtocolConf, String> processor)throws TscpBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProtocolConf parseXml2Object(InputStream in,AbsTscpConfigFileHandler<ProtocolConf, InputStream> processor)throws TscpBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseXml2Object(List<File> configs,AbsTscpConfigFileHandler<ProtocolConf, File> handler)throws TscpBaseException {
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
