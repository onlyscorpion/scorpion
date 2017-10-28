package com.scorpion.huakerongtong.persistence.meta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

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
public class DataTableModel implements TableModel{ 
	
	public static List<PhysicalModel> physicalModels;
	
	private String POCONF = "/conf/po.properites";
	

	@Override
	public int getRowCount() {
	
		if(physicalModels == null)
			return 0;
	
		return physicalModels.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		
			switch(columnIndex){
			
			case 0: return "表名称";
			
			case 1: return "PO包路径";
			
			case 2: return "PO存放路径";
			
			default: return "未定义字段";
			
		   }
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
	
		if(columnIndex == 1||columnIndex == 2)
			return false;
		
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		switch(columnIndex){
		case 0 : return physicalModels.get(rowIndex).getTableName();
		case 1 : return getJarPath();
		case 2 : return getFilePath();
		default : return "";
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	//	System.out.println(aValue+"|"+rowIndex+"|"+columnIndex);

	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		
	}

	public List<PhysicalModel> getPhysicalModels() {
		return physicalModels;
	}

	
	private String getFilePath(){
		
		File configFile = null;
		
		if(System.getProperty("user.web.env") != null)
			configFile = new File(new File(System.getProperty("user.web.env")),"po.properites");
		else
			configFile = new File(new File(System.getProperty("user.dir")).getParent(),POCONF);
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(configFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties.getProperty("filePath");
	}


	private String getJarPath(){
		
		File configFile = null;
		
		if(System.getProperty("user.web.env") != null)
			configFile = new File(new File(System.getProperty("user.web.env")),"po.properites");
		else
			configFile = new File(new File(System.getProperty("user.dir")).getParent(),POCONF);
		
		Properties properties = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(configFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			properties.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties.getProperty("packagePath");

	}
	
	
}
