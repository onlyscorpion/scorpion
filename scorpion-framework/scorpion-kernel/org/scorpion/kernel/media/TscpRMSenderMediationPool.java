package org.scorpion.kernel.media;

import java.util.Map.Entry;

import org.scorpion.api.common.AbsMedaitionPool;
import org.scorpion.api.common.AbsProtocol;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.util.Constant;
import org.scorpion.common.context.SystemContext;
import org.scorpion.kernel.media.client.TMIBaseMessagMediation;
import org.scorpion.kernel.route.ProtocolConf;
import org.scorpion.kernel.route.ProtocolConf.TMIConf;

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
public class TscpRMSenderMediationPool extends AbsMedaitionPool<TMIBaseMessagMediation> {

	private static TscpRMSenderMediationPool tmiMediationPool;

	@Override
	public void init() throws TscpBaseException {

		try {
			
			initSystemProperties();

			for (Entry<String, ProtocolConf.TMIConf> protocol : ProtocolConf.getInstance().getTmiConf().entrySet()) 
				if (!Constant.LAZY.equals(protocol.getValue().getInitType()))
					createQueue(protocol.getKey(), protocol.getValue());
		
			((MediationPoolFactory) MediationPoolFactory.getMessageMediation()).addFatorToPool(TscpRMSenderMediationPool.class.getName(),this);
		} catch (Throwable e) {
			throw new TscpBaseException(e);
		}
	}

	@Override
	public void rebuild() throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rebuildByProtocolId(String key, String protocolType)throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void increase() throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void increaseByProtocolInfo(String key, String protocolType)throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void reduce() throws TscpBaseException {
		// TODO Auto-generated method stub

	}

	public TMIBaseMessagMediation getMediation(String protocolId)throws InterruptedException, TscpBaseException {

		return super.getMediation(protocolId, ProtocolConf.getInstance().getTmiConf().get(protocolId));
	}

	@Override
	public void destroy() throws TscpBaseException {
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
	public TMIBaseMessagMediation getMediation(AbsProtocol protocol)throws TscpBaseException {

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
			tmiMediationPool = new TscpRMSenderMediationPool();

		return tmiMediationPool;
	}

	@Override
	public void dynamicAddProtocol(AbsProtocol protocol)throws TscpBaseException, InterruptedException {
	
		createQueue(protocol.getProtocolId(), protocol);
	}

}
