package org.scorpion.kernel.component;

import java.util.Map;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionComponent;
import org.scorpion.api.util.Constant;
import org.scorpion.common.annotation.Component;
import org.scorpion.common.context.SystemContext;
import org.scorpion.common.util.ScorpionCommand;

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
@Component(name = "TCommandComponent", sequence = -1)
public class TCommandComponent extends AbsScorpionComponent {

	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {

		if (Constant.DEVELOP_MODEL.equals(SystemContext.getApplicationContext().getSystemCoreConfig().getRunModel()))
			new Thread(new ScorpionCommand()).start();
	}

}
