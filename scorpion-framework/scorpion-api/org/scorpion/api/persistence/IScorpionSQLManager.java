package org.scorpion.api.persistence;

import org.scorpion.api.configuration.DBParam;
import org.scorpion.api.configuration.SQLConfig.SQLProperty;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionBasePo;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface IScorpionSQLManager {
	
	/**
	 * @description get sql information from common cache
	 * 
	 * @param key
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public SQLProperty getSQLByKey(String key,DBParam param)throws ScorpionBaseException;
	
	/**
	 * @param po
	 * @return
	 * @throws ScorpionBaseException
	 */
	public SQLProperty getSQLByPoInsert(AbsScorpionBasePo po)throws ScorpionBaseException;
	
	/**
	 * @param po
	 * @return
	 * @throws ScorpionBaseException
	 */
	public SQLProperty getSQLByPoDel(AbsScorpionBasePo po,Integer dbType)throws ScorpionBaseException;
	
	/**
	 * @param po
	 * @return
	 * @throws ScorpionBaseException
	 */
	public SQLProperty getSQLByPoUpdate(AbsScorpionBasePo po,Integer dbType)throws ScorpionBaseException;
	
	/**
	 * @param po
	 * @param dbType
	 * @return
	 * @throws ScorpionBaseException
	 */
	public SQLProperty getSQLByPoInsert(AbsScorpionBasePo po,Integer dbType)throws ScorpionBaseException;
	
	
	
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
	 * @throws ScorpionBaseException
	 */
	public SQLProperty getSQLByKey(String key,DBParam param,int startnum,int endnum,int dbType) throws ScorpionBaseException;
	
	

}
