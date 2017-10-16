package org.scorpion.kernel.media;

import java.util.Map.Entry;

import org.scorpion.api.common.AbsMedaitionPool;
import org.scorpion.api.common.AbsProtocol;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;
import org.scorpion.kernel.media.client.TMIBaseMessagMediation;
import org.scorpion.kernel.route.ProtocolConf;
import org.scorpion.kernel.route.ProtocolConf.TMIConf;

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
public class ScorpionRMSenderMediationPool extends AbsMedaitionPool<TMIBaseMessagMediation> {

	private static ScorpionRMSenderMediationPool tmiMediationPool;

	@Override
	public void init() throws ScorpionBaseException {

		try {
			
			initSystemProperties();

			for (Entry<String, ProtocolConf.TMIConf> protocol : ProtocolConf.getInstance().getTmiConf().entrySet()) 
				if (!Constant.LAZY.equals(protocol.getValue().getInitType()))
					createQueue(protocol.getKey(), protocol.getValue());
		
			((MediationPoolFactory) MediationPoolFactory.getMessageMediation()).addFatorToPool(ScorpionRMSenderMediationPool.class.getName(),this);
		} catch (Throwable e) {
			throw new ScorpionBaseException(e);
		}
	}

	@Override
	public void rebuild() throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rebuildByProtocolId(String key, String protocolType)throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void increase() throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void increaseByProtocolInfo(String key, String protocolType)throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void reduce() throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}

	public TMIBaseMessagMediation getMediation(String protocolId)throws InterruptedException, ScorpionBaseException {

		return super.getMediation(protocolId, ProtocolConf.getInstance().getTmiConf().get(protocolId));
	}

	@Override
	public void destroy() throws ScorpionBaseException {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * 
	 */
	private void initSystemProperties(){
		
		System.setProperty("sun.rmi.transport.tcp.responseTimeout", SystemContext.getApplicationContext().getSystemCoreConfig().getServiceInfo().getRespTimeout());  
		System.setProperty("sun.rmi.transport.tcp.readTimeout",  SystemContext.getApplicationContext().getSystemCoreConfig().getServiceInfo().getCallTimeout());  
		System.setProperty("sun.rmi.transport.connectionTimeout",  SystemContext.getApplicationContext().getSystemCoreConfig().getServiceInfo().getConnTimeout());  
		System.setProperty("sun.rmi.transport.proxy.connectTimeout",  SystemContext.getApplicationContext().getSystemCoreConfig().getServiceInfo().getProxyConnTimeout());  
		System.setProperty("sun.rmi.transport.tcp.handshakeTimeout",  SystemContext.getApplicationContext().getSystemCoreConfig().getServiceInfo().getHandsharkTimeout());
	}

	@Override
	public TMIBaseMessagMediation getMediation(AbsProtocol protocol)throws ScorpionBaseException {

		TMIConf tmiconf = (TMIConf) protocol;
		TMIBaseMessagMediation tmiMediation = new TMIBaseMessagMediation();
		tmiMediation.setProtocolId(tmiconf.getProtocolId());
		tmiMediation.setProvideURL(tmiconf.getProvideURL());
		tmiMediation.initialize();
		return tmiMediation;
	}

	/**
	 * 
	 * @return
	 */
	public static synchronized AbsMedaitionPool<TMIBaseMessagMediation> getInstance() {

		if (tmiMediationPool == null)
			tmiMediationPool = new ScorpionRMSenderMediationPool();

		return tmiMediationPool;
	}

	@Override
	public void dynamicAddProtocol(AbsProtocol protocol)throws ScorpionBaseException, InterruptedException {
	
		createQueue(protocol.getProtocolId(), protocol);
	}

}
