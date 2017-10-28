package com.scorpion.huakerongtong.persistence.handler;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import com.scorpion.huakerongtong.api.configuration.DataSourceLis.DataSourceInfo;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.ScorpionDataSource;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.api.persistence.AbsScorpionDataSourceAdapter;
import com.scorpion.huakerongtong.api.persistence.IScorpionDataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

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
public class C3p0DataSourceAdapter extends AbsScorpionDataSourceAdapter{
	
	
	@Override
	public IScorpionDataSource getDataSource(DataSourceInfo dataSourceInfo)throws ScorpionBaseException {
		
		ComboPooledDataSource dataSource = new ComboPooledDataSource();      
		
		try{
			dataSource.setUser(dataSourceInfo.getUser());       
			dataSource.setPassword(dataSourceInfo.getPasswd());       
			dataSource.setJdbcUrl(dataSourceInfo.getUrl());
			dataSource.setDriverClass(dataSourceInfo.getDriverClassName()); 
			dataSource.setInitialPoolSize(dataSourceInfo.getInitSize()); 
			dataSource.setMinPoolSize(dataSourceInfo.getInitSize()); 
			dataSource.setMaxPoolSize(dataSourceInfo.getMaxActive()); 
			dataSource.setMaxStatements(50); 
			dataSource.setMaxIdleTime(dataSourceInfo.getMaxIdle());
			dataSource.setDebugUnreturnedConnectionStackTraces(dataSourceInfo.isDumpStack());
			dataSource.setUnreturnedConnectionTimeout(dataSourceInfo.getConnTimeout()==0?360:dataSourceInfo.getConnTimeout());
			dataSource.setCheckoutTimeout(30000);
			databaseConnProbe(dataSource);
			if(dataSourceInfo.isDumpStack())
				startMonitor(dataSource);
		}catch(PropertyVetoException e){
			throw new ScorpionBaseException("Scorpion9084:Initialize C3P0 connection pool exception ! ",e);
		}
		
		return new ScorpionDataSource(dataSourceInfo.getName(),dataSource,this.getDBType(dataSourceInfo.getDriverClassName()),dataSourceInfo.isDefaultDataSource());
	}
	
	
	

	private void startMonitor(final ComboPooledDataSource dataSource){
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					PlatformLogger.info("====Current busy connections num is ["+dataSource.getNumBusyConnections()+"]==========");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}, 0,5000);
	}

}
