package org.scorpion.api.persistence;

import java.sql.Connection;

import org.scorpion.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: if developer want to create a component. the developer must extends the abstract </p>
 * <p>class AScorpionComponet. the AScorpionComponent exist life cycle. developer can override</p>
 * <p>the initialization method or service method or destroy method to handle themselves business</p>
 * <p>but we don't suggest the developer do that </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface IScorpionConnection extends Connection{
	
	/**
	 * @param isOpenTransaction
	 * @param isCommitTransaction
	 * @throws ScorpionBaseException
	 */
	public void updateTransactionStatus(boolean isOpenTransaction,boolean isCommitTransaction)throws ScorpionBaseException;
	
	/**
	 * @return
	 */
	public boolean isOpenTransaction();
	
	/**
	 * @return
	 * @throws ScorpionBaseException
	 */
	public boolean isCommitTransaction()throws ScorpionBaseException;
	
	/**
	 * @return
	 */
	public Integer getDBType();
	

}
