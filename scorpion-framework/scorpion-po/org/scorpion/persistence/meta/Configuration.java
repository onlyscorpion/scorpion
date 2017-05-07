package org.scorpion.persistence.meta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.taiji.tscp.api.common.AbsTscpConfigFileHandler;
import com.taiji.tscp.api.common.ItscpXmlParser;
import com.taiji.tscp.api.configuration.DataSourceLis;
import com.taiji.tscp.api.configuration.DataSourceLis.DataSourceInfo;
import com.taiji.tscp.api.exception.TscpBaseException;
import com.taiji.tscp.api.log.PlatformLogger;
import com.taiji.tscp.api.persistence.ITscpPersistenceConfiguration;
import com.taiji.tscp.api.util.Constant;
import com.taiji.tscp.common.command.LoadSQLCommand;
import com.taiji.tscp.common.context.SystemContext;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class Configuration implements ITscpPersistenceConfiguration{
	
	
	private String DATASORUCE_CONF = "conf/tscp-datasource.xml";
	

	/**
	 * 自定义数据源配置文件
	 * 
	 * @param configFile
	 * 
	 * @throws TscpBaseException
	 * 
	 * @Time 2015-06-03上午10:36
	 */
	public static void loadConfiguration(String configFile)throws TscpBaseException{
	
		Configuration persistence = new Configuration();
		
		try {
			persistence.loadDataSourceConfig(configFile,false);
		} catch (FileNotFoundException e) {
			throw new TscpBaseException("TSCP-6082:Datasource configuration file no found !",e);
		}
	
		persistence.loadSQLConfig();
	}
	
	
	/**
	 * @description LOCAL TSCP DEFAULT DATASOURCE CONFIG
	 * 
	 * @throws TscpBaseException
	 * 
	 * @Time 2015-06-03上午10:36
	 */
	public static void loadConfigurationByTscpDefaultSetting()throws TscpBaseException{
	
		Configuration persistence = new Configuration();
		
		try {
			persistence.loadDataSourceConfig(null,true);
		} catch (FileNotFoundException e) {
			throw new TscpBaseException("TSCP-6082:Datasource configuration file no found !",e);
		}
		
		persistence.loadSQLConfig();
	}
	
	
	/**
	 * @description reload sql configuration
	 * 
	 * @throws TscpBaseException
	 */
	public static void reloadSQLConfig() throws TscpBaseException{
		
		Configuration persistence = new Configuration();

		persistence.loadSQLConfig();
	}
	

	/**
	 * @description custom sql configuration
	 * 
	 * @param dataSourceFile
	 * 
	 * @param sqlFiles
	 */
	public static void loadConfiguration(String dataSourceFile,List<String>sqlFiles){
		
	}
	

	/**
	 * @throws TscpBaseException
	 * 
	 * @throws FileNotFoundException 
	 * 
	 * @Time 2015-06-03上午10:36
	 */
	public void loadDataSourceConfig(String dataSourceConfigFile,boolean isLoadSystemDefaultConfig)throws TscpBaseException, FileNotFoundException {
    	
		PlatformLogger.info("Is beginning to initialize datasource configuration");
		File configFile = initDataSourceConfigFile(dataSourceConfigFile,isLoadSystemDefaultConfig);
		ItscpXmlParser<DataSourceLis> logParser = new TscpDataSourceParser();
		logParser.parseXml2Object(logParser.getConfigFileInputStream(configFile), new AbsTscpConfigFileHandler<DataSourceLis, InputStream>() {
		
			@Override
			public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
			
				if(Constant.DATASOURCE.equals(qName)){
					DataSourceInfo datasource = this.getEntity().new DataSourceInfo();
					datasource.setName(attributes.getValue(Constant.NAME));
					datasource.setDriverClassName(attributes.getValue(Constant.DRIVER_CLASS));
					datasource.setUrl(attributes.getValue(Constant.URL));
					datasource.setUseJndiDs((attributes.getValue(Constant.ISJNDI)!=null&&!"".equals(attributes.getValue(Constant.ISJNDI))?Boolean.parseBoolean(attributes.getValue(Constant.ISJNDI)):false));
					datasource.setUser(attributes.getValue(Constant.USERNAME));
					datasource.setPasswd(attributes.getValue(Constant.PASSWD));
					datasource.setMaxActive(Integer.parseInt(attributes.getValue(Constant.MAX_ACTIVE)));
					datasource.setMaxIdle(Integer.parseInt(attributes.getValue(Constant.MAX_IDLE)));
					datasource.setMaxWait(Integer.parseInt(attributes.getValue(Constant.MAX_WAIT)));
					datasource.setInitSize(Integer.parseInt(attributes.getValue(Constant.INIT_SIZE)==null?"0":attributes.getValue(Constant.INIT_SIZE)));
					datasource.setDscp(attributes.getValue(Constant.DSCPT));
					datasource.setConnTimeout(attributes.getValue(Constant.CONN_TIMEOUT)==null?0:Integer.parseInt(attributes.getValue(Constant.CONN_TIMEOUT)));
					datasource.setDumpStack(attributes.getValue(Constant.IS_DUMP_STACK)==null?false:Boolean.parseBoolean(attributes.getValue(Constant.IS_DUMP_STACK)));
					
					if(Boolean.parseBoolean(attributes.getValue(Constant.IS_DEFAULT_DS)))
						this.getEntity().putDefaultDataSource(attributes.getValue(Constant.NAME), datasource, true);
					else
						this.getEntity().putDefaultDataSource(attributes.getValue(Constant.NAME), datasource, false);
				}
			}
			
			@Override
			public DataSourceLis processor(InputStream in)throws TscpBaseException {
			
				this.setParseDocumentStream(in);
				return this.getEntity();
			
			}
		});
    }
	
	
	/**
	 * @param dataSourceConfigFile
	 * 
	 * @param isLoadSystemDefaultConfig
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 * 
	 * @Time 2015-06-03上午10:36
	 */
	private File initDataSourceConfigFile(String dataSourceConfigFile,boolean isLoadSystemDefaultConfig) throws TscpBaseException{
		 
		File configFile = null;
    	
		if(isLoadSystemDefaultConfig)
			configFile = new File(new File(System.getProperty("user.dir")).getParent(),DATASORUCE_CONF);
		else
			configFile = new File(dataSourceConfigFile);
		
		if(System.getProperty("user.web.env")!=null)
			configFile = new File(new File(System.getProperty("user.web.env")),"tscp-datasource.xml");

		if(!configFile.exists())
			throw new TscpBaseException("TSC-6008:The datasource configuration file ["+configFile.getPath()+"] not exist ！");
      
		return configFile;
	}
	

	/**
	 * 
	 * @throws TscpBaseException
	 */
	public Configuration() throws TscpBaseException {
		if(Constant.DEVELOP_MODEL.equals(SystemContext.getApplicationContext().getSystemCoreConfig().getRunModel()))
			LoadSQLCommand.configuration = this;
	}


	@Override
	public void loadSQLConfig() throws TscpBaseException {
		
	}
	
}
