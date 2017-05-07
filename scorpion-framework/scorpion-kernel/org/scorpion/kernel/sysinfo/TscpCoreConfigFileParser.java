package org.scorpion.kernel.sysinfo;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.scorpion.api.common.AbsTscpConfigFileHandler;
import org.scorpion.api.common.AbsTscpXmlParser;
import org.scorpion.api.configuration.TscpCoreConfig;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.common.context.SystemContext;

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
public class TscpCoreConfigFileParser extends AbsTscpXmlParser<TscpCoreConfig> {

	@Override
	public String parseObject2Xml(TscpCoreConfig t,AbsTscpConfigFileHandler<String, TscpCoreConfig> handler)throws TscpBaseException {
		return handler.processor(t);
	}

	@Override
	public TscpCoreConfig parseXml2Object(String xml,AbsTscpConfigFileHandler<TscpCoreConfig, String> handler)throws TscpBaseException {
		return handler.processor(xml);
	}

	@Override
	public TscpCoreConfig parseXml2Object(InputStream in,AbsTscpConfigFileHandler<TscpCoreConfig, InputStream> handler)throws TscpBaseException {
		return handler.setEntity(SystemContext.getApplicationContext().getSystemCoreConfig()).processor(in);
	}

	@Override
	public void parseXml2Object(List<File> configs,AbsTscpConfigFileHandler<TscpCoreConfig, File> handler)throws TscpBaseException {
		handler.setConfigFiles(configs).handler();
	}

}
