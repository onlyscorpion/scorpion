package org.scorpion.api.kernel;

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
public interface ITscpSystemIocManager {
	
	/**
	 * @param serviceName
	 * 
	 * @throws TscpBaseException
	 */
    public void getBeanByServiceName(String serviceName)throws TscpBaseException;
    
    
    /**
     * @param serviceName
     * 
     * @param clazz
     * 
     * @return
     * 
     * @throws TscpBaseException
     */
    public <T> T getBeanByServiceName(String serviceName,Class<T> clazz)throws TscpBaseException;
	 
	 /**
	  * @description find class by class Type
	  * 
	  * @param clazz
	  * 
	  * @return
	  * 
	  * @throws TscpBaseException
	  */
	 public <T> T getBeanByClassType(Class<T> clazz) throws TscpBaseException;
	 
	 /**
	  * @description find class by class name
	  * 
	  * @param name
	  * 
	  * @return
	  * 
	  * @throws TscpBaseException
	  */
	 public <T> T getBeanByClassName(String name) throws TscpBaseException;
	 
	 /**
	  * 
	  * @param name
	  * 
	  * @param clazz
	  * 
	  * @return
	  * 
	  * @throws TscpBaseException
	  */
	 public <T> T getBeanByClassAndName(String name,Class<T> clazz) throws TscpBaseException;

}
