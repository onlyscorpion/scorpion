package org.scorpion.common.mdb;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.scorpion.api.common.AbsKVCache;
import org.scorpion.api.common.ITscpConnection;
import org.scorpion.api.common.ITscpKVCache;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.common.mdb.client.TscpRDClient;

import java.util.Set;

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
public class TscpApplicationRDKVCache  extends AbsKVCache implements ITscpKVCache{
	
	private TscpRDClient client;
	
	private static ITscpKVCache tscpRdKvCache;
	
	private ITscpConnection<TscpApplicationRDKVCache> conn;

	
	
	@Override
	public void insert(String key, Object value) throws TscpBaseException{
		try{
			if(value instanceof String)
				client.set(key, (String)value);
			else 
				client.set(key.getBytes(), this.objectConvertByte(value));
		}finally{
			this.close();
		}
	}

	@Override
	public void insertMapStr(String key, Map<String, String> arguments) throws TscpBaseException {
		try{
			if(arguments == null)
				client.hset(key, null, null);
			for(Entry<String,String>entry:arguments.entrySet()){
				client.hset(key, entry.getKey(), entry.getValue());
			}
		}finally{
			this.close();
		}
	}
	
	@Override
	public void insert(Object key, Object value) throws TscpBaseException {
		try{
			if(key instanceof String)
				insert((String)key,value);
			else
				client.set(this.objectConvertByte(key), this.objectConvertByte(value));
		}finally{
			this.close();
		}
	}

	public void putLis(String key, List<String> lis) throws TscpBaseException {
		try{
			for(String str:lis)
				client.lset(key, lis.size(), str);
		}finally{
			this.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T queryDataByKey(Object key, Class<T> clazz) throws TscpBaseException {
		try{
			return  (T) byteConvertObj(client.get(this.objectConvertByte(key)));
		}finally{
			this.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryMapByKey(String key) throws TscpBaseException {
		try{
			return  (Map<String, Object>) byteConvertObj(client.get(key.getBytes()));
		}finally{
			this.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> queryLisMapByKey(String key) throws TscpBaseException {
		try{
			return  (List<Map<String,Object>>) byteConvertObj(client.get(key.getBytes()));
		}finally{
			this.close();
		}
	}

	public TscpApplicationRDKVCache setClient(TscpRDClient client) throws TscpBaseException {
		this.client = client;
		testConn();
		return this;
	}
	
	
	private void testConn() throws TscpBaseException{
		try{
			client.get("context"); 
		}catch(Throwable ex){
			throw new TscpBaseException("Redis connect refuse , please ip and port information. make sure the redis server is starting ...",ex);
		}
	}
	
	
	public synchronized static ITscpKVCache getRDKVCache(){
		
		if(tscpRdKvCache == null)
			tscpRdKvCache = new TscpApplicationRDKVCache();
		
		return tscpRdKvCache;
	}


	@Override
	public void destroy() {
          
	}

	@Override
	public boolean exist(String key) throws TscpBaseException {
		try{
			return client.exists(key);
		}finally{
			this.close();
		}
	}

	@Override
	public String getValueByKey(String key) throws TscpBaseException {
		try{
			return client.get(key);
		}finally{
			this.close();
		}
	}

	@Override
	public void close() throws TscpBaseException {
		conn.close();
	}

	public ITscpConnection<TscpApplicationRDKVCache> getConn() {
		return conn;
	}

	public void setConn(ITscpConnection<TscpApplicationRDKVCache> conn) {
		this.conn = conn;
	}

	@Override
	public Set<String> queryKeys(String command) throws TscpBaseException {
		try{
			return client.keys(command);
		}finally{
			this.close();
		}
	}

	@Override
	public void clearAll() throws TscpBaseException {
		try{
			client.flushAll();
		}finally{
			this.close();
		}
	}

	
}
