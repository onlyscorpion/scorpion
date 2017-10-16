package org.scorpion.persistence.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.scorpion.api.configuration.DBParam;
import org.scorpion.api.configuration.PojoEntityInfo.PoProperty;
import org.scorpion.api.configuration.SQLConfig.SQLProperty;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionBasePo;
import org.scorpion.api.persistence.AbsPersistenceQueryDao;
import org.scorpion.api.persistence.AbsScorpionResultHandler;
import org.scorpion.api.persistence.IScorpionResultHandler;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.context.SystemContext;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionPersistenceDAO extends AbsPersistenceQueryDao{


	@Override
	public Map<String, Object> queryMapByKey(String key, DBParam param)throws ScorpionBaseException {
		
		SQLProperty sqlproperty = sqlManager.getSQLByKey(key,param);
		
		this.checkSQLSecurity(key, sqlproperty);
	
		return super.queryMapByKey(sqlproperty.getVaule(), param);
	}

	
	
	
	@Override
	public List<Map<String, Object>> queryLisMapByKey(String key, DBParam param)throws ScorpionBaseException {
		
		SQLProperty sqlproperty = sqlManager.getSQLByKey(key,param);
		
		this.checkSQLSecurity(key, sqlproperty);
		
		return super.queryLisMapByKey(sqlproperty.getVaule(), param);
	}
	
	
	

	@Override
	public <T extends AbsScorpionBasePo> T queryPoBykey(String key, DBParam param, Class<T> clazz)throws ScorpionBaseException {
		
		try{
			
			SQLProperty sqlproperty = sqlManager.getSQLByKey(key,param);
			
			this.checkSQLSecurity(key, sqlproperty);
		    
			return this.queryPoBykey(sqlproperty.getVaule(), param, clazz,SystemContext.getApplicationContext());
		
		}catch(Throwable e){
			throw new ScorpionBaseException("scorpion-9087:QUERY DATA BY PO EXCEPTION",e);
		}
	}
	
	

	@Override
	public <T extends AbsScorpionBasePo> List<T> queryLisPoByKey(String key, DBParam param,Class<T> clazz) throws ScorpionBaseException {
		
		try{
			
			SQLProperty sqlproperty = sqlManager.getSQLByKey(key,param);
			
			this.checkSQLSecurity(key, sqlproperty);
			
			return super.queryPoLisByKey(sqlproperty.getVaule(), param, clazz,SystemContext.getApplicationContext());
	
		}catch(Throwable e){
			throw new ScorpionBaseException("scorpion-9087:Query POJO list exception !",e);
		}
	}
	
	

	@Override
	public <T extends AbsScorpionBasePo> T queryPoBySQL(String sql, DBParam param, Class<T> clazz)throws ScorpionBaseException {
		
			try {
				return this.queryPoBykey(sql, param, clazz,SystemContext.getApplicationContext());
			} catch (Throwable e) {
				throw new ScorpionBaseException("scorpion-9086:Query POJO by SQL failure !",e);
			}	
	}
	
	


	@Override
	public Map<String, Object> queryMapBySQL(String sql, DBParam param) throws ScorpionBaseException {
	
		return super.queryMapByKey(sql, param);
	}
	
	
	

	@Override
	public <T> T queryDataBySQL(String sql, DBParam param,IScorpionResultHandler<T> handler) throws ScorpionBaseException {
		
		if(handler == null)
			throw new ScorpionBaseException("scorpion-8954:The parameter [IScorpionResultHandler]can't be null !");
	
		try{
			return queryDataSuperDao(sql,param,handler);
		}catch(Throwable ex){
			throw new ScorpionBaseException("scorpion-9087:Query data by SQL failure !",ex);
		}

	}
	
	
	

	@Override
	public int updateDataBySQL(String sql, String[] tables,DBParam param) throws ScorpionBaseException {
	
		try {
			return super.doExecuteSuper(sql, param);
		} catch (SQLException e) {
			throw new ScorpionBaseException("scorpion-9468:Update data by SQL failure !",e);
		}
	}

	
	
	
	@Override
	public int updateDataByKey(String sqlKey, DBParam param)throws ScorpionBaseException {
		
		try{
			SQLProperty sqlproperty = sqlManager.getSQLByKey(sqlKey,param);
			this.checkSQLSecurity(sqlKey, sqlproperty);
			return super.doExecuteSuper(sqlproperty.getVaule(), param);
		}catch(Throwable ex){
			throw new ScorpionBaseException("scorpion-8907:Update data by SQL KEY failure !", ex);
		}
	}
	
	

	@Override
	public boolean insertPo(AbsScorpionBasePo po) throws ScorpionBaseException {
		
		try{
			if(po == null)
				return true;
			
			PoProperty poProperty = this.getPojoProperty(po.getClass().getName());
			SQLProperty sqlproperty = sqlManager.getSQLByPoInsert(po,this.getConnection().getDBType());
			if(sqlproperty == null)
				throw new ScorpionBaseException("scorpion-9087:PO["+po.getClass().getName()+"] invalidate !");
			
			return this.doInsertPo(po,poProperty,sqlproperty.getVaule());
		}catch(Throwable ex){
			throw new ScorpionBaseException("scorpion-9089:Insert data by Po["+po.getClass().getName()+"] failure !",ex);
		}
	}



	@Override
	public int[] insertPos(Collection<? extends AbsScorpionBasePo> pos)throws ScorpionBaseException {
		
		try{
			if(!SystemUtils.collectionIsNotNull(pos)) return null;
			PoProperty poProperty = this.getPojoProperty(pos.iterator().next().getClass().getName());
			SQLProperty sqlproperty = sqlManager.getSQLByPoInsert(pos.iterator().next(),this.getConnection().getDBType());
			if(sqlproperty == null)
				throw new ScorpionBaseException("scorpion-9087:PO["+pos.iterator().next().getClass().getName()+"] invalidate !");
			return this.doInsertPos(pos,poProperty,sqlproperty.getVaule());
		}catch(Throwable ex){
			throw new ScorpionBaseException("scorpion-9836:Insert batch data information by pos failure !",ex);
		}
	}


	

	@Override
	public int updatePO(AbsScorpionBasePo po) throws ScorpionBaseException {
		
		SQLProperty sqlproperty = sqlManager.getSQLByPoUpdate(po,this.getConnection().getDBType());
	
		return updateDataBySQL(sqlproperty.getVaule(),null,this.getParameterByUserSettingPO(po,SystemContext.getApplicationContext().getSystemPojoInformation().getPojoByName(po.getClass().getName()),false));
	}



	@Override
	public int updatePOs(Collection<? extends AbsScorpionBasePo> pos)throws ScorpionBaseException {
	
		int size = 0;
		
		for(AbsScorpionBasePo po:pos){
			size = size + updatePO(po);
		}
		
		return size;
	}



	@Override
	public boolean deletePO(AbsScorpionBasePo po) throws ScorpionBaseException {
	
		try{
			SQLProperty sqlproperty = sqlManager.getSQLByPoDel(po,this.getConnection().getDBType());
			return super.doDeletePo(sqlproperty.getVaule(), po);
		}catch(Throwable ex){
			throw new ScorpionBaseException("scorpion-6987:Delete POJO exception !",ex);
		}
	}

	@Override
	public int[] deletePOs(Collection<? extends AbsScorpionBasePo> pos)throws ScorpionBaseException {

		try{
			if(!SystemUtils.collectionIsNotNull(pos)) return null;
			PoProperty poProperty = this.getPojoProperty(pos.iterator().next().getClass().getClass().getName());
			SQLProperty sqlproperty = sqlManager.getSQLByPoDel(pos.iterator().next(),this.getConnection().getDBType());
			return this.doDeletePos(sqlproperty.getVaule(), pos, poProperty);
		}catch(Throwable ex){
			throw new ScorpionBaseException("scorpion-9848: Delete batch data by POJO failure !",ex);
		}
	}



	@Override
	public boolean executeSQLByKey(String sqlKey, DBParam param)throws ScorpionBaseException {
		
		try{
			SQLProperty sqlProperty = sqlManager.getSQLByKey(sqlKey,param);
			return super.doExecuteByParams(sqlProperty.getVaule(), param);
		}catch(Throwable ex){
			throw new ScorpionBaseException("scorpion-7945:Execute SQL by SQL key failure !",ex);
		}
	}



	@Override
	public int[] executeBatchByKey(String sqlKey, Collection<DBParam> params)throws ScorpionBaseException {
		
		try{
			if(!SystemUtils.collectionIsNotNull(params))
				throw new ScorpionBaseException("scorpion-9076:执行sqlKey["+sqlKey+"]sql异常 !");
      
			batchDynamicParamCheck(params);
			SQLProperty sqlProperty = sqlManager.getSQLByKey(sqlKey,params.iterator().next());
			return this.doExecuteBatchByParams(sqlProperty.getVaule(),params);
		}catch(Throwable ex){
			throw new ScorpionBaseException("scorpion-9846:Execute batch SQL by SQL key failure !",ex);
		}
	}



	@Override
	public List<Map<String,Object>> executeProcedureByKey(String procedureKey,DBParam param) throws ScorpionBaseException {
		
		SQLProperty sqlProperty = sqlManager.getSQLByKey(procedureKey,null);
		
		return super.doProcedureHandler(sqlProperty.getVaule(), param,DBParam.ProcedureType.OUT);
		
	}

	

	@Override
	public List<Map<String,Object>> queryLisMapBySQL(String sql, DBParam param)throws ScorpionBaseException {
		
		try{
			return queryDataSuperDao(sql,param,new AbsScorpionResultHandler<List<Map<String,Object>>>() {
				@Override
				public List<Map<String,Object>> resultHandler(ResultSet result)throws ScorpionBaseException {
					try {
						List<Map<String,Object>> resultmap = new ArrayList<Map<String,Object>>();
						while(result.next()){
							Map<String,Object> map = new HashMap<String,Object>();
							for(int i=0;i<result.getMetaData().getColumnCount();i++){
								map.put(this.columnConvert(result.getMetaData().getColumnLabel(i+1)),dbDataTypeConvert(result.getObject(i+1)));
							}
							resultmap.add(map);
						}
						return resultmap;
					} catch (SQLException e) {
						throw new ScorpionBaseException("scorpion-9086:Structure handler failure !",e);
					}
			
				}
	
			});
	
		}catch(Throwable e){
			throw new ScorpionBaseException("scorpion-9086:Structure handler failure !",e);
		}
	}
	

	@Override
	public Object queryColumnByKey(String sqlKey, DBParam param, final int indexcolumn)throws ScorpionBaseException {
	
		try{
			SQLProperty sqlproperty = sqlManager.getSQLByKey(sqlKey,param);
			return queryDataSuperDao(sqlproperty.getVaule(),param,new AbsScorpionResultHandler<Object>() {
		
				@Override
				public Object resultHandler(ResultSet result)throws Throwable {
					Object returnObj = null;
					while(result.next()){
						returnObj = dbDataTypeConvert(result.getObject(indexcolumn));
						break;
					}
					return returnObj;
				}
			});
	
		}catch(Throwable e){
			throw new ScorpionBaseException("scorpion-6946:Query database column by key failure !",e);
		}
	}




	@Override
	public List<Object> queryColumnListByKey(String sqlKey, DBParam param,final int indexcolumn) throws ScorpionBaseException {
	
		try{
			SQLProperty sqlproperty = sqlManager.getSQLByKey(sqlKey,param);
			return queryDataSuperDao(sqlproperty.getVaule(),param,new AbsScorpionResultHandler<List<Object>>() {
				@Override
				public List<Object> resultHandler(ResultSet result)throws Throwable {
					List<Object> resultmap = new ArrayList<Object>();
					while(result.next()){
						resultmap.add(dbDataTypeConvert(result.getObject(indexcolumn)));
					}
					return resultmap;
			}
		});
		}catch(Throwable e){
			throw new ScorpionBaseException("scorpion-6946:Query database column List by key failure !",e);
		}
	}
	



	@Override
	public void executeProcedure(String procedure, DBParam param)throws ScorpionBaseException {
		
		super.doProcedureHandler(procedure, param,DBParam.ProcedureType.IN);
	}




	@Override
	public void rollback() throws ScorpionBaseException {
		try {
			this.connection.rollback();
			this.connection.close();
			this.connection = null;
		} catch (SQLException e) {
			throw new ScorpionBaseException("scorpion-7864:There is a exception in rollback transaction !",e);
		}
	}


	@Override
	public void executeFunction(String function, DBParam param)throws ScorpionBaseException {
		
		
	}



	@Override
	public List<Map<String, Object>> executeFunctionByKey(String functionKey,DBParam param) throws ScorpionBaseException {
	
		return null;
	}


	@Override
	public boolean deleteDataByKey(String sqlKey, DBParam param) throws ScorpionBaseException {
		
		return this.executeSQLByKey(sqlKey, param);
	}




	@Override
	public List<Map<String, Object>> queryListMapByPaging(String sqlKey,DBParam param, int startnum, int endnum) throws ScorpionBaseException {

		SQLProperty sqlproperty = sqlManager.getSQLByKey(sqlKey,param,startnum,endnum,this.getConnection().getDBType());

		return this.queryLisMapBySQL(sqlproperty.getVaule(), param);
	}




	@Override
	public <T extends AbsScorpionBasePo> List<T> queryListPoByPaging(String sqlKey,DBParam param,int startnum,int endnum, Class<T> clazz) throws ScorpionBaseException {
		
		try{
		
			SQLProperty sqlproperty = sqlManager.getSQLByKey(sqlKey,param,startnum,endnum,this.getConnection().getDBType());
		
			return super.queryPoLisByKey(sqlproperty.getVaule(), param, clazz,SystemContext.getApplicationContext());
		
		}catch(Throwable ex){
			throw new ScorpionBaseException("scorpion-9857:Query List Po information failure !",ex);
		}
	}




	@Override
	public boolean insertDataByKey(String key, DBParam param)throws ScorpionBaseException {
		
		return this.executeSQLByKey(key, param);
	}
	
}
