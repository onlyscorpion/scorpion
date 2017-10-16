package org.scorpion.common.mdb;

import org.scorpion.api.common.AbsCacheConnection;
import org.scorpion.api.common.IScorpionConnection;
import org.scorpion.api.exception.ScorpionBaseException;

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
public class ScorpionMCConnection extends AbsCacheConnection<ScorpionApplicationMCKVCache>{

	private static final long serialVersionUID = 1L;

	@Override
	public ScorpionApplicationMCKVCache createHandle() throws ScorpionBaseException {
		return null;
	}

	@Override
	public IScorpionConnection<ScorpionApplicationMCKVCache> init() {
		return null;
	}

	@Override
	public void close() throws ScorpionBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void refreshConnection() {
		// TODO Auto-generated method stub
		
	}

}
