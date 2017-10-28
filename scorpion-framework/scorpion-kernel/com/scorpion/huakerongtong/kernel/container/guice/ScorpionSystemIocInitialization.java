package com.scorpion.huakerongtong.kernel.container.guice;

import java.util.Map.Entry;

import com.scorpion.huakerongtong.api.configuration.AopPointcutInfo;
import com.scorpion.huakerongtong.api.configuration.ScorpionSystemScanInfo.ActionInfo;
import com.scorpion.huakerongtong.api.configuration.ScorpionSystemScanInfo.ServiceInfo;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.IScorpionAopAfterAdvice;
import com.scorpion.huakerongtong.api.kernel.IScorpionAopAroundAdvice;
import com.scorpion.huakerongtong.api.kernel.IScorpionAopBeforeAdvice;
import com.scorpion.huakerongtong.api.kernel.IScorpionSystemIocInitListener;
import com.scorpion.huakerongtong.api.kernel.IScorpionSystemIocManager;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.common.context.SystemContext;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.Stage;

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
public class ScorpionSystemIocInitialization implements IScorpionSystemIocInitListener {

	private IScorpionSystemIocManager iocManager;

	public ScorpionSystemIocInitialization() throws ScorpionBaseException {

		if (iocManager != null)
			throw new ScorpionBaseException("TSC-6005:IOC container exist implement！");

		iocManager = new ScorpionSystemIocManager();
		((SystemContext) SystemContext.getApplicationContext()).setIocManager(iocManager);

	}

	@Override
	public void initialization() throws ScorpionBaseException {

		((ScorpionSystemIocManager) iocManager).setInjector(Guice.createInjector(Stage.PRODUCTION, new Module() {
					@Override
					public void configure(Binder binder) {

						initActions(binder);

						initBeans(binder);

						initInterceptors(binder);

						initComponents(binder);
					}
				}));
	}

	/**
	 * 
	 * @param binder
	 * 
	 * @throws ScorpionBaseException
	 */
	private void initActions(Binder binder) {

		try {
			for (Entry<String, ActionInfo> entry : SystemContext.getApplicationContext().getScanInfo().getActionPool().entrySet()) {

				if (entry.getValue().isSingle())
					binder.bind(entry.getValue().getClazz()).in(Scopes.SINGLETON);
				else
					binder.bind(entry.getValue().getClazz());
			}

		} catch (ScorpionBaseException e) {
			PlatformLogger.error("TSC-4087:initialize Action failure!",new ScorpionBaseException("TSC-4087:initialize Action failure!"));
		}
	}

	/**
	 * @param binder
	 */
	private void initBeans(Binder binder) {

		try {
			
			// 做代理的生成
			
			for (Entry<String, ServiceInfo> entry : SystemContext.getApplicationContext().getScanInfo().getServicePool().entrySet()) {

				if (entry.getValue().isSingleton())
					binder.bind(entry.getValue().getClazz()).in(Scopes.SINGLETON);
				else
					binder.bind(entry.getValue().getClazz());
			}
		} catch (ScorpionBaseException e) {
			PlatformLogger.error("TSC-4087:initialize Service failure!",new ScorpionBaseException("TSC-4087:initialize Service failure!"));
		}
	}

	/**
	 * @param binder
	 */
	private void initComponents(Binder binder) {
		try {
			binder.bind(Class.forName(Constant.PERSISTENCE));
		} catch (ClassNotFoundException e) {
			PlatformLogger.error("scorpion-9076:binding persistence failure!", e);
			Runtime.getRuntime().exit(0);
		}

		// for(Entry<String,ComponentInfo>entry:SystemContext.getApplicationContext().getScanInfo().getComponents().entrySet()){
		//
		// if(!entry.getValue().isPersisComponet()||entry.getValue().isAlreadyLoad())
		// continue;
		//
		// AbsScorpionComponent componet =
		// ((AbsScorpionComponent)entry.getValue().getClazz().newInstance());
		// componet.arguement = entry.getValue().getArguments();
		// ScorpionLifecycleManager.registerLifecycleSingle(componet);
		// entry.getValue().setAlreadyLoad(true);
		// }
		// } catch (Exception e) {
		// logger.info("scorpion-9076:加载组件异常",e);
		// }

	}

	/**
	 * @param binder
	 */
	private void initInterceptors(Binder binder) {

		try {
			for (Entry<String, AopPointcutInfo> entry : SystemContext.getApplicationContext().getScanInfo().getPointcutInfos().entrySet()) {

				Object obj = entry.getValue().getInterceptorClass().newInstance();

				if (obj instanceof IScorpionAopBeforeAdvice) {

					binder.bindInterceptor(new ScorpionClassesMatcher().setAoppointcutinfo(entry.getValue()),
							new ScorpionMethodsMatcher().setAoppointcutinfo(entry.getValue()),
							new ScorpionAopDoBeforeInterceptor().setBeforeAdvice((IScorpionAopBeforeAdvice) obj));

				} else if (obj instanceof IScorpionAopAfterAdvice) {

					binder.bindInterceptor(new ScorpionClassesMatcher().setAoppointcutinfo(entry.getValue()),
							new ScorpionMethodsMatcher().setAoppointcutinfo(entry.getValue()),
							new ScorpionAopDoAfterInterceptor().setAfterAdvice((IScorpionAopAfterAdvice) obj));

				} else if (obj instanceof IScorpionAopAroundAdvice) {

					binder.bindInterceptor(new ScorpionClassesMatcher().setAoppointcutinfo(entry.getValue()),
							new ScorpionMethodsMatcher().setAoppointcutinfo(entry.getValue()),
							new ScorpionAopDoAroundInterceptor().setAroundAdvice((IScorpionAopAroundAdvice) obj));

				} else {
					throw new ScorpionBaseException("TSC-4998:don't implement interface of the interceptor ！");
				}
			}
		} catch (Exception e) {
			PlatformLogger.error("TSC-4087:initialize interceptor exception !",e);
		}
	}

}
