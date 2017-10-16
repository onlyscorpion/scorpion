package org.scorpion.kernel.component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionComponent;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.ScorpionFileStreamHandler;
import org.scorpion.common.annotation.Component;
import org.scorpion.common.context.SystemContext;
import org.scorpion.kernel.media.ScorpionRMSenderMediationPool;
import org.scorpion.kernel.media.ScorpionRemoteServiceExpose;
import org.scorpion.kernel.media.ScorpionWSSenderMediationPool;
import org.scorpion.kernel.route.ProtocolConf;
import org.scorpion.kernel.route.ProtocolConf.EJBProtocolConf;
import org.scorpion.kernel.route.ProtocolConf.JMSProtocolConf;
import org.scorpion.kernel.route.ProtocolConf.ServiceConf;
import org.scorpion.kernel.route.ProtocolConf.TMIConf;
import org.scorpion.kernel.route.ProtocolConf.WSProtocolConf;

/**
 * 天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>
 * com.SCORPION.Scorpion.common
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
 * Copyright: Copyright (c) 2015 SCORPION.COM.CN
 * </p>
 * <p>
 * Company: SCORPION.COM.CN
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
@Component(name = "TInternalRouteComponent", params = { "routeFile=route.xml" },iscorecomponent=true, sequence = 5)
public class TInternalRouteComponent extends AbsScorpionComponent {

	private String routeFile;

	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {

		if (SystemContext.getApplicationContext().getSystemCoreConfig().getServiceInfo() == null|| !SystemContext.getApplicationContext().getSystemCoreConfig().getServiceInfo().isExpose())
			return;

		routeFile = arguments.get(Constant.ROUTE_FILE);
		try {
			parserRouteConfiguration();
		} catch (FileNotFoundException e) {
			throw new ScorpionBaseException(e);
		} catch (DocumentException e) {
			throw new ScorpionBaseException("scorpion-6097:Parse route docuemnt failure, check the route is valid or not !",e);
		} catch (IOException e) {
			throw new ScorpionBaseException("scorpion-6097:Open file stream failure !",e);
		}

		try {
			ScorpionRemoteServiceExpose.startServiceProcessor();
		} catch (Exception e) {
			throw new ScorpionBaseException("scorpion-6983:Start remote service proxy failure !", e);
		}

		initMediationPool();

	}

	/**
	 * 
	 * @throws DocumentException
	 * 
	 * @throws IOException
	 * 
	 * @throws ScorpionBaseException
	 */
	public void parserRouteConfiguration() throws DocumentException,IOException, ScorpionBaseException {

		if (routeFile == null || "".equals(routeFile))
			throw new NullPointerException("scorpion-8097:The path of route file don't allow null !");

		URL url = this.getClass().getResource("/" + routeFile);

		if (url == null)
			throw new ScorpionBaseException("scorpion-6094:Resource file [" + routeFile+ "] not exist !");

		if (!new File(url.getFile()).exists())
			throw new FileNotFoundException("scorpion-6098:The route configuration file not found !");

		generateProtocol(url.getFile());

	}

	/**
	 * 
	 * @param path
	 * 
	 * @throws DocumentException
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected void generateProtocol(String path) throws DocumentException,IOException {

		String content = ScorpionFileStreamHandler.getFileContent(path);
		Document document = DocumentHelper.parseText(content);
		Element rootElement = document.getRootElement();
		List<Element> lis = rootElement.elements();

		for (Element element : lis) {
			addServiceConf(element);
			addEJBProtocolConf(element);
			addJMSProtocolConf(element);
			addWSProtocolConf(element);
			addTMIProtocolConf(element);
		}
	}

	public void validCheck() {

	}

	@SuppressWarnings("unchecked")
	protected void addServiceConf(Element element) {

		if (!Constant.SERVICE_CONF.equals(element.getName()))
			return;

		List<Element> lis = element.elements();
		for (Element el : lis) {
			ServiceConf serviceConf = ProtocolConf.getInstance().new ServiceConf();
			serviceConf.setServiceName(el.attributeValue(Constant.SERVICE_NAME));
			serviceConf.setProtocolType(el.attributeValue(Constant.PROTOCOL_TYPE));
			serviceConf.setProtocolId(el.attributeValue(Constant.PROTOCOL_ID));
			ProtocolConf.getInstance().getServiceConf().put(el.attributeValue(Constant.SERVICE_NAME), serviceConf);
		}
	}

	@SuppressWarnings("unchecked")
	protected void addEJBProtocolConf(Element element) {

		if (!Constant.EJB_CONF.equals(element.getName()))
			return;

		List<Element> lis = element.elements();
		for (Element el : lis) {
			EJBProtocolConf ejbconf = ProtocolConf.getInstance().new EJBProtocolConf();
			ejbconf.setProvideURL(el.attributeValue(""));
			ejbconf.setEjbName(el.attributeValue(""));
			ejbconf.setInitConnNum(10);
			ejbconf.setMaxConnNum(10);
			ejbconf.setNextConnNum(10);
			ejbconf.setProtocolId(el.attributeValue(""));
			ejbconf.setContextFactory(el.attributeValue(""));
			ProtocolConf.getInstance().getEjbProtocolConf().put(el.attributeValue(Constant.PROTOCOL_ID), ejbconf);
		}
	}

	@SuppressWarnings("unchecked")
	protected void addJMSProtocolConf(Element element) {

		if (!Constant.JMS_CONF.equals(element.getName()))
			return;

		List<Element> lis = element.elements();
		for (Element el : lis) {
			JMSProtocolConf jmsconf = ProtocolConf.getInstance().new JMSProtocolConf();
			jmsconf.setQueueName("");
			jmsconf.setProvideURL("");
			jmsconf.setContextFactory("");
			jmsconf.setUsername("");
			jmsconf.setPassword("");
			jmsconf.setCallTimeOut(1000);
			jmsconf.setConnTimeOut(1000);
			jmsconf.setInitConnNum(44);
			jmsconf.setMaxConnNum(14);
			ProtocolConf.getInstance().getJmsProtocolConf().put(el.attributeValue(""), jmsconf);
		}
	}

	/**
	 * 
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	protected void addTMIProtocolConf(Element element) {

		if (!Constant.TMI_CONF.equals(element.getName()))
			return;

		List<Element> lis = element.elements();
		for (Element el : lis) {
			TMIConf trmiConf = ProtocolConf.getInstance().new TMIConf();
			trmiConf.setProtocolId(el.attributeValue(Constant.PROTOCOL_ID));
			trmiConf.setProvideURL(el.attributeValue(Constant.PRIVODE_URL));
			trmiConf.setConnTimeOut(("".equals(el.attributeValue(Constant.CONN_TIMEOUT)) || el.attributeValue(Constant.CONN_TIMEOUT) == null) ? 3000: Long.parseLong(el.attributeValue(Constant.CONN_TIMEOUT)));
			trmiConf.setInitType(el.attributeValue(Constant.INIT_TYPE));
			ProtocolConf.getInstance().getTmiConf().put(trmiConf.getProtocolId(), trmiConf);
		}

	}

	private void initMediationPool() throws ScorpionBaseException {

		ScorpionRMSenderMediationPool.getInstance().init();
		ScorpionWSSenderMediationPool.getInstance().init();

	}

	@SuppressWarnings("unchecked")
	protected void addWSProtocolConf(Element element) {

		if (!Constant.WS_CONF.equals(element.getName()))
			return;

		List<Element> lis = element.elements();
		for (Element el : lis) {
			WSProtocolConf wsconf = ProtocolConf.getInstance().new WSProtocolConf();
			wsconf.setProtocolId(el.attributeValue(Constant.PROTOCOL_ID));
			wsconf.setWsdlAddress(el.attributeValue(Constant.PRIVODE_URL));
			wsconf.setMethod(el.attributeValue(Constant.METHOD));
			wsconf.setConnTimeOut(1000);
			wsconf.setCallTimeOut(1000);
			wsconf.setInitConnNum(44);
			wsconf.setMaxConnNum(14);
			ProtocolConf.getInstance().getWsProtocolConf().put(el.attributeValue(Constant.PROTOCOL_ID), wsconf);
		}
	}

}
