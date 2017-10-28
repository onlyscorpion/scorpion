package com.scorpion.huakerongtong.persistence.util;

import com.scorpion.huakerongtong.api.configuration.DBParam;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.common.context.SystemContext;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class PerformanceTracer {
	
	public static void printDetailSQLInfo(String sql,DBParam params) throws ScorpionBaseException{
		if(!Constant.DEVELOP_MODEL.equals(SystemContext.getApplicationContext().getSystemCoreConfig().getRunModel())){
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("执行:").append(sql).append("参数:");
		if(params.isEmpty())
			sb.append("[]");
		sb.append("[");
		for(Object param:params.getParams()){
			sb.append(param);
		}
		sb.append("]");
		PlatformLogger.debug(sb.toString());
	}
	
	

}
