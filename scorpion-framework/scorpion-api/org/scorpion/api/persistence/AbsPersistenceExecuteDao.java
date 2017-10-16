package org.scorpion.api.persistence;

import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.scorpion.api.configuration.DBParam;
import org.scorpion.api.configuration.PojoEntityInfo.PoProperty;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionBasePo;
import org.scorpion.api.log.PlatformLogger;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsPersistenceExecuteDao extends AbsScorpionPersistenceDao{
	

	
	/**
	 * 
	 * @param po
	 * 
	 * @param poproperty
	 * 
	 * @param sql
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws SQLException
	 */
	protected boolean doInsertPo(AbsScorpionBasePo po,PoProperty poproperty,String sql)throws ScorpionBaseException, SQLException{
		
		
		return this.doExecuteByParams(sql, this.getSQLParamByPo(po, poproperty));
		
	}
	
	
	
	/**
	 * @description INSERT POJO INFORMATION
	 * 
	 * @param pos
	 * 
	 * @param poproperty
	 * 
	 * @param sql
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws SQLException 
	 */
	protected int[] doInsertPos(Collection<? extends AbsScorpionBasePo> pos,PoProperty poProperty,String sql)throws ScorpionBaseException, SQLException{
	
		List<DBParam> params = new ArrayList<DBParam>();
	
		for(AbsScorpionBasePo po:pos)
			params.add(this.getSQLParamByPo(po, poProperty));
		
		return this.doExecuteBatchByParams(sql, params);
	}
	
	
	/**
	 * @param sql
	 * 
	 * @param po
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws SQLException 
	 */
	protected boolean doDeletePo(String sql,AbsScorpionBasePo po)throws ScorpionBaseException, SQLException{
		
		PoProperty poProperty = context.getSystemPojoInformation().getPojoByName(po.getClass().getName());
		
		if(poProperty == null)
			throw new ScorpionBaseException("scorpion-System can't scan POJO ["+po.getClass()+"] information !");
		
		return this.doExecuteByParams(sql, getParameterByUserSettingPO(po,poProperty,true));
		
	}
	
	
	
	/**
	 * @param sql
	 * 
	 * @param pos
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws SQLException 
	 * 
	 * @throws InvocationTargetException 
	 * 
	 * @throws IllegalAccessException 
	 * 
	 * @throws IllegalArgumentException 
	 */
	protected int[] doDeletePos(String sql,Collection<? extends AbsScorpionBasePo> pos,PoProperty poProperty)throws ScorpionBaseException, 
	                SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
		List<DBParam> params = new ArrayList<DBParam>();
		
		for(AbsScorpionBasePo po:pos)
			params.add(getParameterByUserSettingPO(po,poProperty,true));
		
		return this.doExecuteBatchByParams(sql, params);
	}
	
	
	/**
	 * 更新操作
	 * 
	 * @param sql
	 * 
	 * @param param
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * @throws SQLException 
	 */
	protected int doExecuteSuper(String sql,DBParam param)throws ScorpionBaseException, SQLException{
	
		try{
			PreparedStatement statement = this.getPreparedStatement(sql);
			printDetailSQLInfo(sql, param);
			this.paramConvertStatement(statement, param);
			return statement.executeUpdate();
		}catch(SQLException e){
			throw e;
		}finally{
			connectionCheck();
		}
		
	}
	
	/**
	 * @param po
	 * 
	 * @param poProperty
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws InvocationTargetException 
	 * 
	 * @throws IllegalAccessException 
	 * 
	 * @throws IllegalArgumentException 
	 */
	protected List<DBParam> getPOPrimaryKeyValue(AbsScorpionBasePo po,PoProperty poProperty)throws ScorpionBaseException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
	    List<DBParam> dbparams = new ArrayList<DBParam>();
		for(String key:poProperty.getPrimaryKey()){
			DBParam param = new DBParam();
			param.addParam(poProperty.getGetAttributeByName(key).invoke(po));
			dbparams.add(param);
		}
		return dbparams;
	}
	
	
	/**
	 * @param po
	 * @param poProperty
	 * @return
	 * @throws ScorpionBaseException
	 */
	protected DBParam getParameterByUserSettingPO(AbsScorpionBasePo po,PoProperty poProperty,boolean isDeleteHandler)throws ScorpionBaseException{
		
		DBParam param = new DBParam();
		
		try {
			if(po.getAlreadySetField() != null&&!isDeleteHandler)
				for(String fieldName:po.getAlreadySetField())
					param.addParam(poProperty.getGetAttributeByName(fieldName).invoke(po));
			if(po.getConditionField() != null)
				for(String fieldName:po.getConditionField())
					param.addParam(poProperty.getGetAttributeByName(fieldName).invoke(po));
		} catch (Throwable e) {
			throw new ScorpionBaseException("scorpion-9965:Delete data by POJO exception ，check whether PO["+po.getClass().getName()+"] is valid or not !");
		}
		
		return param;
	}
	
	
	
	/**
	 * @param sqls
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws SQLException 
	 */
	protected void doExecuteBatchBySQL(List<String> sqls)throws ScorpionBaseException, SQLException{
		
		PlatformLogger.info("批量执行SQL数量["+(sqls==null?0:sqls.size())+"]");
		
		if(sqls == null||sqls.size()<1)
			return;
		
		if(sqls != null&&sqls.size()>1000)
			throw new ScorpionBaseException("scorpion-8906:提交的数据大于1000,请将数据切分多次提交!");
		
		try {
			Statement statement = this.getStatement();
			statement.executeBatch();
		} catch (SQLException e) {
			throw e;
		}finally{
			connectionCheck();
		}
	}
	
	
	/**
	 * 通过参数执行批量操作
	 * 
	 * @param sql
	 * 
	 * @param params
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws SQLException 
	 */
	protected int[] doExecuteBatchByParams(String sql,Collection<DBParam>params)throws ScorpionBaseException, SQLException{
		
		PlatformLogger.info("执行SQL数量["+(params==null?0:params.size())+"]"+sql);
		
		if(params!=null&&params.size()>1000)
			throw new ScorpionBaseException("scorpion-8906:提交的数据大于1000,请将数据切分多次提交!");
		
		try{
			PreparedStatement statement = this.getPreparedStatement(sql);
		
			for(DBParam param:params){
				this.paramConvertStatement(statement, param);
				statement.addBatch();
			}
		
			return statement.executeBatch();
		}catch(SQLException e){
			throw e;
		}finally{
			connectionCheck();
		}
	}
	
	/**
	 * @param sql
	 * @param param
	 * @return
	 * @throws ScorpionBaseException
	 */
	protected List<Map<String,Object>> doProcedureHandler(String sql,DBParam param,DBParam.ProcedureType type)throws ScorpionBaseException{
		
		CallableStatement procedureStatement = this.getProcedureStatement(sql, param,type);
		
		try {
			procedureStatement.execute();
			ResultSet result = procedureStatement.getResultSet();
			List<Map<String,Object>> resultmap = new ArrayList<Map<String,Object>>();
			
			while(result.next()){
				Map<String,Object> map = new HashMap<String,Object>();
			
				for(int i=0;i<result.getMetaData().getColumnCount();i++){
					map.put(result.getMetaData().getColumnLabel(i+1), result.getObject(i+1));
				}
				resultmap.add(map);
			}
			return resultmap;
		} catch (SQLException e) {
			throw new ScorpionBaseException("scorpion-9086:结构处理失败",e);
		}finally{
			try {
				procedureStatement.close();
			} catch (SQLException e) {
				throw new ScorpionBaseException("scorpion-8976:Close STATEMENT exception !",e);
			}
		}
	
	}
	
	
	
	public int doDeleteByKey(String key,DBParam param)throws ScorpionBaseException{
		return 0;
	}
   
	
	
	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws ScorpionBaseException
	 * 
	 * @throws SQLException 
	 */
	public boolean doExecuteByParams(String sql,DBParam param)throws ScorpionBaseException, SQLException{
		
		try{
			PreparedStatement statement = this.getPreparedStatement(sql);
			
			this.paramConvertStatement(statement, param);
			
			return statement.execute();
		
		}finally{
			connectionCheck();
		}
	}
	

}
