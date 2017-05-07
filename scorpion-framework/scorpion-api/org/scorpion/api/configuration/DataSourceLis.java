package org.scorpion.api.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.scorpion.api.exception.TscpBaseException;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class DataSourceLis implements Serializable{
	
	private static final long serialVersionUID = 3431777141251247638L;
	
	public final static String DEFAULT_DATASOURCE = "dds";
	
	private final static Map<String,DataSourceInfo> dataSourceMap = new HashMap<String,DataSourceInfo>();
	
	
	/**
	 * 保存数据源信息
	 * 
	 * @param name
	 * 
	 * @param dataSource
	 * 
	 * @param isDefaultDataSource
	 * 
	 */
    public void putDefaultDataSource(String name,DataSourceInfo dataSource,boolean isDefaultDataSource){
    	
    	if(isDefaultDataSource)
    		dataSourceMap.put(DEFAULT_DATASOURCE, dataSource);
    	
    	else
    		dataSourceMap.put(name, dataSource);
    }
    
    /**
     * 
     * @return
     * @throws TscpBaseException
     */
    public static DataSourceInfo getDefaultDataSource(){
   
    	return dataSourceMap.get(DEFAULT_DATASOURCE);
   
    }
  
    /**
     * @return
     */
    public static Map<String,DataSourceInfo> getAllDataSource(){
    	
    	return dataSourceMap;
  
    }
    
    
    /**
     * 获取多数据源
     * 
     * @return
     * 
     * @throws TscpBaseException
     */
    public static List<DataSourceInfo> getOtherDataSource()throws TscpBaseException{
    	
    	if(dataSourceMap.containsKey(DEFAULT_DATASOURCE)&&dataSourceMap.size()==1)
    		
    		return null;
    	
    	List<DataSourceInfo> dataSourceLis = new ArrayList<DataSourceInfo>();
    	
    	for(Entry<String,DataSourceInfo>entry:dataSourceMap.entrySet()){
    		
    		if(!DEFAULT_DATASOURCE.equals(entry.getKey())){
    		
    			dataSourceLis.add(entry.getValue());
    	
    		}
    	}
    	
    	return dataSourceLis;
    }
    
    /**
     * dataSource entity
     * 
     * @author zcl
     */
	public class DataSourceInfo implements Serializable{
		
		private static final long serialVersionUID = -58606047810273380L;

		private String name;
		
		private String driverClassName;
		
		private boolean isUseJndiDs;
		
		private int initSize;
		
		private int maxActive;
		
		private int maxIdle;
		
		private int maxWait;
		
		private String url;
		
		private String user;
		
		private String dscp;
		
		private String passwd;
		
		private int dbType; 
		
		private boolean isDumpStack;
		
		private int connTimeout;
		
		private boolean isDefaultDataSource;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDriverClassName() {
			return driverClassName;
		}

		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}

		public boolean isUseJndiDs() {
			return isUseJndiDs;
		}

		public void setUseJndiDs(boolean isUseJndiDs) {
			this.isUseJndiDs = isUseJndiDs;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPasswd() {
			return passwd;
		}

		public void setPasswd(String passwd) {
			this.passwd = passwd;
		}

		public boolean isDefaultDataSource() {
			return isDefaultDataSource;
		}

		public void setDefaultDataSource(boolean isDefaultDataSource) {
			this.isDefaultDataSource = isDefaultDataSource;
		}

		public int getInitSize() {
			return initSize;
		}

		public void setInitSize(int initSize) {
			this.initSize = initSize;
		}

		public int getMaxActive() {
			return maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		public int getMaxIdle() {
			return maxIdle;
		}

		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}

		public int getMaxWait() {
			return maxWait;
		}

		public void setMaxWait(int maxWait) {
			this.maxWait = maxWait;
		}

		public String getDscp() {
			return dscp;
		}

		public void setDscp(String dscp) {
			this.dscp = dscp;
		}

		public int getDbType() {
			return dbType;
		}

		public void setDbType(int dbType) {
			this.dbType = dbType;
		}

		public boolean isDumpStack() {
			return isDumpStack;
		}

		public void setDumpStack(boolean isDumpStack) {
			this.isDumpStack = isDumpStack;
		}

		public int getConnTimeout() {
			return connTimeout;
		}

		public void setConnTimeout(int connTimeout) {
			this.connTimeout = connTimeout;
		}
		
	}
	
	
}
