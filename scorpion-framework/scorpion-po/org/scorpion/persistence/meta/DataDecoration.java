package org.scorpion.persistence.meta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.persistence.ScorpionDataBaseType;

import com.kingbase.Driver;

import oracle.jdbc.OracleDriver;

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
public class DataDecoration {
	
	
	public  static String user;
	
	public  static String password;
	
	public  static String url;
	
	
	/**
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	/*public static List<String> getTableName()throws ScorpionBaseException{
		
		List<String> tables = new ArrayList<String>();
		try {
			ResultSet result = dataBaseHandler("select t.table_name from user_tables t");
			while(result.next()){
				tables.add(result.getString(1));
			}
		} catch (SQLException e) {
			throw new ScorpionBaseException("scorpion-6985:数据库操作异常",e);
		}
		return tables;
	}*/
	
	
	/**
	 * @param sql
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	private static ResultSet dataBaseHandler(String sql,String url,String username,String password,int dbType) throws ScorpionBaseException{
		
		//if(user == null||password == null||url == null)
		//	throw new ScorpionBaseException("scorpion-9854:数据源信息不完整");
		try{
			//BitSet
			initDataSource(dbType);
			//Connection conn = DriverManager.getConnection(url, user, password);
			//Connection conn = DriverManager.getConnection("jdbc:kingbase://192.168.30.152:54321/Scorpion", "esb6", "123456");
			Connection conn = DriverManager.getConnection(url, username, password);
			 // Connection conn = DriverManager.getConnection("jdbc:kingbase://127.0.0.1:54321/Scorpion", "Scorpion", "admin");
			//Connection conn = DriverManager.getConnection(url, username, password);
			//Connection conn = DriverManager.getConnection("jdbc:kingbase://192.168.30.152:54321/Scorpion_esb", "Scorpion", "Scorpion123");
			//Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "tj_jcpt", "css");
		    //Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.107:1521:tjfy", "tjfy", "tjfy");
			//Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.152:1521:Scorpion", "esb4", "123456");
	    	/*Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "tj_jcpt", "css");*/
			//"select t.table_name,f.COLUMN_NAME,f.DATA_TYPE from user_tables t, user_tab_columns f where t.table_name = f.TABLE_NAME"
			PreparedStatement statement = conn.prepareStatement(sql);
			return statement.executeQuery();
	
		}catch(Exception e){
			throw new ScorpionBaseException("scorpion-8094:GET DATABASE TYPE EXCEPTION ! ",e);
		}
	}
	
	
	/*private static void resultHandler(ResultSet result) throws SQLException{
		
		List<PhysicalModel> models = new ArrayList<PhysicalModel>();
		
		while(result.next()){
			PhysicalModel model = new PhysicalModel();
			model.setTableName(result.getString(1));
		}
		
	}*/
	
	
	
	/**
	 * @param dbType
	 * 
	 * @throws InstantiationException
	 * 
	 * @throws IllegalAccessException
	 */
	private static void initDataSource(int dbType) throws InstantiationException, IllegalAccessException{
		
		switch(dbType){
		case ScorpionDataBaseType.oracle_db_type: OracleDriver.class.newInstance(); break;
		case ScorpionDataBaseType.kbe_db_type:Driver.class.newInstance();break;
		case ScorpionDataBaseType.mysql_db_type:com.mysql.jdbc.Driver.class.newInstance();break;
		}
	}
	
	
	public static Map<String,Map<String,String>> oconstructAttributeInfo(String url,String username,String password,int dbType) throws ScorpionBaseException{
		
		ResultSet result = dataBaseHandler("select distinct t.table_name,t.COLUMN_NAME,t.DATA_TYPE,t.DATA_PRECISION,t.DATA_SCALE,t.nullable ,"
				+ "f.constraint_type from user_tables k, user_tab_cols t ,(select a.column_name,a.table_name,b.constraint_type "
				+ "from user_tables n, user_cons_columns a, user_constraints b where a.constraint_name = b.constraint_name "
				+ "and a.table_name = b.table_name AND B.constraint_type = 'P')f where t.column_name = f.column_name(+) and "
				+ " t.table_name = f.table_name(+)  and k.table_name = t.table_name",url,username,password,dbType);
		Map<String,Map<String,String>> models = new HashMap<String,Map<String,String>>();
		try {
			while(result.next()){
				if(models.containsKey(result.getString(1))){
					if(("NUMBER".equals(result.getString(3))||"NUMERIC".equals(result.getString(3)))&&result.getInt(5)>0){
						models.get(result.getString(1)).put(result.getString(2), new StringBuilder().append("FLOAT#").append(result.getString(6)).append("#").append(result.getString(7)).toString());
					}else{
						models.get(result.getString(1)).put(result.getString(2), new StringBuilder().append(result.getString(3)).append("#").append(result.getString(6)).append("#").append(result.getString(7)).toString());
					}
				}else{
					Map<String,String> columnMap = new HashMap<String,String>();
					if(("NUMBER".equals(result.getString(3))||"NUMERIC".equals(result.getString(3)))&&result.getInt(5)>0){
						columnMap.put(result.getString(2), new StringBuilder().append("FLOAT#").append(result.getString(6)).append("#").append(result.getString(7)).toString());
					}else{
						columnMap.put(result.getString(2), new StringBuilder().append(result.getString(3)).append("#").append(result.getString(6)).append("#").append(result.getString(7)).toString());
						models.put(result.getString(1), columnMap);
					}
				}
			}
		} catch (Exception e) {
			throw new ScorpionBaseException("scorpion-76554:DATABASE HANDLE FAILURE !",e);
		}
		
		return models;
	}
	
	
	
	/**
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public static Map<String,Map<String,String>> kconstructAttributeInfo(String url,String username,String password,int dbType) throws ScorpionBaseException{
	
		ResultSet result = dataBaseHandler("select distinct t.table_name,t.COLUMN_NAME,t.DATA_TYPE,t.DATA_PRECISION,t.DATA_SCALE,t.nullable ,T.DATA_DEFAULT,f.constraint_type from user_tables k, user_tab_cols t ,(select a.column_name,a.table_name,b.constraint_type from user_tables n, user_cons_columns a, user_constraints b where a.constraint_name = b.constraint_name and a.table_name = b.table_name AND B.constraint_type = 'P')f where t.column_name = f.column_name(+) and  t.table_name = f.table_name(+)  and k.table_name = t.table_name",
				url,username,password,dbType);
		Map<String,Map<String,String>> models = new HashMap<String,Map<String,String>>();
		try {
			while(result.next()){
				if(models.containsKey(result.getString(1))){
					if(("NUMBER".equals(result.getString(3))||"NUMERIC".equals(result.getString(3)))&&result.getInt(5)>0){
						models.get(result.getString(1)).put(result.getString(2), new StringBuilder().append("FLOAT#").append(result.getString(6)).append("#").append(result.getString(7)).append("#").append(result.getString(8)).toString());
					}else{
						models.get(result.getString(1)).put(result.getString(2), new StringBuilder().append(result.getString(3)).append("#").append(result.getString(6)).append("#").append(result.getString(7)).append("#").append(result.getString(8)).toString());
					}
				}else{
					Map<String,String> columnMap = new HashMap<String,String>();
					if(("NUMBER".equals(result.getString(3))||"NUMERIC".equals(result.getString(3)))&&result.getInt(5)>0){
						columnMap.put(result.getString(2), new StringBuilder().append("FLOAT#").append(result.getString(6)).append("#").append(result.getString(7)).append("#").append(result.getString(8)).toString());
					}else{
						columnMap.put(result.getString(2), new StringBuilder().append(result.getString(3)).append("#").append(result.getString(6)).append("#").append(result.getString(7)).append("#").append(result.getString(8)).toString());
						models.put(result.getString(1), columnMap);
					}
				}
			}
		} catch (Exception e) {
			throw new ScorpionBaseException("scorpion-76554:DATABASE HANDLE FAILURE !",e);
		}
		
		return models;
	}
	
	public static Map<String,Map<String,String>> mconstructAttributeInfo(String url,String username,String password,int dbType) throws ScorpionBaseException, SQLException{
		
		
		ResultSet result = dataBaseHandler("select T.TABLE_NAME,T.COLUMN_NAME,T.DATA_TYPE,T.NUMERIC_PRECISION,T.NUMERIC_SCALE,T.IS_NULLABLE,T.COLUMN_KEY from information_schema.`COLUMNS` T WHERE T.TABLE_SCHEMA='"+DriverManager.getConnection(url).getCatalog()+"'",
				url,username,password,dbType);
		Map<String,Map<String,String>> models = new HashMap<String,Map<String,String>>();
		try {
			while(result.next()){
					if(models.containsKey(result.getString(1))){
						if(("int".equals(result.getString(3))||"Integer".equals(result.getString(3)))&&result.getInt(5)>0){
							models.get(result.getString(1)).put(result.getString(2), new StringBuilder().append("FLOAT#").append(result.getString(6)).append("#").append("".equals(result.getString(7))?null:"P").toString());
						}else{
							models.get(result.getString(1)).put(result.getString(2), new StringBuilder().append(result.getString(3)).append("#").append(result.getString(6)).append("#").append("".equals(result.getString(7))?null:"P").toString());
						}
					}else{
						Map<String,String> columnMap = new HashMap<String,String>();
						if(("int".equals(result.getString(3))||"Integer".equals(result.getString(3)))&&result.getInt(5)>0){
							columnMap.put(result.getString(2), new StringBuilder().append("FLOAT#").append(result.getString(6)).append("#").append("".equals(result.getString(7))?null:"P").toString());
						}else{
							columnMap.put(result.getString(2), new StringBuilder().append(result.getString(3)).append("#").append(result.getString(6)).append("#").append("".equals(result.getString(7))?null:"P").toString());
							models.put(result.getString(1), columnMap);
						}
					}
			}
		} catch (Exception e) {
			throw new ScorpionBaseException("scorpion-76554:DATABASE HANDLE FAILURE !",e);
		}
		
		return models;
	}
	
	
	public static void main(String[] args) throws ScorpionBaseException {}
	

}
