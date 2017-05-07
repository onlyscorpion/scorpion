package org.scorpion.api.kernel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: if developer want to create a component. the developer must extends the abstract </p>
 * <p>class ATscpComponet. the ATscpComponent exist life cycle. developer can override</p>
 * <p>the initialization method or service method or destroy method to handle themselves business</p>
 * <p>but we don't suggest the developer do that </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsTscpPlatformatServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected String[] basePath;
	
	private String BASE_PATH = "basePath";

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		if(config.getInitParameter(BASE_PATH)==null||"".equals(config.getInitParameter(BASE_PATH)))
			basePath = new String[]{AbsTscpPlatformatServlet.class.getResource("/").getPath().substring(1, AbsTscpPlatformatServlet.class.getResource("/").getPath().length())};
		else
			basePath = config.getInitParameter(BASE_PATH).split(",");
		
		startPlatform();
	}
	
	 public abstract void startPlatform() throws ServletException;

}
