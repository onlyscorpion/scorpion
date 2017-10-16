package org.scorpion.kernel.route;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.scorpion.api.common.AbsProtocol;
import org.scorpion.api.common.IScorpionProtocal.ProtocolType;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.kernel.media.ScorpionEJBSenderMediationPool;
import org.scorpion.kernel.media.ScorpionJMSSenderMediationPool;
import org.scorpion.kernel.media.ScorpionRMSenderMediationPool;
import org.scorpion.kernel.media.ScorpionWSSenderMediationPool;

/**
 * 天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>
 * com.SCORPION.Scorpion.common
 * <p>
 * File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 SCORPION.COM.CN
 * </p>
 * <p>
 * Company: SCORPION.COM.CN
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ProtocolConf {

	/** ejb protocol configuration information **/
	private Map<String, EJBProtocolConf> ejbProtocolConf;

	private Map<String, JMSProtocolConf> jmsProtocolConf;

	private Map<String, WSProtocolConf> wsProtocolConf;

	private Map<String, ServiceConf> serviceConf;

	private Map<String, TMIConf> tmiConf;

	private ProtocolConf() {
		super();
	}

	/**
	 * @description
	 * 
	 * @param key
	 * 
	 * @return
	 */
	public boolean containsKey(String key) {

		if (serviceConf != null && serviceConf.containsKey(key))
			return true;

		return false;
	}

	public Map<String, ServiceConf> getServiceConf() {

		if (serviceConf == null)
			serviceConf = new HashMap<String, ServiceConf>();

		return serviceConf;
	}

	public Map<String, TMIConf> getTmiConf() {

		if (tmiConf == null)
			tmiConf = new HashMap<String, ProtocolConf.TMIConf>();

		return tmiConf;
	}

	public void setTmiConf(Map<String, TMIConf> tmiConf) {
		this.tmiConf = tmiConf;
	}

	public void setServiceConf(Map<String, ServiceConf> serviceConf) {
		this.serviceConf = serviceConf;
	}

	public static ProtocolConf getProtocolConf() {
		return protocolConf;
	}

	public static void setProtocolConf(ProtocolConf protocolConf) {
		ProtocolConf.protocolConf = protocolConf;
	}

	public Map<String, EJBProtocolConf> getEjbProtocolConf() {

		if (ejbProtocolConf == null)
			ejbProtocolConf = new HashMap<String, EJBProtocolConf>();

		return ejbProtocolConf;
	}

	public void setEjbProtocolConf(Map<String, EJBProtocolConf> ejbProtocolConf) {
		this.ejbProtocolConf = ejbProtocolConf;
	}

	public Map<String, JMSProtocolConf> getJmsProtocolConf() {

		if (jmsProtocolConf == null)
			jmsProtocolConf = new HashMap<String, JMSProtocolConf>();

		return jmsProtocolConf;
	}

	public void setJmsProtocolConf(Map<String, JMSProtocolConf> jmsProtocolConf) {
		this.jmsProtocolConf = jmsProtocolConf;
	}

	public Map<String, WSProtocolConf> getWsProtocolConf() {

		if (wsProtocolConf == null)
			wsProtocolConf = new HashMap<String, WSProtocolConf>();

		return wsProtocolConf;
	}

	public void setWsProtocolConf(Map<String, WSProtocolConf> wsProtocolConf) {
		this.wsProtocolConf = wsProtocolConf;
	}

	
	/**
	 * @description query protocol type by service name
	 * 
	 * @param serviceName
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public Class<?> getProtocolByServiceName(String serviceName)throws ScorpionBaseException {

		String protocolType = serviceConf.get(serviceName).getProtocolType();

		if (ProtocolType.EJB.name().equals(protocolType)) {
			return ScorpionEJBSenderMediationPool.class;
		} else if (ProtocolType.JMS.name().equals(protocolType)) {
			return ScorpionJMSSenderMediationPool.class;
		} else if (ProtocolType.WEBSERVICE.name().equals(protocolType)) {
			return ScorpionWSSenderMediationPool.class;
		} else if (ProtocolType.TMI.name().equals(protocolType)) {
			return ScorpionRMSenderMediationPool.class;
		} else {
			throw new ScorpionBaseException("scorpion-6798：Can't find the service ["+ serviceName + "]mapping protocol type !");
		}

	}

	/**
	 * 
	 * @author zcl
	 *
	 */
	public class TMIConf extends AbsProtocol implements Serializable {

		private static final long serialVersionUID = -7129847153493952833L;

		private String protocolId;

		private String provideURL;

		@Override
		public String getProtocolId() {
			return this.protocolId;
		}

		public void setProtocolId(String protocolId) {
			this.protocolId = protocolId;
		}

		public String getProvideURL() {
			return provideURL;
		}

		public void setProvideURL(String provideURL) {
			this.provideURL = provideURL;
		}

	}

	public class RouteConf {

		private String RouteId;

		private String currentNode;

		private String nextNode;

		private String protocolId;

		public String getRouteId() {
			return RouteId;
		}

		public void setRouteId(String routeId) {
			RouteId = routeId;
		}

		public String getCurrentNode() {
			return currentNode;
		}

		public void setCurrentNode(String currentNode) {
			this.currentNode = currentNode;
		}

		public String getNextNode() {
			return nextNode;
		}

		public void setNextNode(String nextNode) {
			this.nextNode = nextNode;
		}

		public String getProtocolId() {
			return protocolId;
		}

		public void setProtocolId(String protocolId) {
			this.protocolId = protocolId;
		}

	}

	/**
	 * 
	 * @author zcl
	 *
	 */
	public class ServiceConf implements Serializable {

		private static final long serialVersionUID = -2675813216431344930L;

		private String serviceName;

		private String protocolType;

		private String protocolId;

		private String way;

		public String getProtocolId() {
			return protocolId;
		}

		public void setProtocolId(String protocolId) {
			this.protocolId = protocolId;
		}

		public String getServiceName() {
			return serviceName;
		}

		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}

		public String getProtocolType() {
			return protocolType;
		}

		public void setProtocolType(String protocolType) {
			this.protocolType = protocolType;
		}

		public String getWay() {
			return way;
		}

		public void setWay(String way) {
			this.way = way;
		}

	}

	/**
	 * 
	 * @author zcl
	 *
	 */
	public class EJBProtocolConf extends AbsProtocol implements Serializable {

		private static final long serialVersionUID = -2592212378932069986L;

		private String protocolId;

		private String provideURL;

		private String contextFactory;

		private String ejbName;

		private String username;

		private String password;

		public String getProvideURL() {
			return provideURL;
		}

		public void setProvideURL(String provideURL) {
			this.provideURL = provideURL;
		}

		public String getContextFactory() {
			return contextFactory;
		}

		public void setContextFactory(String contextFactory) {
			this.contextFactory = contextFactory;
		}

		public String getEjbName() {
			return ejbName;
		}

		public void setEjbName(String ejbName) {
			this.ejbName = ejbName;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getProtocolId() {
			return protocolId;
		}

		public void setProtocolId(String protocolId) {
			this.protocolId = protocolId;
		}

	}

	/**
	 * 
	 * @author zcl
	 *
	 */
	public class JMSProtocolConf extends AbsProtocol implements Serializable {

		private static final long serialVersionUID = 6578438953247880176L;

		private String protocolId;

		private String provideURL;

		private String contextFactory;

		private String queueName;

		private String username;

		private String password;

		public String getProvideURL() {
			return provideURL;
		}

		public void setProvideURL(String provideURL) {
			this.provideURL = provideURL;
		}

		public String getContextFactory() {
			return contextFactory;
		}

		public void setContextFactory(String contextFactory) {
			this.contextFactory = contextFactory;
		}

		public String getQueueName() {
			return queueName;
		}

		public void setQueueName(String queueName) {
			this.queueName = queueName;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getProtocolId() {
			return protocolId;
		}

		public void setProtocolId(String protocolId) {
			this.protocolId = protocolId;
		}

	}

	public class WSProtocolConf extends AbsProtocol implements Serializable {

		private static final long serialVersionUID = 1L;

		private String protocolId;

		private String wsdlAddress;

		private String currentNode;

		private String nextNode;
		
		private String method;
		
		
		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getWsdlAddress() {
			return wsdlAddress;
		}

		public void setWsdlAddress(String wsdlAddress) {
			this.wsdlAddress = wsdlAddress;
		}

		public String getCurrentNode() {
			return currentNode;
		}

		public void setCurrentNode(String currentNode) {
			this.currentNode = currentNode;
		}

		public String getNextNode() {
			return nextNode;
		}

		public void setNextNode(String nextNode) {
			this.nextNode = nextNode;
		}

		public String getProtocolId() {
			return protocolId;
		}

		public void setProtocolId(String protocolId) {
			this.protocolId = protocolId;
		}
	}

	/**
	 * 
	 * @return
	 */
	public synchronized static ProtocolConf getInstance() {

		if (protocolConf == null)
			protocolConf = new ProtocolConf();

		return protocolConf;
	}

	private static ProtocolConf protocolConf;
}
