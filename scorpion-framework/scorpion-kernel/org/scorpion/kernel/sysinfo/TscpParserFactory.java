package org.scorpion.kernel.sysinfo;

import org.scorpion.api.common.AbsTscpFactory;
import org.scorpion.api.common.AbsTscpXmlParser;
import org.scorpion.api.common.ItscpXmlParser;
import org.scorpion.api.configuration.ExceptionInfo;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.configuration.TscpCoreConfig;
import org.scorpion.api.configuration.TscpLogConfig;
import org.scorpion.api.exception.TscpBaseException;

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
public class TscpParserFactory extends AbsTscpFactory<ItscpXmlParser<?>> {

	private static AbsTscpFactory<ItscpXmlParser<?>> parserFactory = getInstance();

	@Override
	public ItscpXmlParser<?> produceInstance(Object... arg)throws TscpBaseException {
		if (arg == null || arg.length < 1)
			throw new TscpBaseException();
		if (SystemEnumType.logconfigfileparser.getValue().equals(arg[0])|| arg[0] instanceof TscpLogConfig) {
			return new TscpLogFileParser();
		} else if (SystemEnumType.exceptionconfigparser.getValue().equals(arg[0])|| arg[0] instanceof ExceptionInfo) {
			return new TscpExceptionFileParser();
		} else if (SystemEnumType.coreconfigfileparser.getValue().equals(arg[0]) || arg[0] instanceof TscpCoreConfig) {
			return new TscpCoreConfigFileParser();
		} else {
			throw new TscpBaseException("TSCP-9465:The instance type is not exist!");
		}
	}

	@Override
	public AbsTscpXmlParser<?> produceInstance() throws TscpBaseException {
		return null;
	}

	
	/**
	 * 
	 * @return
	 */
	public synchronized static AbsTscpFactory<ItscpXmlParser<?>> getInstance() {
	
		if (parserFactory == null)
			parserFactory = new TscpParserFactory();
	
		return parserFactory;
	}

	@Override
	public <P> P produceInstance(Class<P> clazz) throws TscpBaseException {
		try {
			return clazz.newInstance();
		} catch (Throwable ex) {
			throw new TscpBaseException(ex);
		}
	}

	/*
	 * @Override public AbsMedaitionPool<?> produceInstance(Class<?> clazz)
	 * throws TscpBaseException { return null; }
	 */

}
