package org.scorpion.kernel.bootstrap;

import javax.servlet.ServletException;

import org.scorpion.api.kernel.AbsTscpPlatformatServlet;

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
public class PlatformatServlet extends AbsTscpPlatformatServlet {

	private static final long serialVersionUID = -4857691775199717119L;

	@Override
	public void startPlatform() throws ServletException {

		try {
			TscpPlatformatManager.main(new String[] {});
		} catch (Exception e) {
			throw new ServletException("TSCP-1009:系统启动失败!", e);
		}

	}

}
