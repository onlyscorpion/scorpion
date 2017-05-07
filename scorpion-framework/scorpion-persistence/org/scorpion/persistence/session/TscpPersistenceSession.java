package org.scorpion.persistence.session;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.persistence.AbsTscpPersistenceDao;
import org.scorpion.api.persistence.ITscpConnection;
import org.scorpion.api.persistence.ITscpPersistenceService;
import org.scorpion.api.persistence.ITscpPersistenceSession;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: if developer want to create a component. the developer must extends the abstract </p>
 * <p>class ATscpComponet. the ATscpComponent exist life cycle. developer can override</p>
 * <p>the initialization method or service method or destroy method to handle themselves business</p>
 * <p>but we don't suggest the developer do that </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpPersistenceSession implements ITscpPersistenceSession{
	

	private static final long serialVersionUID = 8574741225778995056L;

	/** 持久层操作接口 **/
	private ITscpPersistenceService persistenceService;
	
	private boolean isDefaultDataSource;
	
	protected String dataSourceName;
	

	@Override
	public boolean isCommitTransaction() throws TscpBaseException {
	
		if(persistenceService == null||((AbsTscpPersistenceDao)persistenceService).getConnection() == null)
			return false;
	
		return ((AbsTscpPersistenceDao)persistenceService).getConnection().isCommitTransaction();
	}
	

    /**
     * 设置事务提交状态
     * 
     * @param isCommitTrasaction
     * 
     * @throws TscpBaseException 
     */
	public void setCommitTrasaction(boolean isCommitTrasaction) throws TscpBaseException {
	
		((AbsTscpPersistenceDao)persistenceService).getConnection().
		   updateTransactionStatus(isCommitTrasaction?false:((AbsTscpPersistenceDao)persistenceService).getConnection().isOpenTransaction(), isCommitTrasaction);
	
	}

	@Override
	public ITscpPersistenceService getPersistenceServcie()throws TscpBaseException {
	
		return persistenceService;
	
	}
	
	/**
	 * 设置持久层操作接口
	 * 
	 * @param persistenceService
	 */
	public void setPersistenceService(ITscpPersistenceService persistenceService) {
	
		this.persistenceService = persistenceService;
	
	}

	
	@Override
	public void commit() throws TscpBaseException {
		
		if(persistenceService == null)
			throw new TscpBaseException("TSCP-9858:Persistence service don't initialize, commit transaction exception !");
		
		persistenceService.commit();
	}


	@Override
	public ITscpConnection getConnection() throws TscpBaseException {
	
		if(persistenceService == null)
			throw new TscpBaseException("TSCP-9858:Persistence service don't initialize");
		
		return ((AbsTscpPersistenceDao)persistenceService).getConnection();
	}


	@Override
	public void setConnection(ITscpConnection connection)throws TscpBaseException {
		
		if(persistenceService == null)
			throw new TscpBaseException("TSCP-9858:Persistence service don't initialize");
		
		if(persistenceService != null)
			((AbsTscpPersistenceDao)persistenceService).setConnection(connection);
	}


	@Override
	public boolean isOpenTransaction() throws TscpBaseException {
		
		if(persistenceService == null||((AbsTscpPersistenceDao)persistenceService).getConnection() == null)
			return false;
	
		return ((AbsTscpPersistenceDao)persistenceService).getConnection().isOpenTransaction();
		
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
