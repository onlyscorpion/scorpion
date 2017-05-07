package org.scorpion.persistence.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.scorpion.api.configuration.DataSourceLis;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.configuration.SystemResourcePool;
import org.scorpion.api.configuration.DataSourceLis.DataSourceInfo;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.persistence.AbsTscpDataSourceAdapter;
import org.scorpion.api.util.Constant;

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
public class DataSourceFactory {
	
	private static DataSourceFactory dataSourceFactory;
	
	@SuppressWarnings("unchecked")
	private final Map<String,DataSource> datasourceMap = SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.datasource.getValue(),Map.class);

	/**
	 * @parameter dataSourceAdapters key 值为 驱动的名称， adapter为用户实现datasourceAdapter
	 */
	private final Map<String,AbsTscpDataSourceAdapter> dataSourceAdapters = new HashMap<String,AbsTscpDataSourceAdapter>();
	
	
	/**
	 * @description get default datasource configuration
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public final DataSource getDefaultDataSource() throws TscpBaseException{
	
		DataSource dataSource = datasourceMap.get(DataSourceLis.DEFAULT_DATASOURCE);
		
		if(dataSource == null)
			throw new TscpBaseException("TSCP-9086:Application default datasource don't initialize, please check datasource configuration !");
		
		return dataSource;
	}
	
	
	/**
	 * @description get default datasource by name
	 * 
	 * @param dataSourceName
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public final DataSource getDataSourceByName(String dataSourceName)throws TscpBaseException{
	
		DataSource dataSource = datasourceMap.get(dataSourceName);
		
		if(dataSource == null)
			throw new TscpBaseException("TSCP-9875:Application don't find datasource ["+dataSourceName+"] !");
	
		return dataSource;
	}
	
	
	/**
	 * @description register data source adapter
	 * 
	 * @param adapterName
	 * 
	 * @param dataSourceAdapter
	 */
	public void registerDataSourceAdapter(String adapterName,AbsTscpDataSourceAdapter dataSourceAdapter) {
		
		dataSourceAdapters.put(adapterName, dataSourceAdapter);
	}


	private DataSourceFactory() throws TscpBaseException {
	}

	
	/**
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public synchronized static DataSourceFactory getInstance() throws TscpBaseException {
		
		if(dataSourceFactory == null)
			dataSourceFactory = new DataSourceFactory();
	
		return dataSourceFactory;
	}


	public void initDataSource() throws TscpBaseException{
		
		for(Entry<String,DataSourceInfo>entry:DataSourceLis.getAllDataSource().entrySet()){
			String key = null;
			
			if(DataSourceLis.DEFAULT_DATASOURCE.equals(entry.getKey()))
				key = DataSourceLis.DEFAULT_DATASOURCE;
			else
				key = entry.getKey();
			
			AbsTscpDataSourceAdapter dataSourceAdapter = null;
			
			if(entry.getValue().isUseJndiDs())
				 dataSourceAdapter = dataSourceAdapters.get(Constant.JNDI);
			else
				 dataSourceAdapter = dataSourceAdapters.get(entry.getValue().getDscp());

			if(dataSourceAdapter == null)
				throw new TscpBaseException("TSCP-9758:Don't implement connection pool type ["+entry.getValue().getDscp()+"] , please modify the attribute of datasource configuration file 'DSCPT' !");
			
			datasourceMap.put(key, dataSourceAdapter.getDataSource(entry.getValue()));
		}
	}
	
}
