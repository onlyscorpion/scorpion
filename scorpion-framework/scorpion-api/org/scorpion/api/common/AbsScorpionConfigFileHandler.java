package org.scorpion.api.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.scorpion.api.exception.ScorpionBaseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: this factory class is the parent of all the other factory</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsScorpionConfigFileHandler<T,E> extends DefaultHandler{
	
	
	
	 /**
	  * @description parser document
	  * 
	  * @param in
	  * 
	  * @throws ScorpionBaseException
	  */
	 protected void setParseDocumentStream(InputStream in) throws ScorpionBaseException{
		 
		 try{
			 SAXParserFactory factory = SAXParserFactory.newInstance();  
			 SAXParser parser = factory.newSAXParser();  
			 parser.parse(in, this);
		 }catch(Exception e){
			 throw new ScorpionBaseException("scorpion-8098:Parse configuration file exception!",e);
		 }
	 }
	 
	 
	 /**
	  * @description 文档处理
	  * 
	  * @param file
	  * 
	  * @throws ScorpionBaseException
	  */
	 protected void setParseDocumentFile(File file)throws ScorpionBaseException{
		 try{
		   setParseDocumentStream(new FileInputStream(file));
		 }catch(IOException e){
			 throw new ScorpionBaseException("scorpion-94321:Open file stream exception, Please check whether the file is used or not exist!",e);
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
	  * @throws ScorpionBaseException
	  */
	 public abstract T processor(E e) throws ScorpionBaseException;
	 
	 
	 /**
	  * @throws ScorpionBaseException
	  */
	 public void handler() throws ScorpionBaseException{
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
	public AbsScorpionConfigFileHandler<T,E> setConfigFiles(List<E> configs) {
	
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
	public AbsScorpionConfigFileHandler<T,E> setEntitys(List<T> entitys){
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

	public AbsScorpionConfigFileHandler<T,E> setEntity(T entity) {
		this.entity = entity;
		return this;
	}
	 
}
