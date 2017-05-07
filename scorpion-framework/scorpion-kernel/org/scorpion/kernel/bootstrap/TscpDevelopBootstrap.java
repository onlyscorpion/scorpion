package org.scorpion.kernel.bootstrap;

import org.apache.tomcat.util.DomUtil;
import org.scorpion.api.exception.TscpBaseException;

/**
 * 自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.tscp.common
 * <p>
 * File: AbsTscpFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 taiji.com.cn
 * </p>
 * <p>
 * Company: taiji.com.cn
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpDevelopBootstrap {

	private static ContainerServer server;

	private String appDir;

	private int port;

	private String welcomePageUrl;

	private TscpDevelopBootstrap startServer() {

		server = new ContainerServer();

		server.startContainer(appDir, port, welcomePageUrl, null);

		return this;

	}

	private TscpDevelopBootstrap initServer() throws TscpBaseException {

		appDir = System.getProperty("app.dir");
		String port = System.getProperty("port");
		String shutdown_port = System.getProperty("shutdown.port");
		String connector_port = System.getProperty("connector.port");
		String virDir = System.getProperty("virDir");
		this.welcomePageUrl = System.getProperty("welcome.page");

		if (appDir == null || "".equals(appDir.trim()))
			throw new TscpBaseException("TSCP-1003:The system root path don't be setted !");

		DomUtil.BASE_DIR = this.appDir;
		
		
		try {

			if (port == null || "".equals(port))
				this.port = 80;
			else
				this.port = Integer.parseInt(port);
			
			if(shutdown_port != null&&!"".equals(shutdown_port.trim()))
				DomUtil.service_shutdown_port = shutdown_port;
			
			if(virDir != null)
				DomUtil.TOMCAT_VIR_PATH = virDir;
			
			if(connector_port != null&&!"".equals(connector_port.trim()))
				DomUtil.connectorPort = Integer.parseInt(connector_port);

			DomUtil.port = this.port;
			DomUtil.url = this.welcomePageUrl;
		
		} catch (NumberFormatException e) {
			throw new TscpBaseException("TSCP-8750:The port must be number !",e);
		}

		return this;

	}

	/**
	 * 
	 * @throws TscpBaseException
	 */
	public static void serverBoot() throws TscpBaseException {

		new TscpDevelopBootstrap().initServer().startServer();

	}

	public static void main(String[] args) throws TscpBaseException {

		new TscpDevelopBootstrap().initServer().startServer();

	}
}
