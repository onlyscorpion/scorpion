package org.scorpion.api.kernel;

import org.scorpion.api.configuration.TscpSystemScanInfo.ServiceInfo;
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
public abstract class AbsServiceContainerProxy implements ITscpServiceProxy{
	
	
	private static final long serialVersionUID = 4362072895703806476L;

	protected Object obj;
	
	protected Object[] parameters;
	
	protected Class<?> clazz;
	
	protected ServiceInfo serviceInfo;
	
	protected ITscpSystemIocManager iocManager;
	
	
	/**
	 * @param serviceName
	 * 
	 * @param serviceInfo
	 * 
	 * @param args
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
    public Object callService(String serviceName,ServiceInfo serviceInfo,ITscpSystemIocManager iocManager,Object... args) throws TscpBaseException{
    	
    	if(serviceInfo == null)
    		throw new TscpBaseException("TSCP-9754:调用服务信息不存在");
    	
    	if((args == null&&serviceInfo.getParameterTypes().length == 1)||(serviceInfo.getParameterTypes().length == 1&&serviceInfo.getParameterTypes()[0].isAssignableFrom(args.getClass()))){
    		parameters =new Object[]{args};
    
    	}else if(args != null&&args.length == 0&&serviceInfo.getParameterTypes() == null){
    		parameters = null;	
    
    	}else if((args == null&&serviceInfo.getParameterTypes().length>1)||(args.length != serviceInfo.getParameterTypes().length)){
    		throw new TscpBaseException("TSCP-9075:调用服务["+serviceName+"]传入参数和方法参数不匹配!");
  
    	}else{
    		parameters = args;
    	}
    	
    	this.serviceInfo = serviceInfo;
    	this.iocManager = iocManager;
    	
    	return invokeService();
    	
    }
	
    
    
    
    

}
