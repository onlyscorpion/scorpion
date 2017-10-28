package com.scorpion.huakerongtong.persistence.configuration;

import com.scorpion.huakerongtong.api.common.AbsScorpionFactory;
import com.scorpion.huakerongtong.api.common.AbsScorpionXmlParser;
import com.scorpion.huakerongtong.api.common.IScorpionXmlParser;
import com.scorpion.huakerongtong.api.configuration.SystemEnumType;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;


/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionParserFactory extends AbsScorpionFactory<IScorpionXmlParser<?>>{

	private static AbsScorpionFactory<IScorpionXmlParser<?>> parserFactory = getInstance();
	
	
	@Override
	public IScorpionXmlParser<?> produceInstance(Object... arg)throws ScorpionBaseException {
		
		if(arg == null || arg.length<1)
			throw new ScorpionBaseException();
		
		if(SystemEnumType.datasourceconfigparser.getValue().equals(arg[0])||arg[0] instanceof ScorpionDataSourceParser){
			return new ScorpionDataSourceParser();
		
		}else if(SystemEnumType.sqlconfigfileparser.getValue().equals(arg[0])||arg[0] instanceof ScorpionSQLFileParser){
			return new ScorpionSQLFileParser();
		
		}else{
			throw new ScorpionBaseException("The instance type is not exist!");
		}
	}

	@Override
	public AbsScorpionXmlParser<?> produceInstance() throws ScorpionBaseException {
		return null;
	}
	
	
	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public synchronized static AbsScorpionFactory<IScorpionXmlParser<?>> getInstance(){
	
		if(parserFactory == null)
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

}
