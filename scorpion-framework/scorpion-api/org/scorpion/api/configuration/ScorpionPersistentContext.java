package org.scorpion.api.configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
public class ScorpionPersistentContext implements Serializable{
	
	private static final long serialVersionUID = -7874037933393174821L;
	
	public Map<String,Map<String,Class<?>>> dbMetas;
	

	public Map<String, Map<String, Class<?>>> getDbMetas() {
		if(dbMetas == null)
			dbMetas = new HashMap<String, Map<String,Class<?>>>();
		return dbMetas;
	}

	public void setDbMetas(Map<String, Map<String, Class<?>>> dbMetas) {
		this.dbMetas = dbMetas;
	} 
	
	
}
