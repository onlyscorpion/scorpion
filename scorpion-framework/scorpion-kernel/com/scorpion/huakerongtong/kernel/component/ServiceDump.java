package com.scorpion.huakerongtong.kernel.component;

import java.util.Map;
import java.util.Map.Entry;

import com.scorpion.huakerongtong.api.configuration.ScorpionSystemScanInfo.ServiceInfo;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.AbsScorpionComponent;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.common.context.SystemContext;

import java.util.Timer;
import java.util.TimerTask;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
//@Component(name = "ServiceDump")
public class ServiceDump extends AbsScorpionComponent{

	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				PlatformLogger.info("=============current server pool content ============= ");
				try {
					for(Entry<String,ServiceInfo>entry:SystemContext.getApplicationContext().getScanInfo().getServicePool().entrySet())
						PlatformLogger.info(entry.getKey());
				} catch (ScorpionBaseException e) {
					e.printStackTrace();
				}
				PlatformLogger.info("=============end ============= ");
			}
		}, 0, 60000);
		
		
	}

}
