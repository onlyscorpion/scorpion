package com.scorpion.huakerongtong.api.configuration;

import java.io.Serializable;

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
public class AopPointcutInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String jarName;

	private String serviceName;
	
	private String[] methodRegex;
	
	private int argumentNum;
	
	private Class<?> returnType;
	
	private String[] classRegex;
	
	private boolean serviceValidate;
	
	private Class<?> interceptorClass;
	
	
	public String getServiceName() {
	
		return serviceName;
	
	}
	
	public void setServiceName(String serviceName) {
	
		this.serviceName = serviceName;
	
	}
	
	
	public String[] getMethodRegex() {
	
		return methodRegex;
	
	}
	
	
	public void setMethodRegex(String[] methodRegex) {
	
		this.methodRegex = methodRegex;
	
	}
	
	public int getArgumentNum() {
	
		return argumentNum;
	
	}
	
	public String getJarName() {
		return jarName;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	public void setArgumentNum(int argumentNum) {
	
		this.argumentNum = argumentNum;
	
	}
	
	
	public Class<?> getReturnType() {
	
		return returnType;
	
	}
	
	
	public void setReturnType(Class<?> returnType) {
	
		this.returnType = returnType;
	
	}
	
	public String[] getClassRegex() {
	
		return classRegex;
	
	}
	
	public void setClassRegex(String[] classRegex) {
	
		this.classRegex = classRegex;
	
	}
	
	public boolean isServiceValidate() {
	
		return serviceValidate;
	
	}
	
	public void setServiceValidate(boolean serviceValidate) {
	
		this.serviceValidate = serviceValidate;
	
	}
	
	public Class<?> getInterceptorClass() {
	
		return interceptorClass;
	
	}
	
	public void setInterceptorClass(Class<?> interceptorClass) {
	
		this.interceptorClass = interceptorClass;
	
	}
	
	public String getName() {
	
		return name;
	
	}
	
	public void setName(String name) {
	
		this.name = name;
	
	}
	
}
