package com.scorpion.huakerongtong.persistence.session;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import com.scorpion.huakerongtong.api.common.AbsScorpionFactory;
import com.scorpion.huakerongtong.api.configuration.DataSourceLis;
import com.scorpion.huakerongtong.api.configuration.SystemEnumType;
import com.scorpion.huakerongtong.api.configuration.SystemResourcePool;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.ConnectionFactory;
import com.scorpion.huakerongtong.api.kernel.IScorpionSystemIocManager;
import com.scorpion.huakerongtong.api.kernel.ScorpionConnection;
import com.scorpion.huakerongtong.api.kernel.ScorpionDataSource;
import com.scorpion.huakerongtong.api.persistence.AbsScorpionPersistenceDao;
import com.scorpion.huakerongtong.api.persistence.IScorpionPersistenceSession;
import com.scorpion.huakerongtong.common.context.SystemContext;
import com.scorpion.huakerongtong.persistence.handler.ScorpionPersistenceDAO;
import com.scorpion.huakerongtong.persistence.handler.ScorpionSQLManager;

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
public class SessionFactory extends AbsScorpionFactory<IScorpionPersistenceSession>{
	
	private static AbsScorpionFactory<IScorpionPersistenceSession> sessionFatory= getInstance();
	/** connection cache **/
	private final Map<String,DataSource> datasourceMap ;
	
	private IScorpionSystemIocManager iocManager;
	
	
	@SuppressWarnings("unchecked")
	private SessionFactory() {
	
		datasourceMap =  SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.datasource.getValue(),Map.class);
	
		iocManager = SystemContext.getApplicationContext().getIocManager();
	}
	
	@Override
	public IScorpionPersistenceSession produceInstance() throws ScorpionBaseException {
	
		if(!datasourceMap.containsKey(DataSourceLis.DEFAULT_DATASOURCE))
			throw new ScorpionBaseException("scorpion-8006:Application default datasource don't initialize, plese check datasource configuration !");
	
		AbsScorpionPersistenceDao persistenceService = iocManager.getBeanByClassType(ScorpionPersistenceDAO.class);
		persistenceService.setContext(SystemContext.getApplicationContext());
		persistenceService.setSqlManager(new ScorpionSQLManager());
		ScorpionPersistenceSession session = new ScorpionPersistenceSession();
		session.setPersistenceService(persistenceService);
		session.setConnection(ConnectionFactory.getDefaultConn());
		session.setCommitTrasaction(false);
		return session;
	}
	
	/**
	 * @return
	 * @throws SQLException
	 */
	public ScorpionConnection getDefaultConn() throws ScorpionBaseException{
	
		try {
			return new ScorpionConnection(datasourceMap.get(DataSourceLis.DEFAULT_DATASOURCE).getConnection(),((ScorpionDataSource)datasourceMap.get(DataSourceLis.DEFAULT_DATASOURCE).getConnection()).getDbType(),false,false);
		} catch (SQLException e) {
			throw new ScorpionBaseException("scorpion-8796:Create connection failure !",e);
		}
	
	}
	

	@Override
	public IScorpionPersistenceSession produceInstance(Object... arg)throws ScorpionBaseException {

		if(arg == null||arg.length<1)
			throw new ScorpionBaseException("scorpion-9760:Input datasource name don't allow null !");
		
		if(!datasourceMap.containsKey(arg[0]))
			throw new ScorpionBaseException("TSC-8006:Application default datasource don't initialize, plese check datasource configuration !");
	
		AbsScorpionPersistenceDao persistenceService = iocManager.getBeanByClassType(ScorpionPersistenceDAO.class);
		persistenceService.setContext(SystemContext.getApplicationContext());
		persistenceService.setSqlManager(new ScorpionSQLManager());
		
		ScorpionPersistenceSession session = new ScorpionPersistenceSession();
		session.setPersistenceService(persistenceService);
		session.setConnection(ConnectionFactory.getConnByDataSourceName((String)arg[0]));
		session.setCommitTrasaction(false);
		return session;
	}

	@Override
	public <P> P produceInstance(Class<P> clazz) throws ScorpionBaseException {
		try {
			return clazz.newInstance();
		} catch (Throwable ex) {
			throw new ScorpionBaseException(ex);
		}
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public static AbsScorpionFactory<IScorpionPersistenceSession> getInstance(){
		
		if(sessionFatory == null)
			sessionFatory = new SessionFactory();
		return sessionFatory;
		
	}

}
