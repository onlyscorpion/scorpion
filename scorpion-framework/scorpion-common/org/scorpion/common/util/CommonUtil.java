package org.scorpion.common.util;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import javax.imageio.ImageIO;

import org.scorpion.api.exception.ScorpionBaseException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: system constant information </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class CommonUtil {
	
	
	/**
	 * @description 获取屏幕的宽度
	 * 
	 * @return
	 */
	public static double getScreenWidth(){
		
		return Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}	
	
	
	/**
	 * @description 获取屏幕的宽度
	 * 
	 * @return
	 */
	public static double getScreenHigth(){
		
		return Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static int getDefaultWinWidthSize(){

		return (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-300;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static int getDefaultWinHigthSize(){

		return (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-250;
	}
	
	
	/**
	 * @param matrix
	 * @param format
	 * @param stream
	 * @throws IOException
	 */
	protected static void writeToStream(BitMatrix matrix, String format,OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "+ format);
		}
	}
	
	/**
	 * @param matrix
	 * @param format
	 * @param file
	 * @throws IOException
	 */
	private static void writeToFile(BitMatrix matrix, String format, File file)throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "+ format + " to " + file);
		}
	}
	
	/**
	 * @param matrix
	 * @return
	 */
	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		return image;
	}
      
	
	/**
	 * @param content
	 * @param filePath
	 * @throws IOException
	 * @throws WriterException
	 */
	public static void generateQRCode(String content,String filePath) throws IOException, WriterException{
		
		MultiFormatWriter multiFormatWrite = new MultiFormatWriter();
		Hashtable<EncodeHintType,String> hints = new Hashtable<EncodeHintType,String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = multiFormatWrite.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
		File file = new File(filePath);
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		writeToFile(bitMatrix, "jpg", file);
	}
	
	
	/**
	 * @param content
	 * @param filePath
	 * @param width
	 * @param high
	 * @throws IOException
	 * @throws WriterException
	 */
	public static void generateQRCode(String content,String filePath,int width,int high) throws IOException, WriterException{
		
		MultiFormatWriter multiFormatWrite = new MultiFormatWriter();
		Hashtable<EncodeHintType,String> hints = new Hashtable<EncodeHintType,String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = multiFormatWrite.encode(content, BarcodeFormat.QR_CODE, width, high, hints);
		File file = new File(filePath);
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		writeToFile(bitMatrix, "jpg", file);
	}
	
	
	public static byte[] O2Byte(Object obj) throws ScorpionBaseException, IOException{
		/*
		if(obj instanceof Serializable)
			throw new ScorpionBaseException("对象为实现序列化接口");*/
		
		ObjectOutputStream oos = null; ByteArrayOutputStream bos = null;
		try{
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
		}finally{
			if(oos != null) oos.close();
		}
		return bos.toByteArray();
	}
	
	/**
	 * @param obj
	 * @return
	 * @throws ScorpionBaseException
	 * @throws IOException
	 */
	public static String O2String(Object obj) throws ScorpionBaseException, IOException{
		return new BASE64Encoder().encode(O2Byte(obj));
	}
	
	public static  Object B2Object(byte[] b) throws IOException, ClassNotFoundException{
		ObjectInputStream ois = null; ByteArrayInputStream ins = null;
		try{
			ins = new ByteArrayInputStream(b);
			ois = new ObjectInputStream(ins);
			return ois.readObject();
		}finally{
			if(ins != null) ins.close();
			if(ois != null) ois.close();
		}
	}
	
	
	/**
	 * @param str
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object S2Object(String str) throws IOException, ClassNotFoundException{
		return B2Object(new BASE64Decoder().decodeBuffer(str));
	}

}
