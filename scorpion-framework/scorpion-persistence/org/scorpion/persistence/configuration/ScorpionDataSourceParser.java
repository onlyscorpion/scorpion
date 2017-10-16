package org.scorpion.persistence.configuration;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.scorpion.api.common.AbsScorpionConfigFileHandler;
import org.scorpion.api.common.AbsScorpionXmlParser;
import org.scorpion.api.configuration.DataSourceLis;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.common.context.SystemContext;

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
public class ScorpionDataSourceParser extends AbsScorpionXmlParser<DataSourceLis>{

	@Override
	public String parseObject2Xml(DataSourceLis t,AbsScorpionConfigFileHandler<String, DataSourceLis> handler)
			throws ScorpionBaseException {
		return handler.processor(t);
	}

	@Override
	public DataSourceLis parseXml2Object(String xml,AbsScorpionConfigFileHandler<DataSourceLis, String> handler)
			throws ScorpionBaseException {
		return handler.processor(xml);
	}

	@Override
	public DataSourceLis parseXml2Object(InputStream in,AbsScorpionConfigFileHandler<DataSourceLis, InputStream> handler)
			throws ScorpionBaseException {
		try{
			return handler.setEntity(new DataSourceLis()).processor(in);
		}catch(ScorpionBaseException e){
			throw new ScorpionBaseException("scorpion-9076:Parser datasource configuration file failure !",e);
		}
	}

	@Override
	public void parseXml2Object(List<File> configs,
			AbsScorpionConfigFileHandler<DataSourceLis, File> handler)
			throws ScorpionBaseException {
		    handler.setEntity(((SystemContext)SystemContext.getApplicationContext()).getDataSource());
		    handler.setConfigFiles(configs).handler();
	}


}
