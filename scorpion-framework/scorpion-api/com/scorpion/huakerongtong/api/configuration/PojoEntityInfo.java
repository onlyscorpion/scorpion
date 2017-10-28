package com.scorpion.huakerongtong.api.configuration;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

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
public class PojoEntityInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Map<String,PoProperty> propertyMap;
	
	
	/**
	 * @param name
	 * @return
	 */
	public PoProperty getPojoByName(String name){
		if(propertyMap == null)
			return null;
		return propertyMap.get(name);
	
	}
	
	
	public class PoProperty{
		
		private String tableName;
		private List<String> primaryKey;
		private List<String> nullable;
		private Map<String,String> dataDefault;
		private String jarName;
		private boolean isValid;
		private Class<?> clazz;
		private LinkedList<String> fieldSequence;
		private Map<String,Method> getAttributes;
		private Map<String,Method> setAttributes;
		
		
		public boolean isValid() {
			return isValid;
		}

		public void setValid(boolean isValid) {
			this.isValid = isValid;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}
		
		public String getJarName() {
			return jarName;
		}

		public void setJarName(String jarName) {
			this.jarName = jarName;
		}

		public LinkedList<String> getFieldSequence() {
			if(fieldSequence == null)
				fieldSequence = new LinkedList<String>();
			return fieldSequence;
		}


		public List<String> getPrimaryKey() {
			if(primaryKey == null)
				primaryKey = new ArrayList<String>();
			return primaryKey;
		}

		public void setPrimaryKey(List<String> primaryKey) {
			this.primaryKey = primaryKey;
		}

		public List<String> getNullable() {
			if(nullable == null)
				nullable = new ArrayList<String>();
			return nullable;
		}

		public void setNullable(List<String> nullable) {
			this.nullable = nullable;
		}

		public Map<String, String> getDataDefault() {
			if(dataDefault == null)
				dataDefault = new HashMap<String,String>();
			return dataDefault;
		}

		public void setDataDefault(Map<String, String> dataDefault) {
			this.dataDefault = dataDefault;
		}

		/**
		 * @return
		 */
		public Map<String,Method> getSetAttributes(){
			if(setAttributes == null)
				setAttributes = new HashMap<String,Method>();
			return setAttributes;
		}

		/**
		 * @return
		 */
		public Map<String, Method> getGetAttributes() {
			if(getAttributes == null)
				getAttributes = new HashMap<String,Method>();
			return getAttributes;
		}
		
		
		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		/**
		 * @param name
		 * @return
		 * @throws ScorpionBaseException
		 */
		public Method getGetAttributeByName(String name) throws ScorpionBaseException{
		
			if(getAttributes == null||getAttributes.get(name) == null)
				throw new ScorpionBaseException("scorpion-9870:POJO信息不存在,请检查系统是否启动成功!");
		
			return getAttributes.get(name);
		}
		
		/**
		 * @param name
		 * @return
		 * @throws ScorpionBaseException
		 */
		public Method getSetAttributeByName(String name)throws ScorpionBaseException{
		
			if(name == null||"".equals(name))
				throw new ScorpionBaseException("scorpion-9864:属性不能为空!");
		
			name = name.toLowerCase();
		
			if(setAttributes == null||setAttributes.get(name)==null)
				throw new ScorpionBaseException("scorpion-9870:数据库字段["+name+"]在PO中找不到对应的映射信息!");
		
			return setAttributes.get(name);
		}
	}
	
	public Map<String, PoProperty> getPropertyMap() {
		
		if(propertyMap == null)
			propertyMap = new HashMap<String,PoProperty>();
		
		return propertyMap;
	}

	public void setPropertyMap(Map<String, PoProperty> propertyMap) {
		this.propertyMap = propertyMap;
	}

}
