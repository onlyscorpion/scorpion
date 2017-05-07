package org.scorpion.common.mdb;

import java.util.List;
import java.util.Map;

import org.scorpion.api.common.ITscpKVCache;
import org.scorpion.api.exception.TscpBaseException;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ConvertCacheData {
	
	
	/**
	 * @param map
	 * @param lis
	 */
	public static void convertCodeToCaption(Map<String,Object> map,List<Map<String,Object>>lis,
			String sorceCode,String targetCode,String caption){
	
		for(Map<String,Object>m:lis){
			if(map.get(sorceCode) != null&&m.get(targetCode)!=null&&map.get(sorceCode).equals(m.get(targetCode))){
				map.put(sorceCode, m.get(caption));
				break;
			}
		}
	}
	
	
	/**
	 * @param map
	 * @param lis
	 * @param sorceCode
	 * @param targetCode
	 * @param caption
	 * @throws TscpBaseException 
	 */
	public static void convertCodeToCaption(ITscpKVCache kv,String[] keys,List<Map<String,Object>> map,List<Map<String,Object>>lis,
			String sorceCode,String targetCode,String caption) throws TscpBaseException{
		
		for(Map<String,Object> somap:map){
			if(kv != null&&keys != null&&keys.length>0){
				StringBuffer sb = new StringBuffer("#");
			    for(String key:keys){
			    	sb.append("#").append(somap.get(key));
			    }
				kv.insert(sb.substring(1), somap);
			convertCodeToCaption(somap,lis,sorceCode,targetCode,caption);
			}
		}
	}


}

