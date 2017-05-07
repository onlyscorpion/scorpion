package org.scorpion.api.kernel;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.scorpion.api.configuration.DataSourceLis;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.configuration.SystemResourcePool;
import org.scorpion.api.exception.TscpBaseException;

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
public class ConnectionFactory {
	
	@SuppressWarnings("unchecked")
	private final static Map<String,DataSource> datasourceMap = SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.datasource.getValue(), Map.class);

	/**
	 * @return
	 * @throws SQLException
	 */
	public static TscpConnection getDefaultConn() throws TscpBaseException{
	
		try {
			return new TscpConnection(datasourceMap.get(DataSourceLis.DEFAULT_DATASOURCE).getConnection(),((TscpDataSource)(datasourceMap.get(DataSourceLis.DEFAULT_DATASOURCE))).getDbType(),false,false);
		} catch (SQLException e) {
			throw new TscpBaseException("TSCP-9075:获取默认数据库连接异常",e);
		}
	
	}
	
	/**
	 * @param dataSourceName
	 * @return
	 * @throws SQLException
	 */
	public static TscpConnection getConnByDataSourceName(String dataSourceName) throws TscpBaseException{
	
		try {
			return new TscpConnection(datasourceMap.get(dataSourceName).getConnection(),((TscpDataSource)(datasourceMap.get(DataSourceLis.DEFAULT_DATASOURCE))).getDbType(),false,false);
		} catch (SQLException e) {
			throw new TscpBaseException("TSCP-9065:获取数据库连接异常",e);
		}
	}

}
