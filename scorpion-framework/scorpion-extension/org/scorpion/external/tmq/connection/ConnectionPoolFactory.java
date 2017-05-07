package org.scorpion.external.tmq.connection;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
public class ConnectionPoolFactory {

	private static ConnectionPoolFactory connectionFactory;
	
	private Map<String, AbsConnectionPool<?>> connPool = new ConcurrentHashMap<String, AbsConnectionPool<?>>();

	/**
	 * @param connType
	 * @param object
	 * @throws ESBConnectionPoolException
	 */
	public void initConnectionFactory(String connType,List<MQDataSource> datasoruce) throws ConnectionPoolException{
		if(ConnectionFactoryType.IBMMQConnection.getValue().equals(connType)){
			connPool.put(connType, new IBMMQConnectionPool(datasoruce));
		}else if(ConnectionFactoryType.AMQConnection.getValue().equals(connType)){
			connPool.put(connType, new AMQConnectionPool(datasoruce));
		}else{
			throw new ConnectionPoolException("Can't instance connection pool "+ datasoruce.toString());
		}
		
	}
	
	public void rebuildConnectionFactory(String connType,List<MQDataSource> datasoruce) throws ConnectionPoolException{
		if(ConnectionFactoryType.IBMMQConnection.getValue().equals(connType)){
			connPool.put(connType, new IBMMQConnectionPool(datasoruce));
		}else if(ConnectionFactoryType.AMQConnection.getValue().equals(connType)){
			connPool.put(connType, new AMQConnectionPool(datasoruce,true));
		}else{
			throw new ConnectionPoolException("Can't instance connection pool "+ datasoruce.toString());
		}
		
	}
	
	/**
	 * @param connType
	 * @return
	 * @throws ESBConnectionPoolException
	 */
	public AbsConnectionPool<?> getConnection(String connType)throws ConnectionPoolException{
		if(!ConnectionFactoryType.IBMMQConnection.getValue().equals(connType)&&!ConnectionFactoryType.AMQConnection.getValue().equals(connType))
			throw new ConnectionPoolException("Undefined Connection pool type !");
		return connPool.get(connType);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public synchronized static ConnectionPoolFactory getInstance(){
		if (connectionFactory == null) 
			 connectionFactory = new ConnectionPoolFactory();;
			return connectionFactory;
	}

}
