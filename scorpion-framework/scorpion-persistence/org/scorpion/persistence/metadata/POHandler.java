package org.scorpion.persistence.metadata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.persistence.ScorpionDataBaseType;
import org.scorpion.api.persistence.ScorpionDataBaseType.KingbaseDataType;
import org.scorpion.common.util.DynamicGeneratorCodeUtil;
import org.scorpion.persistence.util.DbDataTypeConvert;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class POHandler {
	
	Configuration cfg;
	
	
 	
 	public void process(String url,String username,String password) throws IOException, TemplateException, ScorpionBaseException {
 		
 		Map<String,Map<String,String>> maps = DataDecoration.constructAttributeInfo(url,username,password);
 		
 		for(Entry<String,Map<String,String>>entry:maps.entrySet()){
 			PojoClassInfo classInfo = new PojoClassInfo();
 			String  className = entry.getKey().toLowerCase();
 			String newClassName = null;
 			
 			if(className.split("_").length>1){
 				for(int i=0;i<className.split("_").length;i++){
 				    if(i == 0)
 				    	newClassName = Character.toUpperCase(className.split("_")[i].charAt(0))+className.split("_")[i].substring(1, className.split("_")[i].length());
 				    else
 				    	newClassName = newClassName + Character.toUpperCase(className.split("_")[i].charAt(0))+className.split("_")[i].substring(1, className.split("_")[i].length());
 				}
 			}else
 				newClassName = Character.toUpperCase(className.charAt(0))+className.substring(1, className.length());
 			
 	 		classInfo.setClassname(newClassName);
 	 		//classInfo.setImports(new String[]{"java.lang.*"});
 	 		///xtgl.com.taiji.Scorpion.po
 	 		//com.taiji.Scorpion.esb.manager.po
 	 		classInfo.setPackagename("com.taiji.Scorpion.esb.po");
 	 		PhysicalModel model = new PhysicalModel();
 	 		model.setTableName(entry.getKey());
 	 		List<AttributeTemplate> attributes = new ArrayList<AttributeTemplate>();
 	 		
 	 		for(Entry<String,String>entryMap:entry.getValue().entrySet()){
 	 			String field = null;
                String tempField = entryMap.getKey().toLowerCase();
 	 			if(tempField.split("_").length>1){
 	 				for(int i=0;i<tempField.split("_").length;i++){
 	 				    if(i == 0)
 	 				    	field = tempField.split("_")[0];
 	 				    else
 	 				    	field = field + Character.toUpperCase(tempField.split("_")[i].charAt(0))+tempField.split("_")[i].substring(1, tempField.split("_")[i].length());
 	 				}
 	 			}else
 	 				field = tempField;
 	 			
	 	 		AttributeTemplate attribute = new AttributeTemplate();
	 	 		attribute.setAttributeName(field);
	 	 		if(KingbaseDataType.BLOB.name().equals(entryMap.getValue().split("#")[0]))
	 	 			classInfo.getImports().add("java.io.UnsupportedEncodingException");
	 	 		attribute.setAttributeType(DbDataTypeConvert.convertDataType(entryMap.getValue().split("#")[0],ScorpionDataBaseType.kbe_db_type));
	 	 		attribute.setNullenable(entryMap.getValue().split("#")[1]);
	 	 		attribute.setDataDefault(entryMap.getValue().split("#")[2]);
	 	 		attribute.setIsPrimaryKey(entryMap.getValue().split("#")[3]);
	 	 		attribute.setColumnName(entryMap.getKey().toLowerCase());
	 	 		attributes.add(attribute);
 	 		}
	 	 		model.setColumns(attributes);
	 	 		classInfo.setPhysicalModel(model);
	 	 		Map<String, Object> root = new HashMap<String, Object>();
	 	 		root.put("clazz",classInfo);
	 	 		root.put("model",model);
	 	 		root.put("attributes",classInfo.getPhysicalModel().getColumns());
	 	 		write(root,classInfo.getClassname());
 	 		}
 		}
 	
 	
 	/*
 	 * 执行入口
 	 */
 	public static void main(String[] args) throws Throwable {
 		POHandler hfm = new POHandler();
 		//	Connection conn = DriverManager.getConnection("jdbc:kingbase://192.168.1.100:54321/TEST", "zhuchao123", "123");
 		//Map<Object,Object> map  =ScorpionDataHandler.getPropertyInfo("/scorpion-po-generator.properties");
 		//Connection conn = DriverManager.getConnection("jdbc:kingbase://192.168.1.100:54321/TEST11111", "zhuchao123", "123");
 		hfm.process("jdbc:kingbase://192.168.1.100:54321/TEST11111","zhuchao123","123");

 		/*for(Entry<Object,Object>entry:map.entrySet()){
 			hfm.process(((String)entry.getValue()).split("#")[0],((String)entry.getValue()).split("#")[1],((String)entry.getValue()).split("#")[2]);
 		}*/
 	}
 	
 	/*
 	 * 将模版进行指定文件的输出
 	 */
 	public void write(Map<String, Object> root,String name) throws IOException, TemplateException, ScorpionBaseException{
 	
 		Writer out = new OutputStreamWriter(new FileOutputStream(new File("E:\\1\\"+name+"Po.java")));
 		DynamicGeneratorCodeUtil.generatorCodeByConfiguration(new File(this.getClass().getResource("/META-INF/resources/po-template.ftl").getPath()), root, out);
 		out.close();
 	}

}
