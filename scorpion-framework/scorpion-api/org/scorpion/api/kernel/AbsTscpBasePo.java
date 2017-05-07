package org.scorpion.api.kernel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scorpion.api.exception.TscpBaseException;

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
public abstract class AbsTscpBasePo implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	protected Map<String,String> fieldMappingSQL;
	
	private Set<String> alreadSettedFields;
	
	private Set<String> conditionField;
	
	protected Map<String,Class<?>> fieldMappingType = new HashMap<String,Class<?>>();;
	
	protected StringBuilder dynamicKey;
	
	/**
	 * PO转Map
	 * @return
	 * @throws TscpBaseException
	 */
	public abstract Map<String,Object> pojoToMap();
	
	
	/**
	 * 
	 * @param map
	 */
	public abstract AbsTscpBasePo convertMapByPo(Map<?,?>map);
	
	/**
	 * 
	 * @return
	 */
	public abstract String getTableName();
	
	/**
	 * 
	 * @return
	 */
	public abstract Object[] getPrimaryKeyValue(); 
	
	/**
	 * @return
	 */
	public abstract String[] getFieldName();
	
	/**
	 * @return
	 */
	public abstract String[] getPrimaryKeyFieldName();
	
	/**
	 * 
	 * @return
	 */
	public abstract Object getValueByFieldName(String fieldName);
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean isNotNull();
	

	/**
	 * 
	 * @param fieldName
	 */
	public  void setFieldInfo(String fieldName){
		if(alreadSettedFields == null)
			alreadSettedFields = new HashSet<String>();
		
		this.dynamicKey.append("&"+fieldName);
		this.alreadSettedFields.add(fieldName);
	}
	
	
	/**
	 * 
	 * @return List<String>
	 */
	public Set<String> getAlreadySetField(){
		return this.alreadSettedFields;
	}


	public Map<String, String> getFieldMappingSQL() {
		return fieldMappingSQL;
	}
	
	
	/**
	 * 
	 * @param fieldName
	 * @return
	 */
	public abstract String getFieldMappingSQLByFieldName(String fieldName);
	
	
	/**
	 * @param fieldName
	 * @return
	 */
	public Class<?> getFieldTypeByName(String fieldName){
		
		if(this.fieldMappingType == null)
			return null;
		
		return this.fieldMappingType.get(fieldName);
	}
	
	
	/**
	 * 
	 */
	protected abstract void initFieldMappingType();
	
	public StringBuilder getDynamicKey(){
		return this.dynamicKey;
	}
	
	
	public void setConditionField(String fieldName){
		 
		if(conditionField == null)
			conditionField = new HashSet<String>();
		
		if(this.dynamicKey.indexOf("#")>-1)
			this.dynamicKey.append("&"+fieldName);
		else
			this.dynamicKey.append("#"+fieldName);
		
		conditionField.add(fieldName);
	}
	
	public Set<String> getConditionField(){
	
		return this.conditionField;
	}
	
	
	public abstract void clear();
	
}
