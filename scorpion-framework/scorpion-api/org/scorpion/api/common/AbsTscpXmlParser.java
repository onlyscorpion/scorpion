package org.scorpion.api.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ApplicationContext;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.TscpFileStreamHandler;

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
public abstract class AbsTscpXmlParser<T> implements ItscpXmlParser<T>{
	
	protected ApplicationContext systemContext ;
	
	
	/**
	 * @throws FileNotFoundException 
	 * 
	 * @throws TscpBaseException 
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
	 * @throws TscpBaseException
	 */
	public String getConfigContentByPath(String configPath) throws TscpBaseException, IOException{
	
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
			throw new TscpBaseException("TSCP-9007:GET CONFIGURATION FILE FAILURE !",e);
		}finally{
			TscpFileStreamHandler.closeFileReaderHandler(br);
		}
	}
	
	
	/**
	 * @description 检查文件合法性
	 * 
	 * @param file
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 * @throws FileNotFoundException 
	 */
	public File checkFileExists(File file) throws FileNotFoundException{
	
		if(!file.exists())
			throw new FileNotFoundException("TSCP-6098:The file["+file+"] not found, please check configuration !");
		
		PlatformLogger.info("扫描到配置文件["+file.getAbsolutePath()+"]");
		
		return file;
	
	}
	
	
}
