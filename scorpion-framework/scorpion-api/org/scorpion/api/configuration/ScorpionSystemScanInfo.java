package org.scorpion.api.configuration;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.scorpion.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionSystemScanInfo {
	
	private AtomicInteger jarNum;
	private AtomicInteger classNum;
	private AtomicInteger serviceNum;
	private AtomicInteger actionNum;
	private AtomicInteger componentNum;
	private Map<String,ServiceInfo> servicePool;
	private Map<String,ActionInfo> actionPool;
	private Map<String,ComponentInformation>components;
	private Map<String,AopPointcutInfo> pointcutInfos;
	
	public Map<String, AopPointcutInfo> getPointcutInfos() {
		if(pointcutInfos == null)
			pointcutInfos = new HashMap<String,AopPointcutInfo>();
		return pointcutInfos;
	}

	public class ServiceInfo{
		
		private String serviceName;
		private String jarName;
		private Method method;
		private Class<?> returnType;
		private Class<?>[] parameterTypes;
		private Class<?> clazz;
		private boolean isSingleton;
		private String description;

		public String getServiceName() {
			return serviceName;
		}
		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}
		public Method getMethod() {
			return method;
		}
		public void setMethod(Method method) {
			this.method = method;
		}
		public Class<?> getClazz() {
			return clazz;
		}
		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}
		public boolean isSingleton() {
			return isSingleton;
		}
		public void setSingleton(boolean isSingleton) {
			this.isSingleton = isSingleton;
		}
		public Class<?> getReturnType() {
			return returnType;
		}
		public void setReturnType(Class<?> returnType) {
			this.returnType = returnType;
		}
		public Class<?>[] getParameterTypes() {
			return parameterTypes;
		}
		public String[] getParameterTypeName(){
			if(parameterTypes == null)
				return null;
			
			String[] parameterTypeName = new String[parameterTypes.length];
			for(int i = 0;i < parameterTypes.length;i++)
				parameterTypeName[i] = parameterTypes[i].getName();
			
			return parameterTypeName;
		}
		
		public void setParameterTypes(Class<?>[] parameterTypes) {
			this.parameterTypes = parameterTypes;
		}
		public String getJarName() {
			return jarName;
		}
		public void setJarName(String jarName) {
			this.jarName = jarName;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
	}
	
	public class ActionInfo{
		private String actionName;
		private String jarName;
		private Class<?> clazz;
	    private Map<String,Method>methods;
	    private boolean isSingle;
		
		public String getActionName() {
			return actionName;
		}
		public void setActionName(String actionName) {
			this.actionName = actionName;
		}
		public Class<?> getClazz() {
			return clazz;
		}
		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
	    }
		public String getJarName() {
			return jarName;
		}
		public void setJarName(String jarName) {
			this.jarName = jarName;
		}
		public Map<String, Method> getMethods() {
			if(methods == null)
				methods = new HashMap<String,Method>();
			
			return methods;
		}
		public boolean isSingle() {
			return isSingle;
		}
		public void setSingle(boolean isSingle) {
			this.isSingle = isSingle;
		}
	}
	
/*	public class ComponentInfo{
		private String name;
		
		private String jarName;
		
		private AbsScorpionComponent component;
		
		private Map<String,String>arguments;
		
		private boolean isPersisComponet;
		
		private boolean isAlreadyLoad;
		
		private Class<?>clazz;
	
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public Map<String, String> getArguments() {
			return arguments;
		}
		
		public void setArguments(Map<String, String> arguments) {
			this.arguments = arguments;
		}
		
		public Class<?> getClazz() {
			return clazz;
		}
		
		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}
		
		public boolean isPersisComponet() {
			return isPersisComponet;
		}
		
		public void setPersisComponet(boolean isPersisComponet) {
			this.isPersisComponet = isPersisComponet;
		}

		public boolean isAlreadyLoad() {
			return isAlreadyLoad;
		}

		public void setAlreadyLoad(boolean isAlreadyLoad) {
			this.isAlreadyLoad = isAlreadyLoad;
		}

		public String getJarName() {
			return jarName;
		}

		public void setJarName(String jarName) {
			this.jarName = jarName;
		}

		public int getComponentStatue() throws ScorpionBaseException {
	
			if(component != null)
				throw new ScorpionBaseException("scorpion-6894:组件["+this.getName()+"]未启动！");
		
			return component.getComponentstate();
		}

		public void setComponentStatue(int componentStatue) throws ScorpionBaseException {
			
			if(component != null)
				throw new ScorpionBaseException("scorpion-6894:组件["+this.getName()+"]未启动！");
			
			component.setComponentstate(componentStatue);;
		}

		public AbsScorpionComponent getComponents() {
			return component;
		}

		public void setComponent(AbsScorpionComponent component) {
			this.component = component;
		}
		
	}*/
	
	
	/**
	 * @param serviceName
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public ServiceInfo getService(String serviceName)throws ScorpionBaseException{
		  if(!servicePool.containsKey(serviceName))
			  throw new ScorpionBaseException("TSC-9906:调用服务["+serviceName+"]不存在!");
		  return servicePool.get(serviceName);
	}
	

	/**
	 * @param actionName
	 * @return
	 * @throws ScorpionBaseException
	 */
	public ActionInfo getActionByName(String actionName)throws ScorpionBaseException{
		  if(!actionPool.containsKey(actionName))
			  throw new ScorpionBaseException("TSC-9907:调用Action不存在!");
		  return actionPool.get(actionName);
	}
	
	
	
	/**
	 * @param actionName
	 * @param methodName
	 * @param paramNum
	 * @return
	 * @throws ScorpionBaseException 
	 */
	public Method getActionMethod(String actionName,String methodName) throws ScorpionBaseException{
		
		if(actionPool.get(actionName) == null||actionPool.get(actionName).getMethods().get(methodName) == null)
			throw new ScorpionBaseException("scorpion-6009:ACTION["+methodName+"]不存在，检查ACTION名称是否正确!");
		 
		return actionPool.get(actionName).getMethods().get(methodName);
	}
	
	public Map<String, ServiceInfo> getServicePool() {
		if(servicePool == null)
			servicePool = new HashMap<String,ServiceInfo>();
		return servicePool;
	}
	
	public void setServicePool(Map<String, ServiceInfo> servicePool) {
		this.servicePool = servicePool;
	}
	
	public AtomicInteger getJarNum() {
		if(jarNum == null)
			jarNum = new AtomicInteger(0);
		return jarNum;
	}
	
	public void setJarNum(AtomicInteger jarNum) {
		this.jarNum = jarNum;
	}
	
	public AtomicInteger getClassNum() {
		if(classNum == null)
			classNum = new AtomicInteger(0);
		return classNum;
	}
	
	public void setClassNum(AtomicInteger classNum) {
		this.classNum = classNum;
	}
	
	public AtomicInteger getServiceNum() {
		if(serviceNum == null)
			serviceNum = new AtomicInteger(0);
		return serviceNum;
	}
	
	public void setServiceNum(AtomicInteger serviceNum) {
		this.serviceNum = serviceNum;
	}
	
	public AtomicInteger getActionNum() {
		if(actionNum == null)
			actionNum = new AtomicInteger(0);
		return actionNum;
	}
	
	public void setActionNum(AtomicInteger actionNum) {
		this.actionNum = actionNum;
	}
	
	public Map<String, ActionInfo> getActionPool() {
		if(actionPool == null)
			actionPool = new HashMap<String,ActionInfo>();
		return actionPool;
	}
	
	public void setActionPool(Map<String, ActionInfo> actionPool) {
		this.actionPool = actionPool;
	}

	public AtomicInteger getComponentNum() {
		return componentNum;
	}

	public void setComponentNum(AtomicInteger componentNum) {
		this.componentNum = componentNum;
	}

	public Map<String, ComponentInformation> getComponents() {
		if(components == null)
			components = new HashMap<String,ComponentInformation>();
		return components;
	}
	
	public ComponentInformation getComponentByName(String component){
		return this.components.get(component);
	}

}
