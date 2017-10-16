package org.scorpion.common.mdb;

import java.util.concurrent.TimeUnit;

import org.scorpion.api.common.AbsScorpionCacheConnPool;
import org.scorpion.api.common.IScorpionConnection;
import org.scorpion.api.common.IScorpionConnectionPool;
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
public class ScorpionRDCacheConnectionPool extends AbsScorpionCacheConnPool<IScorpionConnection<ScorpionApplicationRDKVCache>,ScorpionRDConnection>{
	
	
	private static IScorpionConnectionPool<IScorpionConnection<ScorpionApplicationRDKVCache>> pool;
	
	@Override
	public void initConnPool() throws ScorpionBaseException {
		
		if(this.getClazz() == null)
			throw new ScorpionBaseException("scorpion-6904:Cache connection class initiation faill ...");
	
		if(this.freeQueue == null|| this.busyQueue == null)
			this.initQueue();
		
		for(int i = 0; i<this.getConnNum();i++)
			try {
				this.freeQueue.offer(this.getClazz().newInstance().setConnInfo(this.getUrl(),this.getPort(),this).init());
			} catch (Throwable e) {
				throw new ScorpionBaseException("scorpion-5097: Instance cache connetion fail ...",e);
			}

	}

	
	@Override
	public IScorpionConnection<ScorpionApplicationRDKVCache> getConnection()throws ScorpionBaseException {
		IScorpionConnection<ScorpionApplicationRDKVCache> conn ;
		try {
			conn = this.freeQueue.poll(10, TimeUnit.SECONDS);
			if(conn == null)
				throw new ScorpionBaseException("scorpion-3460:There isn't idle conneciton to use ...");
		} catch (InterruptedException e) {
			throw new ScorpionBaseException("scorpion-9071: To get cache connection faill....");
		}
		return conn;
	}

	@Override
	public void extendConnection() throws ScorpionBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void statusCheck() throws ScorpionBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() throws ScorpionBaseException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public synchronized void close(IScorpionConnection<ScorpionApplicationRDKVCache> t)throws ScorpionBaseException {
		this.freeQueue.offer(t);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static synchronized IScorpionConnectionPool<IScorpionConnection<ScorpionApplicationRDKVCache>> getInstance(){
		
		if(pool == null)
			pool = new ScorpionRDCacheConnectionPool();
	
		return pool;
	}



}
