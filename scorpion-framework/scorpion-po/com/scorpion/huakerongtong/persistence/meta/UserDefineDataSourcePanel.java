package com.scorpion.huakerongtong.persistence.meta;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserDefineDataSourcePanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public UserDefineDataSourcePanel() {
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel temp1 = new JLabel("        ");
		JLabel temp2 = new JLabel("        ");
		JLabel usernameLabel = new JLabel("用户名：");
		JLabel passwordLabel = new JLabel("密码：");
		JLabel urlLabel = new JLabel("URL信息：");
		JTextField usernameField = new JTextField(9);
		JTextField passwordField = new JTextField(9);
		JTextField urlField = new JTextField(18);
		this.add(usernameLabel);
		this.add(usernameField);
		this.add(temp1);
		this.add(passwordLabel);
		this.add(passwordField);
		this.add(temp2);
		this.add(urlLabel);
		this.add(urlField);
		
	}

}
