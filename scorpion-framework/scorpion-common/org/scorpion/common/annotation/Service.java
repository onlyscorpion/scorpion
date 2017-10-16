package org.scorpion.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.scorpion.api.util.Constant;

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
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)

public @interface Service {
	
	/**
	 * @description This attribute use to define service name . if don't define the name . 
	 *                                     system will regard the method name as the service name
	 * @return
	 */
	public String name() default Constant.DEFULT_METHOD_NAME;
	
	/**
	 * @description Add description for service...
	 * 
	 * @return
	 */
	public String describe() default "";
	
	/**
	 * @description Configure service execution status . mark the whether the service is safe or not...
	 * 
	 * @return
	 */
	public boolean isSynService() default false;
	
	/**
	 * @description Mark current service activity status. so every request if create a new object or not...
	 * 
	 * @return
	 */
	public boolean isStatus() default false;
	
	/**
	 * @description Configure  service is singleton or not...
	 * 
	 * @return
	 */
	public boolean isSingleton() default false;
	
	
	

}
