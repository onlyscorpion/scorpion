package org.scorpion.kernel.tscpclassloader;

import java.net.MalformedURLException;
import java.net.URL;

import org.scorpion.api.common.AbsTscpFactory;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.context.SystemContext;

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
 * Description: if developer want to create a component. the developer must
 * extends the abstract
 * </p>
 * <p>
 * class ATscpComponet. the ATscpComponent exist life cycle. developer can
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
public class TscpClassLoaderFactory extends AbsTscpFactory<ClassLoader> {

	public ClassLoader produceInstance(Object... arg) throws TscpBaseException {

		if (arg == null || arg.length < 1)
			return this.getClass().getClassLoader();

		URL[] urls = new URL[((String[]) arg[0]).length];

		for (int i = 0; i < arg.length; i++) {
			try {
				urls[i] = new URL((String) ((String[]) arg[0])[i]);
			} catch (MalformedURLException e) {
				throw new TscpBaseException("TSCP-8908:Construct classloader exception !", e);
			}
		}

		return new TscpSystemClassLoader(urls);
	}

	@Override
	public ClassLoader produceInstance() throws TscpBaseException {

		if (SystemContext.getApplicationContext().getSystemBuildPath() == null|| SystemContext.getApplicationContext().getSystemBuildPath().length < 1)
			throw new TscpBaseException("TSCP-9054:Building path don't initialize !");

		URL[] urls = new URL[SystemContext.getApplicationContext().getSystemBuildPath().length];

		for (int i = 0; i < urls.length; i++) {

			try {
				urls[i] = new URL(SystemUtils.pathTransformer(SystemContext.getApplicationContext().getSystemBuildPath()[i],Constant.URL_TYPE));
			} catch (MalformedURLException e) {
				throw new TscpBaseException("TSCP-9045:Construct classloader exception ，exception point in["+ SystemContext.getApplicationContext().getSystemBuildPath()[i] + "]", e);
			}
		}

		return new TscpSystemClassLoader(urls, this.getClass().getClassLoader());

	}

	public static AbsTscpFactory<ClassLoader> getNewInstance() {

		return new TscpClassLoaderFactory();

	}

	@Override
	public <P> P produceInstance(Class<P> clazz) throws TscpBaseException {
		try {
			return clazz.newInstance();
		} catch (Throwable ex) {
			throw new TscpBaseException(ex);
		}
	}

}
