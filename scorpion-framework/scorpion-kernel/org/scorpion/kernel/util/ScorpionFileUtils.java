package org.scorpion.kernel.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedList;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.scorpion.api.common.ScorpionLogger;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.log.ScorpionLogFactory;
import org.xml.sax.SAXException;

/**
 * 天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.Scorpion.common
 * <p>
 * File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 taiji.com.cn
 * </p>
 * <p>
 * Company: taiji.com.cn
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionFileUtils {

	private static final ScorpionLogger log = ScorpionLogFactory.getLogger(ScorpionFileUtils.class);

	private static String Scorpion_FILE = null;

	/**
	 * 获取目录下的所有文件
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-3-30下午12:07:26
	 * @param directory
	 * @return
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static LinkedList<String> listFiles(String directory)throws ScorpionBaseException {
		return listFiles(directory, false);
	}

	public static LinkedList<String> listFiles(String directory, boolean skipDir)throws ScorpionBaseException {
		LinkedList<String> files = new LinkedList<String>();
		File dir = new File(directory);

		if (!dir.exists()) {
			throw new ScorpionBaseException("UT-06001:目录" + directory + "不存在");
		}
		if (!dir.isDirectory()) {
			throw new ScorpionBaseException("UT-06002:" + directory + "不是目录");
		}

		findSubFile(new File(directory), files, skipDir);
		return files;
	}

	private static void findSubFile(File f, LinkedList<String> files,boolean skipDir) throws ScorpionBaseException {
		if (!f.exists()) {
			throw new ScorpionBaseException("UT-06003:文件或目录" + f + "不存在或已经删除");
		}

		if (f.isFile()) {
			files.add(f.getAbsolutePath());
		} else if (f.isDirectory()) {
			if (!skipDir) {
				files.add(f.getAbsolutePath());
			}
			for (File subFile : f.listFiles()) {
				findSubFile(subFile, files, skipDir);
			}
		} else {
			// 获取其它类型的对象
		}
	}

	/**
	 * 读取资源
	 * 
	 * @Description 以输入流的形式读取资源
	 * @Time 创建时间:2011-9-1上午9:46:27
	 * @param name
	 * @param clazz
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static InputStream getInputStream(String name, Class<?> clazz) {
		InputStream is = null;
		if (clazz != null) {
			name = "/" + clazz.getPackage().getName().replace('.', '/') + "/"+ name;
			is = clazz.getResourceAsStream(name);
			if (is == null) {
				is = clazz.getClassLoader().getResourceAsStream(name);
				printFile(name, null, clazz.getClassLoader());
			} else {
				printFile(name, clazz, null);
			}
		} else {
			is = ScorpionFileUtils.class.getResourceAsStream(name);
			if (is == null) {
				is = ScorpionFileUtils.class.getClassLoader().getResourceAsStream(name);
				if (is == null) {
					try {
						is = new FileInputStream(name);
					} catch (FileNotFoundException ex) {
						is = null;
					}
				}
				printFile(name, null, ScorpionFileUtils.class.getClassLoader());
			} else {
				printFile(name, ScorpionFileUtils.class, null);
			}
		}
		return is;
	}

	private static void printFile(String file, Class<?> clazz,ClassLoader loader) {
		URL url = null;
		if (clazz != null) {
			url = clazz.getResource(file);
		} else {
			url = loader.getResource(file);
		}
		if (url != null) {
			log.debug("开始读取文件" + url);
		}
	}

	/**
	 * 读取资源
	 * 
	 * @Description 以输入流读取器的形式读取资源
	 * @Time 创建时间:2011-8-29下午4:39:51
	 * @param name
	 * @para clazz
	 * @param charsetName
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static InputStreamReader getInputStreamReader(String name,Class<?> clazz, String charsetName) throws ScorpionBaseException {
		InputStreamReader reader = null;
		try {
			if (charsetName == null) {
				reader = new InputStreamReader(getInputStream(name, clazz));
			} else {
				reader = new InputStreamReader(getInputStream(name, clazz),charsetName);
			}
		} catch (UnsupportedEncodingException ex) {
			throw new ScorpionBaseException("UT-06004:不支持的字符集类型" + charsetName, ex);
		}

		return reader;
	}

	/**
	 * 获取资源URL
	 * 
	 * @Description 获取资源URL
	 * @Time 创建时间:2011-8-29下午4:35:40
	 * @param name
	 * @param clazz
	 * @return
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static URL getResource(String name, Class<?> clazz)throws ScorpionBaseException {
		URL url = null;
		if (clazz != null) {
			url = clazz.getResource(name);
		} else {
			url = ScorpionFileUtils.class.getResource(name);
		}

		if (url == null) {
			try {
				File f = new File(name);
				if (f.exists()) {
					url = f.toURI().toURL();
				}
			} catch (MalformedURLException ex) {
				throw new ScorpionBaseException("生成文件" + name + "的URL对象时发生错误", ex);
			}
		}

		return url;
	}

	/**
	 * 读取资源URL
	 * 
	 * @Description 读取资源URL
	 * @Time 创建时间:2011-9-1上午10:18:48
	 * @param name
	 * @return
	 * @throws IOException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static Enumeration<URL> getResources(String name) throws IOException {
		return ScorpionFileUtils.class.getClassLoader().getResources(name);
	}

	public static void setScorpionRootPath(String path) throws ScorpionBaseException {
		if (Scorpion_FILE != null) {
			log.debug("根目录已经设置为", Scorpion_FILE, "，不允许修改为", path);
			return;
		}

		URL url = getResource(path, null);

		if (url == null) {
			url = ScorpionFileUtils.class.getResource("/");
			// throw new ScorpionBaseException("设置根目录时发生错误，文件" + path + "不存在");
		}

		try {
			Scorpion_FILE = URLDecoder.decode(url.getPath(), "UTF-8");// 解决路径中含空格、中文问题
		} catch (UnsupportedEncodingException ex) {
			throw new ScorpionBaseException("系统不支持的字符集类型UTF-8", ex);
		}

		File root = new File(Scorpion_FILE);

		if (!root.exists()) {
			throw new ScorpionBaseException("根目录或根文件不存在");
		}

		if (root.isFile()) {
			Scorpion_FILE = root.getParent();
		}

		if (Scorpion_FILE.endsWith("/") || Scorpion_FILE.endsWith("\\")) {
			Scorpion_FILE = Scorpion_FILE.substring(0, Scorpion_FILE.length() - 1);
		}
	}

	/**
	 * 获取临时文件目录
	 * 
	 * @Time 创建时间:2011-9-27上午9:26:08
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getTempFileDir() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * 获取用户当前目录
	 * 
	 * @Time 创建时间:2011-9-27上午9:26:08
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getUserDir() {
		return System.getProperty("user.dir");
	}

	/**
	 * 读取文本文件数据
	 * 
	 * @Description 相关说明
	 * @param rs
	 * @param charsetName
	 * @return
	 * @throws ScorpionBaseException
	 * @Time 创建时间:2013-4-3上午10:42:50
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String loadTxtFile(InputStream rs, String charsetName)throws ScorpionBaseException {
	
		StringBuilder str = new StringBuilder();
		BufferedReader reader = null;
		String code = null;

		try {
			reader = new BufferedReader(new InputStreamReader(rs, charsetName));
			while ((code = reader.readLine()) != null) {
				str.append(code).append("\n");
			}
		} catch (IOException ex) {
			throw new ScorpionBaseException("UT-06006:读取文件时发生错误", ex);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ex) {
				throw new ScorpionBaseException("UT-06007:关闭文件时发生错误", ex);
			}
		}

		return str.toString();
	}

	/**
	 * 读取文本文件数据
	 * 
	 * @Description 当文本数据文件较小时可使用此方法一次性将文件读入到内存中；<br>
	 *              如果文件较大，此方法则不适合，有可能造成内存溢出，同时处理较率不高，应当直接使用下面的方法进行处理:
	 * 
	 *              <pre>
	 *              BufferedReader reader = new BufferedReader(readFile(...));
	 *              String str = null;
	 *              try{
	 *              while((str = reader.readLine)!=null){
	 *              	......
	 *              }catch(IOException ex){
	 *              	throw new ScorpionBaseException("......",ex);
	 *              }finally{
	 *              	if(reader != null){
	 *              		reader.close();
	 *              	}
	 *              }
	 * </pre>
	 * 
	 *              自行处理文件内容读取和流关闭
	 * @Time 创建时间:2011-9-1上午11:27:42
	 * @param name
	 * @param clazz
	 * @param charsetName
	 * @return
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String loadTxtFile(String name, Class<?> clazz,String charsetName) throws ScorpionBaseException {
	
		StringBuilder str = new StringBuilder();
		BufferedReader reader = null;
		String code = null;

		try {
			reader = new BufferedReader(readFile(name, clazz, charsetName));
			while ((code = reader.readLine()) != null) {
				str.append(code).append("\n");
			}
		} catch (IOException ex) {
			throw new ScorpionBaseException("UT-06006:读取文件" + name + "时发生错误", ex);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ex) {
				throw new ScorpionBaseException("UT-06007:关闭文件" + name + "时发生错误",
						ex);
			}
		}

		return str.toString();
	}

	/**
	 * 使用BufferedReader读取文本文件
	 * 
	 * @Description 使用BufferedReader读取文本文件
	 * @Time 创建时间:2011-11-2下午1:32:55
	 * @param name
	 * @param clazz
	 * @param charsetName
	 * @return
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static InputStreamReader readFile(String name, Class<?> clazz,String charsetName) throws ScorpionBaseException {
		final InputStream is = getInputStream(name, clazz);

		if (is == null) {
			throw new ScorpionBaseException("文件" + name + "不存在或无法读取");
		}

		InputStreamReader reader = null;
		try {
			if (charsetName != null) {
				reader = new InputStreamReader(is, charsetName);
			} else {
				reader = new InputStreamReader(is);
			}
		} catch (UnsupportedEncodingException ex) {
			throw new ScorpionBaseException("UT-06004:不支持的字符集" + charsetName, ex);
		}

		return reader;
	}

	/**
	 * 读取资源文件
	 * 
	 * @Description 读取资源文件
	 * @Time 创建时间:2011-9-3下午1:35:11
	 * @param name
	 * @param clazz
	 * @return
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static byte[] loadFile(String name, Class<?> clazz)throws ScorpionBaseException {
		return loadFromStream(getInputStream(name, clazz));
	}

	/**
	 * 读取资源流
	 * 
	 * @Description 读取资源流
	 * @Time 创建时间:2011-9-3下午1:35:11
	 * @param input
	 * @return
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static byte[] loadFromStream(InputStream input)throws ScorpionBaseException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		int length = -1;
		ScorpionBaseException e = null;
		try {
			while ((length = input.read(buf)) != -1) {
				baos.write(buf, 0, length);
			}
			buf = baos.toByteArray();
		} catch (IOException ex) {
			e = new ScorpionBaseException("UT-06008:读取文件或写入字节流时发生错误", ex);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException ex) {
				e = (e == null ? new ScorpionBaseException("UT-06007:关闭文件时发生错误", ex)
						: e);
			}
		}

		if (e != null) {
			throw e;
		}
		return buf;
	}

	/**
	 * 将数据写入指定的文件中
	 * 
	 * @Time 创建时间:2011-9-29下午10:07:23
	 * @param data
	 * @param file
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static void writeToFile(byte[] data, String file)throws ScorpionBaseException {
		writeToFile(new ByteArrayInputStream(data), file, true);
	}

	/**
	 * 将读取流中的数据写入指定的文件中
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-17下午2:02:30
	 * @param is
	 * @param file
	 * @param autoCloseInputStream
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static void writeToFile(InputStream is, String file,boolean autoCloseInputStream) throws ScorpionBaseException {
		
		FileOutputStream fos = null;
		try {
			final File f = new File(file);
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException ex) {
			throw new ScorpionBaseException("UT-06006:打开文件输入流时发生错误", ex);
		}

		byte[] buf = new byte[8192];
		int len = -1;

		try {
			while ((len = is.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
		} catch (IOException ex) {
			throw new ScorpionBaseException("UT-06008:向文件" + file + "写入数据时发生错误", ex);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ex) {
					throw new ScorpionBaseException("UT-06007:关闭文件" + file
							+ "输出流时发生错误", ex);
				} finally {
					if (autoCloseInputStream) {
						try {
							is.close();
						} catch (IOException ex) {
							throw new ScorpionBaseException(
									"UT-06009:关闭数据读取流时发生错误", ex);
						}
					}
				}
			}
		}
	}

	/**
	 * 从输入流中读取所有数据到内存中
	 * 
	 * @Description 因为将文件中的数据全部读取到内存中，所以此方法不适宜读取大文件，有可能造成内存溢出
	 * @Time 创建时间:2011-9-29下午11:52:04
	 * @param inputStream
	 * @return
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static byte[] readAllDataFromInputStreamToMemory(InputStream inputStream) throws ScorpionBaseException {
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;

		try {
			while ((length = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			buffer = baos.toByteArray();
		} catch (IOException ex) {
			throw new ScorpionBaseException("UT-06010:从输入流中读取信息时发生异常", ex);
		}

		return buffer;
	}

	/**
	 * 将Java对象序列化成文件
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-4-9下午5:12:27
	 * @param obj
	 * @param file
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static void saveObjectToFile(Object obj, String file)throws ScorpionBaseException {
		
		ObjectOutputStream oos = null;

		try {
			oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			oos.writeObject(obj);
		} catch (Exception ex) {
			throw new ScorpionBaseException("UT-06011:序列化对象到文件" + file + "时发生错误",ex);
		} finally {
			try {
				if (oos != null) {
					oos.close();
					oos = null;
				}
			} catch (IOException ex) {
				throw new ScorpionBaseException("UT-06007:关闭文件" + file + "时发生错误",ex);
			}
		}
	}

	/**
	 * 从文件中反序列化Java对象
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-4-9下午5:12:20
	 * @param file
	 * @return
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static Object readObjectFromFile(String file)throws ScorpionBaseException {
	
		ObjectInputStream ois = null;
		Object obj = null;

		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			obj = ois.readObject();
		} catch (Exception ex) {
			throw new ScorpionBaseException("UT-06011:反序列化文件" + file + "中的对象时发生错误",
					ex);
		} finally {
			if (ois != null) {
				try {
					ois.close();
					ois = null;
				} catch (IOException ex) {
					throw new ScorpionBaseException("UT-06007:关闭文件" + file
							+ "时发生错误", ex);
				}
			}
		}

		return obj;
	}

	/**
	 * 使用XSD校验XML格式的正确性
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-10-22下午1:05:40
	 * @param xml
	 *            XML文本
	 * @param clazz
	 *            与XSD文件处理于同一目录的类
	 * @param xsd
	 *            XSD文件路径
	 * @throws ScorpionBaseException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static void validateXMLByXSD(String xml, Class<?> clazz, String xsd)
			throws ScorpionBaseException {
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema schema = null;
		try {
			schema = schemaFactory.newSchema(getResource(xsd, clazz));
		} catch (SAXException ex) {
			throw new ScorpionBaseException("UT-06012:生成XSD文件" + xsd
					+ "的Schema对象时发生错误", ex);
		}
		try {
			schema.newValidator().validate(
					new StreamSource(new StringReader(xml)));
		} catch (IOException ex) {
		} catch (SAXException ex) {
			throw new ScorpionBaseException("UT-06013:XML文本不符合XSD文件" + xsd
					+ "定义的规范", ex);
		}
	}

	/**
	 * 根据平台远程配置文件中的根地址构建Document对象
	 * 
	 * @Description 相关说明
	 * @param fileName
	 * @param noFoundThrowException
	 * @return
	 * @throws ScorpionBaseException
	 * @Time 创建时间:2014年10月28日下午12:38:42
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	/*
	 * public static Document getConfigXmlDoc(String fileName, boolean
	 * noFoundThrowException) throws ScorpionBaseException { final SAXReader reader
	 * = new SAXReader(); final URL url = getConfigURL(fileName,
	 * noFoundThrowException); if (url == null) { return null; } try {
	 * log.info("开始解析配置文件:" + url); return reader.read(url); } catch
	 * (DocumentException e) { throw new ScorpionBaseException("解析配置文件文件" + fileName
	 * + "时发生错误!", e); } }
	 */

	/**
	 * 获取文件的URL对象
	 * 
	 * @Description 相关说明
	 * @param file
	 * @return
	 * @throws ScorpionBaseException
	 * @Time 创建时间:2014年10月28日下午3:36:37
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static URL getFileURL(File file) throws ScorpionBaseException {
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new ScorpionBaseException("获取文件" + file + "的URL地址时发生错误!", e);
		}
	}

	/**
	 * 获取指定URL地址的最有修改时间
	 * 
	 * @Description 相关说明
	 * @param url
	 * @param throwError
	 * @return
	 * @throws ScorpionBaseException
	 * @Time 创建时间:2014年10月28日下午3:36:54
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getLastModified(URL url, boolean throwError)
			throws ScorpionBaseException {
		try {
			return url.openConnection().getLastModified();
		} catch (IOException e) {
			if (throwError) {
				throw new ScorpionBaseException("获取文件最后修改时间时发生错误!", e);
			} else {
				return 0;
			}
		}
	}
}
