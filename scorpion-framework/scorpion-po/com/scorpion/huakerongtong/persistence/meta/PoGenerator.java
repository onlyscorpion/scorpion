package com.scorpion.huakerongtong.persistence.meta;


import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

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
public class PoGenerator {
	
	
	 JProgressBar jpbFileLoading ;
	 JProgressBar jpbFileLoadingIndeterminate ;
	 JFrame frame; 
	 JPanel title;
	 JPanel panel ;
	
	public void generatorPo(){
		
		ConfigurationWindow cfg = new ConfigurationWindow("scorpion-PO生成器");
		JPanel dsconfiguration = new DataSourceConfigurationPanel();
		cfg.add(dsconfiguration);
		initp();
		JPanel handlePanel = new HandlerPanel(cfg,jpbFileLoading,jpbFileLoadingIndeterminate,title,panel,frame);
		cfg.add(handlePanel);
        
		JScrollPane tablePanel = new DataTablePanel();
		cfg.add(tablePanel);
		((DataSourceConfigurationPanel)dsconfiguration).setDataTablePanel(tablePanel);
		
		cfg.validate();
		cfg.repaint();
		
		
	}
	
	
	public void initp(){
		
	    jpbFileLoading = new JProgressBar();
		jpbFileLoading.setStringPainted(true); // 设置进度条呈现进度字符串,默认为false
		jpbFileLoading.setBorderPainted(false); // 不绘制边框,默认为true
		jpbFileLoading.setVisible(true);
		jpbFileLoading.setValue(0);
		frame = new MyJDialog();
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		title = new JPanel();
		title.add(new JLabel("正在获取数据库表信息...."));
		panel.add(title,BorderLayout.NORTH);
		panel.add(jpbFileLoading,BorderLayout.CENTER);
		frame.add(panel);
	    jpbFileLoadingIndeterminate = new JProgressBar(); 
		jpbFileLoadingIndeterminate.setIndeterminate(true); //设置进度条为不确定模式,默认为确定模式  
		jpbFileLoadingIndeterminate.setStringPainted(true); 
		jpbFileLoadingIndeterminate.setString("正在获取数据库信息......");
		panel.add(jpbFileLoadingIndeterminate,BorderLayout.SOUTH);
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		panel.validate();
		panel.repaint();
		frame.repaint();
		frame.validate();
	}
	
	
	public static void main(String[] args) {
		
		new PoGenerator().generatorPo();
		
	}
	
	
	public static void freeMakerHandler(){
		
	}
	

}
