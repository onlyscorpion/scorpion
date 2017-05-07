package org.scorpion.api.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.scorpion.api.exception.TscpBaseException;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this factory class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface ItscpXmlParser<T> {
	
	/**
	 * 
	 * @Description Object transfer to string
	 * 
	 * @param t
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public String parseObject2Xml(T t,AbsTscpConfigFileHandler<String,T> handler)throws TscpBaseException;
	
	
	/**
	 * @Description String transfer to object
	 * 
	 * @param xml
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public T parseXml2Object(String xml,AbsTscpConfigFileHandler<T,String> processor)throws TscpBaseException;
	
	
	/**
	 * @param in
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public T parseXml2Object(InputStream in,AbsTscpConfigFileHandler<T,InputStream> processor)throws TscpBaseException;
	
	
	/**
	 * @param configs
	 * 
	 * @param handler
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public void parseXml2Object(List<File> configs,AbsTscpConfigFileHandler<T,File> handler)throws TscpBaseException;
	
	
	/**
	 * @param configPath
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public InputStream getConfigFileInputStream(String configPath) throws FileNotFoundException;
	
	
	
	/**
	 * @param file
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
	public InputStream getConfigFileInputStream(File file) throws FileNotFoundException;
	

}
