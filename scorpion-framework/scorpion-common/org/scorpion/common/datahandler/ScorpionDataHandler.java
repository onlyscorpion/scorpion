package org.scorpion.common.datahandler;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipInputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.util.ScorpionSequenceUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

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
public class ScorpionDataHandler {
	
	
		
	public static String byteToString(byte[] b){
	   return new BASE64Encoder().encode(b);
	}
	
	public static byte[] stringToByte(String str) throws IOException{
		return new BASE64Decoder().decodeBuffer(str);
	}
	
	
	/**
	 * 32byte format to 16byte
	 * @param b
	 * @return
	 */
	public static byte[] transform32To16 (byte[] b) throws Exception{
		
		if(b.length<=0||b==null)
			throw new Exception("The byte of zip data can't be null !");
		byte[] zb = new byte[16];
		for(int i=0;i<16;i++){
			zb[i] = (byte) Integer.parseInt(new String(b,i*2,2), 16);
		}
		return zb;
	}
	
	/**
	 * 16byte transform to 32 byte
	 * @param b
	 * @return
	 */
	public static String transform16To32(byte[] b){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<b.length;i++){
			int temp = b[i] & 0xff;
			if(temp<0x10){
				sb.append("0");
			}
			sb.append(Integer.toHexString(temp));
		}
		return sb.toString();
	}
	
	
	/**
	 * 对象转字符串
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String objectToString(Object object) throws Exception{
		ByteArrayOutputStream bos = null;
		ObjectOutputStream out = null;
		try{
		 bos = new ByteArrayOutputStream();
		 out = new ObjectOutputStream(bos);
		 out.writeObject(object);
		}finally{
			if(bos!=null)
				bos.close();
			if(out!=null)
				out.close();
		}
		return byteToString(bos.toByteArray());
	}
	
	/**
	 * 字符串转对象
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Object stringToObject(String str) throws Exception{
		
		ByteArrayInputStream bis = null;
		ObjectInputStream in = null;
		try{
			bis = new ByteArrayInputStream(stringToByte(str));
			in =new ObjectInputStream(bis);
		}finally{
			if(bis!=null)
				bis.close();
			if(in!=null)
				in.close();
		}
		    return in.readObject();
	}
	
	
	/**
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public static byte[] ungzipByteFlow(byte[] b) throws Throwable{
		
		GZIPInputStream gzin = null;
		ByteArrayOutputStream bos = null;
		try{
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
	    bos = new ByteArrayOutputStream();
		gzin = new GZIPInputStream(bis);
		int l;
		byte[] tmp = new byte[1024];
		while((l=gzin.read(tmp))>0){
			bos.write(tmp, 0, l);
		}
		}finally{
			if(gzin!=null)
				gzin.close();
			if(bos!=null)
				bos.close();
		}
		return bos.toByteArray();
	}
	
	/**
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public static byte[] gzipByteFlow(byte[] b)throws Exception{
		GZIPOutputStream gzout = null;
		ByteArrayOutputStream bos = null;
		try{
		    bos = new ByteArrayOutputStream();
			gzout = new GZIPOutputStream(bos);
			gzout.write(b);
		}finally{
			if(gzout!=null)
				gzout.close();
			if(bos!=null)
				bos.close();
		}
		return bos.toByteArray();
	}
	
	/**
	 * 压缩文件操作，传入需要压缩的文件
	 * @param file 需要压缩的文件
	 * @return
	 * @throws Exception
	 */
	public static byte[] zipFile(File file) throws Exception{
		
		ByteArrayOutputStream bos = null;
		ZipOutputStream zos = null;
		try{
	    bos = new ByteArrayOutputStream();
	    zos = new ZipOutputStream(bos);
		zipFile(file,zos,"");
		}finally{
			if(bos!=null)
				bos.close();
			if(zos!=null)
				zos.close();
		}
		return bos.toByteArray();
	}
	
	
	/**
	 * 压缩文件通过路径，返回压缩或的文件字节流
	 * @param path 文件存放路径
	 * @return
	 * @throws Exception
	 */
	public static byte[] zipFile(String path)throws Exception{
		return zipFile(new File(path));
	}
	
	
	/**
	 * 解压文件到物理磁盘，返回类型为空
	 * @param b 压缩的文件字节流，
	 * @param destinationPath 文件解压路径
	 * @throws Exception
	 */
	public static void unzipFile(byte[] b,String destinationPath)throws Exception{
		ByteArrayInputStream bis = null;
		ZipInputStream zis = null;
		try{
		 bis = new ByteArrayInputStream(b);
		 zis = new ZipInputStream(bis);
		 unzipFile(destinationPath,zis);
		}finally{
			if(bis!=null)
				bis.close();
			if(zis!=null)
				zis.close();
		}
	}
	
	
	/**
	 * @param destinationPath
	 * @param zis
	 * @throws IOException
	 */
	private static void unzipFile(String destinationPath,ZipInputStream zis) throws IOException{
		
		java.util.zip.ZipEntry zipEntry = zis.getNextEntry();
		while(zipEntry!=null){
			File file = new File(destinationPath+File.separator+zipEntry.getName());
			if(!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			OutputStream out =new FileOutputStream(file);
			try{
			byte[] b = new byte[1024];
			int l;
			while((l=zis.read(b))>0){
				out.write(b,0,l);
			}
			}finally{
				if(out!=null)
					out.close();
				 zis.closeEntry();
			}
			zipEntry = zis.getNextEntry();
		}
		
		
	}
	
	/**
	 *  用zip压缩字节流
	 * @param data
	 * @param fileName 文件名称 例如  vcft_ABCDA.xml
	 * @return
	 * @throws Exception
	 */
	public static byte[] zipByteFlow(byte[] data,String fileName) throws Exception{
		  ByteArrayOutputStream bos = null;
		  ZipOutputStream zip = null;
		  try {
		   bos = new ByteArrayOutputStream();
		   zip = new ZipOutputStream(bos);
		   ZipEntry entry = new ZipEntry(fileName);
		   entry.setSize(data.length);
		   zip.putNextEntry(entry);
		   zip.write(data);
		   zip.closeEntry();
		  } catch (Exception e) {
			  throw new Exception("压缩字节流异常",e);
		  }finally{
			  if(bos!=null)
				 bos.close();
			  if(zip!=null)
				 zip.close();
		  }
		  return bos.toByteArray();
	}
	
	/**
	 * 用zip压缩字节流，由于没有传压缩实体名称，系统会统一生成默认名称(zip)
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static byte[] zipByteFlow(byte[] data) throws Exception{
		return zipByteFlow(data,"zip");
	}
	
	
	/**
	 * 用ZIP解压字节流
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public static byte[] unZipFlow(byte[] data) throws Exception {
		  ByteArrayInputStream bis = null;
		  ZipInputStream zip = null;
		  ByteArrayOutputStream baos = null;
		  try {
		       bis = new ByteArrayInputStream(data);
		       zip = new ZipInputStream(bis);
		       baos = new ByteArrayOutputStream();
		   while (zip.getNextEntry() != null) {
		      byte[] buf = new byte[1024];
		      int num = -1;
		      while ((num = zip.read(buf, 0, buf.length)) != -1) {
		          baos.write(buf, 0, num);
		        }
		     }
		  } catch (Exception e) {
			  throw new Exception("解压字节流异常",e);
		  }finally{
			  if(zip!=null)
			    zip.close();
			  if(bis!=null)
			    bis.close();
		  }
		  return baos.toByteArray();
		 }

	
	
	/**
	 * @param file
	 * @param out
	 * @param baseDir
	 * @throws Exception
	 */
    private static void zipFile(File file,ZipOutputStream out,String baseDir) throws IOException{
		
    	if(!file.exists())
			throw new FileNotFoundException("scorpion-4165:The zip target file not exist !");
    	
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File f:files){
				if(f.isDirectory())
					zipFile(f,out,baseDir+f.getName()+File.separator);
				else{
					BufferedInputStream bis = null;
					try{
						out.putNextEntry(new ZipEntry(baseDir+f.getName()));
						bis = new BufferedInputStream(new FileInputStream(f));
						byte[] b = new byte[1024];
						int l;
						while((l=bis.read(b))>0){
							out.write(b, 0, l);
						}
					}finally{
						if(bis!=null)
							bis.close();
					}
				}
			}
		}
	}
    
    
    /**
     * 
     * @param propertiesName
     * @return
     * @throws ScorpionBaseException
     * @throws IOException
     */
    public static Map<Object,Object> getPropertyInfo(String propertiesName) throws ScorpionBaseException{
    	
    	if(propertiesName == null)
    		throw new ScorpionBaseException("scorpion-4978:The name of properties don't allow to be null !");
    	
    	InputStream in = ScorpionDataHandler.class.getResourceAsStream("/"+propertiesName);
    	
    	if(in == null)
    		throw new ScorpionBaseException("scorpion-4799:The file["+propertiesName+"] can't find ! ");
    	
    	Properties properties = new Properties();
    
    	try {
			properties.load(in);
		} catch (IOException e) {
			throw new ScorpionBaseException("scorpion-8904:Load properties file exception !",e);
		}
    
    	Map<Object,Object> map = new HashMap<Object,Object>();
    
    	for(Entry<Object,Object>entry:properties.entrySet()){
    		map.put(entry.getKey(), entry.getValue());
    	}
    
    	return map;
    }
    
    
    
    /**
     * @Description get file 
     * @param filePath
     * @param offset
     * @param size
     * @return
     * @throws IOException 
     */
    public static byte[] cutFileToPieceByOffset(String filePath,int offset,int size) throws IOException{
    	
    	File file = new File(filePath);
    	if(!file.exists())
    		throw new FileNotFoundException("scorpion-3076:File ["+filePath+"] can't find !");
    	
    	RandomAccessFile randomAccess = null;
    	
    	try{
    	    randomAccess = new RandomAccessFile(file,"r");
    		randomAccess.seek(offset);
    		byte[] _b = new byte[size];
    		int len = randomAccess.read(_b);
    		if(len <=0 )
    			return null;
    		byte[] rebyte = Arrays.copyOf(_b, len);
    		return rebyte;
    	}finally{
    		if(randomAccess != null)
    			randomAccess.close();
    	}
    	
    }
    
    
    /**
     * @param b
     * @param offset
     * @param len
     * @param filePath
     * @throws IOException
     */
    public static void RandomAccessWriter(byte[] b,int offset,int len,String filePath) throws IOException{
    	
    	File file = new File(filePath);
    	if(!file.getParentFile().exists())
    		file.getParentFile().mkdirs();
    	if(!file.exists())
    		file.createNewFile();
    	RandomAccessFile randomAcess = null;
    	try{
    		randomAcess = new RandomAccessFile(file,"rw");
    		randomAcess.seek(offset);
    		randomAcess.write(b);
    	}finally{
    		if(randomAcess != null)
    			randomAcess.close();
    	}
    	
    }
    
    
    /**
     * @param content
     * @param offset
     * @param size
     * @return
     * @throws IOException 
     */
    public static byte[] cutStringToPieceByOffset(String content,int offset,int size) throws IOException{
    	
    	byte[] _b = content.getBytes();
    	
    	if(_b.length<=offset)
    		return null;
    	
    	if(offset+size <= _b.length){
    		byte[] temp = new byte[size];
    		System.arraycopy(_b, offset, temp, 0, size);
    		return temp;
    	}else{
    		byte[] temp = new byte[_b.length-offset];
    		System.arraycopy(_b, offset, temp, 0, _b.length-offset);
    		return temp;
    	}
    }
    
    
    /**
     * @param _b
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws ScorpionBaseException 
     */
    public static byte[] addMD5CodeToHead(byte[] _b) throws Throwable{
    	byte[] md5 = ScorpionSequenceUtil.generateMd5EncryptByte(_b);
    	byte[] _c = new byte[_b.length+16];
    	System.arraycopy(md5, 0, _c, 0, 16);
    	System.arraycopy(_b, 0, _c, 16, _b.length);
		return _c;
    }
   
    /**
     * @param b
     * @return
     */
    public static byte[] getMd5MessageDataHead(byte[] b){
    	return Arrays.copyOf(b, 16);
    }
    
    
    /**
     * @param b
     * @return
     */
    public static byte[] getMessageBody(byte[] b){
    	byte[] body = new byte[b.length - 16];
    	System.arraycopy(b, 16, body, 0, body.length);
    	return body;
    }
    
    /**
     * @param filePath
     * @return
     * @throws FileNotFoundException 
     */
    public static int getTotalBlocks(String filePath,int size) throws FileNotFoundException{
    	
    	File file = new File(filePath);
    	if(!file.exists())
    		throw new FileNotFoundException("scorpion-4489:The file ["+filePath+"]can't found ...");
    	
    	int ts = (int) (file.length()/size);
    	
    	if(file.length()%size>0)
    		ts = ts + 1;
    	
    	return ts;
    	
    }
    
    /**
     * @param filePath
     * @param b
     * @param sequence
     * @param size
     * @throws IOException
     */
    public static void RandomAccessWriter(String filePath,byte[] b,int sequence,int size) throws IOException{
    	RandomAccessWriter(b,sequence*size,size,filePath);
    }
    
    
    /**
     * @param srcFilePath
     * @param desFilePath
     * @throws IOException
     */
    public static void FileWriter(String srcFilePath,String desFilePath)throws IOException{
    
    	File srcFile = new File(srcFilePath);
    	if(!srcFile.exists())
    		throw new FileNotFoundException("scorpion-6089:The file ["+srcFile+"] can't found ...");
    	File desFile = new File(desFilePath);
    	
    	if(!desFile.getParentFile().exists())
    		desFile.getParentFile().mkdirs();
    	if(!desFile.exists())
    		desFile.createNewFile();
    	
    	InputStream in = null;
    	OutputStream out = null;
    	
    	try{
    		in = new FileInputStream(srcFile);
    		out = new FileOutputStream(desFile,true);
    		byte[] b = new byte[1024];
    		int len;
    		while((len = in.read(b))>0){
    			out.write(b, 0, len);
    		}
    	}finally{
    		if(in != null)
    			in.close();
    		if(out != null)
    			out.close();
    	}
    }
    
    /**
     * @param b
     * @param desFilePath
     * @throws IOException
     */
    public static void FileWriter(byte[] b,String desFilePath)throws IOException{
    
    	File desFile = new File(desFilePath);
    	if(!desFile.getParentFile().exists())
    		desFile.getParentFile().mkdirs();
    	if(!desFile.exists())
    		desFile.createNewFile();
    	
    	OutputStream out = null;
    	
    	try{
    		out = new FileOutputStream(desFile,true);
    		out.write(b);
    	}finally{
    		if(out != null)
    			out.close();
    	}
    }
    
    
	/**
	 * @param filePath
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String generateMd5EncryptForFile(String filePath) throws NoSuchAlgorithmException, IOException{
		
		byte[] _b = ScorpionSequenceUtil.generateMd5EncryptForFile(new File(filePath));
		
		return ScorpionDataHandler.transform16To32(_b);
	}

	
	/**
	 * @param _b
	 * 
	 * @param md5code
	 * 
	 * @return
	 * 
	 * @throws NoSuchAlgorithmException 
	 */
	public static boolean validateDataByMD5(byte[] btInput,String md5code) throws NoSuchAlgorithmException{
		
		if(md5code == null||"".equals(md5code))
			throw new NullPointerException("scorpion-4129:The md5 code don't allow to be null !");
		
		MessageDigest md5Digest = MessageDigest.getInstance(ScorpionSequenceUtil.encryptionType);
		md5Digest.update(btInput);
		byte[] retn = md5Digest.digest();
		return md5code.equals(transform16To32(retn));
	}
	
	
	/**
	 * @param src 需要压缩的文件路径
	 * @param des 压缩的目标文件存放路径
	 * @throws IOException
	 */
	public static void zipFile(String src,String des) throws IOException{
		
		File srcFile = new File(src);
		if(!srcFile.exists())
			throw new FileNotFoundException("scorpion-4165:The zip target file not exist !");
		File desFile = new File(des);
		if(!desFile.getParentFile().exists())
			desFile.getParentFile().mkdirs();
		OutputStream out = null;
		ZipOutputStream zos = null;
		try{
			out = new FileOutputStream(desFile);
			zos = new ZipOutputStream(out);
			zipFile(srcFile,zos,"");
		}finally{
			if(zos != null)
				zos.close();
			if(out != null)
				out.close();
		}
	}
	
	public static boolean deleteFileDirectory(String path){
		
		File file = new File(path);
		if(!file.exists())
			return false;
		if(!file.isDirectory()){
			file.delete();
			return true;
		}else{
			for(File f:file.listFiles()){
				if(f.isDirectory())
					deleteFileDirectory(f.getPath());
				else
					f.delete();
			}
		}
		file.delete();
		return true;
	}
	
	
	/**
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("deprecation")
	public static byte[] readDataFromFile(String filePath) throws IOException{
		
		File file = new File(filePath);
		if(!file.exists())
			throw new FileNotFoundException("The file ["+filePath+"] no found ...");
		
		ByteOutputStream bos = new ByteOutputStream();
		byte[] b = new byte[1024];
		int len;
		InputStream in = new FileInputStream(file);
		try{
			while((len = in.read(b))>0){
				bos.write(b, 0, len);
			}
			return bos.toByteArray();
		}finally{
			if(in != null)
				in.close();
			if(bos != null)
				bos.close();
		}
	}
	
	
	/**
	 * @param filePath
	 * @param ecode
	 * @return
	 * @throws IOException
	 */
	public static String ReadStringFromFile(String filePath,String ecode) throws IOException{
		return new String(readDataFromFile(filePath),ecode);
	}
	
	
    
}
