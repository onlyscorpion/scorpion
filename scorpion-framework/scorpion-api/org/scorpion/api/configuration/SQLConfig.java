package org.scorpion.api.configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class SQLConfig implements Serializable{

	private static final long serialVersionUID = -5484844547656366913L;
	
	private Map<String,SQLProperty> sqlmap;
	
	private Map<String,SQLProperty> poInsertMap;
	
	private Map<String,SQLProperty> poDelMap;
	
	private Map<String,SQLProperty> poUpdateMap;
	
	private String version;
	
	public Map<String, SQLProperty> getSqlmap() {
		return sqlmap;
	}

	public SQLProperty getSQLPropertyByKey(String key){
		
		return sqlmap.get(key);
	
	}
	
	public void putSQLMap(String sqlKey,SQLProperty sqlproperty) {
	    if(sqlmap == null)
	    	sqlmap = new HashMap<String, SQLConfig.SQLProperty>();
	    sqlmap.put(sqlKey, sqlproperty);
	
	}

	public String getVersion() {
	
		return version;
	
	}

	public void setVersion(String version) {
	
		this.version = version;
	
	}

	public Map<String, SQLProperty> getPoInsertMap() {
		if(poInsertMap == null)
			poInsertMap = new HashMap<String, SQLConfig.SQLProperty>();
		return poInsertMap;
	}
	
	public Map<String, SQLProperty> getPoDelMap() {
		if(poDelMap == null)
			poDelMap = new HashMap<String,SQLProperty>();
		return poDelMap;
	}
	
	public Map<String, SQLProperty> getPoUpdateMap() {
		if(poUpdateMap == null)
			poUpdateMap = new HashMap<String,SQLProperty>();
		return poUpdateMap;
	}

	
	public SQLConfig clear(){
		
		if(sqlmap == null)
		    return this;
		
		sqlmap.clear();
		return this;
	}



	public class SQLProperty{
		
		private String key;
		
		private String vaule;
		
		private String[] tables;
		
		private String sqlFileName;
		
		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getVaule() {
			return vaule;
		}

		public void setVaule(String vaule) {
			this.vaule = vaule;
		}

		public String[] getTables() {
			return tables;
		}

		public void setTables(String[] tables) {
			this.tables = tables;
		}

		public String getSqlFileName() {
			return sqlFileName;
		}

		public void setSqlFileName(String sqlFileName) {
			this.sqlFileName = sqlFileName;
		}

	}
	
}
