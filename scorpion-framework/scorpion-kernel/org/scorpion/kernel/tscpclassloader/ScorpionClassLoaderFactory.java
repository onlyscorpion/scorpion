package org.scorpion.kernel.tscpclassloader;

import java.net.MalformedURLException;
import java.net.URL;

import org.scorpion.api.common.AbsScorpionFactory;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.context.SystemContext;

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
public class ScorpionClassLoaderFactory extends AbsScorpionFactory<ClassLoader> {

	public ClassLoader produceInstance(Object... arg) throws ScorpionBaseException {

		if (arg == null || arg.length < 1)
			return this.getClass().getClassLoader();

		URL[] urls = new URL[((String[]) arg[0]).length];

		for (int i = 0; i < arg.length; i++) {
			try {
				urls[i] = new URL((String) ((String[]) arg[0])[i]);
			} catch (MalformedURLException e) {
				throw new ScorpionBaseException("scorpion-8908:Construct classloader exception !", e);
			}
		}

		return new ScorpionSystemClassLoader(urls);
	}

	@Override
	public ClassLoader produceInstance() throws ScorpionBaseException {

		if (SystemContext.getApplicationContext().getSystemBuildPath() == null|| SystemContext.getApplicationContext().getSystemBuildPath().length < 1)
			throw new ScorpionBaseException("scorpion-9054:Building path don't initialize !");

		URL[] urls = new URL[SystemContext.getApplicationContext().getSystemBuildPath().length];

		for (int i = 0; i < urls.length; i++) {

			try {
				urls[i] = new URL(SystemUtils.pathTransformer(SystemContext.getApplicationContext().getSystemBuildPath()[i],Constant.URL_TYPE));
			} catch (MalformedURLException e) {
				throw new ScorpionBaseException("scorpion-9045:Construct classloader exception ，exception point in["+ SystemContext.getApplicationContext().getSystemBuildPath()[i] + "]", e);
			}
		}

		return new ScorpionSystemClassLoader(urls, this.getClass().getClassLoader());

	}

	public static AbsScorpionFactory<ClassLoader> getNewInstance() {

		return new ScorpionClassLoaderFactory();

	}

	@Override
	public <P> P produceInstance(Class<P> clazz) throws ScorpionBaseException {
		try {
			return clazz.newInstance();
		} catch (Throwable ex) {
			throw new ScorpionBaseException(ex);
		}
	}

}
