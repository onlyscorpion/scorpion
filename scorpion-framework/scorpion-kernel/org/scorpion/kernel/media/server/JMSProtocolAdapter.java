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

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IScorpionBaseMessageReceiver;
import org.scorpion.api.kernel.IScorpionReqMedia;
import org.scorpion.api.kernel.IScorpionRespMedia;
import org.scorpion.api.log.PlatformLogger;

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
@MessageDriven(mappedName = "com.SCORPION.Scorpion.kernel.media.server.JMSProtocolAdapter", activationConfig = {
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
@TransactionManagement(TransactionManagementType.CONTAINER)
public class JMSProtocolAdapter implements MessageListener,IScorpionBaseMessageReceiver {

	private static final long serialVersionUID = 9212031304295479699L;

	@Override
	public String recevieXml(String argument) throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IScorpionRespMedia receiveBean(IScorpionReqMedia argument)throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onMessage(Message argument) {

		while (true) {
			if (argument instanceof ObjectMessage) {
				try {
					if (((ObjectMessage) argument).getObject() instanceof IScorpionReqMedia)
						receiveBean((IScorpionReqMedia) ((ObjectMessage) argument).getObject());
				} catch (ScorpionBaseException e) {
					e.printStackTrace();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			} else if (argument instanceof TextMessage) {
				try {
					recevieXml(((TextMessage) argument).getText());
				} catch (ScorpionBaseException e) {
					e.printStackTrace();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			} else {
				PlatformLogger.error("scorpion-4987:unknown adapter type !");
			}
		}
	}

}
