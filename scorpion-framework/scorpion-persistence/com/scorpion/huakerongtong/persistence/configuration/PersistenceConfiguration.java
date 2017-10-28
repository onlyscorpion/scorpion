package com.scorpion.huakerongtong.persistence.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import com.scorpion.huakerongtong.api.common.AbsScorpionConfigFileHandler;
import com.scorpion.huakerongtong.api.common.AbsScorpionXmlParser;
import com.scorpion.huakerongtong.api.common.IScorpionXmlParser;
import com.scorpion.huakerongtong.api.configuration.DataSourceLis;
import com.scorpion.huakerongtong.api.configuration.SQLConfig;
import com.scorpion.huakerongtong.api.configuration.SystemEnumType;
import com.scorpion.huakerongtong.api.configuration.DataSourceLis.DataSourceInfo;
import com.scorpion.huakerongtong.api.configuration.SQLConfig.SQLProperty;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.api.persistence.IScorpionPersistenceConfiguration;
import com.scorpion.huakerongtong.api.util.Constant;
import com.scorpion.huakerongtong.common.command.LoadSQLCommand;
import com.scorpion.huakerongtong.common.context.SystemContext;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

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
public class PersistenceConfiguration implements IScorpionPersistenceConfiguration{
	

	/**
	 * 自定义数据源配置文件
	 * 
	 * @param configFile
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @Time 2015-06-03上午10:36
	 */
	public static void loadConfiguration(String configFile)throws ScorpionBaseException{
	
		PersistenceConfiguration persistence = new PersistenceConfiguration();
		
		try {
			persistence.loadDataSourceConfig(configFile,false);
		} catch (FileNotFoundException e) {
			throw new ScorpionBaseException("scorpion-6082:Datasource configuration file no found !",e);
		}
	
		persistence.loadSQLConfig();
	}
	
	
	/**
	 * @description LOCAL Scorpion DEFAULT DATASOURCE CONFIG
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @Time 2015-06-03上午10:36
	 */
	public static void loadConfigurationByScorpionDefaultSetting()throws ScorpionBaseException{
	
		PersistenceConfiguration persistence = new PersistenceConfiguration();
		
		try {
			persistence.loadDataSourceConfig(null,true);
		} catch (FileNotFoundException e) {
			throw new ScorpionBaseException("scorpion-6082:Datasource configuration file no found !",e);
		}
		
		persistence.loadSQLConfig();
	}
	
	
	/**
	 * @description reload sql configuration
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void reloadSQLConfig() throws ScorpionBaseException{
		
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
	 * @throws ScorpionBaseException
	 * 
	 * @throws FileNotFoundException 
	 * 
	 * @Time 2015-06-03上午10:36
	 */
	public void loadDataSourceConfig(String dataSourceConfigFile,boolean isLoadSystemDefaultConfig)throws ScorpionBaseException, FileNotFoundException {
    	
		PlatformLogger.info("Is beginning to initialize datasource configuration");
		File configFile = initDataSourceConfigFile(dataSourceConfigFile,isLoadSystemDefaultConfig);
		@SuppressWarnings("unchecked")
		IScorpionXmlParser<DataSourceLis> logParser = (AbsScorpionXmlParser<DataSourceLis>) ScorpionParserFactory.getInstance().produceInstance(SystemEnumType.datasourceconfigparser.getValue());
		logParser.parseXml2Object(logParser.getConfigFileInputStream(configFile), new AbsScorpionConfigFileHandler<DataSourceLis, InputStream>() {
		
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
			public DataSourceLis processor(InputStream in)throws ScorpionBaseException {
			
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
	 * @throws ScorpionBaseException
	 * 
	 * @Time 2015-06-03上午10:36
	 */
	private File initDataSourceConfigFile(String dataSourceConfigFile,boolean isLoadSystemDefaultConfig) throws ScorpionBaseException{
		 
		File configFile = null;
    	
		if(isLoadSystemDefaultConfig)
			configFile = SystemContext.getApplicationContext().getSystemConfigFile().getDataSourceFile();
		else
			configFile = new File(dataSourceConfigFile);
      
		if(!configFile.exists())
			throw new ScorpionBaseException("TSC-6008:The datasource configuration file ["+configFile.getPath()+"] not exist ！");
      
		return configFile;
	}
	
	

    
	/**
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @Time 2015-06-03上午10:36
	 */
	public void loadSQLConfig()throws ScorpionBaseException{
    
		PlatformLogger.info("Is beginning to load SQL configuration file!");
		@SuppressWarnings("unchecked")
		IScorpionXmlParser<SQLConfig> sqlParser = (AbsScorpionXmlParser<SQLConfig>) ScorpionParserFactory.getInstance().produceInstance(SystemEnumType.sqlconfigfileparser.getValue());
		sqlParser.parseXml2Object(checkSQLConfigFileVaild(SystemContext.getApplicationContext().getSystemConfigFile().getSqlConfigFiles()),new AbsScorpionConfigFileHandler<SQLConfig, File>() {
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
			public SQLConfig processor(File file)throws ScorpionBaseException {
			
				this.setParseDocumentFile(file);
				return this.getEntity();
			}
		});
    }
	
	/**
	 * @param configFiles
	 * @return
	 * @throws ScorpionBaseException
	 */
	private List<File> checkSQLConfigFileVaild(List<File> configFiles) throws ScorpionBaseException{
		
		for(File file:SystemContext.getApplicationContext().getSystemConfigFile().getSqlConfigFiles()){
			
			if(!file.exists())
				throw new ScorpionBaseException("scorpion-9076:SQL configuration file ["+file.getName()+"]not exist ! ");
		
			PlatformLogger.info("扫描到SQL配置文件["+file.getPath()+"]");
		}
	
		return configFiles;
	}
	

	/**
	 * 
	 * @throws ScorpionBaseException
	 */
	public PersistenceConfiguration() throws ScorpionBaseException {
		if(Constant.DEVELOP_MODEL.equals(SystemContext.getApplicationContext().getSystemCoreConfig().getRunModel()))
			LoadSQLCommand.configuration = this;
	}
	
}
