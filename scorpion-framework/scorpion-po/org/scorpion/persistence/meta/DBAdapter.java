package org.scorpion.persistence.meta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.scorpion.api.configuration.DataSourceLis.DataSourceInfo;
import org.scorpion.api.persistence.TscpDataBaseType;

public class DBAdapter {
	
	private static final String ORACLE_TYPE = "oracle.jdbc.OracleDriver";
	
	private static final String MYSQL_TYPE = "com.mysql.jdbc.Driver";
	
	private static final String KINGBASE_TYPE = "com.kingbase.Driver";
	
	public static String currentDS ;
	
	
	
	/**
	 * @param datasourceInfo
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Throwable 
	 */
	public static int getDbType(DataSourceInfo datasourceInfo) throws Throwable{
		
		if(datasourceInfo.getDriverClassName() != null&& ORACLE_TYPE.equals(datasourceInfo.getDriverClassName().trim()))
			return TscpDataBaseType.oracle_db_type;
		if(datasourceInfo.getDriverClassName() != null&& MYSQL_TYPE.equals(datasourceInfo.getDriverClassName().trim()))
			return TscpDataBaseType.mysql_db_type;
		if(datasourceInfo.getDriverClassName() != null&& KINGBASE_TYPE.equals(datasourceInfo.getDriverClassName().trim()))
			return TscpDataBaseType.kbe_db_type;
		return -1;
		
	}
	
	
	public static List<String> getTables(DataSourceInfo datasourceInfo) throws Throwable{
		
		Connection conn = null;  ResultSet result = null;
		List<String> lis = new ArrayList<String>();

		try{
			String sql = "select table_name from user_tables";
			
			if(datasourceInfo.getDriverClassName() != null&& ORACLE_TYPE.equals(datasourceInfo.getDriverClassName().trim())){
				oracle.jdbc.OracleDriver.class.newInstance();
					
			}if(datasourceInfo.getDriverClassName() != null&& MYSQL_TYPE.equals(datasourceInfo.getDriverClassName().trim())){
				com.mysql.jdbc.Driver.class.newInstance();
				sql = "show tables";
			}if(datasourceInfo.getDriverClassName() != null&& KINGBASE_TYPE.equals(datasourceInfo.getDriverClassName().trim()))
				com.kingbase.Driver.class.newInstance();
			conn = DriverManager.getConnection(datasourceInfo.getUrl(), datasourceInfo.getUser(), datasourceInfo.getPasswd());
			result = conn.prepareStatement(sql).executeQuery();
			while(result.next()){
				lis.add(result.getString(1));
			}
		}finally{
			if(result != null) result.close();
			if(result != null) conn.close();
		}
		return lis;
	}

}
