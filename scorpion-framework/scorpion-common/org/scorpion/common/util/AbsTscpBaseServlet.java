package org.scorpion.common.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.common.util.TscpSystemSessionUtils;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsTscpBaseServlet extends HttpServlet{

	private static final long serialVersionUID = 696039575386095296L;

	/**
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected abstract void tscpDoGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException;
	
	
	/**
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected abstract void tscpDoPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException;

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
	
		try {
			TscpSystemSessionUtils.createOrinsteadOfSession(req);
			tscpDoGet(req,resp);
		} catch (TscpBaseException e) {
			throw new ServletException(e);
		}finally{
			TscpSystemSessionUtils.clear();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {

		try {
			TscpSystemSessionUtils.createOrinsteadOfSession(req);
			tscpDoPost(req,resp);
		} catch (TscpBaseException e) {
			throw new ServletException(e);
		}finally{
			TscpSystemSessionUtils.clear();
		}
	}
	

}
