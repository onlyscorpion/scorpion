package com.scorpion.huakerongtong.common.mdb;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.scorpion.huakerongtong.api.common.AbsKVCache;
import com.scorpion.huakerongtong.api.common.IScorpionConnection;
import com.scorpion.huakerongtong.api.common.IScorpionKVCache;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.common.mdb.client.ScorpionRDClient;

import java.util.Set;

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
public class ScorpionApplicationRDKVCache  extends AbsKVCache implements IScorpionKVCache{
	
	private ScorpionRDClient client;
	
	private static IScorpionKVCache ScorpionRdKvCache;
	
	private IScorpionConnection<ScorpionApplicationRDKVCache> conn;

	
	
	@Override
	public void insert(String key, Object value) throws ScorpionBaseException{
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
	public void insertMapStr(String key, Map<String, String> arguments) throws ScorpionBaseException {
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
	public void insert(Object key, Object value) throws ScorpionBaseException {
		try{
			if(key instanceof String)
				insert((String)key,value);
			else
				client.set(this.objectConvertByte(key), this.objectConvertByte(value));
		}finally{
			this.close();
		}
	}

	public void putLis(String key, List<String> lis) throws ScorpionBaseException {
		try{
			for(String str:lis)
				client.lset(key, lis.size(), str);
		}finally{
			this.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T queryDataByKey(Object key, Class<T> clazz) throws ScorpionBaseException {
		try{
			return  (T) byteConvertObj(client.get(this.objectConvertByte(key)));
		}finally{
			this.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryMapByKey(String key) throws ScorpionBaseException {
		try{
			return  (Map<String, Object>) byteConvertObj(client.get(key.getBytes()));
		}finally{
			this.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> queryLisMapByKey(String key) throws ScorpionBaseException {
		try{
			return  (List<Map<String,Object>>) byteConvertObj(client.get(key.getBytes()));
		}finally{
			this.close();
		}
	}

	public ScorpionApplicationRDKVCache setClient(ScorpionRDClient client) throws ScorpionBaseException {
		this.client = client;
		testConn();
		return this;
	}
	
	
	private void testConn() throws ScorpionBaseException{
		try{
			client.get("context"); 
		}catch(Throwable ex){
			throw new ScorpionBaseException("Redis connect refuse , please ip and port information. make sure the redis server is starting ...",ex);
		}
	}
	
	
	public synchronized static IScorpionKVCache getRDKVCache(){
		
		if(ScorpionRdKvCache == null)
			ScorpionRdKvCache = new ScorpionApplicationRDKVCache();
		
		return ScorpionRdKvCache;
	}


	@Override
	public void destroy() {
          
	}

	@Override
	public boolean exist(String key) throws ScorpionBaseException {
		try{
			return client.exists(key);
		}finally{
			this.close();
		}
	}

	@Override
	public String getValueByKey(String key) throws ScorpionBaseException {
		try{
			return client.get(key);
		}finally{
			this.close();
		}
	}

	@Override
	public void close() throws ScorpionBaseException {
		conn.close();
	}

	public IScorpionConnection<ScorpionApplicationRDKVCache> getConn() {
		return conn;
	}

	public void setConn(IScorpionConnection<ScorpionApplicationRDKVCache> conn) {
		this.conn = conn;
	}

	@Override
	public Set<String> queryKeys(String command) throws ScorpionBaseException {
		try{
			return client.keys(command);
		}finally{
			this.close();
		}
	}

	@Override
	public void clearAll() throws ScorpionBaseException {
		try{
			client.flushAll();
		}finally{
			this.close();
		}
	}

	
}
