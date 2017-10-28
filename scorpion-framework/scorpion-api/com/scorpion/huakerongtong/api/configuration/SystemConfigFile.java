package com.scorpion.huakerongtong.api.configuration;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class SystemConfigFile implements Serializable{
	
    	private static final long serialVersionUID = 1L;

		private File coreConfigFile;
		
		private File dataSourceFile;
		
		private List<File> userDefaultExceptionPropertiesFiles;
		
		private List<File> sqlConfigFiles;
		
		private File logConfigFile;

		public File getCoreConfigFile() {
			return coreConfigFile;
		}

		public void setCoreConfigFile(File coreConfigFile) {
			this.coreConfigFile = coreConfigFile;
		}

		public File getDataSourceFile() {
			return dataSourceFile;
		}

		public void setDataSourceFile(File dataSourceFile) {
			this.dataSourceFile = dataSourceFile;
		}

		public List<File> getUserDefaultExceptionPropertiesFiles() {
			if(userDefaultExceptionPropertiesFiles == null)
				userDefaultExceptionPropertiesFiles = new ArrayList<File>();
			return userDefaultExceptionPropertiesFiles ;
		}

		public void setUserDefaultExceptionPropertiesFiles(
				List<File> userDefaultExceptionPropertiesFiles) {
			this.userDefaultExceptionPropertiesFiles = userDefaultExceptionPropertiesFiles;
		}

		public List<File> getSqlConfigFiles() {
			return sqlConfigFiles;
		}

		public void setSqlConfigFiles(List<File> sqlConfigFiles) {
			this.sqlConfigFiles = sqlConfigFiles;
		}

		public File getLogConfigFile() {
			return logConfigFile;
		}

		public void setLogConfigFile(File logConfigFile) {
			this.logConfigFile = logConfigFile;
		}

}
