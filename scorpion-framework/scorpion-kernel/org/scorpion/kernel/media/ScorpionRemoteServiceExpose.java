package org.scorpion.kernel.media;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.context.SystemContext;
import org.scorpion.kernel.media.server.ScorpionDefaultProtocolAdapter;

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
public class ScorpionRemoteServiceExpose {

	public static void startServiceProcessor() throws RemoteException,MalformedURLException, AlreadyBoundException, UnknownHostException, ScorpionBaseException {

		if (SystemContext.getApplicationContext().getSystemCoreConfig().getServiceInfo().isExpose()) {
		
			String ipAddress ;
		
			if(SystemContext.getApplicationContext().getSystemCoreConfig().getIp() == null||"".equals(SystemContext.getApplicationContext().getSystemCoreConfig().getIp().trim()))
				ipAddress = SystemUtils.getCurrentServiceIP();
			else
				ipAddress = SystemContext.getApplicationContext().getSystemCoreConfig().getIp();
		
			System.setProperty(Constant.HOSTNAME, ipAddress);
			
			LocateRegistry.createRegistry(SystemContext.getApplicationContext().getSystemCoreConfig().getServiceInfo().getPort());
			
			ScorpionDefaultProtocolAdapter protocolAdapter = new ScorpionDefaultProtocolAdapter();
		
			Naming.bind("//"+ ipAddress + ":"+ SystemContext.getApplicationContext().getSystemCoreConfig().getServiceInfo().getPort()+ "/ScorpionDefaultProtocolAdapter", protocolAdapter);
		}
	}
}
