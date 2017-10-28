package com.scorpion.huakerongtong.api.persistence;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scorpion.huakerongtong.api.configuration.DBParam;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.ApplicationContext;

public abstract class AbsPersistenceQueryDao extends AbsPersistenceExecuteDao{
	
	@SuppressWarnings("unchecked")
	protected <T> T queryPoBykey(String sql,DBParam param,final Class<?>clazz,final ApplicationContext appcontext)throws Throwable{
		return (T) this.queryDataSuperDao(sql, param, new AbsScorpionResultHandler<Object>() {
		
			@Override
			public Object resultHandler(ResultSet result)throws Throwable {
				this.context = appcontext;
				while(result.next()){
					return this.resultConvertPojo(clazz, result);
				}
				return null;
			}
		});
	}
	
	
	/**
	 * 
	 * @param sqlKey
	 * 
	 * @param param
	 * 
	 * @param clazz
	 * 
	 * @return
	 */
	public <T> T querySuperColumnByColumn(String sqlKey,DBParam param,Class<T> clazz){
		
		
		
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public  <T> List<T> queryPoLisByKey(String sql,DBParam param,Class<T> clazz,ApplicationContext context)throws Throwable{
		try{
			return  (List<T>) this.queryDataSuperDao(sql, param, new AbsScorpionResultHandler<List<?>>() {
				private Class<?> clazz;
			    private List<Object> pojos = new ArrayList<Object>();
				@Override
				public List<?> resultHandler(ResultSet result)throws Throwable {
					try {
						while(result.next()){
							pojos.add(this.resultConvertPojo(clazz, result));
						}
					} catch (Throwable e) {
						throw e;
					}
					return pojos;
				}
					
				public AbsScorpionResultHandler<?> setClazz(Class<?> clazz,ApplicationContext context) {
					this.clazz = clazz;
					this.context = context;
					return this;
				}
			}.setClazz(clazz,context));
		}catch(Throwable e){
			throw e;
		}
	}
	
	
	/**
	 * 
	 */
	public Map<String,Object> queryMapByKey(String sql,DBParam params)throws ScorpionBaseException{
		try {
		
			return this.queryDataSuperDao(sql, params, new AbsScorpionResultHandler<Map<String,Object>>() {
		
				@Override
				public Map<String, Object> resultHandler(ResultSet result)throws Throwable {
				
					Map<String,Object> resultmap = null;
					while(result.next()){
						resultmap  = new HashMap<String,Object>();
						for(int i=0;i<result.getMetaData().getColumnCount();i++){
							resultmap.put(this.columnConvert(result.getMetaData().getColumnLabel(i+1)),  dbDataTypeConvert(result.getObject(i+1)));
						}
						break;
					}
					return resultmap;
			}
		});
		} catch (Throwable e) {
			throw new ScorpionBaseException("scorpion-9086:Query Map Data by key failure !",e);
		}
	}
	
	
	/**
	 * 
	 */
	public List<Map<String,Object>> queryLisMapByKey(String sql,DBParam params)throws ScorpionBaseException{
	
		try {	
			return this.queryDataSuperDao(sql, params, new AbsScorpionResultHandler<List<Map<String,Object>>>() {
				@Override
				public List<Map<String,Object>> resultHandler(ResultSet result)throws Throwable {
					List<Map<String,Object>> resultmap = new ArrayList<Map<String,Object>>();
					while(result.next()){
						Map<String,Object> map = new HashMap<String,Object>();
						for(int i=0;i<result.getMetaData().getColumnCount();i++){
							map.put(this.columnConvert(result.getMetaData().getColumnLabel(i+1)), dbDataTypeConvert(result.getObject(i+1)));
						}
						resultmap.add(map);
					}
					return resultmap;
			}
		});
		} catch (Throwable e) {
			throw new ScorpionBaseException("scorpion-9086:Query List Map data failure !",e);
		}
	}
	
}
