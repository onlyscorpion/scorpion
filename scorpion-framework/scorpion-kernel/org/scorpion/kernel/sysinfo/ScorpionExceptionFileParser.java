package org.scorpion.kernel.sysinfo;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.scorpion.api.common.AbsScorpionConfigFileHandler;
import org.scorpion.api.common.AbsScorpionXmlParser;
import org.scorpion.api.configuration.ExceptionInfo;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.common.context.SystemContext;

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
public class ScorpionExceptionFileParser extends AbsScorpionXmlParser<ExceptionInfo> {

	@Override
	public String parseObject2Xml(ExceptionInfo t,AbsScorpionConfigFileHandler<String, ExceptionInfo> handler)throws ScorpionBaseException {
		return handler.processor(t);
	}

	@Override
	public ExceptionInfo parseXml2Object(String xml,AbsScorpionConfigFileHandler<ExceptionInfo, String> handler)throws ScorpionBaseException {
		return handler.processor(xml);
	}

	@Override
	public ExceptionInfo parseXml2Object(InputStream in,AbsScorpionConfigFileHandler<ExceptionInfo, InputStream> handler)throws ScorpionBaseException {
		ExceptionInfo exceptionInfo = handler.processor(in);
		return exceptionInfo;
	}

	@Override
	public void parseXml2Object(List<File> configs,AbsScorpionConfigFileHandler<ExceptionInfo, File> handler)throws ScorpionBaseException {
		handler.setConfigFiles(configs).setEntitys(new ArrayList<ExceptionInfo>()).handler();
		((SystemContext) SystemContext.getApplicationContext()).getExceptionsInfo().addAll(handler.getEntitys());
	}

}
