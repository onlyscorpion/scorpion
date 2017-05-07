package org.scorpion.persistence.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.scorpion.api.common.ITscpKVCache;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpComponent;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.TscpFileStreamHandler;
import org.scorpion.common.mdb.ConvertCacheData;
import org.scorpion.common.util.TscpKVMemoryCacheUtil;
import org.scorpion.persistence.cache.CacheConf.CacheRef;
import org.scorpion.persistence.cache.CacheConf.ViewItem;
import org.scorpion.persistence.util.TscpPersistenceUtils;

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
public class TscpDBCacheComponent extends AbsTscpComponent{
	
	
	private Map<String,CacheConf> sqlmap = new HashMap<String,CacheConf>();
	
	
	private String DEFUALT_CACHE_FILE = "tscp-db-cache.xml";
	

	@Override
	public void start(Map<String, String> arguments) throws TscpBaseException {
		
		PlatformLogger.info("Is beginning to load database cache component .....");
		
		if(!loadCacheConfig(arguments.get("cacheFile")==null?DEFUALT_CACHE_FILE:arguments.get("cacheFile")))
			return ;
		
		loadDbDataToCache();
		
	}
	
	@SuppressWarnings("unchecked")
	private boolean loadCacheConfig(String confFile) throws TscpBaseException{
		
		if(!new File(this.getClass().getResource("/"+confFile).getPath()).exists()){
			PlatformLogger.warn("TSCP-4976: System cache file [ tscp-db-cache.xml ] no found .....");
			return false;
		}
		try {
			Document docuemnt = DocumentHelper.parseText(TscpFileStreamHandler.getFileContent(this.getClass().getResource("/"+confFile).getPath()));
			List<Element> elements = docuemnt.getRootElement().elements();
			
			for(Element ele:elements){
			//	String dsname = ele.attributeValue(""); String freshTime = ele.attributeValue("");
				String isCache = ele.attributeValue("isCache");
				if(isCache == null|| "".equals(isCache)||!Boolean.parseBoolean(isCache)) 
					TscpKVMemoryCacheUtil.getRdKvCachePersistence().clearAll();
				List<Element> els = ele.elements();
				for(Element el:els){
					CacheConf cacheConf = new CacheConf();
					cacheConf.setSql(el.attributeValue("sql"));
					sqlmap.put(el.attributeValue("keycolumn"),cacheConf);
					List<Element> ches = el.elements();
					for(Element che:ches){
						ViewItem viewItem = cacheConf.new ViewItem();
						viewItem.setViewItemName(che.attributeValue("name"));
						viewItem.setCache(che.attribute("isCache")!=null?Boolean.parseBoolean(che.attributeValue("isCache")):false);
						List<Element> elres = che.elements();
						cacheConf.getRefs().add(viewItem);
						for(Element elre:elres){
							CacheConf.CacheRef cacheRef = cacheConf.new CacheRef();
							cacheRef.setSql(elre.attributeValue("sql")); 
							cacheRef.setSourceCode(elre.attributeValue("sourceCode"));
							cacheRef.setTargetCode(elre.attributeValue("targetCode"));
							cacheRef.setCaption(elre.attributeValue("caption"));
							if(cacheRef.getSourceCode() == null||"".equals(cacheRef.getSourceCode())||cacheRef.getTargetCode() == null||
									"".equals(cacheRef.getTargetCode())||cacheRef.getSql()==null||"".equals(cacheRef.getSql())||
									"".equals(cacheRef.getCaption())||cacheRef.getCaption() == null)
								throw new TscpBaseException("请检查配置文件["+DEFUALT_CACHE_FILE+"]属性 sourceCode  targetCode sql caption 配置.....");
							viewItem.getCacheRefs().add(cacheRef);
						}
					}
						
				}
			}
			
		} catch (Throwable e) {
			throw new TscpBaseException("TSCP-2144: Loading database cache file fail ...",e);
		}
		
		return true;
		
	}
	
	
	/**
	 * 
	 * @throws TscpBaseException
	 */
	private void loadDbDataToCache() throws TscpBaseException{
		
		for(Entry<String,CacheConf>entry:sqlmap.entrySet()){
			cacheTruckData(entry.getKey(),entry.getValue().getSql(),entry.getValue());
		}
		
	}
	
	/**
	 * @param keyCache
	 * @param sql
	 * @param cacheConf
	 * @throws TscpBaseException
	 */
	private void cacheTruckData(String keyCache,String sql,CacheConf cacheConf) throws TscpBaseException{
		
		ITscpKVCache kv = null;
		try{
			kv = TscpKVMemoryCacheUtil.getKVMemoryPersistence();
			List<Map<String,Object>> lismap = TscpPersistenceUtils.getPersistenceService().queryLisMapBySQL(sql, null);
			if(lismap == null||lismap.size()<1)
				return;
			if(keyCache.split("#").length>1){
				if(keyCache.split("\\.").length>1){
					List<String> keys = new ArrayList<String>();
					for(String tkey:keyCache.split("#")){
						String key = tkey.split("\\.")[0];
						String keyt = key.toLowerCase().split("_").length>1?(key.toLowerCase().split("_")[0]+String.valueOf(key.toLowerCase().split("_")[1].charAt(0)).toUpperCase()+(key.toLowerCase().split("_")[1].length()>1?key.toLowerCase().split("_")[1].substring(1):"")):key.toLowerCase();
						if(tkey.split("\\.").length>1)
							keys.add(keyt+".value");
						else
							keys.add(keyt);
					}
					String[] tmp = new String[keys.size()];
					keys.toArray(tmp);
					if(kv != null&&tmp != null&&tmp.length>0){
						for(Map<String,Object> cm:lismap){
							StringBuffer sb = new StringBuffer();
							for(String key:keys){
								if(key.split("\\.").length>1)
									sb.append("#").append(cm.get(key.split("\\.")[0]));
								else
									sb.append("#").append(key);
							}
							kv.insert(sb.substring(1), cm);
						}
					}
				}else if(keyCache.split("#")[0].split("\\.").length<1&&keyCache.split("#")[1].split("\\.").length<1){
					kv.insert(keyCache,lismap);
				}else{
					throw new TscpBaseException("不支持键值混合配对");
				}
			}else{
				if(keyCache.split("\\.").length>1){
					keyCache = keyCache.split("\\.")[0];
					String key = keyCache.toLowerCase().split("_").length>1?(keyCache.toLowerCase().split("_")[0]+String.valueOf(keyCache.toLowerCase().split("_")[1].charAt(0)).toUpperCase()+(keyCache.toLowerCase().split("_")[1].length()>1?keyCache.toLowerCase().split("_")[1].substring(1):"")):keyCache.toLowerCase();
					if(cacheConf.getRefs().size()<1)
						for(Map<String,Object> map:lismap)
							kv.insert(map.get(key),map);
					else
						cacheRefData(kv,lismap,new String[]{key},cacheConf.getRefs(),true);
				}else{
					if(lismap == null||lismap.size()<1)
						return;
					kv.insert(keyCache,lismap);
					if(cacheConf.getRefs().size()>0)
						cacheRefData(kv,lismap,new String[]{keyCache},cacheConf.getRefs(),false);
				}
			}
		}finally{
			if(kv != null)
				kv.close();
		}
	}
	
	
	/**
	 * @param kv
	 * @param map
	 * @param key
	 * @param viewItems
	 * @throws TscpBaseException
	 */
	private void cacheRefData(ITscpKVCache kv,List<Map<String,Object>> lismap,String[] key,List<ViewItem> viewItems,boolean isRow) throws TscpBaseException{
		
		for(ViewItem viewItem:viewItems){
			for(CacheRef cacheRef:viewItem.getCacheRefs())
				if(isRow)
					ConvertCacheData.convertCodeToCaption(kv,key,lismap,TscpPersistenceUtils.getPersistenceService().queryLisMapBySQL(cacheRef.getSql(), null),
							cacheRef.getSourceCode(),cacheRef.getTargetCode(),cacheRef.getCaption());
				else
					ConvertCacheData.convertCodeToCaption(null,null,lismap,TscpPersistenceUtils.getPersistenceService().queryLisMapBySQL(cacheRef.getSql(), null),
							cacheRef.getSourceCode(),cacheRef.getTargetCode(),cacheRef.getCaption());

			kv.insert(viewItem.getViewItemName(), lismap);
		}
	}

	
}
