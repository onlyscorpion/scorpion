package org.scorpion.persistence.metadata;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
public class HandlerPanel extends JPanel{

	private static final long serialVersionUID = -6115251233586495429L;

	public HandlerPanel() {
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(685, 69));
        this.setBorder(BorderFactory.createTitledBorder("操作"));
        JLabel packagePathLabel = new JLabel("包路径");
        JTextField packagePathField = new JTextField(8);
        this.add(packagePathLabel);
        this.add(packagePathField);
        
        JLabel exportPathLabel = new JLabel("文件导出路径");
        JTextField exportPathField = new JTextField(8);
        this.add(exportPathLabel);
        this.add(exportPathField);
        
        JLabel tableNameLabel = new JLabel("表名称：");
        JTextField tableNameField = new JTextField(5); 
        this.add(tableNameLabel);
        this.add(tableNameField);
        
        JButton research = new JButton("检索");
        JButton export = new JButton("导出");
        this.add(research);
        
    	JComboBox comboBox=new JComboBox();
    	comboBox.addItem("导出文件");
    	comboBox.addItem("导出JAR");
    	this.add(comboBox);
    	this.add(export);
        
	}
	
	
	
	
	

}
