package org.scorpion.common.mdb;

import org.scorpion.api.common.AbsCacheConnection;
import org.scorpion.api.common.ITscpConnection;
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
public class TscpLocalConnection extends AbsCacheConnection<TscpApplicationLocalKVCache>{

	
	private static final long serialVersionUID = 8032160276607620998L;
	
	@Override
	public TscpApplicationLocalKVCache createHandle() throws TscpBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITscpConnection<TscpApplicationLocalKVCache> init() throws TscpBaseException {
		
		return null;
	}

	@Override
	public void close() throws TscpBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void refreshConnection() {
		// TODO Auto-generated method stub
		
	}

}
