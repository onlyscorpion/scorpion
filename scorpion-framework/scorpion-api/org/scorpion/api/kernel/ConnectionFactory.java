package org.scorpion.api.kernel;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.scorpion.api.configuration.DataSourceLis;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.configuration.SystemResourcePool;
import org.scorpion.api.exception.ScorpionBaseException;

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
public class ConnectionFactory {
	
	@SuppressWarnings("unchecked")
	private final static Map<String,DataSource> datasourceMap = SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.datasource.getValue(), Map.class);

	/**
	 * @return
	 * @throws SQLException
	 */
	public static ScorpionConnection getDefaultConn() throws ScorpionBaseException{
	
		try {
			return new ScorpionConnection(datasourceMap.get(DataSourceLis.DEFAULT_DATASOURCE).getConnection(),((ScorpionDataSource)(datasourceMap.get(DataSourceLis.DEFAULT_DATASOURCE))).getDbType(),false,false);
		} catch (SQLException e) {
			throw new ScorpionBaseException("scorpion-9075:获取默认数据库连接异常",e);
		}
	
	}
	
	/**
	 * @param dataSourceName
	 * @return
	 * @throws SQLException
	 */
	public static ScorpionConnection getConnByDataSourceName(String dataSourceName) throws ScorpionBaseException{
	
		try {
			return new ScorpionConnection(datasourceMap.get(dataSourceName).getConnection(),((ScorpionDataSource)(datasourceMap.get(DataSourceLis.DEFAULT_DATASOURCE))).getDbType(),false,false);
		} catch (SQLException e) {
			throw new ScorpionBaseException("scorpion-9065:获取数据库连接异常",e);
		}
	}

}
