package org.scorpion.api.persistence;

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
public class ScorpionDataBaseType {
	
	public final static int oracle_db_type = 1;
	
	public final static int mysql_db_type= 2;
	
	public final static int dm_db_type = 3;
	
	public final static int sqlserver_db_type = 4;
	
	public final static int db2_db_type = 5;
	
	public final static int kbe_db_type = 6;
	
	public final static String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	
	public final static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	
	public final static String KING_DRIVER ="com.kingbase.Driver";
	
	public final static String DM_DRIVER = "";
	
	public final static String SQL_SERVER_DRIVER = "";
	
	
	
	
	/**
	 * @description oracle 数据类型
	 * @author 郑承磊
	 *
	 */
	public enum OracleDataType{
		
		NUMBER,CHAR,VARCHAR2,DATE,TIMESTAMP,LONG,BLOB,CLOB,NCLOB
	}
	
	public enum KingbaseDataType{
		VARCHAR,CHAR,DATA,INTEGER,NUMERIC,FLOAT,TEXT,TIMESTAMP,LONG,BLOB,CLOB,DATE,DATETIME,DOUBLE,INT4,FLOAT8
	}
	
	public enum MysqlDataType{
		VARCHAR,CHAR,DATA,INT,INTEGER,NUMERIC,FLOAT,TEXT,BIGINT,TIMESTAMP,LONG,BLOB,CLOB,DATE,DATETIME,DOUBLE,INT4,FLOAT8
	}
	

}
