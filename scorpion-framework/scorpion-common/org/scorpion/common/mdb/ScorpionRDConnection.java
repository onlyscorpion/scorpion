package org.scorpion.common.mdb;

import org.scorpion.api.common.AbsCacheConnection;
import org.scorpion.api.common.IScorpionConnection;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.common.mdb.client.ScorpionRDClient;

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
public class ScorpionRDConnection extends AbsCacheConnection<ScorpionApplicationRDKVCache>{

	private static final long serialVersionUID = -8060789997800039317L;
	
	private ScorpionApplicationRDKVCache cache;

	
	@Override
	public ScorpionApplicationRDKVCache createHandle() throws ScorpionBaseException {
		return cache;
	}

	@Override
	public IScorpionConnection<ScorpionApplicationRDKVCache> init() throws ScorpionBaseException {
		cache= new ScorpionApplicationRDKVCache();
		cache.setClient(new ScorpionRDClient(this.getUrl(),this.getPort()));
		cache.setConn(this);
		return this;
	}

	@Override
	public void close() throws ScorpionBaseException {
		synchronized (ScorpionRDConnection.class) {
			this.pool.close(this);
		}
	}

	@Override
	protected void refreshConnection() {
		
	}

	public void setPool(ScorpionRDCacheConnectionPool pool) {
		this.pool = pool;
	}

}
