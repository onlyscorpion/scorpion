package org.scorpion.kernel.component;

import java.util.Map;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionComponent;
import org.scorpion.common.mdb.ScorpionRDCacheConnectionPool;
import org.scorpion.common.mdb.ScorpionRDConnection;

/**
 * 天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>
 * com.SCORPION.Scorpion.common
 * <p>
 * File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 SCORPION.COM.CN
 * </p>
 * <p>
 * Company: SCORPION.COM.CN
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionMemoryCacheComponent extends AbsScorpionComponent {

	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {
		
		ScorpionRDCacheConnectionPool cachePool = (ScorpionRDCacheConnectionPool) ScorpionRDCacheConnectionPool.getInstance();
		
		if(arguments.get("ip") == null || arguments.get("port") == null)
			throw new ScorpionBaseException("scorpion-6036:Cache information deficiency, check ip and port configuration ...");
		
		cachePool.setConnInfo(arguments.get("ip"), Integer.parseInt(arguments.get("port")),ScorpionRDConnection.class);
		
		cachePool.initConnPool();
	}

}
