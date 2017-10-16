package org.scorpion.common.datahandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.scorpion.api.kernel.ABigDataDecoderPipeline;
import org.scorpion.api.kernel.SecurityEnum;
import org.scorpion.common.security.ScorpionSecurityFactory;


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
public class FileDownLoadHandler {
	
	
	static String BOUNDARY = "-------AHV2ymHFg03ehDbqgZCaKO6jyHCDB--";
	
	/**
	 * @param req
	 * 
	 * @param resp
	 * 
	 * @param opposite
	 * 
	 * @param fileName
	 * 
	 * @throws ServletException
	 * 
	 * @throws IOException
	 */
	public static void DownLoadFileByRequestDispatcher(HttpServletRequest req,HttpServletResponse resp,String opposite,String fileName) throws ServletException, IOException{
		resp.setContentType("application/x-download");
		String filenamedisplay = URLEncoder.encode(fileName, "UTF-8");
		resp.addHeader("Content-Disposition", "attachment;filename="+ filenamedisplay);
		if(opposite == null)
			throw new FileNotFoundException("scorpion-1034:下载文件路径不能为空");
		RequestDispatcher dis = req.getRequestDispatcher(opposite);
		if (dis != null) {
			dis.forward(req, resp);
		}
		resp.flushBuffer();
	}
	
	
	
	
	/**
	 * 
	 * @param req
	 * 
	 * @param resp
	 * 
	 * @param absolutelyPath 文件的绝对路径
	 * 
	 * @param fileName 提供给用户下载的文件名称
	 * 
	 * @throws IOException
	 */
	public static void DownLoadFileByFileStream(HttpServletRequest req,HttpServletResponse resp,String absolutelyPath,String fileName) throws IOException{

		if(absolutelyPath == null)
			throw new FileNotFoundException("scorpion-1034:下载文件路径不能为空");
		
		if(fileName != null&&fileName.split(BOUNDARY).length>1){
			try {
				DownLoadFileBySecurityFileStream(req,resp,absolutelyPath,fileName.split(BOUNDARY)[0]);
			} catch (Exception e) {
				throw new IOException(e);
			}
			return;
		}
		resp.reset();
		resp.setContentType("application/x-download");
		String filedisplay = null;
		if (req.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			filedisplay = URLEncoder.encode(fileName, "UTF-8");
		} else {
			filedisplay = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		}
		resp.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
		OutputStream outp = null;
		FileInputStream in = null;
		try {
			outp = resp.getOutputStream();
			in = new FileInputStream(absolutelyPath);
			byte[] b = new byte[1024*64];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
		}finally {
			if (in != null) {
				in.close();
			}
			if (outp != null) {
				outp.close();
			}
		}
	}
	
	
	/**
	 * @param req
	 * @param resp
	 * @param absolutelyPath
	 * @param fileName
	 * @throws Exception 
	 */
	protected static void DownLoadFileBySecurityFileStream(HttpServletRequest req,HttpServletResponse resp,
			String absolutelyPath,String fileName) throws Exception{
		
		if(absolutelyPath == null)
			throw new FileNotFoundException("scorpion-1034:下载文件路径不能为空");
		resp.reset();
		resp.setContentType("application/x-download");
		String filedisplay = null;
		if (req.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			filedisplay = URLEncoder.encode(fileName, "UTF-8");
		} else {
			filedisplay = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		}
		resp.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
		OutputStream outp = null;
		FileInputStream in = null;
		StringBuilder sb = new StringBuilder("0");
		long n = 0;
		try {
			outp = resp.getOutputStream();
			in = new FileInputStream(absolutelyPath);
			byte[] b = new byte[1024*1024];
			int len = 0;
			while ((len = in.read(b)) > 0) {
				//秘钥暂时写死，后期修改
				byte[] securityByte = encrypt(b,0,len,SecurityEnum.DecoderAlgorithmType.DES.name(),"xAsVnvGdVJg=");
				outp.write(securityByte);
				n = n + securityByte.length;
				sb.append(",").append(n);
			}
			byte[] headBytes = new byte[1024*32+4];
			writeFileEnd(sb.toString(),headBytes);
			outp.write(headBytes);
			outp.flush();
		}finally {
			if (in != null) {
				in.close();
			}
			if (outp != null) {
				outp.close();
			}
		}
	}
	
	/**
	 * @param fileIndex
	 * @param out
	 * @throws IOException
	 */
	protected static void writeFileEnd(String fileIndex,byte[] headBytes) throws IOException{
	
		if(headBytes == null)
			headBytes = new byte[1024*32+4];
		byte[] index1 = new byte[4];
		byte[] body = new byte[1024*32];
		if(fileIndex.getBytes().length>body.length){
			JOptionPane.showMessageDialog(null,"索引溢出");
			return ;
		}
		int temlen = fileIndex.getBytes().length;
		index1[0] = (byte) (temlen & 0xff);
		index1[1] = (byte) (temlen >> 8 & 0xff);
		index1[2] = (byte) (temlen >> 16 & 0xff);
		index1[3] = (byte) (temlen >> 24 & 0xff);
		System.arraycopy(index1, 0, headBytes, 0, 4);
		System.arraycopy(fileIndex.getBytes(), 0, headBytes, 4, fileIndex.getBytes().length);
	}
	
	
	/**
	 * @param tempData
	 * @param off
	 * @param len
	 * @param algorithm
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] tempData,int off,int len,String algorithm,String key) throws Exception{
	    	
		if(SecurityEnum.DecoderAlgorithmType.DES.name().equals(algorithm)){
			ABigDataDecoderPipeline decoderPipeline = ScorpionSecurityFactory.getDataDecoder(algorithm);
			decoderPipeline.setKey(key);
			decoderPipeline.setAlgorithm(SecurityEnum.DecoderAlgorithmType.DES.name());
			return decoderPipeline.encrypt(tempData,off,len);
		}else if(SecurityEnum.DecoderAlgorithmType.RSA.name().equals(algorithm)){
			ABigDataDecoderPipeline decoderPipeline = ScorpionSecurityFactory.getDataDecoder(algorithm);
			decoderPipeline.setKey(key);
			decoderPipeline.setAlgorithm(SecurityEnum.DecoderAlgorithmType.RSA.name());
			return decoderPipeline.encrypt(tempData,off,len);
		}else if(SecurityEnum.DecoderAlgorithmType.DSA.name().equals(algorithm)){
			ABigDataDecoderPipeline decoderPipeline = ScorpionSecurityFactory.getDataDecoder(algorithm);
			decoderPipeline.setKey(key);
			decoderPipeline.setAlgorithm(SecurityEnum.DecoderAlgorithmType.DSA.name());
			return decoderPipeline.encrypt(tempData,off,len);
		}else{
			byte[] temp = new byte[len];
			System.arraycopy(tempData, off, temp, 0, len);
			return temp;
		}
	}


}
