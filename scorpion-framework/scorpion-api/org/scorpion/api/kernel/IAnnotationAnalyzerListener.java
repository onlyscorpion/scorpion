package org.scorpion.api.kernel;

import org.scorpion.api.exception.ScorpionBaseException;

/**
*  天蝎平台架构(SCORPION Security Controllable Platform)
* <p>com.SCORPION.Scorpion.common
* <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
* <p>Title: abstract factory class </p>
* <p>Description: if developer want to create a component. the developer must extends the abstract </p>
* <p>class AScorpionComponet. the AScorpionComponent exist life cycle. developer can override</p>
* <p>the initialization method or service method or destroy method to handle themselves business</p>
* <p>but we don't suggest the developer do that </p>
* <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
* <p>Company: SCORPION.COM.CN</p>
* <p>module: common abstract class</p>
* @author  郑承磊
* @version 1.0
* @history 修订历史（历次修订内容、修订人、修订时间等）
*/
public interface IAnnotationAnalyzerListener {
	   
   /**
	 * Creates a URL from the specified <code>protocol</code>
	 * name, <code>host</code> name, and <code>file</code> name. The
	 * default port for the specified protocol is used.
	 * <p>
	 * This method is equivalent to calling the four-argument
	 * constructor with the arguments being <code>protocol</code>,
	 * <code>host</code>, <code>-1</code>, and <code>file</code>.
	 *
	 * No validation of the inputs is performed by this constructor.
	 *
	 * @param      clazz   the name of the protocol to use.
	 * @exception  ScorpionBaseException  
	 * @see        java.net.URL#URL(java.lang.String, java.lang.String,
	 *			int, java.lang.String)
	 */
    public void analyse(Class<?> clazz,String jarName) throws ScorpionBaseException;
	

}
