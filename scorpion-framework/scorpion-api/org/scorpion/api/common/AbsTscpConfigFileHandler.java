package org.scorpion.api.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.scorpion.api.exception.TscpBaseException;
import org.xml.sax.helpers.DefaultHandler;

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
public abstract class AbsTscpConfigFileHandler<T,E> extends DefaultHandler{
	
	
	
	 /**
	  * @description parser document
	  * 
	  * @param in
	  * 
	  * @throws TscpBaseException
	  */
	 protected void setParseDocumentStream(InputStream in) throws TscpBaseException{
		 
		 try{
			 SAXParserFactory factory = SAXParserFactory.newInstance();  
			 SAXParser parser = factory.newSAXParser();  
			 parser.parse(in, this);
		 }catch(Exception e){
			 throw new TscpBaseException("TSCP-8098:Parse configuration file exception!",e);
		 }
	 }
	 
	 
	 /**
	  * @description 文档处理
	  * 
	  * @param file
	  * 
	  * @throws TscpBaseException
	  */
	 protected void setParseDocumentFile(File file)throws TscpBaseException{
		 try{
		   setParseDocumentStream(new FileInputStream(file));
		 }catch(IOException e){
			 throw new TscpBaseException("TSCP-94321:Open file stream exception, Please check whether the file is used or not exist!",e);
		 }
	 }
	
	 protected List<E> configs;
	 
	 protected List<T> entitys;
	 
	 protected T entity;
	 
	 
	 /**
	  * @param e
	  * 
	  * @return
	  * 
	  * @throws TscpBaseException
	  */
	 public abstract T processor(E e) throws TscpBaseException;
	 
	 
	 /**
	  * @throws TscpBaseException
	  */
	 public void handler() throws TscpBaseException{
		   if(configs == null|| configs.size()<1)
			   return;
		
		   for(E config:configs){
			   processor(config);
		   }
	 }

	/**
	 * @description Setting configuration file information
	 * 
	 * @param configs
	 * 
	 * @return
	 */
	public AbsTscpConfigFileHandler<T,E> setConfigFiles(List<E> configs) {
	
		this.configs = configs;
		return this;
	}
	
	
	/**
	 * @description Collecting result information
	 * 
	 * @param entitys
	 * 
	 * @return
	 */
	public AbsTscpConfigFileHandler<T,E> setEntitys(List<T> entitys){
		this.entitys = entitys;
		return this;
	}
	
	/**
	 * @return
	 */
	public List<T> getEntitys(){
		return this.entitys;
	}

	public T getEntity() {
		return entity;
	}

	public AbsTscpConfigFileHandler<T,E> setEntity(T entity) {
		this.entity = entity;
		return this;
	}
	 
}
