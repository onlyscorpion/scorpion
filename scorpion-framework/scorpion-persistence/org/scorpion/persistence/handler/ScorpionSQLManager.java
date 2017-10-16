package org.scorpion.persistence.handler;

import java.util.Date;

import org.scorpion.api.configuration.DBParam;
import org.scorpion.api.configuration.SQLConfig.SQLProperty;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionBasePo;
import org.scorpion.api.persistence.IScorpionSQLManager;
import org.scorpion.api.persistence.ScorpionDataBaseType;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionSQLManager implements IScorpionSQLManager{

	
	
	@Override
	public SQLProperty getSQLByKey(String key,DBParam param) throws ScorpionBaseException {
		
		SQLProperty sqlproperty = ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getSQLPropertyByKey(key);

		if(sqlproperty == null)
			throw new ScorpionBaseException("scorpion-6885:SYSTEM CAN'T FIND SQLKEY ["+key+"] MAPPING SQL IN THE SQL CONFIGURATON FILE! ");
		
		if(param == null||param.getDynamicParams() == null)
			return sqlproperty;
		
		SQLProperty property = ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().new SQLProperty();
		property.setKey(sqlproperty.getKey());
		property.setSqlFileName(sqlproperty.getSqlFileName());
		property.setVaule(sqlproperty.getVaule());
		property.setTables(sqlproperty.getTables());
		
		for(String dynamicParam:param.getDynamicParams()){
			property.setVaule(property.getVaule().replaceFirst(Constant.DYNAMICPARAM, dynamicParam));
		}
		
		return property;
	}

	
	
	
	@Override
	public SQLProperty getSQLByPoInsert(AbsScorpionBasePo po)throws ScorpionBaseException {
		
		return ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoInsertMap().get(po.getClass().getName());
	}

	
	
	@Override
	public SQLProperty getSQLByPoDel(AbsScorpionBasePo po,Integer dbType) throws ScorpionBaseException {
		
		if(((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoDelMap().get(po.getDynamicKey().toString())!=null)
			return ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoDelMap().get(po.getDynamicKey().toString());
	
		String sql = null;
		
		if(dbType == null) dbType = -1;
		     
		sql = ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoDelMap().get(po.getClass().getName()).getVaule() + getConditionInfo(po,dbType);
		SQLProperty property = ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().new SQLProperty();
		property.setKey(po.getDynamicKey().toString());
		property.setVaule(sql);
		((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoDelMap().put(po.getDynamicKey().toString(), property);
		return property;
		
	}

	@Override
	public SQLProperty getSQLByPoUpdate(AbsScorpionBasePo po,Integer dbType)throws ScorpionBaseException {
	
		if(((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoUpdateMap().get(po.getDynamicKey().toString())!=null)
			return ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoUpdateMap().get(po.getDynamicKey().toString());
	
		String sql = null;
		
		if(dbType == null) dbType = -1;
		     
		sql = ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoUpdateMap().get(po.getClass().getName()).getVaule() + getUpDateHandlerInfo(po,dbType) + getConditionInfo(po,dbType);
		SQLProperty property = ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().new SQLProperty();
		property.setKey(po.getDynamicKey().toString());
		property.setVaule(sql);
		((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoUpdateMap().put(po.getDynamicKey().toString(), property);
		return property;
	}
	
	
	
	/**
	 * @param po
	 * 
	 * @param dbType
	 * 
	 * @return
	 */
	protected String getUpDateHandlerInfo(AbsScorpionBasePo po,Integer dbType){
		
		StringBuilder condition = new StringBuilder();
		String sql = "";
		
		if(ScorpionDataBaseType.oracle_db_type == dbType){
		
			if(po.getAlreadySetField() == null)
				  return "";
			for(String fieldName:po.getAlreadySetField()){
				condition.append(fieldName+"="+("? ,"));
			}
			sql = " set "+ condition.substring(0, condition.length()-1);
			
		}else if(ScorpionDataBaseType.dm_db_type == dbType){
			
			return null;
		}else if(ScorpionDataBaseType.kbe_db_type == dbType){
			
			if(po.getAlreadySetField() == null)
				  return "";
			for(String fieldName:po.getAlreadySetField()){
				condition.append(fieldName+"="+("? ,"));
			}
			sql = " set "+ condition.substring(0, condition.length()-1);
		}else{

			
			if(po.getAlreadySetField() == null)
				  return "";
			for(String fieldName:po.getAlreadySetField()){
				condition.append(fieldName+"="+("? ,"));
			}
			sql = " set "+ condition.substring(0, condition.length()-1);
			
		
		}
		return sql;
	}
	
	
	/**
	 * 
	 * @param po
	 * 
	 * @param dbType
	 * 
	 * @return
	 */
	protected String getInsDateHandlerInfo(AbsScorpionBasePo po,Integer dbType){
		
		StringBuilder fieldInfo = new StringBuilder("(");
		StringBuilder fieldValue = new StringBuilder("(");
		
		if(ScorpionDataBaseType.oracle_db_type == dbType){
		
			if(po.getAlreadySetField() == null||po.getAlreadySetField().isEmpty())
				return null;
			
			for(String fieldName:po.getAlreadySetField()){
				fieldInfo.append(fieldName+",");
				fieldValue.append("? ,");
			}
			
			return fieldInfo.substring(0,fieldInfo.length()-1)+")"+" values "+fieldValue.substring(0, fieldValue.length()-1)+")";
			
		}else if(ScorpionDataBaseType.dm_db_type == dbType){
			return null;
		}else if(ScorpionDataBaseType.kbe_db_type == dbType){
		
			if(po.getAlreadySetField() == null||po.getAlreadySetField().isEmpty())
				return null;
			
			for(String fieldName:po.getAlreadySetField()){
				fieldInfo.append(fieldName+",");
				fieldValue.append("? ,");
			}
			
			return fieldInfo.substring(0,fieldInfo.length()-1)+")"+" values "+fieldValue.substring(0, fieldValue.length()-1)+")";
		}
	
		return null;
	}
	
	
	/**
	 * @param po
	 * 
	 * @param dbType
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException 
	 */
	protected String getConditionInfo(AbsScorpionBasePo po,Integer dbType) {
		
		StringBuilder condition = new StringBuilder();
		String sql = "";
		
		if(ScorpionDataBaseType.oracle_db_type == dbType){
			
			if(po.getConditionField() == null)
                return "";
			
			for(String fieldName:po.getConditionField()){
				condition.append(fieldName+"="+(po.getFieldTypeByName(fieldName).isAssignableFrom(Date.class)?"to_date(?,'yyyy-MM-dd hh24:mi:ss')":"? and "));
			}
			
			sql = " where "+ condition.toString().trim().substring(0, condition.length()-4);
			
		}else if(ScorpionDataBaseType.dm_db_type == dbType){
			return null;
		}else if(ScorpionDataBaseType.kbe_db_type == dbType){
			
			if(po.getConditionField() == null)
                return "";
			
			for(String fieldName:po.getConditionField()){
				condition.append(fieldName+"="+(po.getFieldTypeByName(fieldName).isAssignableFrom(Date.class)?"to_date(?,'yyyy-MM-dd hh24:mi:ss')":"? and "));
			}
			
			sql = " where "+ condition.toString().trim().substring(0, condition.length()-4);
		}else{

			if(po.getConditionField() == null)
                return "";
			
			for(String fieldName:po.getConditionField()){
				condition.append(fieldName+"="+(po.getFieldTypeByName(fieldName).isAssignableFrom(Date.class)?"to_date(?,'yyyy-MM-dd hh24:mi:ss')":"? and "));
			}
			
			sql = " where "+ condition.toString().trim().substring(0, condition.length()-4);
		}
		
		return sql;
	}


	@Override
	public SQLProperty getSQLByPoInsert(AbsScorpionBasePo po, Integer dbType)throws ScorpionBaseException {
		
		if(((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoInsertMap().get(po.getClass().getName())!=null)
			return ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoInsertMap().get(po.getClass().getName());
	
		String sql = null;
		if(dbType == null) dbType = -1;
		     
		sql = "INSERT INTO "+po.getTableName()+getInsDateHandlerInfo(po,dbType);
		SQLProperty property = ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().new SQLProperty();
		property.setKey(po.getDynamicKey().toString());
		property.setVaule(sql);
		((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoInsertMap().put(po.getDynamicKey().toString(), property);
		return property;
	
	}



	@Override
	public SQLProperty getSQLByKey(String key, DBParam param, int startnum,int endnum,int dbType) throws ScorpionBaseException {
	
		SQLProperty sqlProperty = getSQLByKey(key,param);
		
		SQLProperty property = ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().new SQLProperty();
		
		if(ScorpionDataBaseType.oracle_db_type == dbType){
			property.setKey(key);
			property.setVaule(Constant.PAGING_SQL_TITLE + sqlProperty.getVaule()+") ScorpionNUM) where NUM>"+startnum+" and NUM<="+endnum);
		}else if(ScorpionDataBaseType.kbe_db_type == dbType){
			property.setKey(key);
			property.setVaule(Constant.PAGING_SQL_TITLE + sqlProperty.getVaule()+") ScorpionNUM) where NUM>"+startnum+" and NUM<="+endnum);
		}else if(ScorpionDataBaseType.dm_db_type == dbType){
			return null;
		}else if(ScorpionDataBaseType.mysql_db_type == dbType){
			property.setKey(key);
			property.setVaule(Constant.MYSQL_SQL_TITLE + sqlProperty.getVaule()+") Scorpion_data_paging_limit limit "+startnum+","+endnum);
		}
	
		return property;
	}

}
