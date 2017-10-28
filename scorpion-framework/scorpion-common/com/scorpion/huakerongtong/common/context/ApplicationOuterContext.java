package com.scorpion.huakerongtong.common.context;

import java.util.ArrayList;
import java.util.List;

import com.scorpion.huakerongtong.api.common.IApplicationOuterContext;
import com.scorpion.huakerongtong.api.configuration.DrivenBean;

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
public final class ApplicationOuterContext implements IApplicationOuterContext{
	
	
	
	private static IApplicationOuterContext outerContext;
	
	private final List<DrivenBean> drivenBeans = new ArrayList<DrivenBean>();
	
	
	
	public List<DrivenBean> getDrivenBeans(){
		return drivenBeans;
	}
	
	

	/**
	 * @ description Return context instance ...
	 * 
	 * @return
	 */
	public static synchronized IApplicationOuterContext getOuterContextInstance(){
		
		if(outerContext == null)
			outerContext = new ApplicationOuterContext();
		
		return outerContext;
		
	}
	
}
