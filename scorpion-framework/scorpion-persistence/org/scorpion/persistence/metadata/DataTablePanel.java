package org.scorpion.persistence.metadata;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
public class DataTablePanel extends JScrollPane{

	private static final long serialVersionUID = -8762595370737646637L;
	

	public DataTablePanel() {
		
		this.setPreferredSize(new Dimension(685, 280));
		this.setLayout(new ScrollPaneLayout());
		List<PhysicalModel> physicalModels = new ArrayList<PhysicalModel>();
		physicalModels.add(new PhysicalModel());
		physicalModels.add(new PhysicalModel());
		
		DataTableModel tableModel = new DataTableModel();
		tableModel.setPhysicalModels(physicalModels);
		
		JTable table = new JTable(tableModel);
		setTableProperty(table,tableModel);
	
		
		this.setViewportView(table);
		this.setBorder(BorderFactory.createTitledBorder("数据表"));
	}
	
	
	public void setTableProperty(JTable table,DataTableModel tableModel){
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
		table.setRowSorter(sorter);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(0).setWidth(100);
		table.getColumnModel().getColumn(0).setMinWidth(100);
	}

}
