package com.scorpion.huakerongtong.api.persistence;

import java.io.Serializable;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this factory  class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface IScorpionPersistenceSession extends Serializable{
	
	
	
	/**
	 * @description 查看事务是否开启
	 * 
	 * @throws ScorpionBaseException
	 */
	public boolean isOpenTransaction()throws ScorpionBaseException;
	
	/**
	 * @description 事务是否提交
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * 
	 */
	public boolean isCommitTransaction()throws ScorpionBaseException;
	
	
	/**
	 * @description 获取持久层操作
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * 
	 */
	public IScorpionPersistenceService getPersistenceServcie()throws ScorpionBaseException;
	
	/**
	 * @description 提交事务
	 * 
	 * @throws ScorpionBaseException
	 */
	public void commit()throws ScorpionBaseException;
	
	
	/**
	 * 获取连接
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public IScorpionConnection getConnection()throws ScorpionBaseException;
	
	/**
	 * @param connection
	 * @throws ScorpionBaseException
	 */
	public void setConnection(IScorpionConnection connection)throws ScorpionBaseException;
	
	
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
