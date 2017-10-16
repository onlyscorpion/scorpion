package org.scorpion.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.scorpion.common.enums.Scope;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: store jvm information. Developer can get jvm of the system </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.TYPE)
public @interface BeanContainer {
	
	/**
	 * @description Define bean name. if the name is null system will regard the class name as the bean name
	 * 
	 * @return
	 */
	public String name() default "";
	
	/**
	 * @description Setting action activity status . configure this attribute use to describe whether every request will general a new object or not
	 * 
	 * @return
	 */
	public Scope scope() default Scope.singleton;
	
	
	/**
	 * @description This attribute use to define the bean version . system default version is 1.0
	 * 
	 * @return
	 */
	public String version() default "1.0";
	
	

}
