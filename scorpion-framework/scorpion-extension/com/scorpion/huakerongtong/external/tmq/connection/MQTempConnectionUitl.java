package com.scorpion.huakerongtong.external.tmq.connection;

import java.util.ArrayList;
import java.util.List;
import org.apache.activemq.ActiveMQConnection;


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
public class MQTempConnectionUitl {
	
	static boolean isCacheConn;
	
	
	/**
	 * @param datasource
	 * @return
	 * @throws ConnectionPoolException
	 */
	public static boolean initialize(MQDataSource datasource,boolean isCacheConn) throws ConnectionPoolException{
		MQTempConnectionUitl.isCacheConn = isCacheConn;
		List<MQDataSource> datasources = new ArrayList<MQDataSource>();
		datasources.add(datasource);
		ConnectionPoolFactory.getInstance().initConnectionFactory(ConnectionFactoryType.AMQConnection.getValue(), datasources);
		return true;
	}
	
	
	/**
	 * @param datasources
	 * @return
	 * @throws ConnectionPoolException
	 */
	public static boolean initialize(List<MQDataSource> datasources,boolean isCacheConn) throws ConnectionPoolException{
	
		MQTempConnectionUitl.isCacheConn = isCacheConn;
		ConnectionPoolFactory.getInstance().initConnectionFactory(ConnectionFactoryType.AMQConnection.getValue(), datasources);
		return true;
	}
	
	
	/**
	 * @param datasources
	 * @return
	 * @throws ConnectionPoolException
	 */
	public static synchronized boolean refreshCache(List<MQDataSource> datasources) throws ConnectionPoolException{
		if(ConnectionPoolFactory.getInstance().getConnection(ConnectionFactoryType.AMQConnection.getValue()) != null)
			ConnectionPoolFactory.getInstance().getConnection(ConnectionFactoryType.AMQConnection.getValue()).distoryPool();;
		
		ConnectionPoolFactory.getInstance().rebuildConnectionFactory(ConnectionFactoryType.AMQConnection.getValue(), datasources);
		
		return true;
	}

	
	/**
	 * @param connName
	 * @return
	 * @throws ConnectionPoolException
	 */
	public static ActiveMQConnection getTempConnection(String connName) throws ConnectionPoolException{
	
		return (ActiveMQConnection) ConnectionPoolFactory.getInstance().getConnection(ConnectionFactoryType.AMQConnection.getValue()).getConnection(connName);
	
	}
	
	

}
