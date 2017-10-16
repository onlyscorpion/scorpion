package org.scorpion.kernel.servicemanager;

import org.scorpion.api.common.AbsScorpionFactory;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionServiceProxy;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;
import org.scorpion.kernel.container.customization.ScorpionCustomizationServiceProxy;
import org.scorpion.kernel.container.guice.ScorpionGuiceServiceProxy;
import org.scorpion.kernel.container.guice.ScorpionSystemIocInitialization;
import org.scorpion.kernel.container.spring.ScorpionSpringServiceProxy;

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
public class ScorpionServiceProxyFacotry extends AbsScorpionFactory<AbsScorpionServiceProxy> {

	private static AbsScorpionFactory<AbsScorpionServiceProxy> factory = getInstance();

	@Override
	public AbsScorpionServiceProxy produceInstance(Object... arg) throws ScorpionBaseException {
		return null;
	}

	@Override
	public <P> P produceInstance(Class<P> clazz) throws ScorpionBaseException {
		try {
			return clazz.newInstance();
		} catch (Throwable ex) {
			throw new ScorpionBaseException(ex);
		}
	}

	@Override
	public AbsScorpionServiceProxy produceInstance() throws ScorpionBaseException {

		String containerType = SystemContext.getApplicationContext().getSystemCoreConfig().getPlatformconfiguration().getContainerEngine();

		if (Constant.GUICE.equals(containerType)) {
			
			new ScorpionSystemIocInitialization().initialization();
			
			return new ScorpionGuiceServiceProxy(SystemContext.getApplicationContext().getIocManager());
		
		} else if (Constant.Scorpion.equals(containerType)) {
			
			return new ScorpionCustomizationServiceProxy();
		
		} else if (Constant.SPRING.equals(containerType)) {
			
			return new ScorpionSpringServiceProxy();

		} else {
			throw new ScorpionBaseException("scorpion-8054:Unknow container type [" + containerType+ "] ! ");
		}
	}

	/**
	 * 
	 * @return
	 */
	public synchronized static AbsScorpionFactory<AbsScorpionServiceProxy> getInstance() {

		if (factory == null)
			factory = new ScorpionServiceProxyFacotry();

		return factory;
	}

}
