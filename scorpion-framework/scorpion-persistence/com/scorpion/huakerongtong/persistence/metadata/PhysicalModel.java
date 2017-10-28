package com.scorpion.huakerongtong.persistence.metadata;

import java.io.Serializable;
import java.util.List;

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
public class PhysicalModel implements Serializable{

	private static final long serialVersionUID = 8727096831537537881L;
	
	private String tableName;
	
	private List<AttributeTemplate> columns;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<AttributeTemplate> getColumns() {
		return columns;
	}

	public void setColumns(List<AttributeTemplate> columns) {
		this.columns = columns;
	}
	
}
