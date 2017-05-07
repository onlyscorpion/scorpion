package org.scorpion.kernel.sysinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.scorpion.api.common.AbsTscpConfigFileHandler;
import org.scorpion.api.common.ItscpXmlParser;
import org.scorpion.api.configuration.ComponentInformation;
import org.scorpion.api.configuration.ExceptionInfo;
import org.scorpion.api.configuration.SystemEnumType;
import org.scorpion.api.configuration.TscpCoreConfig;
import org.scorpion.api.configuration.ExceptionInfo.ExceptionDetail;
import org.scorpion.api.configuration.PlatformConfiguration.Scanner;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpComponent;
import org.scorpion.api.kernel.IAnalyzerResourceRegister;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;
import org.scorpion.kernel.classanalyzer.AnalyzerResourceRegister;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * 自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.tscp.common
 * <p>
 * File: AbsTscpFactory.java create time:2015-5-8下午07:57:37
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
public class SystemAppContextConfigManager extends AbsTscpComponent {

	@Override
	public void start(Map<String, String> arguments) throws TscpBaseException {

		try {

			loadLogConfig();

			loadExceptionConfig();

			loadSystemConfig();

		} catch (FileNotFoundException e) {

			throw new TscpBaseException("TSCP-8709:THE CONFIGURATION FILE NOT FOUND, PLEASE CHECK IF EXIST OR NOT !");
		}
	}

	/**
	 * 
	 * @throws TscpBaseException
	 */
	private void loadLogConfig() throws TscpBaseException {
		PlatformLogger.info("Is beginning to initialize log configuration");
		// @SuppressWarnings("unchecked")
		// ItscpXmlParser<TscpLogConfig> systemconfigparser =
		// (ItscpXmlParser<TscpLogConfig>)
		// TscpParserFactory.getInstance().produceInstance(SystemEnumType.logconfigfileparser.getValue());
		/*
		 * systemconfigparser.parseXml2Object(systemconfigparser.
		 * getConfigFileInputStream
		 * (SystemContext.getInstance().getSystemConfigFile
		 * ().getLogConfigFile()), new AbsTscpConfigFileHandler<TscpLogConfig,
		 * InputStream>() {
		 * 
		 * @Override public TscpLogConfig processor(InputStream in)throws
		 * TscpBaseException { return null; } });
		 */
	}

	/**
	 * 
	 * @throws TscpBaseException
	 */
	private void loadExceptionConfig() throws TscpBaseException {

		PlatformLogger.info("Is beginning to initialize exception configuration");
		@SuppressWarnings("unchecked")
		ItscpXmlParser<ExceptionInfo> systemconfigparser = (ItscpXmlParser<ExceptionInfo>) TscpParserFactory.getInstance().produceInstance(SystemEnumType.exceptionconfigparser.getValue());
		
		systemconfigparser.parseXml2Object(SystemContext.getApplicationContext().getSystemConfigFile().getUserDefaultExceptionPropertiesFiles(),
				new AbsTscpConfigFileHandler<ExceptionInfo, File>() {
					@Override
					public ExceptionInfo processor(File file)throws TscpBaseException {
						Properties properties = new Properties();
						try {
							properties.load(new FileInputStream(file));
							ExceptionInfo exceptionInfo = new ExceptionInfo();
							for (Entry<Object, Object> entry : properties.entrySet()) {
								ExceptionDetail ed = exceptionInfo.new ExceptionDetail();
								ed.setKey((String) entry.getKey());
								ed.setContent((String) entry.getValue());
								exceptionInfo.getExceptionmap().put((String) entry.getKey(), ed);
							}
							this.entitys.add(exceptionInfo);
						} catch (IOException e) {
							throw new TscpBaseException("TSCP-6094:Open exception configuration file exception,please check whether the file exist or not!");
						}
						return null;
					}
				});
	}

	/**
	 * 
	 * @throws TscpBaseException
	 * @throws FileNotFoundException
	 */
	private void loadSystemConfig() throws TscpBaseException,FileNotFoundException {

		PlatformLogger.info("Is beginning to initialize kernel configuration");
		@SuppressWarnings("unchecked")
		ItscpXmlParser<TscpCoreConfig> systemconfigparser = (ItscpXmlParser<TscpCoreConfig>) TscpParserFactory.getInstance().produceInstance(SystemEnumType.coreconfigfileparser.getValue());
		systemconfigparser.parseXml2Object(systemconfigparser.getConfigFileInputStream(SystemContext.getApplicationContext().getSystemConfigFile().getCoreConfigFile()),
				new AbsTscpConfigFileHandler<TscpCoreConfig, InputStream>() {
					@Override
					public void startElement(String uri, String localName,String qName, Attributes attributes)throws SAXException {
						if (Constant.MACHINE_NAME.equals(qName)) {
							this.getEntity().setMachine(attributes.getValue(Constant.NAME));
							this.getEntity().setIp(attributes.getValue(Constant.IP));
						} else if (Constant.NODE_NAME.equals(qName)) {
							this.getEntity().setNodeName(attributes.getValue(Constant.NAME));
						} else if (Constant.SYSTEM_CHARSET.equals(qName)) {
							this.getEntity().setSystemCharset(Constant.SYSTEM_CHARSET);
						} else if (Constant.RUNNING_MODEL.equals(qName)) {
							this.getEntity().setRunModel(attributes.getValue(Constant.VALUE));
						} else if (Constant.LOG_LEVEL.equals(qName)) {
							this.getEntity().setLoglevel(attributes.getValue(Constant.VALUE));
						} else if (Constant.JMX_SERVER.equals(qName)) {
							this.getEntity().setJmxserver(attributes.getValue(Constant.JMX_SERVER));
						} else if (Constant.CONTAINER_ENGINE.equals(qName)) {
							this.getEntity().getPlatformconfiguration().setContainerEngine(attributes.getValue(Constant.VALUE));
						} else if (Constant.SCANNERS.equals(qName)) {
							this.getEntity().getPlatformconfiguration().setUserDefineLibPath(attributes.getValue(Constant.USERDEFINELIBPATH).split(","));
							this.getEntity().getPlatformconfiguration().setJarexpression(attributes.getValue(Constant.JAREXPRESSION));
							this.getEntity().getPlatformconfiguration().setClassexpression(attributes.getValue(Constant.CLASSEXPRESSION));
						} else if (Constant.SCANNER.equals(qName)) {
							Scanner scanner = this.getEntity().getPlatformconfiguration().new Scanner();
							scanner.setName(attributes.getValue(Constant.NAME));
							scanner.setClazz(attributes.getValue(Constant.CLASS));
							this.getEntity().getPlatformconfiguration().getScanners().add(scanner);
						} else if (Constant.ANALYZER.equals(qName)) {
							registerSystemAnalyzer(attributes);
						} else if (Constant.COMPONENT.equals(qName)) {
							constructComponentInfo(attributes);
						} else if (Constant.LOG_FRAMEWORK.equals(qName)|| Constant.LOG.equals(qName)) {
							constructLogFrameworkInfo(attributes, qName);
						} else if (Constant.SERVICE.equals(qName)|| "".equals(qName)) {
							constructService(attributes);
						}
					}

					@Override
					public TscpCoreConfig processor(InputStream in)throws TscpBaseException {

						this.setParseDocumentStream(in);

						return this.getEntity();
					}

				
					/**
					 * @param num
					 * 
					 * @return
					 */
					protected int getBootSequence(String num) {
						try {
							if (num == null || "".equals(num))
								return 100;
							return Integer.parseInt(num);
						} catch (Exception e) {
							throw new NumberFormatException("TSCP-6870:The sequence of component must be number !");
						}
					}

					
					/**
					 * @param attributes
					 * 
					 * @throws TscpBaseException
					 */
					private void registerSystemAnalyzer(Attributes attributes)throws SAXException {

						Map<String, String> analyzer = new HashMap<String, String>();
						if (Constant.CORE_ANALYZER.equals(attributes.getValue(Constant.NAME))) {
							IAnalyzerResourceRegister register = null;
							try {
								register = (AnalyzerResourceRegister) Class.forName(attributes.getValue(Constant.CLASS)).newInstance();
							} catch (InstantiationException e) {
								throw new SAXException("TSCP-8095:Instance class["+ attributes.getValue(Constant.CLASS)+ "] exception !", e);
							} catch (IllegalAccessException e) {
								throw new SAXException("TSCP:8097:IllegalAccessException", e);
							} catch (ClassNotFoundException e) {
								throw new SAXException("TSCP-8099:Can't find class["+ attributes.getValue(Constant.CLASS)+ "] exception !", e);
							}
							analyzer.putAll(register.getApplicationDefaultAnalyzer());
							this.getEntity().getPlatformconfiguration().getScanners().get(this.getEntity().getPlatformconfiguration().getScanners().size() - 1).getAnalyzers().add(analyzer);
							return;
						}

						analyzer.put(attributes.getValue(Constant.NAME),attributes.getValue(Constant.CLASS));
						this.getEntity().getPlatformconfiguration().getScanners().get(this.getEntity().getPlatformconfiguration().getScanners().size() - 1).getAnalyzers().add(analyzer);
					}

					/**
					 * 
					 * @param attributes
					 */
					private void constructComponentInfo(Attributes attributes) {

						ComponentInformation component = new ComponentInformation();
						if(attributes.getValue(Constant.NAME) == null||"".equals(attributes.getValue(Constant.NAME))){
							PlatformLogger.dumpStack("tscp.xml the name of component can't be null !",Thread.currentThread());
						    return;
						}
						component.setName(attributes.getValue(Constant.NAME));
						component.setBootsequence(getBootSequence(attributes.getValue(Constant.BOOT_SEQUENCE)));
						component.setClazzName(attributes.getValue(Constant.CLASS));
						component.setParameter(attributes.getValue(Constant.PARAMETER));
						this.getEntity().getComponents().add(component);
					}

					/**
					 * @param attributes
					 * 
					 * @param qName
					 */
					private void constructLogFrameworkInfo(Attributes attributes, String qName) {

						if (this.getEntity().getLogframeworkInfo() == null)
							this.getEntity().setLogframeworkInfo(this.getEntity().new LogFrameworkInfo());
						if (Constant.LOG_FRAMEWORK.equals(qName)) {
							this.getEntity().getLogframeworkInfo().setName(attributes.getValue(Constant.NAME));
							this.getEntity().getLogframeworkInfo().setEnable(Boolean.parseBoolean(attributes.getValue(Constant.ISENABLE)));
							this.getEntity().getLogframeworkInfo().setBrokerName(attributes.getValue(Constant.BROKER_NAME));
						} else {
							Map<String, String> logmap = new HashMap<String, String>();
							logmap.put(this.getEntity().getLogframeworkInfo().THEME,attributes.getValue(Constant.THEME));
							logmap.put(this.getEntity().getLogframeworkInfo().HANDLE_CLASS,attributes.getValue(Constant.CLASS));
							this.getEntity().getLogframeworkInfo().getLogs().add(logmap);
						}
					}

					
					/**
					 * @param attributes
					 * 
					 * @param qName
					 */
					private void constructService(Attributes attributes) {

						this.getEntity().getServiceInfo().setExpose(Boolean.parseBoolean(attributes.getValue(Constant.EXPOSE)));
						this.getEntity().getServiceInfo().setPort(Integer.parseInt(attributes.getValue(Constant.PORT)));
						this.getEntity().getServiceInfo().setProtocolType(attributes.getValue(Constant.PROTOCOL));
						this.getEntity().getServiceInfo().setProxyConnTimeout(attributes.getValue("protocol"));
						this.getEntity().getServiceInfo().setConnTimeout(attributes.getValue(Constant.CONN_TIMEOUT));
						this.getEntity().getServiceInfo().setCallTimeout(attributes.getValue(Constant.CALL_TIMEOUT));
						this.getEntity().getServiceInfo().setRespTimeout(attributes.getValue(Constant.RESP_TIMEOUT));
						this.getEntity().getServiceInfo().setHandsharkTimeout(attributes.getValue(Constant.HANDSHAR_TIMEOUT));
					}
				});
	}
}
