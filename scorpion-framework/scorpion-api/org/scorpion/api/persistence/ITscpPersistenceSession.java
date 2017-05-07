package org.scorpion.api.persistence;

import java.io.Serializable;

import org.scorpion.api.exception.TscpBaseException;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this factory  class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface ITscpPersistenceSession extends Serializable{
	
	
	
	/**
	 * @description 查看事务是否开启
	 * 
	 * @throws TscpBaseException
	 */
	public boolean isOpenTransaction()throws TscpBaseException;
	
	/**
	 * @description 事务是否提交
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 * 
	 */
	public boolean isCommitTransaction()throws TscpBaseException;
	
	
	/**
	 * @description 获取持久层操作
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 * 
	 */
	public ITscpPersistenceService getPersistenceServcie()throws TscpBaseException;
	
	/**
	 * @description 提交事务
	 * 
	 * @throws TscpBaseException
	 */
	public void commit()throws TscpBaseException;
	
	
	/**
	 * 获取连接
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public ITscpConnection getConnection()throws TscpBaseException;
	
	/**
	 * @param connection
	 * @throws TscpBaseException
	 */
	public void setConnection(ITscpConnection connection)throws TscpBaseException;
	
	
	/**
	 * 
	 * @return
	 */
	public String getDataSourceName();


	/**
	 * 
	 * @param dataSourceName
	 */
	public void setDataSourceName(String dataSourceName);


	/**
	 * 
	 * @return
	 */
	public boolean isDefaultDataSource() ;


	/**
	 * 
	 * @param isDefaultDataSource
	 */
	public void setDefaultDataSource(boolean isDefaultDataSource) ;
	
	
	
}
