package org.scorpion.kernel.bootstrap;

import org.apache.catalina.startup.ServerBoot;
import org.scorpion.api.log.PlatformLogger;

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
public class ContainerServer {

	private static ServerBoot daemon;

	/**
	 * @Description 启动容器
	 * 
	 * @param baseDir
	 * 
	 * @param port
	 * 
	 * @param args
	 */
	public void startContainer(String baseDir, int port, String welcomePageUrl, String[] args) {

		if (daemon == null) {
			daemon = new ServerBoot(baseDir, port);
			try {
				daemon.init();
			} catch (Throwable t) {
				PlatformLogger.error("scorpion-1001:容器初始化失败", t);
				return;
			}
		}

		try {
			String command = "start";
			if (command.equals("startd")) {
				args[args.length - 1] = "start";
				daemon.load(args);
				daemon.start();
			} else if (command.equals("stopd")) {
				args[args.length - 1] = "stop";
				daemon.stop();
			} else if (command.equals("start")) {
				daemon.setAwait(true);
				daemon.load(args);
				daemon.start();
			} else if (command.equals("stop")) {
				daemon.stopServer(args);
			} else {
				PlatformLogger.warn("Bootstrap: command \"" + command+ "\" does not exist.");
			}

		} catch (Throwable t) {
			PlatformLogger.error("scorpion-1002:容器启动失败", t);
		}
		
	}

}
