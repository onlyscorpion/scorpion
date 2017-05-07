package org.scorpion.persistence.handler;

import org.scorpion.api.configuration.DataSourceLis.DataSourceInfo;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.TscpDataSource;
import org.scorpion.api.persistence.AbsTscpDataSourceAdapter;
import org.scorpion.api.persistence.ITscpDataSource;
import org.scorpion.persistence.dbcp.BasicDataSource;

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
public class TscpDbcpDataSourceAdater extends AbsTscpDataSourceAdapter{

	@Override
	public ITscpDataSource getDataSource(DataSourceInfo dataSourceInfo)throws TscpBaseException {
		
		if(dataSourceInfo == null)
			throw new TscpBaseException("TSCP-8765:Datasource information is null !");
		
		BasicDataSource ds = new BasicDataSource();  
		ds.setDriverClassName(dataSourceInfo.getDriverClassName());  
		ds.setUsername(dataSourceInfo.getUser());  
		ds.setPassword(dataSourceInfo.getPasswd());  
		ds.setUrl(dataSourceInfo.getUrl());  
		ds.setInitialSize(dataSourceInfo.getInitSize()); 
		ds.setMaxActive(dataSourceInfo.getMaxActive());  
		ds.setMaxIdle(dataSourceInfo.getMaxIdle());  
		ds.setMaxWait(dataSourceInfo.getMaxWait());
		
		return new TscpDataSource(dataSourceInfo.getName(),ds,this.getDBType(dataSourceInfo.getDriverClassName()),dataSourceInfo.isDefaultDataSource());
	}

}
