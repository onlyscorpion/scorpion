package com.scorpion.huakerongtong.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description:  construct a component have two ways</p>
 * <p>this first one is that you can extends AScorpionComponent the other one is</p>
 * <p>that you can you the annotation 'component' to signal your method </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.TYPE)
public @interface Component {
   
   public String name() default "";
   
   public String[] params() default {};
  
   public String depend() default "";
   
   /**
    * @Description Marking the component start sequence ... where is a special value -1 . 
    *              When you make the sequence equal -1 ,Current component will be start in the last...
    * @return
    */
   public int sequence() default 100;
   
   public boolean iscorecomponent() default false;
   
   public boolean isPersistComponent() default false;

}
