package org.scorpion.persistence.meta;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import org.scorpion.api.configuration.DataSourceLis;
import org.scorpion.api.exception.ScorpionBaseException;

import freemarker.template.TemplateException;

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
 
	private String POCONF = "/conf/po.properites";
	
	public static String PACKAGE_PATH ;
	
	public static String File_PATH;
	
	public static int currentNum;
	
	public static Map<String,String> map = new HashMap<String,String>();
	
	public HandlerPanel(final ConfigurationWindow config,final JProgressBar progressBar,final JProgressBar bar2,final JPanel title,final JPanel panel,final JFrame frame){
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(685, 69));
        this.setBorder(BorderFactory.createTitledBorder("操作"));
        JLabel packagePathLabel = new JLabel("包路径");
    	/*JComboBox comboBox = new JComboBox();
    	comboBox.addItem("导出JAR");
    	comboBox.addItem("导出文件");*/
        JTextField packagePathField = new JTextField(16);
        packagePathField.setText(getJarPath());
        packagePathField.setEditable(false);
        this.add(packagePathLabel);
        this.add(packagePathField);
        
        JLabel exportPathLabel = new JLabel("文件导出路径");
        JTextField exportPathField = new JTextField(16);
        exportPathField.setText(getFilePath());
        exportPathField.setEditable(false);
        this.add(exportPathLabel);
        this.add(exportPathField);
        
     /*   JLabel tableNameLabel = new JLabel("表名称：");
        JTextField tableNameField = new JTextField(5); 
        this.add(tableNameLabel);
        this.add(tableNameField);*/
        
        //JButton research = new JButton("检索");
        JButton export = new JButton("导出");
      //  this.add(research);
        
        export.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processorBarFile(title,panel,frame,progressBar,bar2,config);
	        	HandlerPanel.currentNum = 0;
				if(DBAdapter.currentDS == null||"".equals(DBAdapter.currentDS.trim()))
					JOptionPane.showMessageDialog(null,"数据源信息没有配置");
				
				POHandler handler = new POHandler();
				try {
					handler.process(DataSourceLis.getAllDataSource().get(DBAdapter.currentDS).getUrl(), 
							DataSourceLis.getAllDataSource().get(DBAdapter.currentDS).getUser(),
							DataSourceLis.getAllDataSource().get(DBAdapter.currentDS).getPasswd(),
							DBAdapter.getDbType(DataSourceLis.getAllDataSource().get(DBAdapter.currentDS)),
							getJarPath(), getFilePath());
				} catch (ScorpionBaseException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (TemplateException e1) {
					e1.printStackTrace();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
				
			//	JOptionPane.showMessageDialog(null,"导出成功!");
				
			}
		});
        
    
    //	this.add(comboBox);
    	this.add(export);
        
	}
	
	
	public void processorBarFile(final JPanel title,final JPanel panel,final JFrame frame,final JProgressBar jpbFileLoading,final JProgressBar jpbFileLoadingIndeterminate,ConfigurationWindow config){
	
		frame.setBounds((config.getX()+config.getWidth()/2)-200, (config.getY()+config.getHeight()/2)-50, 400, 100);
		frame.setVisible(true);
		config.validate();
		config.repaint();
	/*	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
				if(DataTableModel.physicalModels == null||DataTableModel.physicalModels.size()==0)
					jpbFileLoading.setValue(0);
				else
					jpbFileLoading.setValue((currentNum*100)/(DataTableModel.physicalModels.size()));
				title.removeAll();
				title.add(new JLabel(map.get("status")));
				jpbFileLoadingIndeterminate.setString(map.get("status"));
				panel.validate();
				panel.repaint();
				frame.validate();
				frame.repaint();
				if(currentNum/DataTableModel.physicalModels.size() == 1){
					jpbFileLoading.setValue(100);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					frame.setVisible(false);
					frame.dispose();
					break;
				}
				}
			}
		}).start();
	}
	
	

	private String getFilePath() {
	
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
		try {
			properties.load(new FileInputStream(configFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties.getProperty("packagePath");

	}

}
