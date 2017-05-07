package org.scorpion.api.configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.scorpion.api.kernel.AbsTscpComponent;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ComponentInformation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private String name;
	
	private String jarName;
	
	private AbsTscpComponent component;
	
	private Map<String,String>arguments;
	
	private boolean isPersisComponet;
	
	private boolean isAlreadyLoad;
	
	private List<String> dependComponet;
	
	private Class<?>clazz;
	
	private int bootsequence;
	
	private String clazzName;
	
	private String parameter;
	
	private boolean isLoad;
	
	private boolean iscorecomponent = false;
	
	private int componentstatus;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJarName() {
		return jarName;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	public AbsTscpComponent getComponent() {
		return component;
	}

	public void setComponent(AbsTscpComponent component) {
		this.component = component;
	}

	public Map<String, String> getArguments() {
		
		if(arguments == null)
			arguments = new HashMap<String,String>();
		
		return arguments;
	}

	public void setArguments(Map<String, String> arguments) {
		this.arguments = arguments;
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

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public int getBootsequence() {
		return bootsequence;
	}

	public void setBootsequence(int bootsequence) {
		this.bootsequence = bootsequence;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public boolean isLoad() {
		return isLoad;
	}

	public void setLoad(boolean isLoad) {
		this.isLoad = isLoad;
	}

	public boolean isIscorecomponent() {
		return iscorecomponent;
	}

	public void setIscorecomponent(boolean iscorecomponent) {
		this.iscorecomponent = iscorecomponent;
	}

	public int getComponentstatus() {
		return componentstatus;
	}

	public void setComponentstatus(int componentstatus) {
		this.componentstatus = componentstatus;
	}

	public List<String> getDependComponet() {
		return dependComponet;
	}

	public void setDependComponet(List<String> dependComponet) {
		this.dependComponet = dependComponet;
	}

}
