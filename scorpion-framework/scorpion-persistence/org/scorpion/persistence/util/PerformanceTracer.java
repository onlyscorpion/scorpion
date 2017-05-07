package org.scorpion.persistence.util;

import org.scorpion.api.configuration.DBParam;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;

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
public class PerformanceTracer {
	
	public static void printDetailSQLInfo(String sql,DBParam params) throws TscpBaseException{
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
