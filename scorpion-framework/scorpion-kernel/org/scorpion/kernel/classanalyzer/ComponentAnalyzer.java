package org.scorpion.kernel.classanalyzer;

import java.util.HashMap;
import java.util.Map;

import org.scorpion.api.configuration.ComponentInformation;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IAnnotationAnalyzerListener;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.annotation.Component;
import org.scorpion.common.context.SystemContext;

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
public class ComponentAnalyzer implements IAnnotationAnalyzerListener {


	@Override
	public void analyse(Class<?> clazz, String jarName)throws ScorpionBaseException {

		if (clazz.getAnnotation(Component.class) == null)
			return;

		Component component = clazz.getAnnotation(Component.class);
		ComponentInformation componentInfo = new ComponentInformation();
		componentInfo.setClazz(clazz);
		componentInfo.setName("".equals(component.name()) ? clazz.getSimpleName() : component.name());
		componentInfo.setPersisComponet(component.isPersistComponent());
		componentInfo.setJarName(jarName);
		componentInfo.setIscorecomponent(component.iscorecomponent());
		componentInfo.setBootsequence(component.sequence());

		if (component.params().length > 0) {
			Map<String, String> arguments = new HashMap<String, String>();

			for (String param : component.params()) {
				if (!SystemUtils.regularExpressionValidate(param != null ? param.replace(" ", "") : param,Constant.PARAM_REGEX))
					throw new ScorpionBaseException("扫描组件["+ componentInfo.getName()+ "]出现异常, 组件参数属性格式不正确,异常类出现在["+ clazz.getName()+ "]中，param正确属性为 params={\"KEY1=VALUE1\",\"KEY2==VALUE2\"}");

				arguments.put(param.split("=")[0], param.split("=")[1]);
			}

			componentInfo.setArguments(arguments);
		}

		SystemContext.getApplicationContext().getScanInfo().getComponents().put(componentInfo.getName(), componentInfo);
		PlatformLogger.info("在类[" + componentInfo.getClazz().getName()+ "]中扫描到组件[" + componentInfo.getName() + "]");

	}

}
