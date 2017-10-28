package com.scorpion.huakerongtong.kernel.context;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.scorpion.huakerongtong.api.configuration.ComponentInformation;
import com.scorpion.huakerongtong.api.configuration.ExceptionInfo;
import com.scorpion.huakerongtong.api.configuration.SystemEnumType;
import com.scorpion.huakerongtong.api.configuration.SystemResourcePool;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.AbsScorpionComponent;
import com.scorpion.huakerongtong.common.context.SystemContext;

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
public class SystemConfResourceContext {

	/**
	 * 异常组件管理器
	 */
	public static AbsScorpionComponent exceptionComponentManager;

	/**
	 * 组件信息
	 */
	public static Map<String, ComponentInformation> componentInfos;

	/**
	 * 日志组件管理器
	 */
	public static AbsScorpionComponent logComponentManager;

	/**
	 * 服务组件管理
	 */
	public static AbsScorpionComponent serviceComponentManager;

	/**
	 * 
	 */
	public static Stack<AbsScorpionComponent> outerComponentManager;

	/**
	 * 
	 * @param componentName
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public static ComponentInformation getComponentInfoByName(String componentName) throws ScorpionBaseException {

		if (componentInfos != null && componentInfos.containsKey(componentName))
			return componentInfos.get(componentName);

		return null;
	}

	/**
	 * @description get system exception content by exception code
	 * 
	 * @param code
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final static String getExceptionContentByCode(String code) {

		List<ExceptionInfo> exceptions = SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.systemexceptionconfigresource.getValue(),List.class);

		for (ExceptionInfo exception : exceptions) {
			if (exception.getContentByCode(code) != null)
				return exception.getContentByCode(code);
		}
		return "";

	}

	/**
	 * @param componentName
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void getComponentManagerByName(String componentName)throws ScorpionBaseException {

	}

	/**
	 * 刷新组件信息
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void refreshComponentInfo() throws ScorpionBaseException {

		componentInfos = SystemContext.getApplicationContext().getScanInfo().getComponents();
	}

	/**
	 * 注销组件管理器
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void stopComponentManager() throws ScorpionBaseException {

		for (int i = 0; i < outerComponentManager.size(); i++)
			outerComponentManager.peek().destroy();

		if (exceptionComponentManager == null)
			exceptionComponentManager.destroy();

		if (logComponentManager == null)
			logComponentManager.destroy();

		if (serviceComponentManager == null)
			serviceComponentManager.destroy();
	}

}
