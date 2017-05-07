package org.scorpion.kernel.component;

import java.util.Map;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.AbsTscpComponent;
import org.scorpion.api.util.Constant;
import org.scorpion.common.annotation.Component;
import org.scorpion.common.context.SystemContext;
import org.scorpion.common.util.TscpCommand;

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
@Component(name = "TCommandComponent", sequence = -1)
public class TCommandComponent extends AbsTscpComponent {

	@Override
	public void start(Map<String, String> arguments) throws TscpBaseException {

		if (Constant.DEVELOP_MODEL.equals(SystemContext.getApplicationContext().getSystemCoreConfig().getRunModel()))
			new Thread(new TscpCommand()).start();
	}

}
