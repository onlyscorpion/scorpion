package org.scorpion.common.session;

import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.scorpion.api.common.ITscpProtocal.ProtocolType;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ConnectionFactory;
import org.scorpion.api.kernel.ITscpAppliactionSession;
import org.scorpion.api.kernel.ITscpGlobalSystemSession;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.persistence.ITscpPersistenceSession;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.TscpSequenceUtil;
import org.scorpion.common.context.SystemContext;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ApplicationSession implements ITscpGlobalSystemSession{

	private static final long serialVersionUID = 1L;
	
    /**
     * SESSION ID
     */
	private String sessionId;
	
	/**
	 * THE START TIME OF SESSION
	 */
	private Date startTime;
	
    /**
     * CALL LEVEL
     */
	private AtomicInteger serviceCalledLevel = new AtomicInteger(0);
	
	/**
	 * PARENT SESSION ID
	 */
	private String parentSessionId;
	
	/**
	 * SESSION INFORMATION
	 */
	public HttpSession httpSession;
	
	/**
	 * DATASOURCE INFORMATION
	 */
	private DataSource dataSource;
	
	/**
	 * DEFAULT PERSISTENT SESSION
	 */
	private ITscpPersistenceSession defaultPersistenceSession;
	
	
	private ConcurrentMap<String,Object> tempAttribute;
	
	/**
	 * CURRENT SERVER STACK...
	 * 
	 */
	private Stack<String> servers;
	
	/**
	 * SESSION INFORMATION BINGDING CURRENT THREAD
	 */
	private static ThreadLocal<ITscpAppliactionSession> sessionvariable = new ThreadLocal<ITscpAppliactionSession>();
	
	/**
	 * MULTI-DATASOURCE HANDLE
	 */
	private Map<String,ITscpPersistenceSession> otherPersistenceSession;
	
	/**
	 * CURRENT PERSISTENT
	 */
	private ITscpPersistenceSession currentPersistence;
	
	/**
	 * 
	 */
	private String webReqId;
	
	/**
	 * 
	 */
	private Map<String,Object> tempData;
	
	
	public boolean isOpenTransactionManager = false;
	
	
	

	@Override
	public String getSessionId() {
		return sessionId;
	}

	
	/**
	 * @Description Get default persistent session configuration ...
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public ITscpPersistenceSession getDefaultPersistenceSession()throws TscpBaseException {
	
		return defaultPersistenceSession;
	
	}
	
	
	
	
	/**
	 * @Dscription Get attribute of the user setting
	 */
	@Override
	public Object getAttributes(String name) throws TscpBaseException {
		
		if(httpSession != null){
			return httpSession.getAttribute(name);
		}else{
			if(tempAttribute != null)
				return tempAttribute.get(name);
			else
				return null;
		}
	}
    
	
	/**
	 * 获取开始时间
	 * 
	 * @return
	 */
	public Date getStartTime() {
		return startTime;
	}

	
	/**
	 * @description  Call level
	 * 
	 * @return
	 */
	public AtomicInteger getServiceCalledLevel() {
		
		return serviceCalledLevel;
	
	}
	

	public ITscpPersistenceSession getCurrentPersistence() {
		
		return currentPersistence;

	}


	public void setCurrentPersistence(ITscpPersistenceSession currentPersistence) {
	
		this.currentPersistence = currentPersistence;
	
	}


	/**
	 * @Description Setting default datasource information ...
	 * 
	 * @param defaultPersistenceSession
	 * 
	 * @throws SQLException 
	 * 
	 * @throws TscpBaseException 
	 * 
	 */
	public void setDefaultPersistenceSession(ITscpPersistenceSession defaultPersistenceSession) throws TscpBaseException, SQLException {
	
		this.defaultPersistenceSession = defaultPersistenceSession;
		this.defaultPersistenceSession.getConnection().setAutoCommit(!this.isOpenTransactionManager);
	
		if(this.isOpenTransactionManager)
			((org.scorpion.api.persistence.ITscpTransactionManager)this.defaultPersistenceSession.getPersistenceServcie()).startTransactionManager();
	
	}
	
	
	/**
	 * @description Reset system connection
	 * 
	 * @param defaultPersistenceSession
	 * 
	 * @throws SQLException
	 */
	public void resetConnection(String dataSourceName,ITscpPersistenceSession persistenceSession,int sessionType) throws TscpBaseException, SQLException{
	
		if(Constant.DEFAULT_DATASOURCE == sessionType)
			persistenceSession.setConnection(ConnectionFactory.getDefaultConn());

		else if(Constant.OTHER_DATASOURCE == sessionType){
			persistenceSession.setConnection(ConnectionFactory.getConnByDataSourceName(dataSourceName));

		}else{
			throw new TscpBaseException("TSCP-8649:Unknow datasource Type !");
		}
		
		persistenceSession.getConnection().setAutoCommit(!this.isOpenTransactionManager);
		
		if(this.isOpenTransactionManager)
			((org.scorpion.api.persistence.ITscpTransactionManager)persistenceSession.getPersistenceServcie()).startTransactionManager();
	
	}

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public DataSource getDataSource() {
		return dataSource;
	}
    
	/**
	 * @Description Setting datasource...
	 * 
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
	
		this.dataSource = dataSource;
	
	}
	
	
	/**
	 * @Description Get application multi-datasource ...
	 * 
	 * @return
	 * 
	 */
	public ITscpPersistenceSession getOtherPersistenceSessionByName(String dataSourceName) {
		
		if(otherPersistenceSession == null)
			otherPersistenceSession = new HashMap<String, ITscpPersistenceSession>();
		
		return otherPersistenceSession.get(dataSourceName);
	}
	
	/**
	 * @Descriptiono Get default datasource ...
	 * 
	 * @return
	 */
	public Map<String,ITscpPersistenceSession> getOtherPersistenceSession(){
		
		return this.otherPersistenceSession;
		
	}
	

	/**
	 * @Description Setting multi-datasoruce ...
	 * 
	 * @param otherPersistenceSession
	 * @throws SQLException 
	 * @throws TscpBaseException 
	 * 
	 */
	public void setOtherPersistenceSession(String dataSourceName,ITscpPersistenceSession session) throws TscpBaseException, SQLException {
	   
		if(this.otherPersistenceSession == null)
			this.otherPersistenceSession = new HashMap<String,ITscpPersistenceSession>();
	 
		session.getConnection().setAutoCommit(this.isOpenTransactionManager);
		this.otherPersistenceSession.put(dataSourceName, session);
	
	}
	

	/**
	 * @description Create Session
	 * @Time 2015-08-24
	 * @author zcl
	 */
	public static ITscpAppliactionSession  createSession(){
		
		ApplicationSession session = new ApplicationSession();
		
		session.sessionId = TscpSequenceUtil.generateSequeueString();
		
		session.startTime = new Date();
		
		sessionvariable.set(session);
		
		return session;
	}
	
	
	/**
	 * @description Create Session
	 * @Time 2015-08-24
	 * @author zcl
	 */
	public static ITscpAppliactionSession  createSession(HttpSession httpSession){
		
		ApplicationSession session = new ApplicationSession();
		
		session.sessionId = TscpSequenceUtil.generateSequeueString();
		
		session.httpSession = httpSession;
		
		session.startTime = new Date();
		
		sessionvariable.set(session);
		
		return session;
	}
	
	
	/**
	 * @Description Setting session information 
	 * 
	 * @param session
	 */
	public static ITscpAppliactionSession createOrInsteadOfSession(ITscpGlobalSystemSession session){
		
		if(sessionvariable.get() != null)
			
			return sessionvariable.get();
		
		sessionvariable.set(session);
		
		return session;
		
	}
	
	
/*	*//**
	 * @param session
	 * @return
	 *//*
	public static ITscpAppliactionSession SetSession(ITscpAppliactionSession session){
	
		sessionvariable.set(session);
		
		return session;
	}*/
	
	
	/**
	 * @Description Setting session information 
	 * 
	 * @param sessionSignal
	 * 
	 * @param req
	 * 
	 * @throws TscpBaseException
	 */
	public ITscpAppliactionSession createOrInsteadOfSession(String signal,HttpServletRequest req)throws TscpBaseException{
	
		if(sessionvariable.get() != null){
			((ApplicationSession)sessionvariable.get()).setHttpSession(req.getSession());
			return sessionvariable.get();
		}
		
		if(req.getAttribute(signal) != null&&(req.getAttribute(signal) instanceof ITscpGlobalSystemSession)){
			sessionvariable.set((ITscpGlobalSystemSession)req.getAttribute(signal));
			sessionvariable.get().setSessionId(TscpSequenceUtil.generateSequeueString());
			((ApplicationSession)sessionvariable.get()).setWebReqId(TscpSequenceUtil.generateSequeueString());
			return sessionvariable.get();
		}else if(req.getAttribute(signal) == null){
			sessionvariable.set((ITscpGlobalSystemSession)createSession(req.getSession()));
			req.getSession().setAttribute(signal, sessionvariable.get());
			return sessionvariable.get();
		}else 
			throw new TscpBaseException("TSC-6549:Request中存储的session对象类型不是为ITscpSystemSession!");
			
	}
	
	
	/**
	 * @Description Clear session ...
	 * 
	 * @throws TscpBaseException
	 */
	public ITscpAppliactionSession clear(){
		
		ApplicationSession session = (ApplicationSession) sessionvariable.get();
		if(session == null)
			session = (ApplicationSession) createSession();
		sessionvariable.set(session);
		session.tempData  = null;
		session.sessionId = TscpSequenceUtil.generateSequeueString();
		session.currentPersistence = null;
		session.dataSource = null;
		try {
			if(session.defaultPersistenceSession !=null&&session.defaultPersistenceSession.getConnection()!=null&&!session.defaultPersistenceSession.getConnection().isClosed())
				session.defaultPersistenceSession.getConnection().close();
			if(session.otherPersistenceSession != null&&session.otherPersistenceSession.size()>0){
				for(Entry<String,ITscpPersistenceSession>entry:session.otherPersistenceSession.entrySet()){
					if(entry.getValue() !=null&&entry.getValue().getConnection()!=null&&!entry.getValue().getConnection().isClosed())
						entry.getValue().getConnection().close();
				}
			}
		} catch (Throwable e) {
			PlatformLogger.error("TSCP-9756：Close connection failure !",e);
		}
		session.defaultPersistenceSession = null;
		session.otherPersistenceSession = null;
		session.parentSessionId = null;
		session.servers = null;
		session.serviceCalledLevel = new AtomicInteger(0);
		session.startTime = null;
		return sessionvariable.get();
	}
	
	
	
	public void dropSession(){
		
		ApplicationSession session = (ApplicationSession) sessionvariable.get();
		if(session == null)
			session = (ApplicationSession) createSession();
		sessionvariable.set(session);
		session.tempData  = null;
		session.sessionId = TscpSequenceUtil.generateSequeueString();
		session.currentPersistence = null;
		session.dataSource = null;
		try {
			if(session.defaultPersistenceSession !=null&&session.defaultPersistenceSession.getConnection()!=null&&!session.defaultPersistenceSession.getConnection().isClosed())
				session.defaultPersistenceSession.getConnection().close();
			if(session.otherPersistenceSession != null&&session.otherPersistenceSession.size()>0){
				for(Entry<String,ITscpPersistenceSession>entry:session.otherPersistenceSession.entrySet()){
					if(entry.getValue() !=null&&entry.getValue().getConnection()!=null&&!entry.getValue().getConnection().isClosed())
						entry.getValue().getConnection().close();
				}
			}
		} catch (Throwable e) {
			PlatformLogger.error("TSCP-9756：Close connection failure !",e);
		}
		session.defaultPersistenceSession = null;
		session.otherPersistenceSession = null;
		session.parentSessionId = null;
		session.servers = null;
		session.serviceCalledLevel = new AtomicInteger(0);
		session.startTime = null;
		if(session.httpSession != null)
			cleanSession(session.httpSession);
	}

	/**
	 * 获取SESSION信息
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 * 
	 */
	public static ITscpAppliactionSession getSession(){
		
		if(sessionvariable.get() == null)
			createSession();
		
		return sessionvariable.get();
	
	}
	
	
	@SuppressWarnings("unchecked")
	public static void cleanSession(HttpSession session){
		
		if(session == null)
			return ;
		Enumeration<String> enumeration = session.getAttributeNames();
		while(enumeration.hasMoreElements())
			session.removeAttribute(enumeration.nextElement());
		
	}
	

	public String getParentSessionId() {
	
		return parentSessionId;
	
	}

	public void setParentSessionId(String parentSessionId) {
	
		this.parentSessionId = parentSessionId;
	}
	
	
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}


	public void setSessionId(String sessionId) {

		this.sessionId = sessionId;
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}

	

	@Override
	public String getUserId() throws TscpBaseException {
		
		return (String) getSession().getAttributes(USER_ID);
	}


	@Override
	public void setUserId(String userId) throws TscpBaseException {
		
		getSession().setAttribute(USER_ID, userId);
	}


	@Override
	public void setAttribute(String key, Object value) {
		
		if(httpSession == null)
			PlatformLogger.warn("TSCP-9087:Session information don't initialize , system will launch default session !");
		
		if(httpSession != null){
			this.httpSession.setAttribute(key, value);
			return;
		}else{
			if(tempAttribute == null)
				tempAttribute = new ConcurrentHashMap<String, Object>();
			tempAttribute.put(key, value);
			return;
		}
	}


	@Override
	public void addServers(LinkedList<String> servers) throws TscpBaseException {
	
		if(this.servers == null)
			this.servers = new Stack<String>();
		
		this.servers.addAll(servers);
	}


	@Override
	public void addCurrentServer() throws TscpBaseException {
		this.servers.push(SystemContext.getApplicationContext().getServerName());
	}
	
	
	public String getWebReqId() {
		return webReqId;
	}

	public void setWebReqId(String webReqId) {
		this.webReqId = webReqId;
	}


	@Override
	public void setTmpData(String key,Object value) {
		
		synchronized (ApplicationSession.class) {
			if(this.tempData == null)
				this.tempData = new HashMap<String,Object>();
		}
		
		this.tempData.put(key, value);
	}
	
	@Override
	public void setTmpData(Map<String,Object> tempData) {
		
		synchronized (ApplicationSession.class) {
			if(this.tempData == null)
				this.tempData = new HashMap<String,Object>();
		}
		
		this.tempData.putAll(tempData);;
	}
	


	@Override
	public void setUserID(String userId) {
		
	}


	@Override
	public void setProtocolType(ProtocolType protocolType) {
		
	}


	@Override
	public ProtocolType getProtocolType() {
		return null;
	}
	

	@Override
	public Map<String, Object> getTmpData() {
		
		if(this.tempData == null)
			tempData = new HashMap<String,Object>();
		
		return this.tempData;
	}


	@Override
	public Object getTmpData(String key) {
		
		if(this.tempData == null)
			return null;
		
		return this.tempData.get(key);
	}


	@Override
	public void openTransactionManager() {
		this.isOpenTransactionManager = true;
	}


	@Override
	public void closeTransactionManager() {
		this.isOpenTransactionManager = false;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void fresh(boolean isBeginning) {
		
		if(isBeginning)
			openTransactionManager();
		else{
			closeTransactionManager();
			if(this.tempData != null&&(this.tempData.get(Constant.REQ_TMP_DATA) != null)&&((Stack<Map<String,Object>>)this.tempData.get(Constant.REQ_TMP_DATA)).size()>0&&this.serviceCalledLevel.get() > 0){
				((Stack<Map<String,Object>>)this.tempData.get(Constant.REQ_TMP_DATA)).pop();
			}if(this.tempData != null&&this.serviceCalledLevel.get() == 0)
				this.tempData.clear();
		}
	}


	@Override
	public void currentHandlerData(String key, Stack<Map<String, Object>> stack) {
		this.setTmpData(key,stack);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getReqTempData() {
		if(this.tempData.get(Constant.REQ_TMP_DATA) != null&&((Stack<Map<String,Object>>)this.tempData.get(Constant.REQ_TMP_DATA)).size()>0)
			return ((Stack<Map<String,Object>>)this.tempData.get(Constant.REQ_TMP_DATA)).peek();
		else
			return null;
		
	}


	@Override
	public Object removeAttribute(String name) {
		
		if(httpSession != null){
			Object object = this.httpSession.getAttribute(name);
			this.httpSession.removeAttribute(name);
			return object;
		}else if(tempAttribute != null){
			return tempAttribute.remove(name);
		}else{
			return null;
		}
	}


	@Override
	public ITscpAppliactionSession setSession(ITscpAppliactionSession session) {
		 sessionvariable.set(session);
		 return session;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> sesstionDataConvertMap() {
		
		if(httpSession != null){
			Enumeration<String> enumeration = this.httpSession.getAttributeNames();
			if(enumeration == null||!enumeration.hasMoreElements())
				return null;
			Map<String,Object> map = new ConcurrentHashMap<String, Object>();
			while(enumeration.hasMoreElements()){
				String key = enumeration.nextElement();
				map.put(key, this.httpSession.getAttribute(key));
			}
			return map;
		}else{
			if(this.tempAttribute != null)
				return tempAttribute;
			else 
				return null;
		}
		
	}
	
	
	/**
	 * @param arguments
	 */
	public void tempMapDataConvertSession(Map<String,Object> arguments){
		
		if(arguments == null||arguments.size() == 0)
			return;
		
		for(Entry<String,Object> entry:arguments.entrySet()){
			this.setAttribute(entry.getKey(), entry.getValue());
		}
	}
	
}
