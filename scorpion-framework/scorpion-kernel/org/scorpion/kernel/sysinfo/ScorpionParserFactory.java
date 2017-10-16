package org.scorpion.kernel.sysinfo;

import org.scorpion.api.common.AbsScorpionFactory;
import org.scorpion.api.common.AbsScorpionXmlParser;
import org.scorpion.api.common.IScorpionXmlParser;
import org.scorpion.api.configuration.ExceptionInfo;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.configuration.ScorpionCoreConfig;
import org.scorpion.api.configuration.ScorpionLogConfig;
import org.scorpion.api.exception.ScorpionBaseException;

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
public class ScorpionParserFactory extends AbsScorpionFactory<IScorpionXmlParser<?>> {

	private static AbsScorpionFactory<IScorpionXmlParser<?>> parserFactory = getInstance();

	@Override
	public IScorpionXmlParser<?> produceInstance(Object... arg)throws ScorpionBaseException {
		if (arg == null || arg.length < 1)
			throw new ScorpionBaseException();
		if (SystemEnumType.logconfigfileparser.getValue().equals(arg[0])|| arg[0] instanceof ScorpionLogConfig) {
			return new ScorpionLogFileParser();
		} else if (SystemEnumType.exceptionconfigparser.getValue().equals(arg[0])|| arg[0] instanceof ExceptionInfo) {
			return new ScorpionExceptionFileParser();
		} else if (SystemEnumType.coreconfigfileparser.getValue().equals(arg[0]) || arg[0] instanceof ScorpionCoreConfig) {
			return new ScorpionCoreConfigFileParser();
		} else {
			throw new ScorpionBaseException("scorpion-9465:The instance type is not exist!");
		}
	}

	@Override
	public AbsScorpionXmlParser<?> produceInstance() throws ScorpionBaseException {
		return null;
	}

	
	/**
	 * 
	 * @return
	 */
	public synchronized static AbsScorpionFactory<IScorpionXmlParser<?>> getInstance() {
	
		if (parserFactory == null)
			parserFactory = new ScorpionParserFactory();
	
		return parserFactory;
	}

	@Override
	public <P> P produceInstance(Class<P> clazz) throws ScorpionBaseException {
		try {
			return clazz.newInstance();
		} catch (Throwable ex) {
			throw new ScorpionBaseException(ex);
		}
	}

	/*
	 * @Override public AbsMedaitionPool<?> produceInstance(Class<?> clazz)
	 * throws ScorpionBaseException { return null; }
	 */

}
