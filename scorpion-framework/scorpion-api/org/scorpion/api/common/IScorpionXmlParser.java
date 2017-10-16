package org.scorpion.api.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.scorpion.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this factory class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface IScorpionXmlParser<T> {
	
	/**
	 * 
	 * @Description Object transfer to string
	 * 
	 * @param t
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public String parseObject2Xml(T t,AbsScorpionConfigFileHandler<String,T> handler)throws ScorpionBaseException;
	
	
	/**
	 * @Description String transfer to object
	 * 
	 * @param xml
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public T parseXml2Object(String xml,AbsScorpionConfigFileHandler<T,String> processor)throws ScorpionBaseException;
	
	
	/**
	 * @param in
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public T parseXml2Object(InputStream in,AbsScorpionConfigFileHandler<T,InputStream> processor)throws ScorpionBaseException;
	
	
	/**
	 * @param configs
	 * 
	 * @param handler
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public void parseXml2Object(List<File> configs,AbsScorpionConfigFileHandler<T,File> handler)throws ScorpionBaseException;
	
	
	/**
	 * @param configPath
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public InputStream getConfigFileInputStream(String configPath) throws FileNotFoundException;
	
	
	
	/**
	 * @param file
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public InputStream getConfigFileInputStream(File file) throws FileNotFoundException;
	

}
