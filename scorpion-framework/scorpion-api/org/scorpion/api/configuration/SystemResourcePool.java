package org.scorpion.api.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: system constant information </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class SystemResourcePool {
	
	private static SystemResourcePool systemResourceFactory;
	
	private final Map<String,Object> resourceHashPool = new ConcurrentHashMap<String,Object>();
	
	
	private SystemResourcePool(){
		initResource();
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T getResourceByKey(String key,Class<T> T){
		return (T) resourceHashPool.get(key);
	}
	
	public void initResource(){
		
		resourceHashPool.put(SystemEnumType.systemcoreconfigresource.getValue(), new TscpCoreConfig());
		resourceHashPool.put(SystemEnumType.systemcomponentresource.getValue(), new HashMap<String,ComponentInformation>());
		resourceHashPool.put(SystemEnumType.systemdatasourceresource.getValue(), new DataSourceLis());
		resourceHashPool.put(SystemEnumType.systemexceptionconfigresource.getValue(),new ArrayList<ExceptionInfo>());
		resourceHashPool.put(SystemEnumType.datasource.getValue(), new HashMap<String,DataSource>());
		resourceHashPool.put(SystemEnumType.systemsqlconfigresource.getValue(), new SQLConfig());
		resourceHashPool.put(SystemEnumType.persistentMediate.getValue(), new TscpPersistentContext());
	}
	
	
	public synchronized static SystemResourcePool getDefaultInstance(){
	
		if(systemResourceFactory == null)
			systemResourceFactory = new SystemResourcePool();
		
		return systemResourceFactory;
	}

}
