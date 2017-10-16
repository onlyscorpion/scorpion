package org.scorpion.common.mdb;

import org.scorpion.api.common.AbsCacheConnection;
import org.scorpion.api.common.IScorpionConnection;
import org.scorpion.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionLocalConnection extends AbsCacheConnection<ScorpionApplicationLocalKVCache>{

	
	private static final long serialVersionUID = 8032160276607620998L;
	
	@Override
	public ScorpionApplicationLocalKVCache createHandle() throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IScorpionConnection<ScorpionApplicationLocalKVCache> init() throws ScorpionBaseException {
		
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
