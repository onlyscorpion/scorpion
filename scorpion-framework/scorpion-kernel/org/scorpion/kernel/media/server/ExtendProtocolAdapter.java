package org.scorpion.kernel.media.server;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.IExtendBaseMessageReceiver;

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
public class ExtendProtocolAdapter implements IExtendBaseMessageReceiver {

	@Override
	public String extendReceiver(String argument) throws TscpBaseException {
		return null;
	}

	@Override
	public Object extendReceiver(Object obj) throws TscpBaseException {
		return null;
	}

}
