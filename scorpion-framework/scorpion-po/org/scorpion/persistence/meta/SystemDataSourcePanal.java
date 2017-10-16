package org.scorpion.persistence.meta;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.scorpion.api.configuration.DataSourceLis;

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
public class SystemDataSourcePanal  extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private JScrollPane tablePanel;
	
	JComboBox comboBox=new JComboBox();
	
	public SystemDataSourcePanal() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(new JLabel("选择数据源："));
		this.add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataTableModel.physicalModels.clear();
				List<String> tables = null;
				try {
					DBAdapter.currentDS = (String)comboBox.getSelectedItem();
					tables = DBAdapter.getTables(DataSourceLis.getAllDataSource().get(comboBox.getSelectedItem()));
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
				for(String name:tables){
					PhysicalModel m = new PhysicalModel();
					m.setTableName(name);
					DataTableModel.physicalModels.add(m);
				}
				if(tablePanel != null){
					tablePanel.validate();
					tablePanel.repaint();
				}
			}
		});
	}


	public JPanel setData(List<String> dataSourceNames){
		comboBox.removeAllItems();
		for(String ds:dataSourceNames)
			comboBox.addItem(ds);
		return this;
	}


	public JScrollPane getTablePanel() {
		return tablePanel;
	}


	public void setTablePanel(JScrollPane tablePanel) {
		this.tablePanel = tablePanel;
	}
	
}
