package org.scorpion.api.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.scorpion.api.configuration.DataSourceLis.DataSourceInfo;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.log.PlatformLogger;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsScorpionDataSourceAdapter {
	
	/**
	 * @param dataSourceInfo
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public abstract IScorpionDataSource getDataSource(DataSourceInfo dataSourceInfo)throws ScorpionBaseException;
	
	
	/**
	 * @param dbDriver
	 * @return
	 */
	protected Integer getDBType(String dbDriver){
		
		if(ScorpionDataBaseType.ORACLE_DRIVER.equals(dbDriver)){
			return ScorpionDataBaseType.oracle_db_type;
		}else if(ScorpionDataBaseType.KING_DRIVER.equals(dbDriver)){
			return ScorpionDataBaseType.kbe_db_type;
		}else if(ScorpionDataBaseType.MYSQL_DRIVER.equals(dbDriver)){
			return ScorpionDataBaseType.mysql_db_type;
		}else if(ScorpionDataBaseType.DM_DRIVER.equals(dbDriver)){
			return -1;
		}else if(ScorpionDataBaseType.SQL_SERVER_DRIVER.equals(dbDriver)){
			return -1;
		}
		return null;
	}
	

	/**
	 * @param dataSource
	 * @throws SQLException
	 */
	protected void databaseConnProbe(DataSource dataSource) throws ScorpionBaseException{
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
			throw new ScorpionBaseException(ex);
		}
	}
	
	
}
