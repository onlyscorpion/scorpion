package org.scorpion.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.scorpion.common.enums.Scope;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: store jvm information. Developer can get jvm of the system </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.TYPE)

public @interface Action {
	
	
	/**
	 * @description Define action name
	 * 
	 * @return
	 */
	public String name() default "";
	
	
	/**
	 * @description Configure action activity status
	 * 
	 * @return
	 */
	public Scope scope() default Scope.singleton;
	
	
	/**
	 * @description Add description to the action
	 * 
	 * @return
	 */
	public String description() default "";
	
	
	/**
	 * @description Define the action version. default version is 1.0
	 * 
	 * @return
	 */
	public String version() default "1.0";

}
