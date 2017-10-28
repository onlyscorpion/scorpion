package com.scorpion.huakerongtong.common.mdb;

import com.scorpion.huakerongtong.api.common.AbsCacheConnection;
import com.scorpion.huakerongtong.api.common.IScorpionConnection;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.common.mdb.client.ScorpionRDClient;

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
