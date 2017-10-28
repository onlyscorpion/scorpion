package com.scorpion.huakerongtong.api.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.util.SystemUtils;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
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
	 * @throws ScorpionBaseException
	 * 
	 * @throws IOException
	 */
	protected byte[] objectConvertByte(Object obj) throws ScorpionBaseException{
		
		ObjectOutput oop = null;
		ByteArrayOutputStream bos = null;
		
		try{
			SystemUtils.argumentSerializableValidate(obj);
		    bos = new ByteArrayOutputStream();
			oop = new ObjectOutputStream(bos);
			oop.writeObject(obj);
			return bos.toByteArray();
		}catch(Throwable e){
			throw new ScorpionBaseException(e);
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
	 * @throws ScorpionBaseException
	 */
	protected Object byteConvertObj(byte[] b)throws ScorpionBaseException{
		
		try {
			if(b == null|| b.length <1)
				return null;
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b));
			return in.readObject();
		} catch (Throwable e) {
			throw new ScorpionBaseException(e);
		}
		
	}
	
	

}
