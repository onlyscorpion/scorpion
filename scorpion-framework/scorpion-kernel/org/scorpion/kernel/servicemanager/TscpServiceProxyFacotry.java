package org.scorpion.kernel.servicemanager;

import org.scorpion.api.common.AbsTscpFactory;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpServiceProxy;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;
import org.scorpion.kernel.container.customization.TscpCustomizationServiceProxy;
import org.scorpion.kernel.container.guice.TscpGuiceServiceProxy;
import org.scorpion.kernel.container.guice.TscpSystemIocInitialization;
import org.scorpion.kernel.container.spring.TscpSpringServiceProxy;

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
public class TscpServiceProxyFacotry extends AbsTscpFactory<AbsTscpServiceProxy> {

	private static AbsTscpFactory<AbsTscpServiceProxy> factory = getInstance();

	@Override
	public AbsTscpServiceProxy produceInstance(Object... arg) throws TscpBaseException {
		return null;
	}

	@Override
	public <P> P produceInstance(Class<P> clazz) throws TscpBaseException {
		try {
			return clazz.newInstance();
		} catch (Throwable ex) {
			throw new TscpBaseException(ex);
		}
	}

	@Override
	public AbsTscpServiceProxy produceInstance() throws TscpBaseException {

		String containerType = SystemContext.getApplicationContext().getSystemCoreConfig().getPlatformconfiguration().getContainerEngine();

		if (Constant.GUICE.equals(containerType)) {
			
			new TscpSystemIocInitialization().initialization();
			
			return new TscpGuiceServiceProxy(SystemContext.getApplicationContext().getIocManager());
		
		} else if (Constant.TSCP.equals(containerType)) {
			
			return new TscpCustomizationServiceProxy();
		
		} else if (Constant.SPRING.equals(containerType)) {
			
			return new TscpSpringServiceProxy();

		} else {
			throw new TscpBaseException("TSCP-8054:Unknow container type [" + containerType+ "] ! ");
		}
	}

	/**
	 * 
	 * @return
	 */
	public synchronized static AbsTscpFactory<AbsTscpServiceProxy> getInstance() {

		if (factory == null)
			factory = new TscpServiceProxyFacotry();

		return factory;
	}

}
