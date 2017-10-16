package org.scorpion.persistence.meta;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import org.scorpion.api.configuration.DataSourceLis;
import org.scorpion.api.configuration.DataSourceLis.DataSourceInfo;
import org.scorpion.api.exception.ScorpionBaseException;

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

public class DataSourceConfigurationPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private UserDefineDataSourcePanel userDefine = new UserDefineDataSourcePanel();
	private SystemDataSourcePanal systemPanel = new SystemDataSourcePanal();
	private JRadioButton systemConfig;
	private JRadioButton configByYourself;
	

	public DataSourceConfigurationPanel() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		try{
		this.setPreferredSize(new Dimension(685, 100));
		this.setBorder(BorderFactory.createTitledBorder("数据源设置"));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("设置数据源信息");
		JLabel block = new JLabel("                     ");
		JLabel block2 = new JLabel("                     ");
		JLabel block3 = new JLabel("                    ");
		ButtonGroup group = new ButtonGroup();
		systemConfig = new JRadioButton("使用系统配置数据源");
		systemConfig.addActionListener(this);
		configByYourself = new JRadioButton("自定义配置数据源");
		configByYourself.addActionListener(this);
		systemConfig.setSelected(true);
		group.add(systemConfig);
		group.add(configByYourself);
		this.add(label);
		this.add(block);
		this.add(systemConfig);
		this.add(block2);
		this.add(configByYourself);
		this.add(block3);
		List<String> datasources = getDataSourceLis();
		if(datasources.size()>0)
			initPysiModel(datasources.get(0));
				
		DBAdapter.currentDS = datasources.get(0);
		systemPanel.setData(datasources);
		this.add(systemPanel);
		this.remove(userDefine);
		this.validate();
		this.repaint();
		}catch(Throwable e){
			JOptionPane.showMessageDialog(null, ThrowableUtil.e2s(e), "错误提示", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	public void setDataTablePanel(JScrollPane tablePanel){
		systemPanel.setTablePanel(tablePanel);
	}
	

	private void initPysiModel(String dataSourceName) throws Throwable{
		
		List<String> tables = DBAdapter.getTables(DataSourceLis.getAllDataSource().get(dataSourceName));
		if(DataTableModel.physicalModels == null)
			DataTableModel.physicalModels= new ArrayList<PhysicalModel>();
		for(String name:tables){
			PhysicalModel m = new PhysicalModel();
			m.setTableName(name);
			DataTableModel.physicalModels.add(m);
		}
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == systemConfig){
			this.add(systemPanel);
			this.remove(userDefine);
			this.validate();
			this.repaint();
		}else if(e.getSource() == configByYourself){
			this.remove(systemPanel);
			this.add(userDefine);
			this.validate();
			this.repaint();
		}
	}
	
	
	/**
	 * @return
	 * @throws ScorpionBaseException
	 */
	public List<String> getDataSourceLis() throws ScorpionBaseException{
		List<String> datasourceName = new ArrayList<String>();
		Configuration.loadConfigurationByScorpionDefaultSetting();
		if(DataSourceLis.getDefaultDataSource()!=null)
			datasourceName.add(DataSourceLis.DEFAULT_DATASOURCE);
		if(DataSourceLis.getOtherDataSource() == null)
			return datasourceName;
		for(DataSourceInfo datasourceinfo:DataSourceLis.getOtherDataSource()){
			datasourceName.add(datasourceinfo.getName());
		}
		return datasourceName;
	}

}
