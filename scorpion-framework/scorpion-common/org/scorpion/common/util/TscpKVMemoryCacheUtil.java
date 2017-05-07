package org.scorpion.common.util;

import org.scorpion.api.common.ITscpKVCache;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.common.mdb.CacheType;
import org.scorpion.common.mdb.TscpApplicationLocalKVCache;
import org.scorpion.common.mdb.TscpRDCacheConnectionPool;

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
public class TscpKVMemoryCacheUtil {
	
	
	private static Boolean isRefreshing = false;
	
	
	/**
	 * 
	 * @Description Obtain default KVMemory persistent
	 * 
	 * @Time 2015-08-23
	 * 
	 * @return
	 */
	public static ITscpKVCache getLocalKVMemoryPersistence(){
		
		return TscpApplicationLocalKVCache.getLocalKvCache();
	}
	
	/**
	 * 
	 * @return
	 * @throws TscpBaseException 
	 */
	public static ITscpKVCache getRdKvCachePersistence() throws TscpBaseException{
		return TscpRDCacheConnectionPool.getInstance().getConnection().createHandle();
	}
	
	/**
	 * @description 
	 * 
	 * @param cacheType
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public static ITscpKVCache getKVMemoryPersistence(String cacheType) throws TscpBaseException{
		
		if(CacheType.LOCAL.name().equals(cacheType)){
			return TscpApplicationLocalKVCache.getLocalKvCache();
		}else if(CacheType.RDCACHE.name().equals(cacheType)){
			return TscpRDCacheConnectionPool.getInstance().getConnection().createHandle();
		}else if(CacheType.MCCACHE.name().equals(cacheType)){
			return null;
		}else{
			throw new TscpBaseException("TSCP-4098:Unknow cache type ["+cacheType+"] !");
		}
		
	}
	
	/**
	 * @return
	 * @throws TscpBaseException
	 */
	public static ITscpKVCache getKVMemoryPersistence() throws TscpBaseException{
		return getKVMemoryPersistence(CacheType.RDCACHE.name());
	}
	
	/**
	 * 
	 * @throws TscpBaseException
	 */
	public static void refreshDBCache() throws TscpBaseException{
		
		synchronized (isRefreshing) {
			if(isRefreshing ==  true)
				throw new TscpBaseException("TSCP-2347：系统正在刷新缓存....");
			else
				isRefreshing = true;
		}
		
		isRefreshing = false;

	}
	
}
