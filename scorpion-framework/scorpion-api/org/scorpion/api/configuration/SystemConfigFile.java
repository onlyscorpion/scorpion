package org.scorpion.api.configuration;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
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
