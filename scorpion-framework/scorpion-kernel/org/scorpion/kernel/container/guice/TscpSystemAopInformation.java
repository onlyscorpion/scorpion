package org.scorpion.kernel.container.guice;

import java.util.List;
import java.util.Map;

import org.scorpion.api.configuration.AopPointcutInfo;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpAopAfterAdvice;
import org.scorpion.api.kernel.ITscpSystemAopInformation;

/**
 * 自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.tscp.common
 * <p>
 * File: AbsTscpFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 taiji.com.cn
 * </p>
 * <p>
 * Company: taiji.com.cn
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpSystemAopInformation implements ITscpSystemAopInformation {

	private Map<String, ITscpAopAfterAdvice> advices;

	private List<AopPointcutInfo> pointcuts;

	public TscpSystemAopInformation(Map<String, ITscpAopAfterAdvice> advices,List<AopPointcutInfo> pointcuts) {
		super();
		this.advices = advices;
		this.pointcuts = pointcuts;
	}

	@Override
	public Map<String, ITscpAopAfterAdvice> getSystemAdvices()throws TscpBaseException {
		return this.advices;
	}

	@Override
	public List<AopPointcutInfo> getSystemAopPointCut()throws TscpBaseException {
		return this.pointcuts;
	}

}
