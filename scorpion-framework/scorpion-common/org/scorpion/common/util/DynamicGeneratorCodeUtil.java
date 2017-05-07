package org.scorpion.common.util;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.scorpion.api.exception.TscpBaseException;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: system constant information </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class DynamicGeneratorCodeUtil {
	
	
	
	/**
	 * @param templateFile 模板文件
	 * 
	 * @param dataModel 数据模型
	 * 
	 * @param out 输出流，不提供流关闭操作
	 * 
	 * @throws TscpBaseException 异常操作
	 */
	public static void generatorCodeByConfiguration(File templateFile,Object dataModel,Writer out) throws TscpBaseException{
		
		if(!templateFile.exists())
			throw new TscpBaseException("TSCP-9073:can't find file template ["+templateFile.getAbsolutePath()+"]");
		
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
		
		try {
			configuration.setDirectoryForTemplateLoading(templateFile.getParentFile());
		}catch (IOException e){
			throw new TscpBaseException("TSCP-7654：load file template failure !",e);
		}
		
		configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_22));
		
		try {
			Template template = configuration.getTemplate(templateFile.getName());
			template.process(dataModel, out);
		} catch (Exception e) {
			throw new TscpBaseException("TSCP-8965:generate code exception !",e);
		}
	}

}
