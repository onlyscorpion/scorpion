package com.scorpion.huakerongtong.kernel.servicemanager;

import com.scorpion.huakerongtong.api.common.AbsScorpionFactory;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.AbsScorpionServiceProxy;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.common.context.SystemContext;
import com.scorpion.huakerongtong.kernel.container.customization.ScorpionCustomizationServiceProxy;
import com.scorpion.huakerongtong.kernel.container.guice.ScorpionGuiceServiceProxy;
import com.scorpion.huakerongtong.kernel.container.guice.ScorpionSystemIocInitialization;
import com.scorpion.huakerongtong.kernel.container.spring.ScorpionSpringServiceProxy;

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
