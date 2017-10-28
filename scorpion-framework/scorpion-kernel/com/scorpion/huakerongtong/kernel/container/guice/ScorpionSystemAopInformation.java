package com.scorpion.huakerongtong.kernel.container.guice;

import java.util.List;
import java.util.Map;

import com.scorpion.huakerongtong.api.configuration.AopPointcutInfo;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IScorpionAopAfterAdvice;
import com.scorpion.huakerongtong.api.kernel.IScorpionSystemAopInformation;

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
public class ScorpionSystemAopInformation implements IScorpionSystemAopInformation {

	private Map<String, IScorpionAopAfterAdvice> advices;

	private List<AopPointcutInfo> pointcuts;

	public ScorpionSystemAopInformation(Map<String, IScorpionAopAfterAdvice> advices,List<AopPointcutInfo> pointcuts) {
		super();
		this.advices = advices;
		this.pointcuts = pointcuts;
	}

	@Override
	public Map<String, IScorpionAopAfterAdvice> getSystemAdvices()throws ScorpionBaseException {
		return this.advices;
	}

	@Override
	public List<AopPointcutInfo> getSystemAopPointCut()throws ScorpionBaseException {
		return this.pointcuts;
	}

}
