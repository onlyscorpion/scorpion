package org.scorpion.persistence.handler;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.scorpion.api.configuration.DataSourceLis.DataSourceInfo;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.TscpDataSource;
import org.scorpion.api.persistence.AbsTscpDataSourceAdapter;
import org.scorpion.api.persistence.ITscpDataSource;
import org.scorpion.api.persistence.TscpDataBaseType;

public class TscpDBJndiContext extends AbsTscpDataSourceAdapter{

	@Override
	public ITscpDataSource getDataSource(DataSourceInfo dataSourceInfo)throws TscpBaseException {
		
	
		try {
			
            if(TscpDataBaseType.kbe_db_type == dataSourceInfo.getDbType()){
    			Context ctx;
    			ctx = new InitialContext();
    		    DataSource ds = (DataSource)ctx .lookup(dataSourceInfo.getName());   
    		    return new TscpDataSource(dataSourceInfo.getName(),ds,TscpDataBaseType.oracle_db_type,dataSourceInfo.isDefaultDataSource());
            }else if(TscpDataBaseType.oracle_db_type == dataSourceInfo.getDbType()){
            	
            }else if(TscpDataBaseType.mysql_db_type == dataSourceInfo.getDbType()){
            	
            }else if(TscpDataBaseType.db2_db_type == dataSourceInfo.getDbType()){
            	
            }
			Context ctx;
			ctx = new InitialContext();
		    DataSource ds = (DataSource)ctx .lookup(dataSourceInfo.getName());   
		    return new TscpDataSource(dataSourceInfo.getName(),ds,TscpDataBaseType.oracle_db_type,dataSourceInfo.isDefaultDataSource());
		} catch (Exception e) {
			throw new TscpBaseException("TSCP-9865:GET DATABASE CONNECTION BY JNDI EXCEPTION !",e);
		}     
		  
	}

}
