package org.scorpion.persistence.handler;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.scorpion.api.configuration.DataSourceLis.DataSourceInfo;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.ScorpionDataSource;
import org.scorpion.api.persistence.AbsScorpionDataSourceAdapter;
import org.scorpion.api.persistence.IScorpionDataSource;
import org.scorpion.api.persistence.ScorpionDataBaseType;

public class ScorpionDBJndiContext extends AbsScorpionDataSourceAdapter{

	@Override
	public IScorpionDataSource getDataSource(DataSourceInfo dataSourceInfo)throws ScorpionBaseException {
		
	
		try {
			
            if(ScorpionDataBaseType.kbe_db_type == dataSourceInfo.getDbType()){
    			Context ctx;
    			ctx = new InitialContext();
    		    DataSource ds = (DataSource)ctx .lookup(dataSourceInfo.getName());   
    		    return new ScorpionDataSource(dataSourceInfo.getName(),ds,ScorpionDataBaseType.oracle_db_type,dataSourceInfo.isDefaultDataSource());
            }else if(ScorpionDataBaseType.oracle_db_type == dataSourceInfo.getDbType()){
            	
            }else if(ScorpionDataBaseType.mysql_db_type == dataSourceInfo.getDbType()){
            	
            }else if(ScorpionDataBaseType.db2_db_type == dataSourceInfo.getDbType()){
            	
            }
			Context ctx;
			ctx = new InitialContext();
		    DataSource ds = (DataSource)ctx .lookup(dataSourceInfo.getName());   
		    return new ScorpionDataSource(dataSourceInfo.getName(),ds,ScorpionDataBaseType.oracle_db_type,dataSourceInfo.isDefaultDataSource());
		} catch (Exception e) {
			throw new ScorpionBaseException("scorpion-9865:GET DATABASE CONNECTION BY JNDI EXCEPTION !",e);
		}     
		  
	}

}
