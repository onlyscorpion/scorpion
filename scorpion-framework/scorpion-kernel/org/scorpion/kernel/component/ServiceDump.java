package org.scorpion.kernel.component;

import java.util.Map;
import java.util.Map.Entry;

import org.scorpion.api.configuration.TscpSystemScanInfo.ServiceInfo;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpComponent;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.common.annotation.Component;
import org.scorpion.common.context.SystemContext;

import java.util.Timer;
import java.util.TimerTask;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
//@Component(name = "ServiceDump")
public class ServiceDump extends AbsTscpComponent{

	@Override
	public void start(Map<String, String> arguments) throws TscpBaseException {
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				PlatformLogger.info("=============current server pool content ============= ");
				try {
					for(Entry<String,ServiceInfo>entry:SystemContext.getApplicationContext().getScanInfo().getServicePool().entrySet())
						PlatformLogger.info(entry.getKey());
				} catch (TscpBaseException e) {
					e.printStackTrace();
				}
				PlatformLogger.info("=============end ============= ");
			}
		}, 0, 60000);
		
		
	}

}
