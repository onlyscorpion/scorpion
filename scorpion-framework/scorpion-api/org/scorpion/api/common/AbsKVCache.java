package org.scorpion.api.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.util.SystemUtils;

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
public abstract class AbsKVCache {
	
	
	public abstract void destroy();
	
	
	/**
	 * @description object convert byte ...
	 * 
	 * @param obj
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 * 
	 * @throws IOException
	 */
	protected byte[] objectConvertByte(Object obj) throws TscpBaseException{
		
		ObjectOutput oop = null;
		ByteArrayOutputStream bos = null;
		
		try{
			SystemUtils.argumentSerializableValidate(obj);
		    bos = new ByteArrayOutputStream();
			oop = new ObjectOutputStream(bos);
			oop.writeObject(obj);
			return bos.toByteArray();
		}catch(Throwable e){
			throw new TscpBaseException(e);
		}finally{
			try {
				if(oop != null)
					oop.close();
				if(bos != null)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * @param b
	 * @return
	 * @throws TscpBaseException
	 */
	protected Object byteConvertObj(byte[] b)throws TscpBaseException{
		
		try {
			if(b == null|| b.length <1)
				return null;
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b));
			return in.readObject();
		} catch (Throwable e) {
			throw new TscpBaseException(e);
		}
		
	}
	
	

}
