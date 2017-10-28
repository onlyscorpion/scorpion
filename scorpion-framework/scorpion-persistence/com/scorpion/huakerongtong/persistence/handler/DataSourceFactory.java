package com.scorpion.huakerongtong.persistence.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import com.scorpion.huakerongtong.api.configuration.DataSourceLis;
import com.scorpion.huakerongtong.api.configuration.SystemEnumType;
import com.scorpion.huakerongtong.api.configuration.SystemResourcePool;
import com.scorpion.huakerongtong.api.configuration.DataSourceLis.DataSourceInfo;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.persistence.AbsScorpionDataSourceAdapter;
import com.scorpion.huakerongtong.api.util.Constant;

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
public class DataSourceFactory {
	
	private static DataSourceFactory dataSourceFactory;
	
	@SuppressWarnings("unchecked")
	private final Map<String,DataSource> datasourceMap = SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.datasource.getValue(),Map.class);

	/**
	 * @parameter dataSourceAdapters key 值为 驱动的名称， adapter为用户实现datasourceAdapter
	 */
	private final Map<String,AbsScorpionDataSourceAdapter> dataSourceAdapters = new HashMap<String,AbsScorpionDataSourceAdapter>();
	
	
	/**
	 * @description get default datasource configuration
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public final DataSource getDefaultDataSource() throws ScorpionBaseException{
	
		DataSource dataSource = datasourceMap.get(DataSourceLis.DEFAULT_DATASOURCE);
		
		if(dataSource == null)
			throw new ScorpionBaseException("scorpion-9086:Application default datasource don't initialize, please check datasource configuration !");
		
		return dataSource;
	}
	
	
	/**
	 * @description get default datasource by name
	 * 
	 * @param dataSourceName
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public final DataSource getDataSourceByName(String dataSourceName)throws ScorpionBaseException{
	
		DataSource dataSource = datasourceMap.get(dataSourceName);
		
		if(dataSource == null)
			throw new ScorpionBaseException("scorpion-9875:Application don't find datasource ["+dataSourceName+"] !");
	
		return dataSource;
	}
	
	
	/**
	 * @description register data source adapter
	 * 
	 * @param adapterName
	 * 
	 * @param dataSourceAdapter
	 */
	public void registerDataSourceAdapter(String adapterName,AbsScorpionDataSourceAdapter dataSourceAdapter) {
		
		dataSourceAdapters.put(adapterName, dataSourceAdapter);
	}


	private DataSourceFactory() throws ScorpionBaseException {
	}

	
	/**
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public synchronized static DataSourceFactory getInstance() throws ScorpionBaseException {
		
		if(dataSourceFactory == null)
			dataSourceFactory = new DataSourceFactory();
	
		return dataSourceFactory;
	}


	public void initDataSource() throws ScorpionBaseException{
		
		for(Entry<String,DataSourceInfo>entry:DataSourceLis.getAllDataSource().entrySet()){
			String key = null;
			
			if(DataSourceLis.DEFAULT_DATASOURCE.equals(entry.getKey()))
				key = DataSourceLis.DEFAULT_DATASOURCE;
			else
				key = entry.getKey();
			
			AbsScorpionDataSourceAdapter dataSourceAdapter = null;
			
			if(entry.getValue().isUseJndiDs())
				 dataSourceAdapter = dataSourceAdapters.get(Constant.JNDI);
			else
				 dataSourceAdapter = dataSourceAdapters.get(entry.getValue().getDscp());

			if(dataSourceAdapter == null)
				throw new ScorpionBaseException("scorpion-9758:Don't implement connection pool type ["+entry.getValue().getDscp()+"] , please modify the attribute of datasource configuration file 'DSCPT' !");
			
			datasourceMap.put(key, dataSourceAdapter.getDataSource(entry.getValue()));
		}
	}
	
}
