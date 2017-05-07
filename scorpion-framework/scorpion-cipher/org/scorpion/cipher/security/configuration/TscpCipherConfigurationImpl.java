package org.scorpion.cipher.security.configuration;

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

import org.scorpion.api.exception.TscpBaseException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TscpCipherConfigurationImpl extends AbsTscpCipherConfiguration {
	
	private final Map<String, TscpCipherInfo> configMap = new HashMap<String, TscpCipherInfo>();
	
	private String rootPath;
	
	private String certPath;
	
	private String keyStorePath;
	
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private final Lock r = lock.readLock();
	
	private final Lock w = lock.writeLock();
	
	protected TscpCipherConfigurationImpl(){}
	
	public class MyHandler extends DefaultHandler{
		
		@Override
		public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
			if(Constant.Cipher.CIPHERS.equals(qName)){
				w.lock();
			}
			if(Constant.Cipher.CIPHER.equals(qName)){
				TscpCipherInfo tscpCipher = new TscpCipherInfo();
				tscpCipher.setName(attributes.getValue(Constant.Cipher.NAME));
				tscpCipher.setType(attributes.getValue(Constant.Cipher.TYPE));
				tscpCipher.setAlgorithm(attributes.getValue(Constant.Cipher.ALGORITHM));
				tscpCipher.setSignAlgorithm(attributes.getValue(Constant.Cipher.SIGNALGORITHM));
				tscpCipher.setCerPath(attributes.getValue(Constant.Cipher.CERPATH) != null ? TscpCipherConfigurationImpl.this.certPath + attributes.getValue(Constant.Cipher.CERPATH) : null);
				tscpCipher.setCerType(attributes.getValue(Constant.Cipher.CERTYPE));
				tscpCipher.setKeyLength(attributes.getValue(Constant.Cipher.KEYLENGTH) != null ? Integer.parseInt(attributes.getValue(Constant.Cipher.KEYLENGTH)) : null);
				tscpCipher.setKeyStorePath(attributes.getValue(Constant.Cipher.KEYSTOREPATH) != null ? TscpCipherConfigurationImpl.this.keyStorePath + attributes.getValue(Constant.Cipher.KEYSTOREPATH) : null);
				tscpCipher.setKeyStoreType(attributes.getValue(Constant.Cipher.KEYSTORETYPE));
				tscpCipher.setAlias(attributes.getValue(Constant.Cipher.ALIAS));
				tscpCipher.setPassword(attributes.getValue(Constant.Cipher.PASSWORD));
				
				if(!tscpCipher.validate()){
					w.unlock();
					throw new SAXException("TSCP-6002:parse cipher config file exception!");
				}
				configMap.put(tscpCipher.getName(), tscpCipher);
			}
		}
		
		@Override
	    public void endElement (String uri, String localName, String qName)	throws SAXException{
			if(Constant.Cipher.CIPHERS.equals(qName)){
				w.unlock();
			}
	    }
	}
	
	private String getRootPath() throws TscpBaseException{
//		String rootPath = this.getClass().getResource("/").getPath();
//		rootPath = rootPath.substring(1, rootPath.length());
//		return URLDecoder.decode(rootPath, "UTF-8");
		return this.getTscpRootPath("/tscp.xml");
	}
	
	public String getTscpRootPath(String path) throws TscpBaseException {

		String rootPath = null;
		
		URL url = getResource(path, null);

		if (url == null) {
			url = TscpCipherConfigurationImpl.class.getResource("/");
		}

		try {
			rootPath = URLDecoder.decode(url.getPath(), "UTF-8");// 解决路径中含空格、中文问题
		} catch (UnsupportedEncodingException ex) {
			throw new TscpBaseException("系统不支持的字符集类型UTF-8", ex);
		}

		File root = new File(rootPath);

		if (!root.exists()) {
			throw new TscpBaseException("根目录或根文件不存在");
		}

		if (root.isFile()) {
			rootPath = root.getParent();
		}

		if (rootPath.endsWith("/") || rootPath.endsWith("\\")) {
			rootPath = rootPath.substring(0, rootPath.length() - 1);
		}
		
		return rootPath + File.separator;
	}
	
	public static URL getResource(String name, Class<?> clazz) throws TscpBaseException {
		URL url = null;
		if (clazz != null) {
			url = clazz.getResource(name);
		} else {
			url = TscpCipherConfigurationImpl.class.getResource(name);
		}

		if (url == null) {
			try {
				File f = new File(name);
				if (f.exists()) {
					url = f.toURI().toURL();
				}
			} catch (MalformedURLException ex) {
				throw new TscpBaseException("生成文件" + name + "的URL对象时发生错误", ex);
			}
		}

		return url;
	}

	@Override
	public void loadCipherConfiguration()  throws TscpBaseException{
		try {
			this.rootPath = this.getRootPath();
			this.certPath = this.rootPath + Constant.CipherFilePath.CERTIFICATEPATH + File.separator;
			this.keyStorePath = this.rootPath + Constant.CipherFilePath.KEYSTOREPATH + File.separator;
			initParseXml(new File(this.rootPath + Constant.CIPHER_CONFIG_FILE));
		} catch (TscpBaseException e) {
			throw new TscpBaseException("TSCP-6001:load cipher config file exception!", e);
		}
		
	}
	
	@Override
	public void reloadCipherConfiguration()  throws TscpBaseException{
		configMap.clear();
		this.loadCipherConfiguration();
	}
	
	public void initParseXml(File file) throws TscpBaseException{
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
			 throw new TscpBaseException("TSCP-6001:load cipher config file exception!", e);
		 }
	}

	@Override
	public TscpCipherInfo getType(String name) {
		r.lock();
		try{
			return configMap.get(name);
		}finally{
			r.unlock();
		}
	}
}
