package org.scorpion.persistence.util;

import java.sql.SQLException;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.persistence.ITscpPersistenceService;
import org.scorpion.api.util.Constant;
import org.scorpion.common.session.ApplicationSession;
import org.scorpion.persistence.session.SessionFactory;


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
public class TscpPersistenceUtils {
	
	
	/**
	 * @description persistence handle by default database
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 * 
	 */
	public static ITscpPersistenceService getPersistenceService()throws TscpBaseException{
		
		ApplicationSession session = (ApplicationSession) ApplicationSession.getSession();
		if(session == null)
			session = (ApplicationSession) ApplicationSession.createSession();
		try {
			if(session.getDefaultPersistenceSession() == null)
				session.setDefaultPersistenceSession(SessionFactory.getInstance().produceInstance());
			else if(session.getDefaultPersistenceSession() != null&&session.getDefaultPersistenceSession().getConnection() == null){
				session.resetConnection(null,session.getDefaultPersistenceSession(),Constant.DEFAULT_DATASOURCE);
			}
		} catch (SQLException e) {
		    throw new TscpBaseException("TSCP-9034:Create persistence session exception !",e);
		}
		
		session.getDefaultPersistenceSession().setDefaultDataSource(true);
		session.setCurrentPersistence(session.getDefaultPersistenceSession());
		return session.getDefaultPersistenceSession().getPersistenceServcie();
	}
	
	/**
	 * @description 通过数据源名称，获取持久层操作入口
	 * 
	 * @param dataSourceName
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 * 
	 */
	public static ITscpPersistenceService getPersistenceService(String dataSourceName)throws TscpBaseException{
		
		ApplicationSession session = (ApplicationSession) ApplicationSession.getSession();
		
		try{
		
			if(session.getOtherPersistenceSessionByName(dataSourceName)==null)
			
				session.setOtherPersistenceSession(dataSourceName, SessionFactory.getInstance().produceInstance(dataSourceName));
	
			else if(session.getOtherPersistenceSessionByName(dataSourceName) != null&&session.getOtherPersistenceSessionByName(dataSourceName).getConnection() == null){
			
				session.resetConnection(dataSourceName,session.getOtherPersistenceSessionByName(dataSourceName),Constant.OTHER_DATASOURCE);	
		
			}
		
		}catch(SQLException e){
			throw new TscpBaseException("TSCP-9033:Create persistence session exception !",e);
		}
		
		session.setCurrentPersistence(session.getOtherPersistenceSessionByName(dataSourceName));
		session.getCurrentPersistence().setDefaultDataSource(false);
		session.getCurrentPersistence().setDataSourceName(dataSourceName);
		return session.getOtherPersistenceSessionByName(dataSourceName).getPersistenceServcie();
	}


}
