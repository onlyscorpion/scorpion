package org.scorpion.api.configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ExceptionInfo implements Serializable{

	private static final long serialVersionUID = 3820832934847832247L;

	private Map<String,ExceptionDetail> exceptionmap ;
	

	
	/**
	 * @param code
	 * 
	 * @return
	 */
	public ExceptionDetail getExceptionDetailByCode(String code){
		
		if(exceptionmap == null)
			return null;
		
		return exceptionmap.get(code);
	}
	
	
	
	/**
	 * @param code
	 * 
	 * @return
	 */
	public String getContentByCode(String code){
		
		if(exceptionmap == null||exceptionmap.get(code)==null)
			return null;
		
		return exceptionmap.get(code).getContent();
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public Map<String, ExceptionDetail> getExceptionmap() {
		
		if(exceptionmap == null)
			exceptionmap = new HashMap<String,ExceptionDetail>();
		
		return exceptionmap;
	}




	public class ExceptionDetail{
		
		 private String key;
		 
		 private String content;
		 
		 private String resource;
	
		public String getKey() {
			return key;
		}
	
		public void setKey(String key) {
			this.key = key;
		}
	
		public String getContent() {
			return content;
		}
	
		public void setContent(String content) {
			this.content = content;
		}
	
		public String getResource() {
			return resource;
		}
	
		public void setResource(String resource) {
			this.resource = resource;
		}
	} 

}
