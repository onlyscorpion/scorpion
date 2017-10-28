package com.scorpion.huakerongtong.persistence.metadata;

import java.io.Serializable;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class AttributeTemplate implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private String attributeName;
	
	private String attributeType;
	
	private String columnName;
	
	private String isPrimaryKey;
	
	private String nullenable;
	
	private String dataDefault;
	
	private int primaryKeyNum;
	

	
	

	public String getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getNullenable() {
		return nullenable;
	}

	public void setNullenable(String nullenable) {
		this.nullenable = nullenable;
	}
	
	public String getDataDefault() {
		return dataDefault;
	}

	public void setDataDefault(String dataDefault) {
		if(dataDefault != null && !"".equals(dataDefault) && !"null".equals(dataDefault))
			this.dataDefault = dataDefault.replace("'", "");
		else
			this.dataDefault = dataDefault;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getPrimaryKeyNum() {
		return primaryKeyNum;
	}

	public void setPrimaryKeyNum(int primaryKeyNum) {
		this.primaryKeyNum = primaryKeyNum;
	}
	
	
}
