package org.scorpion.kernel.media.server;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpBaseMessageReceiver;
import org.scorpion.api.kernel.ITscpReqMedia;
import org.scorpion.api.kernel.ITscpRespMedia;
import org.scorpion.api.log.PlatformLogger;

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
@MessageDriven(mappedName = "com.taiji.tscp.kernel.media.server.JMSProtocolAdapter", activationConfig = {
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
@TransactionManagement(TransactionManagementType.CONTAINER)
public class JMSProtocolAdapter implements MessageListener,ITscpBaseMessageReceiver {

	private static final long serialVersionUID = 9212031304295479699L;

	@Override
	public String recevieXml(String argument) throws TscpBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITscpRespMedia receiveBean(ITscpReqMedia argument)throws TscpBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onMessage(Message argument) {

		while (true) {
			if (argument instanceof ObjectMessage) {
				try {
					if (((ObjectMessage) argument).getObject() instanceof ITscpReqMedia)
						receiveBean((ITscpReqMedia) ((ObjectMessage) argument).getObject());
				} catch (TscpBaseException e) {
					e.printStackTrace();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			} else if (argument instanceof TextMessage) {
				try {
					recevieXml(((TextMessage) argument).getText());
				} catch (TscpBaseException e) {
					e.printStackTrace();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			} else {
				PlatformLogger.error("TSCP-4987:unknown adapter type !");
			}
		}
	}

}
