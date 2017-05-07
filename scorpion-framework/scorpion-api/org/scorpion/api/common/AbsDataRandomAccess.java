package org.scorpion.api.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsDataRandomAccess extends InputStream{
	
	
	protected LinkedBlockingQueue<Map<String,byte[]>> dataBuffer; 
	
	
	/**
	 * 
	 * @param _b
	 * @param offset
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public abstract Map<String,Object> randomAccessread(int offset,int size)throws IOException,InterruptedException;
	
	/**
	 * 
	 * @param _b
	 * @param offset
	 * @return
	 * @throws IOException
	 */
	public abstract int randomAccessread(byte[] _b,int offset)throws IOException;
	
	/**
	 * @param _b
	 * @param offset
	 * @param size
	 * @return
	 * @throws IOException
	 */
	public abstract int randomAccessread(byte[]_b,int offset, int size)throws IOException;
	
	/**
	 * 
	 * @param _b
	 * @return
	 * @throws IOException
	 */
	public abstract int randomAccessread(byte[] _b)throws IOException;

	
	
	

}
