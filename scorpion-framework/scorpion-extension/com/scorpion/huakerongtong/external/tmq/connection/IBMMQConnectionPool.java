package com.scorpion.huakerongtong.external.tmq.connection;

import java.util.List;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class IBMMQConnectionPool extends AbsConnectionPool<Object> {
	
	
	

	public IBMMQConnectionPool(List<MQDataSource> datasoruces) {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initPool() throws ConnectionPoolException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reloadPool() throws ConnectionPoolException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void distoryPool() throws ConnectionPoolException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void distoryConnection(String key, Object k)
			throws ConnectionPoolException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getConnection(String key) throws ConnectionPoolException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object rebuildConntion(Object k, String connName, Exception e,
			int waitTime, int tryNum, long version)
			throws ConnectionPoolException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void extendPool(String key) throws ConnectionPoolException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close(String key, Object k) throws ConnectionPoolException {
		// TODO Auto-generated method stub
		
	}

}
