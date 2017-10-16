package org.scorpion.common.util;

import org.scorpion.api.common.IScorpionKVCache;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.common.mdb.CacheType;
import org.scorpion.common.mdb.ScorpionApplicationLocalKVCache;
import org.scorpion.common.mdb.ScorpionRDCacheConnectionPool;

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
public class ScorpionKVMemoryCacheUtil {
	
	
	private static Boolean isRefreshing = false;
	
	
	/**
	 * 
	 * @Description Obtain default KVMemory persistent
	 * 
	 * @Time 2015-08-23
	 * 
	 * @return
	 */
	public static IScorpionKVCache getLocalKVMemoryPersistence(){
		
		return ScorpionApplicationLocalKVCache.getLocalKvCache();
	}
	
	/**
	 * 
	 * @return
	 * @throws ScorpionBaseException 
	 */
	public static IScorpionKVCache getRdKvCachePersistence() throws ScorpionBaseException{
		return ScorpionRDCacheConnectionPool.getInstance().getConnection().createHandle();
	}
	
	/**
	 * @description 
	 * 
	 * @param cacheType
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public static IScorpionKVCache getKVMemoryPersistence(String cacheType) throws ScorpionBaseException{
		
		if(CacheType.LOCAL.name().equals(cacheType)){
			return ScorpionApplicationLocalKVCache.getLocalKvCache();
		}else if(CacheType.RDCACHE.name().equals(cacheType)){
			return ScorpionRDCacheConnectionPool.getInstance().getConnection().createHandle();
		}else if(CacheType.MCCACHE.name().equals(cacheType)){
			return null;
		}else{
			throw new ScorpionBaseException("scorpion-4098:Unknow cache type ["+cacheType+"] !");
		}
		
	}
	
	/**
	 * @return
	 * @throws ScorpionBaseException
	 */
	public static IScorpionKVCache getKVMemoryPersistence() throws ScorpionBaseException{
		return getKVMemoryPersistence(CacheType.RDCACHE.name());
	}
	
	/**
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void refreshDBCache() throws ScorpionBaseException{
		
		synchronized (isRefreshing) {
			if(isRefreshing ==  true)
				throw new ScorpionBaseException("scorpion-2347：系统正在刷新缓存....");
			else
				isRefreshing = true;
		}
		
		isRefreshing = false;

	}
	
}
