package org.scorpion.api.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.scorpion.api.configuration.DataSourceLis.DataSourceInfo;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.log.PlatformLogger;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsTscpDataSourceAdapter {
	
	/**
	 * @param dataSourceInfo
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public abstract ITscpDataSource getDataSource(DataSourceInfo dataSourceInfo)throws TscpBaseException;
	
	
	/**
	 * @param dbDriver
	 * @return
	 */
	protected Integer getDBType(String dbDriver){
		
		if(TscpDataBaseType.ORACLE_DRIVER.equals(dbDriver)){
			return TscpDataBaseType.oracle_db_type;
		}else if(TscpDataBaseType.KING_DRIVER.equals(dbDriver)){
			return TscpDataBaseType.kbe_db_type;
		}else if(TscpDataBaseType.MYSQL_DRIVER.equals(dbDriver)){
			return TscpDataBaseType.mysql_db_type;
		}else if(TscpDataBaseType.DM_DRIVER.equals(dbDriver)){
			return -1;
		}else if(TscpDataBaseType.SQL_SERVER_DRIVER.equals(dbDriver)){
			return -1;
		}
		return null;
	}
	

	/**
	 * @param dataSource
	 * @throws SQLException
	 */
	protected void databaseConnProbe(DataSource dataSource) throws TscpBaseException{
		try{
			Connection conn = null;
			try {
				conn = dataSource.getConnection();
			}catch(Throwable e){
				PlatformLogger.error("Get database connection failure , check datasource url configuration !", e);
				Runtime.getRuntime().exit(0);
			}finally{
				if(conn != null)
					conn.close();
			}
		}catch(Throwable ex){
			throw new TscpBaseException(ex);
		}
	}
	
	
}
