package org.scorpion.api.kernel;

import org.scorpion.api.configuration.AopPointcutInfo;

import com.google.inject.matcher.Matcher;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: if developer want to create a component. the developer must extends the abstract </p>
 * <p>class AScorpionComponet. the AScorpionComponent exist life cycle. developer can override</p>
 * <p>the initialization method or service method or destroy method to handle themselves business</p>
 * <p>but we don't suggest the developer do that </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsScorpionClassAndMethodFilter<T> implements Matcher<T>{
	
	/**
	 * 切面信息
	 */
	private  AopPointcutInfo aoppointcutinfo;

	/**
	 * 获取切面信息
	 * 
	 * @return
	 * 
	 */
	public AopPointcutInfo getAoppointcutinfo() {
		
		return aoppointcutinfo;
	
	}
    
	/**
	 * 设置且切面信息
	 * 
	 * @param aoppointcutinfo
	 * 
	 * @return
	 * 
	 */
	public Matcher<T> setAoppointcutinfo(AopPointcutInfo aoppointcutinfo) {
		
		this.aoppointcutinfo = aoppointcutinfo;
		
		return this;
	
	}
	
	/**
	 * 检查系统切面信息
	 * 
	 * @param aoppointcutinfo
	 * 
	 * @return
	 * 
	 */
	public boolean checkPointcutInfo(AopPointcutInfo aoppointcutinfo){
		
		if(aoppointcutinfo == null)
		
			return false;
		
		if((aoppointcutinfo.getClassRegex()==null||aoppointcutinfo.getClassRegex().length<1)&&
				(aoppointcutinfo.getMethodRegex()==null||aoppointcutinfo.getMethodRegex().length<1)&&
				(aoppointcutinfo.getServiceName() == null||"".equals(aoppointcutinfo.getServiceName())))
		
			return false;
	
		return true;
	
	}

}
