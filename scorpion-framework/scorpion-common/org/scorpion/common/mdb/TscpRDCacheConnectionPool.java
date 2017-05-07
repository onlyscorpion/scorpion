package org.scorpion.common.mdb;

import java.util.concurrent.TimeUnit;

import org.scorpion.api.common.AbsTscpCacheConnPool;
import org.scorpion.api.common.ITscpConnection;
import org.scorpion.api.common.ITscpConnectionPool;
import org.scorpion.api.exception.TscpBaseException;

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
public class TscpRDCacheConnectionPool extends AbsTscpCacheConnPool<ITscpConnection<TscpApplicationRDKVCache>,TscpRDConnection>{
	
	
	private static ITscpConnectionPool<ITscpConnection<TscpApplicationRDKVCache>> pool;
	
	@Override
	public void initConnPool() throws TscpBaseException {
		
		if(this.getClazz() == null)
			throw new TscpBaseException("TSCP-6904:Cache connection class initiation faill ...");
	
		if(this.freeQueue == null|| this.busyQueue == null)
			this.initQueue();
		
		for(int i = 0; i<this.getConnNum();i++)
			try {
				this.freeQueue.offer(this.getClazz().newInstance().setConnInfo(this.getUrl(),this.getPort(),this).init());
			} catch (Throwable e) {
				throw new TscpBaseException("TSCP-5097: Instance cache connetion fail ...",e);
			}

	}

	
	@Override
	public ITscpConnection<TscpApplicationRDKVCache> getConnection()throws TscpBaseException {
		ITscpConnection<TscpApplicationRDKVCache> conn ;
		try {
			conn = this.freeQueue.poll(10, TimeUnit.SECONDS);
			if(conn == null)
				throw new TscpBaseException("TSCP-3460:There isn't idle conneciton to use ...");
		} catch (InterruptedException e) {
			throw new TscpBaseException("TSCP-9071: To get cache connection faill....");
		}
		return conn;
	}

	@Override
	public void extendConnection() throws TscpBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void statusCheck() throws TscpBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() throws TscpBaseException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public synchronized void close(ITscpConnection<TscpApplicationRDKVCache> t)throws TscpBaseException {
		this.freeQueue.offer(t);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static synchronized ITscpConnectionPool<ITscpConnection<TscpApplicationRDKVCache>> getInstance(){
		
		if(pool == null)
			pool = new TscpRDCacheConnectionPool();
	
		return pool;
	}



}
