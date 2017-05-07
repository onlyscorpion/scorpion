package org.scorpion.persistence.session;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.scorpion.api.common.AbsTscpFactory;
import org.scorpion.api.configuration.DataSourceLis;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.configuration.SystemResourcePool;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ConnectionFactory;
import org.scorpion.api.kernel.ITscpSystemIocManager;
import org.scorpion.api.kernel.TscpConnection;
import org.scorpion.api.kernel.TscpDataSource;
import org.scorpion.api.persistence.AbsTscpPersistenceDao;
import org.scorpion.api.persistence.ITscpPersistenceSession;
import org.scorpion.common.context.SystemContext;
import org.scorpion.persistence.handler.TscpPersistenceDAO;
import org.scorpion.persistence.handler.TscpSQLManager;

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
public class SessionFactory extends AbsTscpFactory<ITscpPersistenceSession>{
	
	private static AbsTscpFactory<ITscpPersistenceSession> sessionFatory= getInstance();
	/** connection cache **/
	private final Map<String,DataSource> datasourceMap ;
	
	private ITscpSystemIocManager iocManager;
	
	
	@SuppressWarnings("unchecked")
	private SessionFactory() {
	
		datasourceMap =  SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.datasource.getValue(),Map.class);
	
		iocManager = SystemContext.getApplicationContext().getIocManager();
	}
	
	@Override
	public ITscpPersistenceSession produceInstance() throws TscpBaseException {
	
		if(!datasourceMap.containsKey(DataSourceLis.DEFAULT_DATASOURCE))
			throw new TscpBaseException("TSCP-8006:Application default datasource don't initialize, plese check datasource configuration !");
	
		AbsTscpPersistenceDao persistenceService = iocManager.getBeanByClassType(TscpPersistenceDAO.class);
		persistenceService.setContext(SystemContext.getApplicationContext());
		persistenceService.setSqlManager(new TscpSQLManager());
		TscpPersistenceSession session = new TscpPersistenceSession();
		session.setPersistenceService(persistenceService);
		session.setConnection(ConnectionFactory.getDefaultConn());
		session.setCommitTrasaction(false);
		return session;
	}
	
	/**
	 * @return
	 * @throws SQLException
	 */
	public TscpConnection getDefaultConn() throws TscpBaseException{
	
		try {
			return new TscpConnection(datasourceMap.get(DataSourceLis.DEFAULT_DATASOURCE).getConnection(),((TscpDataSource)datasourceMap.get(DataSourceLis.DEFAULT_DATASOURCE).getConnection()).getDbType(),false,false);
		} catch (SQLException e) {
			throw new TscpBaseException("TSCP-8796:Create connection failure !",e);
		}
	
	}
	

	@Override
	public ITscpPersistenceSession produceInstance(Object... arg)throws TscpBaseException {

		if(arg == null||arg.length<1)
			throw new TscpBaseException("TSCP-9760:Input datasource name don't allow null !");
		
		if(!datasourceMap.containsKey(arg[0]))
			throw new TscpBaseException("TSC-8006:Application default datasource don't initialize, plese check datasource configuration !");
	
		AbsTscpPersistenceDao persistenceService = iocManager.getBeanByClassType(TscpPersistenceDAO.class);
		persistenceService.setContext(SystemContext.getApplicationContext());
		persistenceService.setSqlManager(new TscpSQLManager());
		
		TscpPersistenceSession session = new TscpPersistenceSession();
		session.setPersistenceService(persistenceService);
		session.setConnection(ConnectionFactory.getConnByDataSourceName((String)arg[0]));
		session.setCommitTrasaction(false);
		return session;
	}

	@Override
	public <P> P produceInstance(Class<P> clazz) throws TscpBaseException {
		try {
			return clazz.newInstance();
		} catch (Throwable ex) {
			throw new TscpBaseException(ex);
		}
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public static AbsTscpFactory<ITscpPersistenceSession> getInstance(){
		
		if(sessionFatory == null)
			sessionFatory = new SessionFactory();
		return sessionFatory;
		
	}

}
