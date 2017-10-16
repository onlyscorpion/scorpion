package org.scorpion.kernel.classanalyzer;

import java.lang.reflect.Method;

import org.scorpion.api.configuration.ScorpionSystemScanInfo;
import org.scorpion.api.configuration.ScorpionSystemScanInfo.ActionInfo;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IAnnotationAnalyzerListener;
import org.scorpion.api.kernel.IReqData;
import org.scorpion.api.kernel.IResData;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.common.annotation.Action;
import org.scorpion.common.context.SystemContext;
import org.scorpion.common.enums.Scope;

/**
 * 天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.Scorpion.common
 * <p>
 * File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: if developer want to create a component. the developer must
 * extends the abstract
 * </p>
 * <p>
 * class AScorpionComponet. the AScorpionComponent exist life cycle. developer can
 * override
 * </p>
 * <p>
 * the initialization method or service method or destroy method to handle
 * themselves business
 * </p>
 * <p>
 * but we don't suggest the developer do that
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
public class ActionAnalyzer implements IAnnotationAnalyzerListener {

	/**
	 * 
	 * @param actionInfo
	 * 
	 * @param clazz
	 * 
	 * @param action
	 * 
	 * @throws ScorpionBaseException
	 */
	private void constructActionInfo(ActionInfo actionInfo, Class<?> clazz,Action action) throws ScorpionBaseException {

		actionInfo.setActionName("".equals(action.name()) ? clazz.getSimpleName() : action.name());
		actionInfo.setClazz(clazz);
		actionInfo.setSingle(("".equals(action.scope().name()) || Scope.prototype.name().equals(action.scope().name())) ? false : true);

		for (Method method : clazz.getMethods()) {

			if (!method.getReturnType().isAssignableFrom(IResData.class)|| method.getParameterTypes() == null|| method.getParameterTypes().length != 1|| !method.getParameterTypes()[0].isAssignableFrom(IReqData.class))
				continue;

			String name = actionInfo.getActionName() + "_" + method.getName();
			actionInfo.getMethods().put(name, method);
			PlatformLogger.info("在类[" + clazz.getName() + "]扫描到Action[" + name+ "]控制属性为[" + actionInfo.isSingle() + "]");
		}

	}

	@Override
	public void analyse(Class<?> clazz, String jarName)throws ScorpionBaseException {

		if (clazz.getAnnotation(Action.class) == null)
			return;

		Action action = clazz.getAnnotation(Action.class);
		ScorpionSystemScanInfo context = SystemContext.getApplicationContext().getScanInfo();
		ActionInfo actionInfo = SystemContext.getApplicationContext().getScanInfo().new ActionInfo();
		actionInfo.setActionName(jarName);
		constructActionInfo(actionInfo, clazz, action);
		context.getActionPool().put(actionInfo.getActionName(), actionInfo);
	}

}
