package org.scorpion.common.mdb;

import org.scorpion.api.common.AbsCacheConnection;
import org.scorpion.api.common.ITscpConnection;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.common.mdb.client.TscpRDClient;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpRDConnection extends AbsCacheConnection<TscpApplicationRDKVCache>{

	private static final long serialVersionUID = -8060789997800039317L;
	
	private TscpApplicationRDKVCache cache;

	
	@Override
	public TscpApplicationRDKVCache createHandle() throws TscpBaseException {
		return cache;
	}

	@Override
	public ITscpConnection<TscpApplicationRDKVCache> init() throws TscpBaseException {
		cache= new TscpApplicationRDKVCache();
		cache.setClient(new TscpRDClient(this.getUrl(),this.getPort()));
		cache.setConn(this);
		return this;
	}

	@Override
	public void close() throws TscpBaseException {
		synchronized (TscpRDConnection.class) {
			this.pool.close(this);
		}
	}

	@Override
	protected void refreshConnection() {
		
	}

	public void setPool(TscpRDCacheConnectionPool pool) {
		this.pool = pool;
	}

}
