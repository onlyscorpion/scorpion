package org.scorpion.api.persistence;

import java.util.Collection;
import java.util.List;

import org.scorpion.api.configuration.DBParam;
import org.scorpion.api.exception.ScorpionBaseException;

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
public interface IScorpionExecuteService {
	
	


	/**
	 * 执行insert、update或delete语句
	 * 
	 * @Description 相关说明
	 * @param name
	 * @param sql
	 * @param tables
	 * @param parameter
	 * @return
	 * @throws ScorpionBaseException
	 * @Time 创建时间:2013-1-18上午11:36:51
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	int executeUpdateBySql(String name, String sql, String[] tables, DBParam parameter) throws ScorpionBaseException;

	/**
	 * 执行insert、update或delete语句
	 * 
	 * @Description 相关说明
	 * @param sqlKey
	 * @param param
	 * @return
	 * @throws ScorpionBaseException
	 * @Time 创建时间:2013-1-18上午11:36:53
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	int executeUpdateByKey(String sqlKey, DBParam param) throws ScorpionBaseException;

	/**
	 * 把一个PO对象插入到数据库中
	 * 
	 * @param po
	 *            PO对象
	 * @return 插入的实际记录数
	 * @throws ScorpionBaseException
	 *             如果插入过程中发生错误则抛出异常
	 */
	int insertPO(Object po) throws ScorpionBaseException;

	/**
	 * 把一组PO对象插入到数据库中
	 * 
	 * @param pos
	 *            PO集合对象
	 * @return 插入的实际记录数
	 * @throws ScorpionBaseException
	 *             如果插入过程中发生错误则抛出异常
	 */
	int insertPOs(Collection<? extends Object> pos) throws ScorpionBaseException;

	/**
	 * 使用一个PO对象更新数据库的记录
	 * 
	 * @param po
	 *            PO对象
	 * @return 更新的实际记录数
	 * @throws ScorpionBaseException
	 *             如果更新过程中发生错误则抛出异常
	 */
	int updatePO(Object po) throws ScorpionBaseException;

	/**
	 * 使用一个PO对象更新数据库的记录，自行指定where表达式
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2011-12-8上午9:46:05
	 * @param po
	 * @param sqlWhereExpr
	 * @param sqlWhereParameters
	 * @return
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
/*	public int updatePOWithSQLExpr(Object po, String sqlWhereExpr, BaseDataElement<?>[] sqlWhereParameters)
			throws ScorpionBaseException;
*/
	/**
	 * 把一组PO对象更新到数据库中
	 * 
	 * @param pos
	 *            PO集合对象
	 * @return 更新的实际记录数
	 * @throws ScorpionBaseException
	 *             如果更新过程中发生错误则抛出异常
	 */
	int updatePOs(Collection<?> pos) throws ScorpionBaseException;

	/**
	 * 把一个PO对象从数据库中删除
	 * 
	 * @param po
	 *            PO对象
	 * @return 删除的实际记录数
	 * @throws ScorpionBaseException
	 *             如果删除中发生错误则抛出异常
	 */
	int deletePO(Object po) throws ScorpionBaseException;

	/**
	 * 把一组PO对象从数据库中删除
	 * 
	 * @param po
	 *            PO对象
	 * @return 删除的实际记录数
	 * @throws ScorpionBaseException
	 *             如果删除过程中发生错误则抛出异常
	 */
	int deletePOs(Collection<?> pos) throws ScorpionBaseException;

	/**
	 * 执行复杂的增删改SQL
	 * 
	 * @param sqlKey
	 *            处理的SQL语句key
	 * @param param
	 *            SQL参数
	 * @return 操作SQL所影响的数据库记录数
	 * @throws ScorpionBaseException
	 *             如果执行SQL过程中发生错误则抛出异常
	 */
	int executeByKey(String sqlKey, DBParam param) throws ScorpionBaseException;

	/**
	 * 
	 * 对复杂的增删改SQL执行一批操作
	 * 
	 * @param sqlKey
	 *            处理的SQL语句key
	 * @param param
	 *            SQL参数集合
	 * @return 操作SQL所影响的数据库记录数
	 * @throws ScorpionBaseException
	 *             如果执行SQL过程中发生错误则抛出异常
	 */
	int executeBatchByKey(String sqlKey, Collection<DBParam> params) throws ScorpionBaseException;

	/**
	 * 执行存储过程
	 * 
	 * @Description 支持将过程调用语句放入配置文件中
	 * @Time 创建时间:2012-4-11上午9:44:28
	 * @param procedureKey
	 * @param parameter
	 * @return
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public List<Object> executeProcedureByKey(String procedureKey, DBParam parameter) throws ScorpionBaseException;

	/**
	 * 执行存储过程
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2011-11-25下午4:23:23
	 * @param procedure
	 * @param parameter
	 * @return
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	List<Object> executeProcedure(String procedure, DBParam parameter) throws ScorpionBaseException;

	/**
	 * 执行数据库临时任务
	 * 
	 * @Description 支持将过程调用语句放入配置文件中
	 * @Time 创建时间:2012-3-13下午4:20:31
	 * @param procedure
	 * @param parameter
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public void executeTempJobByKey(String procedureKey, DBParam parameter) throws ScorpionBaseException;

	/**
	 * 执行数据库临时任务
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-3-13下午4:20:31
	 * @param procedure
	 * @param parameter
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public void executeTempJob(String procedure, DBParam parameter) throws ScorpionBaseException;

	/**
	 * 提交指定数据库的事务
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-4-18下午3:57:33
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public void commit() throws ScorpionBaseException;

	/**
	 * 提交所有数据库的事务
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-4-18下午3:57:33
	 * @throws ScorpionBaseException=
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public void commitAll() throws ScorpionBaseException;

	/**
	 * 回滚指定数据库的事务
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-4-18下午3:57:41
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public void rollback() throws ScorpionBaseException;

	/**
	 * 回滚所有数据库的事务
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-4-18下午3:57:41
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public void rollbackAll() throws ScorpionBaseException;

	/**
	 * 数据是否发生变化
	 * 
	 * @Description 相关说明
	 * @return
	 * @throws ScorpionBaseException
	 * @Time 创建时间:2013-1-15下午2:10:40
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public boolean dataChanged() throws ScorpionBaseException;


}
