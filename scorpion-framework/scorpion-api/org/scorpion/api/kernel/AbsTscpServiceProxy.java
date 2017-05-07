package org.scorpion.api.kernel;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.persistence.ITscpPersistenceSession;
import org.scorpion.api.util.Constant;

import java.util.Stack;

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
public abstract class AbsTscpServiceProxy {
	
	protected ApplicationContext context;
	
	/**
	 * ioc manager
	 */
	protected ITscpSystemIocManager iocManager;
	
	/**
	 * @param actionName
	 * @param params
	 * @return
	 * @throws TscpBaseException
	 */
	public abstract Object invokeAction(String actionName,IReqData req)throws TscpBaseException;
	
	/**
	 * @param serviceName
	 * @param params
	 * @return
	 * @throws TscpBaseException
	 */
	public abstract Object invokeService(String serviceName,Object... params) throws TscpBaseException;
	
	
	
	/**
	 * @param actionName
	 * 
	 * @param params
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public Object callActionProxy(String actionName,IReqData req)throws TscpBaseException{
		
		return this.invokeAction(actionName, req);
		
	}
	
	
	/**
	 * @description session 处理
	 * 
	 * @param session
	 * 
	 * @throws TscpBaseException
	 */
	protected void sessionExceptionHandler(ITscpGlobalSystemSession session) throws TscpBaseException{
		
		if(session == null)	return ;
		
		session.closeTransactionManager();
		
		ITscpPersistenceSession defualtPersistence = session.getDefaultPersistenceSession();
		
		int level = session.getServiceCalledLevel().decrementAndGet();
		
		if(defualtPersistence != null&&defualtPersistence.getConnection() != null &&
				defualtPersistence.isOpenTransaction()&&!defualtPersistence.isCommitTransaction()&&level == 0)
			defualtPersistence.getPersistenceServcie().rollback();
		
		if(session.getOtherPersistenceSession() != null){

			for(Entry<String,ITscpPersistenceSession>entry:session.getOtherPersistenceSession().entrySet()){
				
				if(entry.getValue().getConnection() != null&&entry.getValue().isOpenTransaction()&&!entry.getValue().isCommitTransaction() )
					entry.getValue().getPersistenceServcie().rollback();
			}
		}
		
		if(level == 0)
			session.clear();
	}
	
	
	/**
	 * 
	 * @param iocManager
	 */
	public AbsTscpServiceProxy setIocManager(ITscpSystemIocManager iocManager) {
	
		this.iocManager = iocManager;
		
		return this;
	
	}
	
	
	
	/**
	 * @description session 处理
	 * 
	 * @param session
	 * 
	 * @throws TscpBaseException
	 */
	protected void sessionHandler(ITscpGlobalSystemSession session) throws TscpBaseException{
		
		if(session == null)	return ;
		
		if(session.getServiceCalledLevel().decrementAndGet() != 0)
			return;
		
		session.closeTransactionManager();
		
		ITscpPersistenceSession defualtPersistence = session.getDefaultPersistenceSession();
			
		if(defualtPersistence != null&&defualtPersistence.getConnection() != null &&defualtPersistence.isOpenTransaction()&&!defualtPersistence.isCommitTransaction()){
			defualtPersistence.getPersistenceServcie().commit();
			defualtPersistence.setConnection(null);
		}
			
		if(session.getOtherPersistenceSession() == null)
			return;
			
		for(Entry<String,ITscpPersistenceSession>entry:session.getOtherPersistenceSession().entrySet()){
			
			if(entry.getValue().getConnection() != null&&entry.getValue().isOpenTransaction()&&!entry.getValue().isCommitTransaction()){
				entry.getValue().getPersistenceServcie().commit();
				entry.getValue().setConnection(null);
			}else if(entry.getValue().getConnection() != null){
				entry.getValue().getPersistenceServcie().close();
				entry.getValue().setConnection(null);
			}
		}
		
		if(session.getDefaultPersistenceSession() != null)
			session.getDefaultPersistenceSession().setConnection(null);
		if(session.getOtherPersistenceSession() != null)
			session.getOtherPersistenceSession().clear();
	}
	
	
	
	/**
	 * @description call service proxy 
	 * 
	 * @param serviceName
	 * 
	 * @param params
	 * 
	 * @return
	 * 
	 * @throws Throwable 
	 */
	public Object callServiceProxy(ITscpGlobalSystemSession session,String serviceName,Object... params)throws Throwable{
	
		try{
			
			beforeServiceCall(session);
			
			constructTmpData(serviceName,session,params);
		
			Object obj = invokeService(serviceName, params);
		
			sessionHandler(session);
		
			return obj;
			
		} catch (Throwable ex) {
	    
			sessionExceptionHandler(session);
	    	
			throw ex;
			
	    }
	}
	
	
	
	/**
	 * @param serviceName
	 * 
	 * @param session
	 * 
	 * @param param
	 * 
	 * @throws TscpBaseException
	 */
	@SuppressWarnings("unchecked")
	protected void constructTmpData(String serviceName,ITscpGlobalSystemSession session,Object... param) throws TscpBaseException{
		
		Map<String,Object> tmpMap = new HashMap<String,Object>();
		
		try{
		
			tmpMap.put(Constant.SERVICE_NAME, serviceName);
		
			tmpMap.put(Constant.DESCRIPTION, this.context.getScanInfo().getService(serviceName).getDescription());
	
		}catch(Throwable ex){
		
			PlatformLogger.error(ex);
		
		}
		
		if(!session.getTmpData().containsKey(Constant.REQ_TMP_DATA)){
			
			Stack<Map<String,Object>> stack = new Stack<Map<String,Object>>();
	
			stack.push(tmpMap);
			
			session.currentHandlerData(Constant.REQ_TMP_DATA,stack);
			
		}else{
			
			((Stack<Map<String,Object>>)session.getTmpData().get(Constant.REQ_TMP_DATA)).push(tmpMap);
		}
		
	}
	
	
	/**
	 * @description service pretreatment
	 * 
	 * @param session
	 * 
	 * @throws TscpBaseException
	 */
	protected void beforeServiceCall(ITscpGlobalSystemSession session) throws TscpBaseException{
		
		if(session.getServiceCalledLevel().incrementAndGet() == 1)
			
			session.openTransactionManager();
		
		if(session.getServiceCalledLevel().get() > Constant.MAX_CALL_LEVEL)
			throw new StackOverflowError("TSCP-6098:Call level over max deep !");
	}
	
	
}
