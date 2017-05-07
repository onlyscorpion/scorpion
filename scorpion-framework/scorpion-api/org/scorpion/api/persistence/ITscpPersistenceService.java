package org.scorpion.api.persistence;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.scorpion.api.configuration.DBParam;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpBasePo;

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
public interface ITscpPersistenceService {
	

	/**
	 * @param key
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public Map<String,Object> queryMapByKey(String key,DBParam param)throws TscpBaseException;
	
	
	/**
	 * @param key
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public List<Map<String,Object>>queryLisMapByKey(String key,DBParam param)throws TscpBaseException;
	
	
	
	/**
	 * 
	 * @param key
	 * 
	 * @param param
	 * 
	 * @param clazz
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public <T extends AbsTscpBasePo> T queryPoBykey(String key,DBParam param,Class<T> clazz) throws TscpBaseException;

	
	/**
	 * @param key
	 * 
	 * @param param
	 * 
	 * @param clazz
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
    public <T extends AbsTscpBasePo> List<T>queryLisPoByKey(String key,DBParam param,Class<T> clazz) throws TscpBaseException;
    
    
    /**
     * @param sql
     * 
     * @param param
     * 
     * @return
     * 
     * @throws TscpBaseException
     */
    public <T extends AbsTscpBasePo> T queryPoBySQL(String sql,DBParam param,Class<T> clazz)throws TscpBaseException;

    
    
    /**
     * @param sql
     * 
     * @param param
     * 
     * @param handler
     * 
     * @return
     * 
     * @throws TscpBaseException
     */
    public Map<String,Object> queryMapBySQL(String sql,DBParam param)throws TscpBaseException;
    
    
    
	/**
	 * @param sql
	 * 
	 * @param param
	 * 
	 * @param handler
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
    public List<Map<String,Object>> queryLisMapBySQL(String sql,DBParam param)throws TscpBaseException;
    
    
    
    /**
     * @param sql
     * 
     * @param param
     * 
     * @param handler
     * 
     * @return
     * 
     * @throws TscpBaseException
     */
    public <T> T queryDataBySQL(String sql,DBParam param,ITscpResultHandler<T> handler)throws TscpBaseException;

    
    
    /**
     * @param sqlKey
     * 
     * @param param
     * 
     * @param indexcolumn
     * 
     * @return
     * 
     * @throws TscpBaseException
     */
    public Object queryColumnByKey(String sqlKey,DBParam param,int indexcolumn)throws TscpBaseException;
    
    
    
    /**
     * @param sqlKey
     * 
     * @param param
     * 
     * @param indexcolumn
     * 
     * @return
     * 
     * @throws TscpBaseException
     */
    public List<Object> queryColumnListByKey(String sqlKey,DBParam param,int indexcolumn)throws TscpBaseException;
    
    
    
    /**
     * @param name
     * 
     * @param sql
     * 
     * @param tables
     * 
     * @param parameter
     * 
     * @return
     * 
     * @throws TscpBaseException
     */
	int updateDataBySQL(String sql, String[] tables, DBParam parameter) throws TscpBaseException;

	
	
	/**
	 * @param sqlKey
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	int updateDataByKey(String sqlKey, DBParam param) throws TscpBaseException;

	
	/**
	 * @param po
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	boolean insertPo(AbsTscpBasePo po) throws TscpBaseException;

	
	/**
	 * @param pos
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	int[] insertPos(Collection<? extends AbsTscpBasePo> pos) throws TscpBaseException;

	
	
	/**
	 * @param po
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	int updatePO(AbsTscpBasePo po) throws TscpBaseException;

	
	/**
	 * @param pos
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	int updatePOs(Collection<? extends AbsTscpBasePo> pos) throws TscpBaseException;

	
	
	/**
	 * @param po
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	boolean deletePO(AbsTscpBasePo po) throws TscpBaseException;

	
	/**
	 * @param pos
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	int[] deletePOs(Collection<? extends AbsTscpBasePo> pos) throws TscpBaseException;
	
	
	
	/**
	 * @param sqlKey
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	boolean deleteDataByKey(String sqlKey, DBParam param)throws TscpBaseException;

	
	
	/**
	 * @param sqlKey
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	boolean executeSQLByKey(String sqlKey, DBParam param) throws TscpBaseException;

	
	/**
	 * @param sqlKey
	 * 
	 * @param params
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	int[] executeBatchByKey(String sqlKey, Collection<DBParam> params) throws TscpBaseException;

	
	/**
	 * @param procedureKey
	 * 
	 * @param parameter
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
    List<Map<String,Object>> executeProcedureByKey(String procedureKey, DBParam param) throws TscpBaseException;

    
	/**
	 * @param procedure
	 * 
	 * @param parameter
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	void executeProcedure(String procedure, DBParam param) throws TscpBaseException;
	
	
	/**
	 * @param functionKey
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	List<Map<String,Object>> executeFunctionByKey(String functionKey,DBParam param)throws TscpBaseException;
	

	/**
	 * @param function
	 * 
	 * @param param
	 * 
	 * @throws TscpBaseException
	 */
	void executeFunction(String function,DBParam param)throws TscpBaseException;

	
	/**
	 * @description commit database transaction 
	 * 
	 * @Description 相关说明
	 * 
	 * @Time 创建时间:2015-5-8下午07:57:37
	 * 
	 * @throws SQLException
	 * 
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public void commit() throws TscpBaseException;


	
	/**
	 * @description roll back database transaction
	 * 
	 * @Description 相关说明
	 * 
	 * @Time 创建时间:2012-4-18下午3:57:41
	 * 
	 * @throws TscpBaseException
	 * 
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public void rollback() throws TscpBaseException;
	
	
	/**
	 * 
	 * @param sqlKey
	 * 
	 * @param param
	 * 
	 * @param startsize
	 * 
	 * @param endsize
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	List<Map<String,Object>> queryListMapByPaging(String sqlKey,DBParam param,int startsize,int endsize)throws TscpBaseException;
	

	
	/**
	 * 
	 * @param sqlKey
	 * 
	 * @param param
	 * 
	 * @param clazz
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public <T extends AbsTscpBasePo> List<T> queryListPoByPaging(String sqlKey,DBParam param,int startnum,int endnum, Class<T> clazz)throws TscpBaseException;

	
	
	/**
	 * 
	 * @param key
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	boolean insertDataByKey(String key,DBParam param)throws TscpBaseException;
	
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public void close() throws TscpBaseException;
	

	
 
}
