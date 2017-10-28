package com.scorpion.huakerongtong.cipher.security.configuration;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ScorpionCipherConfigurationImpl extends AbsScorpionCipherConfiguration {
	
	private final Map<String, ScorpionCipherInfo> configMap = new HashMap<String, ScorpionCipherInfo>();
	
	private String rootPath;
	
	private String certPath;
	
	private String keyStorePath;
	
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private final Lock r = lock.readLock();
	
	private final Lock w = lock.writeLock();
	
	protected ScorpionCipherConfigurationImpl(){}
	
	public class MyHandler extends DefaultHandler{
		
		@Override
		public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
			if(Constant.Cipher.CIPHERS.equals(qName)){
				w.lock();
			}
			if(Constant.Cipher.CIPHER.equals(qName)){
				ScorpionCipherInfo ScorpionCipher = new ScorpionCipherInfo();
				ScorpionCipher.setName(attributes.getValue(Constant.Cipher.NAME));
				ScorpionCipher.setType(attributes.getValue(Constant.Cipher.TYPE));
				ScorpionCipher.setAlgorithm(attributes.getValue(Constant.Cipher.ALGORITHM));
				ScorpionCipher.setSignAlgorithm(attributes.getValue(Constant.Cipher.SIGNALGORITHM));
				ScorpionCipher.setCerPath(attributes.getValue(Constant.Cipher.CERPATH) != null ? ScorpionCipherConfigurationImpl.this.certPath + attributes.getValue(Constant.Cipher.CERPATH) : null);
				ScorpionCipher.setCerType(attributes.getValue(Constant.Cipher.CERTYPE));
				ScorpionCipher.setKeyLength(attributes.getValue(Constant.Cipher.KEYLENGTH) != null ? Integer.parseInt(attributes.getValue(Constant.Cipher.KEYLENGTH)) : null);
				ScorpionCipher.setKeyStorePath(attributes.getValue(Constant.Cipher.KEYSTOREPATH) != null ? ScorpionCipherConfigurationImpl.this.keyStorePath + attributes.getValue(Constant.Cipher.KEYSTOREPATH) : null);
				ScorpionCipher.setKeyStoreType(attributes.getValue(Constant.Cipher.KEYSTORETYPE));
				ScorpionCipher.setAlias(attributes.getValue(Constant.Cipher.ALIAS));
				ScorpionCipher.setPassword(attributes.getValue(Constant.Cipher.PASSWORD));
				
				if(!ScorpionCipher.validate()){
					w.unlock();
					throw new SAXException("scorpion-6002:parse cipher config file exception!");
				}
				configMap.put(ScorpionCipher.getName(), ScorpionCipher);
			}
		}
		
		@Override
	    public void endElement (String uri, String localName, String qName)	throws SAXException{
			if(Constant.Cipher.CIPHERS.equals(qName)){
				w.unlock();
			}
	    }
	}
	
	private String getRootPath() throws ScorpionBaseException{
//		String rootPath = this.getClass().getResource("/").getPath();
//		rootPath = rootPath.substring(1, rootPath.length());
//		return URLDecoder.decode(rootPath, "UTF-8");
		return this.getScorpionRootPath("/Scorpion.xml");
	}
	
	public String getScorpionRootPath(String path) throws ScorpionBaseException {

		String rootPath = null;
		
		URL url = getResource(path, null);

		if (url == null) {
			url = ScorpionCipherConfigurationImpl.class.getResource("/");
		}

		try {
			rootPath = URLDecoder.decode(url.getPath(), "UTF-8");// 解决路径中含空格、中文问题
		} catch (UnsupportedEncodingException ex) {
			throw new ScorpionBaseException("系统不支持的字符集类型UTF-8", ex);
		}

		File root = new File(rootPath);

		if (!root.exists()) {
			throw new ScorpionBaseException("根目录或根文件不存在");
		}

		if (root.isFile()) {
			rootPath = root.getParent();
		}

		if (rootPath.endsWith("/") || rootPath.endsWith("\\")) {
			rootPath = rootPath.substring(0, rootPath.length() - 1);
		}
		
		return rootPath + File.separator;
	}
	
	public static URL getResource(String name, Class<?> clazz) throws ScorpionBaseException {
		URL url = null;
		if (clazz != null) {
			url = clazz.getResource(name);
		} else {
			url = ScorpionCipherConfigurationImpl.class.getResource(name);
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

	@Override
	public void loadCipherConfiguration()  throws ScorpionBaseException{
		try {
			this.rootPath = this.getRootPath();
			this.certPath = this.rootPath + Constant.CipherFilePath.CERTIFICATEPATH + File.separator;
			this.keyStorePath = this.rootPath + Constant.CipherFilePath.KEYSTOREPATH + File.separator;
			initParseXml(new File(this.rootPath + Constant.CIPHER_CONFIG_FILE));
		} catch (ScorpionBaseException e) {
			throw new ScorpionBaseException("scorpion-6001:load cipher config file exception!", e);
		}
		
	}
	
	@Override
	public void reloadCipherConfiguration()  throws ScorpionBaseException{
		configMap.clear();
		this.loadCipherConfiguration();
	}
	
	public void initParseXml(File file) throws ScorpionBaseException{
		try{
			 SAXParserFactory factory = SAXParserFactory.newInstance();
			 SAXParser parser = factory.newSAXParser();  
			 parser.parse(file, new MyHandler());
		 }catch(Exception e){
			 try{
				 w.unlock();
			 }catch(Exception e1){
				 //nothing
			 }
			 throw new ScorpionBaseException("scorpion-6001:load cipher config file exception!", e);
		 }
	}

	@Override
	public ScorpionCipherInfo getType(String name) {
		r.lock();
		try{
			return configMap.get(name);
		}finally{
			r.unlock();
		}
	}
}
