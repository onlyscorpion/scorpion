package com.scorpion.huakerongtong.api.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionFileStreamHandler {
	
	
	
	/**
	 * 
	 * @param path
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	public static String getFileContent(String path) throws IOException{
		
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(new File(path)));
			String tmp = null;
			StringBuilder sb = new StringBuilder();
			while((tmp = br.readLine()) != null){
				sb.append(tmp);
			}
			return sb.toString();
		}finally{
			closeFileReaderHandler(br);
		}
	}
	
	
	
	/**
	 * @param reader
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void closeFileReaderHandler(Reader reader)throws IOException{
		
		try{
			if(reader != null)
				reader.close();
		}catch(IOException e){
			throw new IOException("scorpion-9907:close file stream exception ！",e);
		}
	}
	
	/**
	 * @param in
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void closeFileStreamHandler(InputStream in)throws ScorpionBaseException{
		
		try{
			if(in != null)
				in.close();
		}catch(IOException e){
			throw new ScorpionBaseException("scorpion-9908:关闭文件流异常！");
		}
	
	}

}
