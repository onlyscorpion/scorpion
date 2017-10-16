package org.scorpion.persistence.handler;

import org.scorpion.api.configuration.DataSourceLis.DataSourceInfo;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.ScorpionDataSource;
import org.scorpion.api.persistence.AbsScorpionDataSourceAdapter;
import org.scorpion.api.persistence.IScorpionDataSource;
import org.scorpion.persistence.dbcp.BasicDataSource;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionDbcpDataSourceAdater extends AbsScorpionDataSourceAdapter{

	@Override
	public IScorpionDataSource getDataSource(DataSourceInfo dataSourceInfo)throws ScorpionBaseException {
		
		if(dataSourceInfo == null)
			throw new ScorpionBaseException("scorpion-8765:Datasource information is null !");
		
		BasicDataSource ds = new BasicDataSource();  
		ds.setDriverClassName(dataSourceInfo.getDriverClassName());  
		ds.setUsername(dataSourceInfo.getUser());  
		ds.setPassword(dataSourceInfo.getPasswd());  
		ds.setUrl(dataSourceInfo.getUrl());  
		ds.setInitialSize(dataSourceInfo.getInitSize()); 
		ds.setMaxActive(dataSourceInfo.getMaxActive());  
		ds.setMaxIdle(dataSourceInfo.getMaxIdle());  
		ds.setMaxWait(dataSourceInfo.getMaxWait());
		
		return new ScorpionDataSource(dataSourceInfo.getName(),ds,this.getDBType(dataSourceInfo.getDriverClassName()),dataSourceInfo.isDefaultDataSource());
	}

}
