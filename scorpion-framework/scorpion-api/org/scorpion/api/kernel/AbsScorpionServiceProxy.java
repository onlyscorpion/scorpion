package org.scorpion.api.kernel;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.persistence.IScorpionPersistenceSession;
import org.scorpion.api.util.Constant;

import java.util.Stack;

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
public abstract class AbsScorpionServiceProxy {
	
	protected ApplicationContext context;
	
	/**
	 * ioc manager
	 */
	protected IScorpionSystemIocManager iocManager;
	
	/**
	 * @param actionName
	 * @param params
	 * @return
	 * @throws ScorpionBaseException
	 */
	public abstract Object invokeAction(String actionName,IReqData req)throws ScorpionBaseException;
	
	/**
	 * @param serviceName
	 * @param params
	 * @return
	 * @throws ScorpionBaseException
	 */
	public abstract Object invokeService(String serviceName,Object... params) throws ScorpionBaseException;
	
	
	
	/**
	 * @param actionName
	 * 
	 * @param params
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public Object callActionProxy(String actionName,IReqData req)throws ScorpionBaseException{
		
		return this.invokeAction(actionName, req);
		
	}
	
	
	/**
	 * @description session 处理
	 * 
	 * @param session
	 * 
	 * @throws ScorpionBaseException
	 */
	protected void sessionExceptionHandler(IScorpionGlobalSystemSession session) throws ScorpionBaseException{
		
		if(session == null)	return ;
		
		session.closeTransactionManager();
		
		IScorpionPersistenceSession defualtPersistence = session.getDefaultPersistenceSession();
		
		int level = session.getServiceCalledLevel().decrementAndGet();
		
		if(defualtPersistence != null&&defualtPersistence.getConnection() != null &&
				defualtPersistence.isOpenTransaction()&&!defualtPersistence.isCommitTransaction()&&level == 0)
			defualtPersistence.getPersistenceServcie().rollback();
		
		if(session.getOtherPersistenceSession() != null){

			for(Entry<String,IScorpionPersistenceSession>entry:session.getOtherPersistenceSession().entrySet()){
				
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
	public AbsScorpionServiceProxy setIocManager(IScorpionSystemIocManager iocManager) {
	
		this.iocManager = iocManager;
		
		return this;
	
	}
	
	
	
	/**
	 * @description session 处理
	 * 
	 * @param session
	 * 
	 * @throws ScorpionBaseException
	 */
	protected void sessionHandler(IScorpionGlobalSystemSession session) throws ScorpionBaseException{
		
		if(session == null)	return ;
		
		if(session.getServiceCalledLevel().decrementAndGet() != 0)
			return;
		
		session.closeTransactionManager();
		
		IScorpionPersistenceSession defualtPersistence = session.getDefaultPersistenceSession();
			
		if(defualtPersistence != null&&defualtPersistence.getConnection() != null &&defualtPersistence.isOpenTransaction()&&!defualtPersistence.isCommitTransaction()){
			defualtPersistence.getPersistenceServcie().commit();
			defualtPersistence.setConnection(null);
		}
			
		if(session.getOtherPersistenceSession() == null)
			return;
			
		for(Entry<String,IScorpionPersistenceSession>entry:session.getOtherPersistenceSession().entrySet()){
			
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
	public Object callServiceProxy(IScorpionGlobalSystemSession session,String serviceName,Object... params)throws Throwable{
	
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
	 * @throws ScorpionBaseException
	 */
	@SuppressWarnings("unchecked")
	protected void constructTmpData(String serviceName,IScorpionGlobalSystemSession session,Object... param) throws ScorpionBaseException{
		
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
	 * @throws ScorpionBaseException
	 */
	protected void beforeServiceCall(IScorpionGlobalSystemSession session) throws ScorpionBaseException{
		
		if(session.getServiceCalledLevel().incrementAndGet() == 1)
			
			session.openTransactionManager();
		
		if(session.getServiceCalledLevel().get() > Constant.MAX_CALL_LEVEL)
			throw new StackOverflowError("scorpion-6098:Call level over max deep !");
	}
	
	
}
