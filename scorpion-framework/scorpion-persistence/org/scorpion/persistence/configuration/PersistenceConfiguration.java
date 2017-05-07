package org.scorpion.persistence.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.scorpion.api.common.AbsTscpConfigFileHandler;
import org.scorpion.api.common.AbsTscpXmlParser;
import org.scorpion.api.common.ItscpXmlParser;
import org.scorpion.api.configuration.DataSourceLis;
import org.scorpion.api.configuration.SQLConfig;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.configuration.DataSourceLis.DataSourceInfo;
import org.scorpion.api.configuration.SQLConfig.SQLProperty;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.persistence.ITscpPersistenceConfiguration;
import org.scorpion.api.util.Constant;
import org.scorpion.common.command.LoadSQLCommand;
import org.scorpion.common.context.SystemContext;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

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
public class PersistenceConfiguration implements ITscpPersistenceConfiguration{
	

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
	
		PersistenceConfiguration persistence = new PersistenceConfiguration();
		
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
	
		PersistenceConfiguration persistence = new PersistenceConfiguration();
		
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
		
		PersistenceConfiguration persistence = new PersistenceConfiguration();

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
		@SuppressWarnings("unchecked")
		ItscpXmlParser<DataSourceLis> logParser = (AbsTscpXmlParser<DataSourceLis>) TscpParserFactory.getInstance().produceInstance(SystemEnumType.datasourceconfigparser.getValue());
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
			configFile = SystemContext.getApplicationContext().getSystemConfigFile().getDataSourceFile();
		else
			configFile = new File(dataSourceConfigFile);
      
		if(!configFile.exists())
			throw new TscpBaseException("TSC-6008:The datasource configuration file ["+configFile.getPath()+"] not exist ！");
      
		return configFile;
	}
	
	

    
	/**
	 * 
	 * @throws TscpBaseException
	 * 
	 * @Time 2015-06-03上午10:36
	 */
	public void loadSQLConfig()throws TscpBaseException{
    
		PlatformLogger.info("Is beginning to load SQL configuration file!");
		@SuppressWarnings("unchecked")
		ItscpXmlParser<SQLConfig> sqlParser = (AbsTscpXmlParser<SQLConfig>) TscpParserFactory.getInstance().produceInstance(SystemEnumType.sqlconfigfileparser.getValue());
		sqlParser.parseXml2Object(checkSQLConfigFileVaild(SystemContext.getApplicationContext().getSystemConfigFile().getSqlConfigFiles()),new AbsTscpConfigFileHandler<SQLConfig, File>() {
			@Override
			public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
		       
				if(Constant.SQL.equals(qName)){
					SQLProperty sqlProperty =  this.getEntity().new SQLProperty();
					sqlProperty.setKey(attributes.getValue(Constant.NAME));
					sqlProperty.setVaule(attributes.getValue(Constant.VALUE));
					sqlProperty.setTables(attributes.getValue(Constant.TABLES)!=null&&!"".equals(attributes.getValue(Constant.TABLES))?attributes.getValue(Constant.TABLES).split(","):new String[]{});
					this.getEntity().putSQLMap(attributes.getValue(Constant.NAME), sqlProperty);
				}
			}
		
			@Override
			public SQLConfig processor(File file)throws TscpBaseException {
			
				this.setParseDocumentFile(file);
				return this.getEntity();
			}
		});
    }
	
	/**
	 * @param configFiles
	 * @return
	 * @throws TscpBaseException
	 */
	private List<File> checkSQLConfigFileVaild(List<File> configFiles) throws TscpBaseException{
		
		for(File file:SystemContext.getApplicationContext().getSystemConfigFile().getSqlConfigFiles()){
			
			if(!file.exists())
				throw new TscpBaseException("TSCP-9076:SQL configuration file ["+file.getName()+"]not exist ! ");
		
			PlatformLogger.info("扫描到SQL配置文件["+file.getPath()+"]");
		}
	
		return configFiles;
	}
	

	/**
	 * 
	 * @throws TscpBaseException
	 */
	public PersistenceConfiguration() throws TscpBaseException {
		if(Constant.DEVELOP_MODEL.equals(SystemContext.getApplicationContext().getSystemCoreConfig().getRunModel()))
			LoadSQLCommand.configuration = this;
	}
	
}
