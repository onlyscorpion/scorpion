package org.scorpion.persistence.meta;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

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
public class ConfigurationWindow extends JFrame{

	private static final long serialVersionUID = 4541255959990348508L;
	
	private JRadioButton userSystemConfig;
	private JRadioButton configByYourself;
	private List<String> dataSourceList;
	

	/**
	 * 
	 * @param title
	 * 
	 * @throws HeadlessException
	 */
	public ConfigurationWindow(String title) throws HeadlessException {
		super(title);
		
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image image=tk.createImage(this.getClass().getResource("/META-INF/resources/tj.gif")); 
		this.setIconImage(image); 
		this.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-350,
			(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-250, 700, 500);
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		if(System.getProperty("user.web.env") == null)
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}
	

	public JRadioButton getUserSystemConfig() {
		return userSystemConfig;
	}


	public void setUserSystemConfig(JRadioButton userSystemConfig) {
		this.userSystemConfig = userSystemConfig;
	}


	public JRadioButton getConfigByYourself() {
		return configByYourself;
	}


	public void setConfigByYourself(JRadioButton configByYourself) {
		this.configByYourself = configByYourself;
	}


	public List<String> getDataSourceList() {
		return dataSourceList;
	}

	
	public void setDataSourceList(List<String> dataSourceList) {
		this.dataSourceList = dataSourceList;
	}
	
}
