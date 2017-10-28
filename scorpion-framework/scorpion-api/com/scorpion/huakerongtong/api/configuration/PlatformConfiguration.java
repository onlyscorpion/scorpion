package com.scorpion.huakerongtong.api.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class PlatformConfiguration implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private List<Scanner> scanners;
	
	private String containerEngine;
	
	private String jarexpression;
	
	private String classexpression;
	
	private String[] userDefineLibPath;
	
	
	public String getContainerEngine() {
		return containerEngine;
	}

	public void setContainerEngine(String containerEngine) {
		this.containerEngine = containerEngine;
	}

	public class Scanner{
		
		private String name;
		
		private String clazz;
		
		private String memo;
		
		private List<Map<String,String>> analyzers;

		public List<Map<String, String>> getAnalyzers() {
			if(analyzers == null)
				analyzers = new ArrayList<Map<String,String>>();
			return analyzers;
		}
		public void setAnalyzers(List<Map<String, String>> analyzers) {
			this.analyzers = analyzers;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getClazz() {
			return clazz;
		}
		public void setClazz(String clazz) {
			this.clazz = clazz;
		}
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
	}
	
	

	public List<Scanner> getScanners() {
		if(scanners == null)
			scanners = new ArrayList<Scanner>();
		return scanners;
	}

	public void setScanners(List<Scanner> scanners) {
		this.scanners = scanners;
	}

	public String getJarexpression() {
		return jarexpression;
	}

	public void setJarexpression(String jarexpression) {
		this.jarexpression = jarexpression;
	}

	public String getClassexpression() {
		return classexpression;
	}

	public void setClassexpression(String classexpression) {
		this.classexpression = classexpression;
	}

	public String[] getUserDefineLibPath() {
		return userDefineLibPath;
	}

	public void setUserDefineLibPath(String[] userDefineLibPath) {
		this.userDefineLibPath = userDefineLibPath;
	}
}

