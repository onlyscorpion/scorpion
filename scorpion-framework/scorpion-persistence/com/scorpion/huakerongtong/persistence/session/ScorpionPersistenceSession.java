package com.scorpion.huakerongtong.persistence.session;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.persistence.AbsScorpionPersistenceDao;
import com.scorpion.huakerongtong.api.persistence.IScorpionConnection;
import com.scorpion.huakerongtong.api.persistence.IScorpionPersistenceService;
import com.scorpion.huakerongtong.api.persistence.IScorpionPersistenceSession;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: if developer want to create a component. the developer must extends the abstract </p>
 * <p>class AScorpionComponet. the AScorpionComponent exist life cycle. developer can override</p>
 * <p>the initialization method or service method or destroy method to handle themselves business</p>
 * <p>but we don't suggest the developer do that </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionPersistenceSession implements IScorpionPersistenceSession{
	

	private static final long serialVersionUID = 8574741225778995056L;

	/** 持久层操作接口 **/
	private IScorpionPersistenceService persistenceService;
	
	private boolean isDefaultDataSource;
	
	protected String dataSourceName;
	

	@Override
	public boolean isCommitTransaction() throws ScorpionBaseException {
	
		if(persistenceService == null||((AbsScorpionPersistenceDao)persistenceService).getConnection() == null)
			return false;
	
		return ((AbsScorpionPersistenceDao)persistenceService).getConnection().isCommitTransaction();
	}
	

    /**
     * 设置事务提交状态
     * 
     * @param isCommitTrasaction
     * 
     * @throws ScorpionBaseException 
     */
	public void setCommitTrasaction(boolean isCommitTrasaction) throws ScorpionBaseException {
	
		((AbsScorpionPersistenceDao)persistenceService).getConnection().
		   updateTransactionStatus(isCommitTrasaction?false:((AbsScorpionPersistenceDao)persistenceService).getConnection().isOpenTransaction(), isCommitTrasaction);
	
	}

	@Override
	public IScorpionPersistenceService getPersistenceServcie()throws ScorpionBaseException {
	
		return persistenceService;
	
	}
	
	/**
	 * 设置持久层操作接口
	 * 
	 * @param persistenceService
	 */
	public void setPersistenceService(IScorpionPersistenceService persistenceService) {
	
		this.persistenceService = persistenceService;
	
	}

	
	@Override
	public void commit() throws ScorpionBaseException {
		
		if(persistenceService == null)
			throw new ScorpionBaseException("scorpion-9858:Persistence service don't initialize, commit transaction exception !");
		
		persistenceService.commit();
	}


	@Override
	public IScorpionConnection getConnection() throws ScorpionBaseException {
	
		if(persistenceService == null)
			throw new ScorpionBaseException("scorpion-9858:Persistence service don't initialize");
		
		return ((AbsScorpionPersistenceDao)persistenceService).getConnection();
	}


	@Override
	public void setConnection(IScorpionConnection connection)throws ScorpionBaseException {
		
		if(persistenceService == null)
			throw new ScorpionBaseException("scorpion-9858:Persistence service don't initialize");
		
		if(persistenceService != null)
			((AbsScorpionPersistenceDao)persistenceService).setConnection(connection);
	}


	@Override
	public boolean isOpenTransaction() throws ScorpionBaseException {
		
		if(persistenceService == null||((AbsScorpionPersistenceDao)persistenceService).getConnection() == null)
			return false;
	
		return ((AbsScorpionPersistenceDao)persistenceService).getConnection().isOpenTransaction();
		
	}


	public String getDataSourceName() {
		return dataSourceName;
	}


	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}


	public boolean isDefaultDataSource() {
		return isDefaultDataSource;
	}


	public void setDefaultDataSource(boolean isDefaultDataSource) {
		this.isDefaultDataSource = isDefaultDataSource;
	}
	
}
