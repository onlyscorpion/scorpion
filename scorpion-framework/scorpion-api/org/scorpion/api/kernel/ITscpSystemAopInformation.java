package org.scorpion.api.kernel;

import java.util.List;
import java.util.Map;

import org.scorpion.api.configuration.AopPointcutInfo;
import org.scorpion.api.exception.TscpBaseException;

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
public interface ITscpSystemAopInformation {
	
	
	/**
	 * 获取系统的Advice信息
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public Map<String,ITscpAopAfterAdvice> getSystemAdvices() throws TscpBaseException;
	
	
	/**
	 * 获取系统point cut 信息
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public List<AopPointcutInfo> getSystemAopPointCut() throws TscpBaseException;

}
