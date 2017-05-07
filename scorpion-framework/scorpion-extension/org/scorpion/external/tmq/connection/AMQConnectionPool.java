package org.scorpion.external.tmq.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.scorpion.api.log.PlatformLogger;

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
public class AMQConnectionPool extends AbsConnectionPool<ActiveMQConnection>{


	public static boolean isInit = false;
	
	private Map<String,Boolean> isCacheMap = new HashMap<String,Boolean>();
	
	
	/**
	 * @param amqPo
	 * 
	 * @throws ConnectionPoolException 
	 * 
	 */
	public AMQConnectionPool(List<MQDataSource> datasources) throws ConnectionPoolException {
		super();
		if(this.datasoruces == null)
			this.datasoruces = new ArrayList<MQDataSource>();
		this.datasoruces.addAll(datasources);
		initPool();
	}

	public AMQConnectionPool(List<MQDataSource> datasources,boolean isRebuild) throws ConnectionPoolException {
		super();
		if(isRebuild)
			this.datasoruces = new ArrayList<MQDataSource>();
		this.datasoruces.addAll(datasources);
		initPool();
		isInit = false;
	}
	
	@Override
	public void initPool() throws ConnectionPoolException {
		checkAmqInfo(datasoruces);
		try{
			for(MQDataSource datasource:datasoruces){
				if(datasource.getConnUrl()==null||"".equals(datasource.getConnUrl()))
					return;
				if(freePool != null && freePool.containsKey((datasource.getIdentity()==null?datasource.getConnUrl():datasource.getIdentity())))
					return ;
				this.isCacheMap.put(datasource.getIdentity()==null?datasource.getConnUrl():datasource.getIdentity(), datasource.isCacheConn());
				this.initConnQueue(datasource.getIdentity()==null?datasource.getConnUrl():datasource.getIdentity(),datasource.getMaxActive());
			/*	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(datasource.getConnUrl());
				for (int i = 0; i < datasource.getMaxActive(); i++) {
					this.freePool.get(datasource.getIdentity()).put((ActiveMQConnection) connectionFactory.createConnection());
				}*/
			}
			//connecitonMonitor();
		}catch(Exception e){
			throw new ConnectionPoolException(e.getMessage(),e);
		}
	}
	
	
/*	public void connecitonMonitor(){
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				for(Entry<String,LinkedBlockingQueue<ActiveMQConnection>> map:freePool.entrySet()){
					PlatformLogger.info("========Current free connections ["+map.getKey()+"   "+map.getValue().size()+"]=======");
				}
			}
		}, 0, 5000);
	}*/

	/**
	 * @param amqXyPo
	 * @throws ConnectionPoolException 
	 */
	public void checkAmqInfo(List<MQDataSource> datasources) throws ConnectionPoolException {
		
		if(datasources == null)
			throw new ConnectionPoolException("MQ datasource init information can't be null !");
		for(MQDataSource datasource:datasoruces)
			if (datasource.getConnUrl() == null || "".equals(datasource.getConnUrl()))
				throw new ConnectionPoolException("Can't initialize mq connection pool. The Url["+datasource.getConnUrl()+"]");
	}

	@Override
	public void reloadPool() throws ConnectionPoolException {
		initPool();
	}

	@Override
	public ActiveMQConnection getConnection(String key) throws ConnectionPoolException {
		
		isInit();
		try {
			synchronized (this) {
				MQDataSource datasource = null;
				for(MQDataSource source:this.datasoruces){
					if(!key.equals(source.getIdentity()==null?source.getConnUrl():source.getIdentity()))
						continue;
					datasource = source;
					break;
				}
				if(datasource == null)
					throw new ConnectionPoolException("The conneciton Pool don't initialize. The Identity key ["+key+"]");
				
				if(!datasource.isCacheConn())
					return (ActiveMQConnection) new ActiveMQConnectionFactory(datasource.getConnUrl()).createConnection();

				
				if(datasource.isTempDataSource())
					if(!initialiazeTemp(datasource))
						throw new ConnectionPoolException("The temp mqdatasource information isn't integrated");
						
				ActiveMQConnection conn = freePool.get(key).poll(datasource.getMaxWait(), TimeUnit.SECONDS);
				if(conn == null)
					throw new ConnectionPoolException("There isn't available connection in the activemq connection pool !");
				return conn;
			}
		} catch (Throwable e) {
			throw new ConnectionPoolException("TSCP-9075:Generate connection from MQ conneciton pool failure !",e);
		}
	}
	
	
	/**
	 * @param datasource
	 * @return
	 * @throws InterruptedException
	 * @throws JMSException
	 */
	private boolean initialiazeTemp(MQDataSource datasource) throws InterruptedException, JMSException{

		if(datasource.getConnUrl()==null||"".equals(datasource.getConnUrl()))
			return false;
		this.isCacheMap.put(datasource.getIdentity()==null?datasource.getConnUrl():datasource.getIdentity(), datasource.isCacheConn());
		if(!datasource.isCacheConn()||this.freePool.get(datasource.getIdentity()==null?datasource.getConnUrl():datasource.getIdentity())!=null)
			return true;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(datasource.getConnUrl());
		if(super.version == null)
			super.version = new HashMap<String,Long>();
		super.version.put(datasource.getIdentity(), 1L);
		for (int i = 0; i < datasource.getMaxActive(); i++) {
			this.freePool.get(datasource.getIdentity()==null?datasource.getConnUrl():datasource.getIdentity()).put((ActiveMQConnection) connectionFactory.createConnection());
		}
		return true;
	
	}
	
	
	private synchronized void isInit() throws ConnectionPoolException{
		try{
			if(!isInit)
				isInit = true;
			else 
				return; 
			for(MQDataSource datasource:datasoruces){
				if(datasource.getConnUrl()==null||"".equals(datasource.getConnUrl())||!datasource.isCacheConn()||!datasource.isCacheConn())
					continue;
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(datasource.getConnUrl());
				if(super.version == null)
					super.version = new HashMap<String,Long>();
				super.version.put(datasource.getIdentity()==null?datasource.getConnUrl():datasource.getIdentity(), 1L);
				for (int i = 0; i < datasource.getMaxActive(); i++) {
					this.freePool.get(datasource.getIdentity()==null?datasource.getConnUrl():datasource.getIdentity()).put((ActiveMQConnection) connectionFactory.createConnection());
				}
			}
		}catch(Exception e){
			throw new ConnectionPoolException(e);
		}
	}
	
	
	@Override
	public void close(String key,ActiveMQConnection k) throws ConnectionPoolException {
		synchronized (this) {
			try {
				if(this.isCacheMap.get(key) != null&&!this.isCacheMap.get(key))
					k.close();
				else
					freePool.get(key).put(k);
			} catch (Throwable e) {
				throw new ConnectionPoolException("TSCP-6876:Release connection failure . The connection key is ["+key+"]");
			}
		}
	}

	@Override
	public void distoryPool() throws ConnectionPoolException {
		isInit = false;
		if(freePool != null){
			for(Entry<String, LinkedBlockingQueue<ActiveMQConnection>> entry:freePool.entrySet()){
				for(ActiveMQConnection connection:entry.getValue())
					try {
						connection.close();
					} catch (JMSException e) {
						PlatformLogger.error(e);
					}
				entry.getValue().clear();
			}
			freePool.clear();
		}
		if(CURRENT_NUM != null) CURRENT_NUM.clear();
	}

	@Override
	public void distoryConnection(String key, ActiveMQConnection k)throws ConnectionPoolException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized ActiveMQConnection rebuildConntion(ActiveMQConnection k,String connName, Exception e, int waitTime, int tryNum,long version)throws ConnectionPoolException {
	
		/*if(version != super.version.get(connName))
			return null;*/
		
		try{
			if(k != null)
				k.close();
		}catch(Throwable ex){
			ex.printStackTrace();
		}
		
		if(connName == null)
			throw new ConnectionPoolException("Rebuilding activemq connection failure, the name of protocol don't allow to be null !");
		
		boolean signal  = true;
		
		for(MQDataSource datasource:this.datasoruces){
			if(!connName.equals(datasource.getIdentity()))
				continue;
			
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(datasource.getConnUrl());
			signal = false;
			try {
				freePool.get(datasource.getIdentity()).clear();
				for (int i = 0; i < datasource.getMaxActive(); i++) {
					this.freePool.get(datasource.getIdentity()).put((ActiveMQConnection) connectionFactory.createConnection());
				}
			//	super.version.put(datasource.getIdentity(), super.version.get(datasource.getIdentity())+1);
				return freePool.get(datasource.getIdentity()).poll(datasource.getMaxWait(), TimeUnit.SECONDS);
			} catch (Throwable e1) {
				throw new ConnectionPoolException("Rebuilding connection by protocol id exception !",e1);
			}
		}
		
		if(signal){
			throw new ConnectionPoolException("Application don't rebuild connection , the reason is that don't find the protocol id ["+connName+"] mapping amqxy !");
		}
		
		return null;
	}

	@Override
	public void extendPool(String key) throws ConnectionPoolException {
		
	}


}
