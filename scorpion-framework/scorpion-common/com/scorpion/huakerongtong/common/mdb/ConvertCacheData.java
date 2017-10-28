package com.scorpion.huakerongtong.common.mdb;

import java.util.List;
import java.util.Map;

import com.scorpion.huakerongtong.api.common.IScorpionKVCache;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
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
	 * @throws ScorpionBaseException 
	 */
	public static void convertCodeToCaption(IScorpionKVCache kv,String[] keys,List<Map<String,Object>> map,List<Map<String,Object>>lis,
			String sorceCode,String targetCode,String caption) throws ScorpionBaseException{
		
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

