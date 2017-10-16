package org.scorpion.common.datahandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.scorpion.api.common.AbsDataRandomAccess;

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
public class ScorpionStringRandomAccessReader extends AbsDataRandomAccess{
	
	

	public ScorpionStringRandomAccessReader(int size){
		dataBuffer = new LinkedBlockingQueue<Map<String,byte[]>>(size);
	}
	
	

	@Override
	public int randomAccessread(byte[] _b, int offset, int size)throws IOException {
		
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int randomAccessread(byte[] _b, int offset) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int randomAccessread(byte[] _b) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}


  
	@Override
	public Map<String,Object> randomAccessread(int offset, int size) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

}
