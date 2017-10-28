package com.scorpion.huakerongtong.api.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.ApplicationContext;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.api.util.ScorpionFileStreamHandler;

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
public abstract class AbsScorpionXmlParser<T> implements IScorpionXmlParser<T>{
	
	protected ApplicationContext systemContext ;
	
	
	/**
	 * @throws FileNotFoundException 
	 * 
	 * @throws ScorpionBaseException 
	 * 
	 * @description get configuration file stream
	 * 
	 * @Time 2015-06-01
	 */
	public InputStream getConfigFileInputStream(String configPath) throws  FileNotFoundException{
	
			return getConfigFileInputStream(checkFileExists(new File(configPath)));
		
	};
	
	/**
	 * @param file
	 * @return
	 * @throws FileNotFoundException OPEN FILE STREAM EXCETION,PLEASE CHECK IF THE FILE IS VALID OR NOT !
	 */
	public InputStream getConfigFileInputStream(File file) throws FileNotFoundException{
		
			return new FileInputStream(checkFileExists(file));
	}
	
	
	/**
	 * @description Get configuration file content ....
	 * 
	 * @param configPath
	 * 
	 * @return
	 * 
	 * @throws IOException
	 * 
	 * @throws ScorpionBaseException
	 */
	public String getConfigContentByPath(String configPath) throws ScorpionBaseException, IOException{
	
		BufferedReader br = null;
	
		try{
		
			br = new BufferedReader(new FileReader(checkFileExists(new File(configPath))));
			StringBuffer sb = new StringBuffer();
			String temp = null;
		
			while((temp = br.readLine())!=null){
				sb.append(temp);
			}
		
			return sb.toString();
	
		}catch(IOException e){
			throw new ScorpionBaseException("scorpion-9007:GET CONFIGURATION FILE FAILURE !",e);
		}finally{
			ScorpionFileStreamHandler.closeFileReaderHandler(br);
		}
	}
	
	
	/**
	 * @description 检查文件合法性
	 * 
	 * @param file
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 * @throws FileNotFoundException 
	 */
	public File checkFileExists(File file) throws FileNotFoundException{
	
		if(!file.exists())
			throw new FileNotFoundException("scorpion-6098:The file["+file+"] not found, please check configuration !");
		
		PlatformLogger.info("扫描到配置文件["+file.getAbsolutePath()+"]");
		
		return file;
	
	}
	
	
}
