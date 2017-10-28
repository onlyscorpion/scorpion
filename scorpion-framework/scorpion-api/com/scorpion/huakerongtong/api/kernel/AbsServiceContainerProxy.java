package com.scorpion.huakerongtong.api.kernel;

import com.scorpion.huakerongtong.api.configuration.ScorpionSystemScanInfo.ServiceInfo;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

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
public abstract class AbsServiceContainerProxy implements IScorpionServiceProxy{
	
	
	private static final long serialVersionUID = 4362072895703806476L;

	protected Object obj;
	
	protected Object[] parameters;
	
	protected Class<?> clazz;
	
	protected ServiceInfo serviceInfo;
	
	protected IScorpionSystemIocManager iocManager;
	
	
	/**
	 * @param serviceName
	 * 
	 * @param serviceInfo
	 * 
	 * @param args
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
    public Object callService(String serviceName,ServiceInfo serviceInfo,IScorpionSystemIocManager iocManager,Object... args) throws ScorpionBaseException{
    	
    	if(serviceInfo == null)
    		throw new ScorpionBaseException("scorpion-9754:调用服务信息不存在");
    	
    	if((args == null&&serviceInfo.getParameterTypes().length == 1)||(serviceInfo.getParameterTypes().length == 1&&serviceInfo.getParameterTypes()[0].isAssignableFrom(args.getClass()))){
    		parameters =new Object[]{args};
    
    	}else if(args != null&&args.length == 0&&serviceInfo.getParameterTypes() == null){
    		parameters = null;	
    
    	}else if((args == null&&serviceInfo.getParameterTypes().length>1)||(args.length != serviceInfo.getParameterTypes().length)){
    		throw new ScorpionBaseException("scorpion-9075:调用服务["+serviceName+"]传入参数和方法参数不匹配!");
  
    	}else{
    		parameters = args;
    	}
    	
    	this.serviceInfo = serviceInfo;
    	this.iocManager = iocManager;
    	
    	return invokeService();
    	
    }
	
    
    
    
    

}
