package com.scorpion.huakerongtong.persistence.configuration;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.scorpion.huakerongtong.api.common.AbsScorpionConfigFileHandler;
import com.scorpion.huakerongtong.api.common.AbsScorpionXmlParser;
import com.scorpion.huakerongtong.api.configuration.SQLConfig;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.common.context.SystemContext;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionSQLFileParser extends AbsScorpionXmlParser<SQLConfig>{

	@Override
	public String parseObject2Xml(SQLConfig config, AbsScorpionConfigFileHandler<String, SQLConfig> handler)
			throws ScorpionBaseException {
		return handler.processor(config);
	}

	@Override
	public SQLConfig parseXml2Object(String xml, AbsScorpionConfigFileHandler<SQLConfig, String> handler)
			throws ScorpionBaseException {
		return handler.processor(xml);
	}

	@Override
	public SQLConfig parseXml2Object(InputStream in, AbsScorpionConfigFileHandler<SQLConfig, InputStream> handler)
			throws ScorpionBaseException {
		return handler.processor(in);
	}

	@Override
	public void parseXml2Object(List<File> configs, AbsScorpionConfigFileHandler<SQLConfig, File> handler)
			throws ScorpionBaseException {
		    handler.setEntity(((SystemContext)SystemContext.getApplicationContext()).getSqlconfig());
		    handler.setConfigFiles(configs).handler();
	}

}
