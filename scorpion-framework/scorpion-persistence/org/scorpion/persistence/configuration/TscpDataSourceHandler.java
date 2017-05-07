package org.scorpion.persistence.configuration;

import java.util.HashMap;
import java.util.Map;

import org.scorpion.api.common.ITscpDataSourceHandler;
import org.scorpion.api.configuration.DataSourceLis.DataSourceInfo;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.util.TscpSequenceUtil;

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
public class TscpDataSourceHandler implements ITscpDataSourceHandler{
	
	
	private String DEFAULT_DATASOURCE_KEY  = TscpSequenceUtil.generateSequeueString();
	
	private static ITscpDataSourceHandler dataSourceHandler = getDefaultInstace();
	
	private final Map<String,DataSourceInfo> dataSourceMap = new HashMap<String,DataSourceInfo>();

	
	@Override
	public DataSourceInfo getDefaultDataSource(){
		
		return dataSourceMap.get(DEFAULT_DATASOURCE_KEY);
	
	}

	@Override
	public void putDataSource(String name, DataSourceInfo dataSource,boolean isDefaultDataSource) throws TscpBaseException {
		
		if(isDefaultDataSource)
			dataSourceMap.put(DEFAULT_DATASOURCE_KEY, dataSource);
		else
			dataSourceMap.put(name,dataSource);
	}

	@Override
	public void setDefaultDataSource(DataSourceInfo dataSource) throws TscpBaseException {
	
		dataSourceMap.put(DEFAULT_DATASOURCE_KEY, dataSource);
	
	}
	
	public final synchronized static ITscpDataSourceHandler getDefaultInstace(){
		
		if(dataSourceHandler == null)
		
			dataSourceHandler = new TscpDataSourceHandler();
		
		return dataSourceHandler;
	}

}
