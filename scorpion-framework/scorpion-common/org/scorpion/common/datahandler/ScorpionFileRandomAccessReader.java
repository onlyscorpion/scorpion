package org.scorpion.common.datahandler;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

import org.scorpion.api.common.AbsDataRandomAccess;
import org.scorpion.api.exception.ScorpionBaseException;

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
public class ScorpionFileRandomAccessReader extends AbsDataRandomAccess{
	
	private File file;
	private int cachesize;
	protected int lastOffset;
	
	/**
	 * @param cachesize
	 * @param filePath
	 * @throws ScorpionBaseException
	 */
	public ScorpionFileRandomAccessReader(int cachesize,String filePath) throws ScorpionBaseException{
		
		if(filePath == null)
			throw new ScorpionBaseException("scorpion-4907：The cutting file don't allow to be null !");
		this.file = new File(filePath);
		
		if(this.cachesize <0){
			this.cachesize = 0;
			return;
		}else
			this.cachesize = cachesize;
	
		dataBuffer = new LinkedBlockingQueue<Map<String,byte[]>>(this.cachesize);
	}
	
	
	/**
	 * @param filePath
	 * 
	 * @throws ScorpionBaseException
	 */
	public ScorpionFileRandomAccessReader(String filePath) throws ScorpionBaseException{
		
		if(filePath == null)
			throw new ScorpionBaseException("scorpion-4907：The cutting file don't allow to be null !");
		this.file = new File(filePath);
		
		if(this.cachesize <0){
			this.cachesize = 0;
			return;
		}
		
	}
	
	
	/**
	 * @param file
	 * @throws ScorpionBaseException
	 */
	public ScorpionFileRandomAccessReader(File file) throws ScorpionBaseException{
		
		if(file == null)
			throw new ScorpionBaseException("scorpion-4907：The cutting file don't allow to be null !");
		this.file = file;
		
		if(this.cachesize <0){
			this.cachesize = 0;
			return;
		}
	}
	

	@Override
	public int randomAccessread(byte[] _b, int offset) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * @param size
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Map<String,Object> randomAccessread(int size) throws IOException, InterruptedException {
		
		if(cachesize == 0){
			byte[] b = ScorpionDataHandler.cutFileToPieceByOffset(file.getPath(), lastOffset, size);
			if(b == null)
				return null;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("offset", lastOffset);
			map.put("size", b.length);
			map.put("data", b);
			lastOffset = lastOffset+b.length;
			return map;
		}else{
			return getDatafromQueneCache(size);
		}
	
	}
	
	
	/**
	 * @param size
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private Map<String,Object> getDatafromQueneCache(int size) throws IOException, InterruptedException{
		if(dataBuffer.size()>0){
			Map<String,byte[]> dataMap = dataBuffer.take();
			for(Entry<String,byte[]>entry:dataMap.entrySet()){
				return generateRetnMap(entry);
			}
		}
		return getDataFromFile(size);
	}
	
	
	/**
	 * @param size
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private Map<String,Object> getDataFromFile(int size) throws IOException, InterruptedException{
		
		RandomAccessFile randomAccess = new RandomAccessFile(file,"rw");
		try{
			byte[] _b = new byte[size];
			int len = 0;
			randomAccess.seek(lastOffset);
			while((len = randomAccess.read(_b))>0){
				Map<String,byte[]> map = new HashMap<String,byte[]>();
				map.put(lastOffset+","+len, Arrays.copyOf(_b, len));
				lastOffset = lastOffset + len;
				dataBuffer.put(map);
				if(dataBuffer.size()+1 > cachesize){
					for(Entry<String,byte[]>entry:dataBuffer.take().entrySet()){
						return generateRetnMap(entry);
					}
				}
			}
			if(dataBuffer.size()>0){
				for(Entry<String,byte[]>entry:dataBuffer.take().entrySet()){
					return generateRetnMap(entry);
				}
				return null;
			}
			else 
				return null;
		}finally{
			if(randomAccess != null)
				randomAccess.close();
		}
	}
	
	
	/**
	 * @param entry
	 * @return
	 */
	private Map<String,Object> generateRetnMap(Entry<String,byte[]>entry){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("offset", Integer.parseInt(entry.getKey().split(",")[0]));
		map.put("size",Integer.parseInt(entry.getKey().split(",")[1]));
		map.put("data", entry.getValue());
		return map;
	}
	
	
	@Override
	public int randomAccessread(byte[] _b) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int read() throws IOException {
		return 0;
	}


	@Override
	public synchronized Map<String,Object> randomAccessread(int offset, int size) throws IOException, InterruptedException {
		
		if(cachesize == 0){
			Map<String,Object> temp = new HashMap<String,Object>();
			byte[] _b = ScorpionDataHandler.cutFileToPieceByOffset(file.getPath(), offset, size);
			temp.put("offset", offset);
			temp.put("size",offset+_b.length);
			temp.put("data",_b);
			return temp;
		}else{
			if(dataBuffer.size()>0){
				Map<String,byte[]> dataMap = dataBuffer.take();
				for(Entry<String,byte[]>entry:dataMap.entrySet())
					if(dataMap.containsKey(offset+","+size)){
						return generateRetnMap(entry);
					}
			}
			RandomAccessFile randomAccess = new RandomAccessFile(file,"rw");
			try{
				byte[] _b = new byte[size];
				int len = 0;
				randomAccess.seek(offset);
				while((len = randomAccess.read(_b))>0){
					Map<String,byte[]> map = new HashMap<String,byte[]>();
					offset = offset + len;
					map.put(offset-len+","+len, Arrays.copyOf(_b, len));
					dataBuffer.put(map);
					if(dataBuffer.size()+1 > cachesize){
						for(Entry<String,byte[]>entry:dataBuffer.take().entrySet()){
							return generateRetnMap(entry);
						}
					}
				}
				if(dataBuffer.size()>0){
					for(Entry<String,byte[]>entry:dataBuffer.take().entrySet()){
						return generateRetnMap(entry);
					}
					return null;
				}
				else 
					return null;
			}finally{
				if(randomAccess != null)
					randomAccess.close();
			}
		}
	}


	@Override
	public int randomAccessread(byte[] _b, int offset, int size)throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void close() throws IOException {
		dataBuffer.clear();
		super.close();
	}
	

}
