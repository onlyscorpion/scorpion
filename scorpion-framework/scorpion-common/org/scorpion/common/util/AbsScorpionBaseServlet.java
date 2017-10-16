package org.scorpion.common.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.common.util.ScorpionSystemSessionUtils;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsScorpionBaseServlet extends HttpServlet{

	private static final long serialVersionUID = 696039575386095296L;

	/**
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected abstract void ScorpionDoGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException;
	
	
	/**
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected abstract void ScorpionDoPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException;

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
	
		try {
			ScorpionSystemSessionUtils.createOrinsteadOfSession(req);
			ScorpionDoGet(req,resp);
		} catch (ScorpionBaseException e) {
			throw new ServletException(e);
		}finally{
			ScorpionSystemSessionUtils.clear();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {

		try {
			ScorpionSystemSessionUtils.createOrinsteadOfSession(req);
			ScorpionDoPost(req,resp);
		} catch (ScorpionBaseException e) {
			throw new ServletException(e);
		}finally{
			ScorpionSystemSessionUtils.clear();
		}
	}
	

}
