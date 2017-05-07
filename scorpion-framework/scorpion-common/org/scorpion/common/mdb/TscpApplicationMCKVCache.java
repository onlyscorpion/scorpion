package org.scorpion.common.mdb;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scorpion.api.common.AbsKVCache;
import org.scorpion.api.common.ITscpKVCache;
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
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpApplicationMCKVCache extends AbsKVCache implements ITscpKVCache{

	
	@Override
	public void insert(String key, Object value) throws TscpBaseException{}

	@Override
	public void insertMapStr(String key, Map<String, String> arguments) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T queryDataByKey(Object key, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryMapByKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String,Object>> queryLisMapByKey(String key) {
		return null;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exist(String key) {
		return false;
	}


	@Override
	public String getValueByKey(String key) throws TscpBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws TscpBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(Object key, Object value) throws TscpBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> queryKeys(String command) throws TscpBaseException {
		return null;
	}

	@Override
	public void clearAll() throws TscpBaseException {
		
	}


}
