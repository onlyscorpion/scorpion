package org.scorpion.api.persistence;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map.Entry;

import org.scorpion.api.configuration.DBParam;
import org.scorpion.api.configuration.PojoEntityInfo.PoProperty;
import org.scorpion.api.configuration.SQLConfig.SQLProperty;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpBasePo;
import org.scorpion.api.kernel.ApplicationContext;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsTscpPersistenceDao implements ITscpTransactionManager{
	
	protected static ITscpSQLManager sqlManager;
	
	protected ITscpConnection connection;
	
	protected ApplicationContext context;
	
	private boolean isStartTransactionManager ;
	
	
	
	/**
	 * @description 获取prepare statement
	 * 
	 * @param sql
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	protected PreparedStatement getPreparedStatement(String sql) throws SQLException, TscpBaseException{
		
		if(connection == null||connection == null)
			throw new TscpBaseException("TSCP-9087:Database connection don't initialize !");
		
		try {
			return connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	
	
	/**
	 * @param sql
	 * 
	 * @throws TscpBaseException
	 */
	protected CallableStatement getProcedureStatement(String sql,DBParam param,DBParam.ProcedureType type)throws TscpBaseException{
		
		try {
			CallableStatement statement = connection.prepareCall(sql);
			this.paramConvertStatement(statement, param);
		
			if(DBParam.ProcedureType.IN.name().equals(type.name()))
				return statement;
			
			if(param.getProcedureOutParamType() == null||param.getProcedureOutParamType().isEmpty())
				throw new TscpBaseException("TSCP-9076:Procedure don't set out parameter type !");
			
			for(Entry<Integer,Integer>entry:param.getProcedureOutParamType().entrySet())
				statement.registerOutParameter(entry.getKey(), entry.getValue());
			
			return statement;
		} catch (SQLException e) {
			throw new TscpBaseException("TSCP-6549:Procedure get statement exception ! ",e);
		}
	}
	
	
	/**
	 * @param name
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	protected PoProperty getPojoProperty(String name)throws TscpBaseException{
		
		PoProperty pojo = context.getSystemPojoInformation().getPojoByName(name);
        
		if(pojo == null)
			throw new TscpBaseException("TSCP-9758:System don't scan PO["+name+"] !");

		if(!pojo.isValid())
        	throw new TscpBaseException("TSCP-9087:PO["+name+"]is invalid !");
        	
        return pojo;
	}
	
	
	/**
	 * @param po
	 * 
	 * @param poProperty
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	protected DBParam getSQLParamByPo(AbsTscpBasePo po,PoProperty poProperty)throws TscpBaseException{
		
		if(po == null)
			return null;
	
		DBParam params = new DBParam();
	
		try{
			for(String field:poProperty.getFieldSequence())
				params.addParam(checkPoEnvironment(poProperty,poProperty.getGetAttributeByName(field).invoke(po),field));
		}catch(Exception e){
			throw new TscpBaseException("TSCP-8065:Setting argument of pojo information failure !",e);
		}
		
		return params;
	}
	
	
	/**
	 * @param poProperty
	 * @param param
	 * @param field
	 * @return
	 * @throws TscpBaseException
	 */
	private Object checkPoEnvironment(PoProperty poProperty,Object param,String field) throws TscpBaseException{
		if(poProperty.getPrimaryKey().contains(param)&&param == null)
			throw new TscpBaseException("TSCP-4498：The table ["+poProperty.getTableName()+"] column ["+field+"] is primary key , don't allow to be null !");
		if(poProperty.getNullable().contains(param)&&param == null)
			throw new TscpBaseException("TSCP-4498：The table ["+poProperty.getTableName()+"] column ["+field+"] is primary key , don't allow to be null !");
		if(param == null&&poProperty.getDataDefault().get(field)!=null){
			param = poProperty.getDataDefault().get(field);
		}
		return param;
	}
	
	/**
	 * @param dbparams
	 * @throws TscpBaseException
	 */
	public void batchDynamicParamCheck(Collection<DBParam> params) throws TscpBaseException{
	
		DBParam[] dbparams = new DBParam[params.size()];
        params.toArray(dbparams);
	
        for(int i=0;i<dbparams.length;i++){
			for(int j=0;j<dbparams[i].getDynamicParams().size()-1;j++){
			
				if(dbparams[i].getDynamicParams() == null&&dbparams[i+1].getDynamicParams()==null)
					continue;
	        	
				else if((dbparams[i].getDynamicParams() != null&&dbparams[i+1].getDynamicParams()==null)||(dbparams[i].getDynamicParams() != null&&dbparams[i+1].getDynamicParams()==null))
					throw new TscpBaseException("TSCP-7643:批量执行时，执行的多条SQL的动态参数[#dynamicParam#]必须相同");
	        	
				if(!dbparams[i].getDynamicParams().get(j).equals(dbparams[i+1].getDynamicParams().get(j)))
					throw new TscpBaseException("TSCP-7643:批量执行时，执行的多条SQL的动态参数[#dynamicParam#]必须相同");
			}
		}
	
	}
	
	
	/**
	 * 获取statement
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	protected Statement getStatement()throws TscpBaseException{
		
		if(connection == null||connection == null)
			throw new TscpBaseException("TSCP-9087:Database connection don't initialize !");
	
		try {
			return this.connection.createStatement();
		} catch (SQLException e) {
			throw new TscpBaseException("TSCP-9876:Get statement failure ！",e);
		}
		
	}
	
	
	protected String getUpdateSQLByPo(AbsTscpBasePo po,PoProperty poproperty)throws TscpBaseException{
		return null;
	}
	
	
	/**
	 * 
	 * @param sql
	 * 
	 * @param param
	 * 
	 * @param handler
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 * 
	 * @throws ClassNotFoundException
	 */

	protected <T> T queryDataSuperDao(String sql,DBParam param,ITscpResultHandler<T> handler) throws Throwable{
		long begin = System.currentTimeMillis();
		printDetailSQLInfo(sql, param);
		PreparedStatement statement = this.getPreparedStatement(sql);
		this.paramConvertStatement(statement, param);
		ResultSet result = null;
	
		try {
		    result = statement.executeQuery();
			T map = handler.resultHandler(result);
			//PlatformLogger.info("成功执行["+sql+"],共耗时为["+(System.currentTimeMillis() - begin)+"]毫秒,查询记录数["+result.getRow()+"]");
			PlatformLogger.info("成功执行["+sql+"],共耗时为["+(System.currentTimeMillis() - begin)+"]毫秒");
			return map;
		} catch (Throwable ex) {
			throw ex;
		}finally{
			//this.resultSetClose(result);
			connectionCheck();
		}
		
	}
	
	/**
	 * @param key
	 * @param sqlproperty
	 * @return
	 * @throws TscpBaseException
	 */
	public boolean checkSQLSecurity(String key,SQLProperty sqlproperty) throws TscpBaseException{
		
		if(sqlproperty == null||sqlproperty.getKey() == null)
			throw new TscpBaseException("TSCP-9865:SQL KEY ["+key+"] NO FOUND");
		
		return true;
	}
	
	
	/**
	 * @param sql
	 * @param params
	 * @throws TscpBaseException
	 */
	public void printDetailSQLInfo(String sql,DBParam params) throws TscpBaseException{
		
		if(!Constant.DEVELOP_MODEL.equals(context.getSystemCoreConfig().getRunModel())){
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("执行:").append(sql).append("参数:");
		
		if(params == null){
			sb.append("[null]");
			PlatformLogger.debug(sb.toString());
			return;
		}
		
		PlatformLogger.debug(sb.append(params.toString()).toString());
	}
	
	
	
	/**
	 * @param result
	 * 
	 * @throws TscpBaseException 
	 */
	protected void resultSetClose(ResultSet result) throws SQLException{
	
		if(result == null)
			return;
	
		try {
			result.close();
		} catch (SQLException e) {
			throw e;
		}
	}
	
	
	/**
	 * convert argument information
	 * 
	 * @param statement
	 * 
	 * @param params
	 * 
	 * @throws SQLException
	 */
	protected void paramConvertStatement(PreparedStatement statement,DBParam params) throws SQLException{
		
		if (statement == null || params == null|| params.isEmpty()) 
			return;
		
			for (int i = 0; i < params.getParams().size(); i++) {
				Object param = params.getParams().get(i);
				if (param == null)
			    	statement.setObject(i+1, null);
				if (param instanceof Character)
					statement.setString(i + 1, param.toString());
				else if (param instanceof String)
					statement.setString(i + 1, (String) param);
				else if (param instanceof Integer)
					statement.setInt(i + 1, (Integer) param);
				else if (param instanceof Double)
					statement.setDouble(i + 1, (Double) param);
				else if (param instanceof Long)
					statement.setLong(i + 1, (Long) param);
				else if (param instanceof Timestamp)
					statement.setTimestamp(i + 1, (Timestamp) param);
				else if (param instanceof java.sql.Date)
					statement.setDate(i + 1, (java.sql.Date) param);
				else if (param instanceof java.util.Date)
					statement.setTimestamp(i + 1, new Timestamp(((java.util.Date) param).getTime()));
				else if (param instanceof BigDecimal)
					statement.setBigDecimal(i + 1, (BigDecimal) param);
				else if (param instanceof Byte)
					statement.setByte(i + 1, (Byte) param);
				else if (param instanceof Short)
					statement.setShort(i + 1, (Short) param);
				else if (param instanceof Float)
					statement.setFloat(i + 1, (Float) param);
				else if (param instanceof Boolean)
					statement.setString(i + 1, (Boolean) param ? "Y" : "N");
				else
					statement.setObject(i + 1, param);
			}
	}
	
	

	/**
	 * @param inType
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	protected Object dbDataTypeConvert(Object inType) throws TscpBaseException{
		
		if (inType == null) 
			return null;
		
		    if (inType instanceof Timestamp)
				return (Date)inType;
			
			else if (inType instanceof java.sql.Date)
				return (Date)inType;
			
			else if (inType instanceof BigDecimal)
				return ((BigDecimal)inType).intValue();
			
			return inType;
	}
	

	public ITscpConnection getConnection() {
		return connection;
	}

	public ITscpPersistenceService setConnection(ITscpConnection connection) {
		this.connection = connection;
		return this;
	}

	@Override
	public void commit() throws TscpBaseException {
		try {
			this.connection.commit();
			this.connection.close();
			this.connection = null;
		} catch (SQLException e) {
			throw new TscpBaseException("TSCP-9065:Commit database transaction failure !",e);
		}
	}

	public static ITscpSQLManager getSqlManager() {
		return sqlManager;
	}

	public void setSqlManager(ITscpSQLManager sqlManager) {
		AbsTscpPersistenceDao.sqlManager = sqlManager;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}
	

	/**
	 * @description 检查连接
	 * 
	 * @throws TscpBaseException 
	 * 
	 * @throws SQLException
	 */
	protected final void connectionCheck() throws SQLException{
		
		if(this.isStartTransactionManager()&&this.connection.isOpenTransaction())
			return;
		
		this.connection.close();
		this.connection = null;
	}


	@Override
	public boolean isStartTransactionManager() {
		return isStartTransactionManager;
	}


	@Override
	public void startTransactionManager() {
		
		this.isStartTransactionManager = true;
	}



	@Override
	public void close() throws TscpBaseException {
		try {
			this.connection.close();
		} catch (SQLException e) {
			throw new TscpBaseException("TSCP-4582:Close connection failure !",e);
		}
	}
	
}
