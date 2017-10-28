package com.scorpion.huakerongtong.kernel.component;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import com.scorpion.huakerongtong.api.configuration.ComponentInformation;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.AbsScorpionComponent;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.api.util.SystemUtils;
import com.scorpion.huakerongtong.common.context.SystemContext;
import com.scorpion.huakerongtong.common.lifecycle.ScorpionLifecycleManager;

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
public class ScorpionComponentManager extends AbsScorpionComponent {

	private LinkedList<String> componentsequence;

	/**
	 * @description component sequence
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public boolean sortComponentSequence() throws ScorpionBaseException {

		if (SystemContext.getApplicationContext().getScanInfo().getComponents() == null|| SystemContext.getApplicationContext().getScanInfo().getComponents().size() < 1)
			return true;

		componentsequence = new LinkedList<String>();
		LinkedList<String> lastLink = new LinkedList<String>();

		for (Entry<String, ComponentInformation> entry : SystemContext.getApplicationContext().getScanInfo().getComponents().entrySet()) {

			if (entry.getValue().getBootsequence() < 0) {
				lastLink.add(entry.getKey());
				continue;
			}

			if (componentsequence.size() == 0) {
				componentsequence.add(entry.getKey() + ","+ entry.getValue().getBootsequence());
				continue;
			}

			for (int i = 0; i < componentsequence.size(); i++) {

				if (entry.getValue().getBootsequence() < Integer.parseInt(componentsequence.get(i).split(",")[1])) {
					componentsequence.add(i, entry.getKey() + ","+ entry.getValue().getBootsequence());
					break;
				} else {
					if (i + 1 == componentsequence.size()) {
						componentsequence.addLast(entry.getKey() + ","+ entry.getValue().getBootsequence());
						break;
					}
				}
			}
		}

		componentsequence.addAll(lastLink);
		return false;
	}

	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {

		if (sortComponentSequence())
			return;

		startPersistenceComponet();
		
		for (String componentName : componentsequence) {
			ComponentInformation componentInfo = SystemContext.getApplicationContext().getScanInfo().getComponentByName(componentName.split(",")[0]);
			AbsScorpionComponent componet = null;
			try {
				if (componentInfo.isAlreadyLoad())
					continue;

				PlatformLogger.info("Is beginning to load component["+ componentName.split(",")[0] + "]");
				componet = ((AbsScorpionComponent) componentInfo.getClazz().newInstance());
				componentInfo.setComponent(componet);
				componet.arguement = componentInfo.getArguments();
				ScorpionLifecycleManager.registerLifecycleSingle(componet);
				// entry.getValue().setComponentStatue(Constant.RUNNING);
			} catch (Throwable e) {
				PlatformLogger.error("TSC-6005:Loading the component ["+componentInfo.getName()+"] failure！", e);
				if(!componentInfo.isIscorecomponent()){
					if (componet != null) {
						componentInfo.setComponent(componet);
						componentInfo.setComponentstatus(Constant.ERROR);
					}
				}else{
					PlatformLogger.error("系统启动失败");
					SystemUtils.exitApplication();
				}
			}
		}
	}

	/**
	 * @throws ScorpionBaseException
	 */
	public void startPersistenceComponet() throws ScorpionBaseException {

		ComponentInformation componetInfo = SystemContext.getApplicationContext().getScanInfo().getComponentByName("persistenceComponentManager");
		if (componetInfo != null && !componetInfo.isAlreadyLoad()) {
			PlatformLogger.info("Is beginning to load component["+ componetInfo.getName() + "]");
			try {
				AbsScorpionComponent persistencecomponet = ((AbsScorpionComponent) componetInfo.getClazz().newInstance());
				componetInfo.setComponent(persistencecomponet);
				persistencecomponet.arguement = componetInfo.getArguments();
				ScorpionLifecycleManager.registerLifecycleSingle(persistencecomponet);
				componetInfo.setAlreadyLoad(true);
			} catch (Exception e) {
				throw new ScorpionBaseException("TSC-6005:Loading the component["+ componetInfo.getName()+ "]failure, please check the component configuration or implement !",e);
			}
		}
	}

	
	
	@Override
	public void initialization() throws ScorpionBaseException {
		
		for(ComponentInformation component:((SystemContext)SystemContext.getApplicationContext()).getSystemCoreConfig().getComponents()){
			
			if (component.getParameter() != null&&SystemUtils.regularExpressionValidate(component.getParameter() != null ? component.getParameter().replace(" ", "") : component.getParameter(),Constant.PARAM_REGEX)){
				for(String parameter:component.getParameter().split(","))
					component.getArguments().put(parameter.split("=")[0], parameter.split("=")[1]);
			}else{
				throw new ScorpionBaseException("组件["+ component.getName()+ "]出现异常, 组件参数属性格式不正确,异常类出现在Scorpion.xml配置文件中 组件["+ component.getName()+ "]中，"
						+ "parameters正确属性为 parameters={\"KEY1=VALUE1\",\"KEY2==VALUE2\"}");
			}
			
			try {
				component.setClazz(((SystemContext)SystemContext.getApplicationContext()).getSystemClassLoader().loadClass(component.getClazzName()));
			} catch (ClassNotFoundException e) {
				throw new ScorpionBaseException(e);
			}
			
            SystemContext.getApplicationContext().getScanInfo().getComponents().put(component.getName(), component);
		}
		
		super.initialization();
	
	}
	

}
