package org.scorpion.kernel.component;

import java.util.Map;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpComponent;
import org.scorpion.common.mdb.TscpRDCacheConnectionPool;
import org.scorpion.common.mdb.TscpRDConnection;

/**
 * 自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.tscp.common
 * <p>
 * File: AbsTscpFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 taiji.com.cn
 * </p>
 * <p>
 * Company: taiji.com.cn
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpMemoryCacheComponent extends AbsTscpComponent {

	@Override
	public void start(Map<String, String> arguments) throws TscpBaseException {
		
		TscpRDCacheConnectionPool cachePool = (TscpRDCacheConnectionPool) TscpRDCacheConnectionPool.getInstance();
		
		if(arguments.get("ip") == null || arguments.get("port") == null)
			throw new TscpBaseException("TSCP-6036:Cache information deficiency, check ip and port configuration ...");
		
		cachePool.setConnInfo(arguments.get("ip"), Integer.parseInt(arguments.get("port")),TscpRDConnection.class);
		
		cachePool.initConnPool();
	}

}
