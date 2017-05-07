package org.scorpion.api.persistence;

import org.scorpion.api.configuration.DBParam;
import org.scorpion.api.configuration.SQLConfig.SQLProperty;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpBasePo;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface ITscpSQLManager {
	
	/**
	 * @description get sql information from common cache
	 * 
	 * @param key
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public SQLProperty getSQLByKey(String key,DBParam param)throws TscpBaseException;
	
	/**
	 * @param po
	 * @return
	 * @throws TscpBaseException
	 */
	public SQLProperty getSQLByPoInsert(AbsTscpBasePo po)throws TscpBaseException;
	
	/**
	 * @param po
	 * @return
	 * @throws TscpBaseException
	 */
	public SQLProperty getSQLByPoDel(AbsTscpBasePo po,Integer dbType)throws TscpBaseException;
	
	/**
	 * @param po
	 * @return
	 * @throws TscpBaseException
	 */
	public SQLProperty getSQLByPoUpdate(AbsTscpBasePo po,Integer dbType)throws TscpBaseException;
	
	/**
	 * @param po
	 * @param dbType
	 * @return
	 * @throws TscpBaseException
	 */
	public SQLProperty getSQLByPoInsert(AbsTscpBasePo po,Integer dbType)throws TscpBaseException;
	
	
	
	/**
	 * 
	 * @param key
	 * 
	 * @param param
	 * 
	 * @param startnum
	 * 
	 * @param endnum
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public SQLProperty getSQLByKey(String key,DBParam param,int startnum,int endnum,int dbType) throws TscpBaseException;
	
	

}
