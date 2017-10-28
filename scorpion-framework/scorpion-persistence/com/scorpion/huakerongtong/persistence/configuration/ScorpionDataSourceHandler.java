package com.scorpion.huakerongtong.persistence.configuration;

import java.util.HashMap;
import java.util.Map;

import com.scorpion.huakerongtong.api.common.IScorpionDataSourceHandler;
import com.scorpion.huakerongtong.api.configuration.DataSourceLis.DataSourceInfo;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.util.ScorpionSequenceUtil;

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
public class ScorpionDataSourceHandler implements IScorpionDataSourceHandler{
	
	
	private String DEFAULT_DATASOURCE_KEY  = ScorpionSequenceUtil.generateSequeueString();
	
	private static IScorpionDataSourceHandler dataSourceHandler = getDefaultInstace();
	
	private final Map<String,DataSourceInfo> dataSourceMap = new HashMap<String,DataSourceInfo>();

	
	@Override
	public DataSourceInfo getDefaultDataSource(){
		
		return dataSourceMap.get(DEFAULT_DATASOURCE_KEY);
	
	}

	@Override
	public void putDataSource(String name, DataSourceInfo dataSource,boolean isDefaultDataSource) throws ScorpionBaseException {
		
		if(isDefaultDataSource)
			dataSourceMap.put(DEFAULT_DATASOURCE_KEY, dataSource);
		else
			dataSourceMap.put(name,dataSource);
	}

	@Override
	public void setDefaultDataSource(DataSourceInfo dataSource) throws ScorpionBaseException {
	
		dataSourceMap.put(DEFAULT_DATASOURCE_KEY, dataSource);
	
	}
	
	public final synchronized static IScorpionDataSourceHandler getDefaultInstace(){
		
		if(dataSourceHandler == null)
		
			dataSourceHandler = new ScorpionDataSourceHandler();
		
		return dataSourceHandler;
	}

}
