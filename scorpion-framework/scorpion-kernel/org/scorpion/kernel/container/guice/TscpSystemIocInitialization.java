package org.scorpion.kernel.container.guice;

import java.util.Map.Entry;

import org.scorpion.api.configuration.AopPointcutInfo;
import org.scorpion.api.configuration.TscpSystemScanInfo.ActionInfo;
import org.scorpion.api.configuration.TscpSystemScanInfo.ServiceInfo;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpAopAfterAdvice;
import org.scorpion.api.kernel.ITscpAopAroundAdvice;
import org.scorpion.api.kernel.ITscpAopBeforeAdvice;
import org.scorpion.api.kernel.ITscpSystemIocInitListener;
import org.scorpion.api.kernel.ITscpSystemIocManager;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.Stage;

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
public class TscpSystemIocInitialization implements ITscpSystemIocInitListener {

	private ITscpSystemIocManager iocManager;

	public TscpSystemIocInitialization() throws TscpBaseException {

		if (iocManager != null)
			throw new TscpBaseException("TSC-6005:IOC container exist implement！");

		iocManager = new TscpSystemIocManager();
		((SystemContext) SystemContext.getApplicationContext()).setIocManager(iocManager);

	}

	@Override
	public void initialization() throws TscpBaseException {

		((TscpSystemIocManager) iocManager).setInjector(Guice.createInjector(Stage.PRODUCTION, new Module() {
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
	 * @throws TscpBaseException
	 */
	private void initActions(Binder binder) {

		try {
			for (Entry<String, ActionInfo> entry : SystemContext.getApplicationContext().getScanInfo().getActionPool().entrySet()) {

				if (entry.getValue().isSingle())
					binder.bind(entry.getValue().getClazz()).in(Scopes.SINGLETON);
				else
					binder.bind(entry.getValue().getClazz());
			}

		} catch (TscpBaseException e) {
			PlatformLogger.error("TSC-4087:initialize Action failure!",new TscpBaseException("TSC-4087:initialize Action failure!"));
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
		} catch (TscpBaseException e) {
			PlatformLogger.error("TSC-4087:initialize Service failure!",new TscpBaseException("TSC-4087:initialize Service failure!"));
		}
	}

	/**
	 * @param binder
	 */
	private void initComponents(Binder binder) {
		try {
			binder.bind(Class.forName(Constant.PERSISTENCE));
		} catch (ClassNotFoundException e) {
			PlatformLogger.error("TSCP-9076:binding persistence failure!", e);
			Runtime.getRuntime().exit(0);
		}

		// for(Entry<String,ComponentInfo>entry:SystemContext.getApplicationContext().getScanInfo().getComponents().entrySet()){
		//
		// if(!entry.getValue().isPersisComponet()||entry.getValue().isAlreadyLoad())
		// continue;
		//
		// AbsTscpComponent componet =
		// ((AbsTscpComponent)entry.getValue().getClazz().newInstance());
		// componet.arguement = entry.getValue().getArguments();
		// TscpLifecycleManager.registerLifecycleSingle(componet);
		// entry.getValue().setAlreadyLoad(true);
		// }
		// } catch (Exception e) {
		// logger.info("TSCP-9076:加载组件异常",e);
		// }

	}

	/**
	 * @param binder
	 */
	private void initInterceptors(Binder binder) {

		try {
			for (Entry<String, AopPointcutInfo> entry : SystemContext.getApplicationContext().getScanInfo().getPointcutInfos().entrySet()) {

				Object obj = entry.getValue().getInterceptorClass().newInstance();

				if (obj instanceof ITscpAopBeforeAdvice) {

					binder.bindInterceptor(new TscpClassesMatcher().setAoppointcutinfo(entry.getValue()),
							new TscpMethodsMatcher().setAoppointcutinfo(entry.getValue()),
							new TscpAopDoBeforeInterceptor().setBeforeAdvice((ITscpAopBeforeAdvice) obj));

				} else if (obj instanceof ITscpAopAfterAdvice) {

					binder.bindInterceptor(new TscpClassesMatcher().setAoppointcutinfo(entry.getValue()),
							new TscpMethodsMatcher().setAoppointcutinfo(entry.getValue()),
							new TscpAopDoAfterInterceptor().setAfterAdvice((ITscpAopAfterAdvice) obj));

				} else if (obj instanceof ITscpAopAroundAdvice) {

					binder.bindInterceptor(new TscpClassesMatcher().setAoppointcutinfo(entry.getValue()),
							new TscpMethodsMatcher().setAoppointcutinfo(entry.getValue()),
							new TscpAopDoAroundInterceptor().setAroundAdvice((ITscpAopAroundAdvice) obj));

				} else {
					throw new TscpBaseException("TSC-4998:don't implement interface of the interceptor ！");
				}
			}
		} catch (Exception e) {
			PlatformLogger.error("TSC-4087:initialize interceptor exception !",e);
		}
	}

}
