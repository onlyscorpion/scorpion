package org.scorpion.persistence.meta;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.taiji.tscp.api.common.AbsTscpConfigFileHandler;
import com.taiji.tscp.api.common.AbsTscpXmlParser;
import com.taiji.tscp.api.configuration.DataSourceLis;
import com.taiji.tscp.api.exception.TscpBaseException;
import com.taiji.tscp.common.context.SystemContext;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpDataSourceParser extends AbsTscpXmlParser<DataSourceLis>{

	@Override
	public String parseObject2Xml(DataSourceLis t,AbsTscpConfigFileHandler<String, DataSourceLis> handler)
			throws TscpBaseException {
		return handler.processor(t);
	}

	@Override
	public DataSourceLis parseXml2Object(String xml,AbsTscpConfigFileHandler<DataSourceLis, String> handler)
			throws TscpBaseException {
		return handler.processor(xml);
	}

	@Override
	public DataSourceLis parseXml2Object(InputStream in,AbsTscpConfigFileHandler<DataSourceLis, InputStream> handler)
			throws TscpBaseException {
		try{
			return handler.setEntity(new DataSourceLis()).processor(in);
		}catch(TscpBaseException e){
			throw new TscpBaseException("TSCP-9076:Parser datasource configuration file failure !",e);
		}
	}

	@Override
	public void parseXml2Object(List<File> configs,
			AbsTscpConfigFileHandler<DataSourceLis, File> handler)
			throws TscpBaseException {
		    handler.setEntity(((SystemContext)SystemContext.getApplicationContext()).getDataSource());
		    handler.setConfigFiles(configs).handler();
	}


}
