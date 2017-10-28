package com.scorpion.huakerongtong.api.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scorpion.huakerongtong.api.util.Constant;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: store jvm information. Developer can get jvm of the system </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionCoreConfig implements Serializable{
	
	private static final long serialVersionUID = -6363495435299921207L;

	private String nodeName;
	
	private String server;
	
	private String machine;
	
	private String ip;
	
	private long version;
	
	private String systemCharset;
	
	private String loglevel;
	
	private String jmxserver;
	
	private Map<String,String> properties;
	
	private PlatformConfiguration platformconfiguration;
	
	private List<String> bulidPaths;
	
	private String currentSystemStatus;
	
	private String runModel;
	
	private ServiceInfo serviceInfo;
	
	private LogFrameworkInfo logframeworkInfo;
	
	private List<ComponentInformation> components;

	/**
	 * get current system node
	 * @return
	 */
	public String getNodeName() {
		
		return nodeName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}



	/**
	 * setting system node name
	 * @param nodeName
	 */
	public void setNodeName(String nodeName) {
		Constant.SERVER_NODE = nodeName;
		this.nodeName = nodeName;
	}

	public String getCurrentSystemStatus() {
	
		return currentSystemStatus;
	}

	public void setCurrentSystemStatus(String currentSystemStatus) {
		
		this.currentSystemStatus = currentSystemStatus;
	}

	public String getRunModel() {
	
		return runModel;
	}

	public void setRunModel(String runModel) {
	
		this.runModel = runModel;
	}

	public List<ComponentInformation> getComponents() {
		
		if(components == null)
			components = new ArrayList<ComponentInformation>();
		
		return components;
	}

	public void setComponets(List<ComponentInformation> components) {
	
		this.components = components;
	}

	public List<String> getBulidPaths() {
	
		return bulidPaths;
	}

	public void setBulidPaths(List<String> bulidPaths) {
	
		this.bulidPaths = bulidPaths;
	}

	public String getServer() {
		
		return server;
	}

	public void setServer(String server) {
	
		this.server = server;
	}

	public ServiceInfo getServiceInfo() {
		
		if(serviceInfo == null)
			serviceInfo = new ServiceInfo();
		
		return serviceInfo;
	}

	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public String getMachine() {
	
		return machine;
	}
	

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public void setMachine(String machine) {
	
		this.machine = machine;
	}

	public String getSystemCharset() {
	
		return systemCharset;
	}

	public void setSystemCharset(String systemCharset) {
	
		this.systemCharset = systemCharset;
	}

	public String getLoglevel() {
	
		return loglevel;
	}

	public void setLoglevel(String loglevel) {
	
		this.loglevel = loglevel;
	}

	public String getJmxserver() {
	
		return jmxserver;
	}

	public void setJmxserver(String jmxserver) {
	
		this.jmxserver = jmxserver;
	}

	public PlatformConfiguration getPlatformconfiguration() {
	
		if(platformconfiguration == null)
			platformconfiguration = new PlatformConfiguration();
	
		return platformconfiguration;
	}

	public void setPlatformconfiguration(PlatformConfiguration platformconfiguration) {
		
		this.platformconfiguration = platformconfiguration;
	}

	public Map<String, String> getProperties() {
	
		return properties;
	}
	public String getValueByKey(String key) {
		if(properties == null)
			properties = new HashMap<String,String>();
		
		return properties.get(key);
	}

	public void setProperties(Map<String, String> properties) {
	
		this.properties = properties;
	}

	public LogFrameworkInfo getLogframeworkInfo() {
		return logframeworkInfo;
	}

	public void setLogframeworkInfo(LogFrameworkInfo logframeworkInfo) {
		this.logframeworkInfo = logframeworkInfo;
	}
	
	
	public class ServiceInfo implements Serializable{
		
		private static final long serialVersionUID = 5871638082154461973L;

		private boolean expose;
		
		private String protocolType;
		
		private int port;
		
		private String connTimeout;
		
		private String respTimeout;
		
		private String callTimeout;
		
		private String proxyConnTimeout;
		
		private String handsharkTimeout;
		
		private String memo;

		public boolean isExpose() {
			return expose;
		}

		public void setExpose(boolean expose) {
			this.expose = expose;
		}

		public String getProtocolType() {
			return protocolType;
		}

		public void setProtocolType(String protocolType) {
			this.protocolType = protocolType;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}
		

		public String getConnTimeout() {
		
			if(connTimeout == null||"".equals(connTimeout))
				connTimeout = "30000";
		
			return connTimeout;
		}

		public void setConnTimeout(String connTimeout) {
			this.connTimeout = connTimeout;
		}

		public String getRespTimeout() {
			
			if(respTimeout == null||"".equals(respTimeout))
				respTimeout = "10000";
			
			return respTimeout;
		}

		public void setRespTimeout(String respTimeout) {
			this.respTimeout = respTimeout;
		}

		public String getCallTimeout() {
			
			if(callTimeout == null||"".equals(callTimeout))
				callTimeout = "60000";
			
			return callTimeout;
		}

		public void setCallTimeout(String callTimeout) {
			this.callTimeout = callTimeout;
		}

		public String getProxyConnTimeout() {
			
			if(proxyConnTimeout == null||"".equals(proxyConnTimeout))
				proxyConnTimeout = "10000";
			
			return proxyConnTimeout;
		}

		public void setProxyConnTimeout(String proxyConnTimeout) {
			this.proxyConnTimeout = proxyConnTimeout;
		}

		public String getHandsharkTimeout() {
			
			if(handsharkTimeout == null||"".equals(handsharkTimeout))
				handsharkTimeout = "10000";
			
			return handsharkTimeout;
		}

		public void setHandsharkTimeout(String handsharkTimeout) {
			this.handsharkTimeout = handsharkTimeout;
		}


		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}
		
	}
	
	
	public class LogFrameworkInfo{
		
		public String THEME = "theme";
		
		public String HANDLE_CLASS = "class";
		
		private String name;
		
		private boolean isEnable;
		
		private boolean isLocalQueue;
		
		private String brokerName;
		
		private List<Map<String,String>> logs;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isEnable() {
			return isEnable;
		}

		public void setEnable(boolean isEnable) {
			this.isEnable = isEnable;
		}

		public String getBrokerName() {
			return brokerName;
		}

		public void setBrokerName(String brokerName) {
			this.brokerName = brokerName;
		}

		public List<Map<String, String>> getLogs() {
			if(logs == null)
				logs = new ArrayList<Map<String, String>>();
			return logs;
		}

		public void setLogs(List<Map<String, String>> logs) {
			this.logs = logs;
		}

		public boolean isLocalQueue() {
			return isLocalQueue;
		}

		public void setLocalQueue(boolean isLocalQueue) {
			this.isLocalQueue = isLocalQueue;
		}
		
	}
}
