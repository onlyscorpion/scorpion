package org.scorpion.kernel.bootstrap;

import org.apache.tomcat.util.DomUtil;
import org.scorpion.api.exception.ScorpionBaseException;

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
public class ScorpionDevelopBootstrap {

	private static ContainerServer server;

	private String appDir;

	private int port;

	private String welcomePageUrl;

	private ScorpionDevelopBootstrap startServer() {

		server = new ContainerServer();

		server.startContainer(appDir, port, welcomePageUrl, null);

		return this;

	}

	private ScorpionDevelopBootstrap initServer() throws ScorpionBaseException {

		appDir = System.getProperty("app.dir");
		String port = System.getProperty("port");
		String shutdown_port = System.getProperty("shutdown.port");
		String connector_port = System.getProperty("connector.port");
		String virDir = System.getProperty("virDir");
		this.welcomePageUrl = System.getProperty("welcome.page");

		if (appDir == null || "".equals(appDir.trim()))
			throw new ScorpionBaseException("scorpion-1003:The system root path don't be setted !");

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
			throw new ScorpionBaseException("scorpion-8750:The port must be number !",e);
		}

		return this;

	}

	/**
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void serverBoot() throws ScorpionBaseException {

		new ScorpionDevelopBootstrap().initServer().startServer();

	}

	public static void main(String[] args) throws ScorpionBaseException {

		new ScorpionDevelopBootstrap().initServer().startServer();

	}
}
