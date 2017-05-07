package org.scorpion.persistence.util;


import org.scorpion.api.persistence.TscpDataBaseType;
import org.scorpion.api.persistence.TscpDataBaseType.KingbaseDataType;
import org.scorpion.api.persistence.TscpDataBaseType.OracleDataType;

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
public class DbDataTypeConvert {
	
	
	/**
	 * 
	 * @param dbType 
	 * @return
	 */
	public static String convertDataType(String dataType,int dbType){
		
		if(TscpDataBaseType.oracle_db_type == dbType){
			return oracleTypeConvert(dataType);
		}else if(TscpDataBaseType.kbe_db_type == dbType){
			return kingbaseConvertType(dataType);
		}else
			return null;
	}
	
	
	
	
	/**
	 * 
	 * @param dataType
	 * 
	 * @return
	 */
	public static String oracleTypeConvert(String dataType){

		if(OracleDataType.VARCHAR2.name().equals(dataType))
			return "java.lang.String";
		else if("FLOAT".equals(dataType)){
			return "float";
		}else if(OracleDataType.NUMBER.name().equals(dataType))
			//return "java.math.BigDecimal";
			return "java.lang.Integer";
		else if(OracleDataType.CHAR.name().equals(dataType))
			return "java.lang.String";
		else if(OracleDataType.DATE.name().equals(dataType))
			return "java.util.Date";
		else if(OracleDataType.BLOB.name().equals(dataType)){
			//return "oracle.sql.BLOB";
			return "byte[]";
		}else if(OracleDataType.CLOB.name().equals(dataType))
			// return "oracle.sql.CLOB";
			return "java.lang.String";
		else if(OracleDataType.LONG.name().equals(dataType))
			return "java.lang.String";
		else if(dataType.indexOf(OracleDataType.TIMESTAMP.name())>=0)
			return "java.util.Date";
		else if(OracleDataType.NCLOB.name().equals(dataType))
			//return "oracle.sql.CLOB";
			return "java.lang.String";
		else 
			return null;
		
	}
	
	
	/**
	 * 
	 * @param dataType
	 * 
	 * @return
	 */
	public static String kingbaseConvertType(String dataType){
		//VARCHAR,CHAR,DATA,INTEGER,FLOAT,TEXT,TIMESTAMP,LONG,BLOB
		if(KingbaseDataType.VARCHAR.name().equals(dataType)){
			return "java.lang.String";
		}else if("FLOAT".equals(dataType)){
			return "float";
		}else if(KingbaseDataType.INT4.name().equals(dataType)||KingbaseDataType.INTEGER.name().equals(dataType)||KingbaseDataType.NUMERIC.name().equals(dataType))
			//return "java.math.BigDecimal";
			return "java.lang.Integer";
		else if(KingbaseDataType.FLOAT.equals(dataType))
			return "float";
		else if(KingbaseDataType.CHAR.name().equals(dataType))
			return "java.lang.String";
		else if(KingbaseDataType.DATE.name().equals(dataType))
			return "java.util.Date";
		else if(KingbaseDataType.TIMESTAMP.name().equals(dataType)){
			return "java.util.Date";
		}else if(KingbaseDataType.BLOB.name().equals(dataType)){
			//return "oracle.sql.BLOB";
			return "byte[]";
		}else if(KingbaseDataType.CLOB.name().equals(dataType))
			// return "oracle.sql.CLOB";
			return "java.lang.String";
		else if(KingbaseDataType.LONG.name().equals(dataType))
			return "java.lang.String";
		else if(dataType.indexOf(KingbaseDataType.TIMESTAMP.name())>=0||KingbaseDataType.DATETIME.name().equals(dataType))
			return "java.util.Date";
		else if(KingbaseDataType.FLOAT8.name().equals(dataType)||KingbaseDataType.DOUBLE.name().equals(dataType)){
			return "double";
		}
			return null;
		
	}
	
	
	
	
}
