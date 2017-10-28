package com.scorpion.huakerongtong.kernel.classanalyzer;

import java.util.HashMap;
import java.util.Map;

import com.scorpion.huakerongtong.api.kernel.IAnalyzerResourceRegister;

/**
 * 天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>
 * com.SCORPION.Scorpion.common
 * <p>
 * File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 SCORPION.COM.CN
 * </p>
 * <p>
 * Company: SCORPION.COM.CN
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class AnalyzerResourceRegister implements IAnalyzerResourceRegister {

	/**
	 * 
	 * @return
	 */
	public Map<String, String> getApplicationDefaultAnalyzer() {

		Map<String, String> analyzers = new HashMap<String, String>();
		analyzers.put("action","com.SCORPION.Scorpion.kernel.classanalyzer.ActionAnalyzer");
		analyzers.put("service","com.SCORPION.Scorpion.kernel.classanalyzer.BeanAnalyzer");
		analyzers.put("component","com.SCORPION.Scorpion.kernel.classanalyzer.ComponentAnalyzer");
		analyzers.put("interceptor","com.SCORPION.Scorpion.kernel.classanalyzer.InterceptorAnalyzer");
		analyzers.put("pojo","com.SCORPION.Scorpion.kernel.classanalyzer.PojoAnalyzer");
		return analyzers;
	}

}
