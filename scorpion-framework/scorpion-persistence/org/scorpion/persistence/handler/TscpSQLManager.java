package org.scorpion.persistence.handler;

import java.util.Date;

import org.scorpion.api.configuration.DBParam;
import org.scorpion.api.configuration.SQLConfig.SQLProperty;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpBasePo;
import org.scorpion.api.persistence.ITscpSQLManager;
import org.scorpion.api.persistence.TscpDataBaseType;
import org.scorpion.api.util.Constant;
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
public class TscpSQLManager implements ITscpSQLManager{

	
	
	@Override
	public SQLProperty getSQLByKey(String key,DBParam param) throws TscpBaseException {
		
		SQLProperty sqlproperty = ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getSQLPropertyByKey(key);

		if(sqlproperty == null)
			throw new TscpBaseException("TSCP-6885:SYSTEM CAN'T FIND SQLKEY ["+key+"] MAPPING SQL IN THE SQL CONFIGURATON FILE! ");
		
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
	public SQLProperty getSQLByPoInsert(AbsTscpBasePo po)throws TscpBaseException {
		
		return ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().getPoInsertMap().get(po.getClass().getName());
	}

	
	
	@Override
	public SQLProperty getSQLByPoDel(AbsTscpBasePo po,Integer dbType) throws TscpBaseException {
		
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
	public SQLProperty getSQLByPoUpdate(AbsTscpBasePo po,Integer dbType)throws TscpBaseException {
	
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
	protected String getUpDateHandlerInfo(AbsTscpBasePo po,Integer dbType){
		
		StringBuilder condition = new StringBuilder();
		String sql = "";
		
		if(TscpDataBaseType.oracle_db_type == dbType){
		
			if(po.getAlreadySetField() == null)
				  return "";
			for(String fieldName:po.getAlreadySetField()){
				condition.append(fieldName+"="+("? ,"));
			}
			sql = " set "+ condition.substring(0, condition.length()-1);
			
		}else if(TscpDataBaseType.dm_db_type == dbType){
			
			return null;
		}else if(TscpDataBaseType.kbe_db_type == dbType){
			
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
	protected String getInsDateHandlerInfo(AbsTscpBasePo po,Integer dbType){
		
		StringBuilder fieldInfo = new StringBuilder("(");
		StringBuilder fieldValue = new StringBuilder("(");
		
		if(TscpDataBaseType.oracle_db_type == dbType){
		
			if(po.getAlreadySetField() == null||po.getAlreadySetField().isEmpty())
				return null;
			
			for(String fieldName:po.getAlreadySetField()){
				fieldInfo.append(fieldName+",");
				fieldValue.append("? ,");
			}
			
			return fieldInfo.substring(0,fieldInfo.length()-1)+")"+" values "+fieldValue.substring(0, fieldValue.length()-1)+")";
			
		}else if(TscpDataBaseType.dm_db_type == dbType){
			return null;
		}else if(TscpDataBaseType.kbe_db_type == dbType){
		
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
	 * @throws TscpBaseException 
	 */
	protected String getConditionInfo(AbsTscpBasePo po,Integer dbType) {
		
		StringBuilder condition = new StringBuilder();
		String sql = "";
		
		if(TscpDataBaseType.oracle_db_type == dbType){
			
			if(po.getConditionField() == null)
                return "";
			
			for(String fieldName:po.getConditionField()){
				condition.append(fieldName+"="+(po.getFieldTypeByName(fieldName).isAssignableFrom(Date.class)?"to_date(?,'yyyy-MM-dd hh24:mi:ss')":"? and "));
			}
			
			sql = " where "+ condition.toString().trim().substring(0, condition.length()-4);
			
		}else if(TscpDataBaseType.dm_db_type == dbType){
			return null;
		}else if(TscpDataBaseType.kbe_db_type == dbType){
			
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
	public SQLProperty getSQLByPoInsert(AbsTscpBasePo po, Integer dbType)throws TscpBaseException {
		
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
	public SQLProperty getSQLByKey(String key, DBParam param, int startnum,int endnum,int dbType) throws TscpBaseException {
	
		SQLProperty sqlProperty = getSQLByKey(key,param);
		
		SQLProperty property = ((SystemContext)SystemContext.getApplicationContext()).getSqlconfig().new SQLProperty();
		
		if(TscpDataBaseType.oracle_db_type == dbType){
			property.setKey(key);
			property.setVaule(Constant.PAGING_SQL_TITLE + sqlProperty.getVaule()+") TSCPNUM) where NUM>"+startnum+" and NUM<="+endnum);
		}else if(TscpDataBaseType.kbe_db_type == dbType){
			property.setKey(key);
			property.setVaule(Constant.PAGING_SQL_TITLE + sqlProperty.getVaule()+") TSCPNUM) where NUM>"+startnum+" and NUM<="+endnum);
		}else if(TscpDataBaseType.dm_db_type == dbType){
			return null;
		}else if(TscpDataBaseType.mysql_db_type == dbType){
			property.setKey(key);
			property.setVaule(Constant.MYSQL_SQL_TITLE + sqlProperty.getVaule()+") tscp_data_paging_limit limit "+startnum+","+endnum);
		}
	
		return property;
	}

}
