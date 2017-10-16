package org.scorpion.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.scorpion.api.util.ScorpionSequenceUtil;
import org.scorpion.common.datahandler.ScorpionDataHandler;

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
public class DomcumentUtil {
	
	
	
	/**
	 * @param resources
	 * @param tempDir
	 * @return
	 * @throws IOException
	 */
	public static String generateScanZipFile(List<ResourceInfo> resources,String tempDir) throws IOException{
		
		String zipName = ScorpionSequenceUtil.generateSequeueString();
		
		File tempFile = new File(tempDir+File.separator+zipName);
		if(!tempFile.exists())
			tempFile.mkdirs();
		
		for(ResourceInfo resource: resources){
			File srcFile = new File(resource.getAbsolutePath());
			OutputStream out = null;
			InputStream in = null;
			try{
				File desFile = new File(tempFile.getPath()+File.separator+"Files"+File.separator+srcFile.getName());
				if(!desFile.getParentFile().exists())
					desFile.getParentFile().mkdirs();
				out = new FileOutputStream(tempFile.getPath()+File.separator+"Files"+File.separator+srcFile.getName());
				in = new FileInputStream(srcFile);
				int len;
				byte[] b = new byte[1024];
				while((len = in.read(b))>0){
					out.write(b, 0, len);
				}
			}finally{
				if(out != null)
					out.close();
				if(in != null)
					in.close();
			}
		}
		String desZip = tempDir+File.separator+zipName+".zip";
		createDescribetFile(resources,tempFile.getPath()+File.separator+"Main.xml");
		ScorpionDataHandler.zipFile(tempFile.getPath(), desZip);
		ScorpionDataHandler.deleteFileDirectory(tempFile.getPath());
		return desZip;
	}
	
	
	
	/**
	 * @param resources
	 * @param des
	 * @throws IOException
	 */
	public static void createDescribetFile(List<ResourceInfo> resources,String des) throws IOException{
		
		File desFile = new File(des);
		
		if(!desFile.getParentFile().exists())
			desFile.getParentFile().mkdirs();
		
		Writer writer = null;
		try{
			writer = new FileWriter(desFile);
			writer.write(generateDomInfo(resources));
		}finally{
			if(writer != null)
				writer.close();
		}
		
		String mateInfo = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
				"<MetaRoot xs:SchemaLocation=\"metadata.xsd\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema-instance\">"+
				"<DocID>8dd2ac6aa23946e39f845db77e795dfc</DocID>"+
				"<Title>测试文档</Title><Author>Suwell</Author>"+
				"<Subject>测试</Subject><Abstract>一个测试文档</Abstract><ModDate>2015-01-01</ModDate>"+
				"<DocUsage></DocUsage><Keywords><Keyword>测试</Keyword></Keywords><CustomDatas><CustomData Name=\"Url\"></CustomData>"+
				"</CustomDatas></MetaRoot>";
		File metaFile = new File(desFile.getParent()+File.separator+"Meta.xml");
		Writer writerMetaFile = null;
		try{
			writerMetaFile = new FileWriter(metaFile);
			writerMetaFile.write(mateInfo);
		}finally{
			if(writerMetaFile != null)
				writerMetaFile.close();
		}
	}
	
	
	/**
	 * @param resources
	 * 
	 * @return
	 */
	public static String generateDomInfo(List<ResourceInfo> resources){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("FileRoot");
		root.addAttribute("Type", "wenjian.tongyong");
		root.addAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema-instance");
		root.addAttribute("xs:SchemaLocation", "packagefile.xsd");
		Element metadata = root.addElement("Metadata");
		metadata.setText("Meta.xml");
		Element docBody = root.addElement("DocBody");
		for(int i=0;i<resources.size();i++){
			Element component = docBody.addElement("Component");
			component.addAttribute("ID", resources.get(i).getId()==null?i+1+"":resources.get(i).getId()+"");
			Element file = component.addElement("File");
			file.addAttribute("Title", resources.get(i).getTitle());
			file.addAttribute("Format", resources.get(i).getFormat());
			file.addElement("FileLoc").setText(resources.get(i).getFileLoc());;
		}
		return doc.asXML();
	}
	
	
}
