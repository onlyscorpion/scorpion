package org.scorpion.api.persistence;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.scorpion.api.configuration.DBParam;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionBasePo;

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
public interface IScorpionPersistenceService {
	

	/**
	 * @param key
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public Map<String,Object> queryMapByKey(String key,DBParam param)throws ScorpionBaseException;
	
	
	/**
	 * @param key
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public List<Map<String,Object>>queryLisMapByKey(String key,DBParam param)throws ScorpionBaseException;
	
	
	
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
	 * @throws ScorpionBaseException
	 */
	public <T extends AbsScorpionBasePo> T queryPoBykey(String key,DBParam param,Class<T> clazz) throws ScorpionBaseException;

	
	/**
	 * @param key
	 * 
	 * @param param
	 * 
	 * @param clazz
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
    public <T extends AbsScorpionBasePo> List<T>queryLisPoByKey(String key,DBParam param,Class<T> clazz) throws ScorpionBaseException;
    
    
    /**
     * @param sql
     * 
     * @param param
     * 
     * @return
     * 
     * @throws ScorpionBaseException
     */
    public <T extends AbsScorpionBasePo> T queryPoBySQL(String sql,DBParam param,Class<T> clazz)throws ScorpionBaseException;

    
    
    /**
     * @param sql
     * 
     * @param param
     * 
     * @param handler
     * 
     * @return
     * 
     * @throws ScorpionBaseException
     */
    public Map<String,Object> queryMapBySQL(String sql,DBParam param)throws ScorpionBaseException;
    
    
    
	/**
	 * @param sql
	 * 
	 * @param param
	 * 
	 * @param handler
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
    public List<Map<String,Object>> queryLisMapBySQL(String sql,DBParam param)throws ScorpionBaseException;
    
    
    
    /**
     * @param sql
     * 
     * @param param
     * 
     * @param handler
     * 
     * @return
     * 
     * @throws ScorpionBaseException
     */
    public <T> T queryDataBySQL(String sql,DBParam param,IScorpionResultHandler<T> handler)throws ScorpionBaseException;

    
    
    /**
     * @param sqlKey
     * 
     * @param param
     * 
     * @param indexcolumn
     * 
     * @return
     * 
     * @throws ScorpionBaseException
     */
    public Object queryColumnByKey(String sqlKey,DBParam param,int indexcolumn)throws ScorpionBaseException;
    
    
    
    /**
     * @param sqlKey
     * 
     * @param param
     * 
     * @param indexcolumn
     * 
     * @return
     * 
     * @throws ScorpionBaseException
     */
    public List<Object> queryColumnListByKey(String sqlKey,DBParam param,int indexcolumn)throws ScorpionBaseException;
    
    
    
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
     * @throws ScorpionBaseException
     */
	int updateDataBySQL(String sql, String[] tables, DBParam parameter) throws ScorpionBaseException;

	
	
	/**
	 * @param sqlKey
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	int updateDataByKey(String sqlKey, DBParam param) throws ScorpionBaseException;

	
	/**
	 * @param po
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	boolean insertPo(AbsScorpionBasePo po) throws ScorpionBaseException;

	
	/**
	 * @param pos
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	int[] insertPos(Collection<? extends AbsScorpionBasePo> pos) throws ScorpionBaseException;

	
	
	/**
	 * @param po
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	int updatePO(AbsScorpionBasePo po) throws ScorpionBaseException;

	
	/**
	 * @param pos
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	int updatePOs(Collection<? extends AbsScorpionBasePo> pos) throws ScorpionBaseException;

	
	
	/**
	 * @param po
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	boolean deletePO(AbsScorpionBasePo po) throws ScorpionBaseException;

	
	/**
	 * @param pos
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	int[] deletePOs(Collection<? extends AbsScorpionBasePo> pos) throws ScorpionBaseException;
	
	
	
	/**
	 * @param sqlKey
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	boolean deleteDataByKey(String sqlKey, DBParam param)throws ScorpionBaseException;

	
	
	/**
	 * @param sqlKey
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	boolean executeSQLByKey(String sqlKey, DBParam param) throws ScorpionBaseException;

	
	/**
	 * @param sqlKey
	 * 
	 * @param params
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	int[] executeBatchByKey(String sqlKey, Collection<DBParam> params) throws ScorpionBaseException;

	
	/**
	 * @param procedureKey
	 * 
	 * @param parameter
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
    List<Map<String,Object>> executeProcedureByKey(String procedureKey, DBParam param) throws ScorpionBaseException;

    
	/**
	 * @param procedure
	 * 
	 * @param parameter
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	void executeProcedure(String procedure, DBParam param) throws ScorpionBaseException;
	
	
	/**
	 * @param functionKey
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	List<Map<String,Object>> executeFunctionByKey(String functionKey,DBParam param)throws ScorpionBaseException;
	

	/**
	 * @param function
	 * 
	 * @param param
	 * 
	 * @throws ScorpionBaseException
	 */
	void executeFunction(String function,DBParam param)throws ScorpionBaseException;

	
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
	public void commit() throws ScorpionBaseException;


	
	/**
	 * @description roll back database transaction
	 * 
	 * @Description 相关说明
	 * 
	 * @Time 创建时间:2012-4-18下午3:57:41
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public void rollback() throws ScorpionBaseException;
	
	
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
	 * @throws ScorpionBaseException
	 */
	List<Map<String,Object>> queryListMapByPaging(String sqlKey,DBParam param,int startsize,int endsize)throws ScorpionBaseException;
	

	
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
	 * @throws ScorpionBaseException
	 */
	public <T extends AbsScorpionBasePo> List<T> queryListPoByPaging(String sqlKey,DBParam param,int startnum,int endnum, Class<T> clazz)throws ScorpionBaseException;

	
	
	/**
	 * 
	 * @param key
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	boolean insertDataByKey(String key,DBParam param)throws ScorpionBaseException;
	
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public void close() throws ScorpionBaseException;
	

	
 
}
