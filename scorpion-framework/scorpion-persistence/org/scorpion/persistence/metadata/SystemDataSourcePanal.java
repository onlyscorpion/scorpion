package org.scorpion.persistence.metadata;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
public class SystemDataSourcePanal  extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	JComboBox comboBox=new JComboBox();
	
	
	public SystemDataSourcePanal() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(new JLabel("选择数据源："));
		this.add(comboBox);
	}


	public JPanel setData(List<String> dataSourceNames){
		comboBox.removeAllItems();
		for(String ds:dataSourceNames)
			comboBox.addItem(ds);
		return this;
	}
}
