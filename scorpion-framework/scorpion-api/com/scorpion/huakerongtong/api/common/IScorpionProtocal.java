package com.scorpion.huakerongtong.api.common;

import java.util.Map;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public interface IScorpionProtocal {
	
	public static enum ProtocolType {
		LOCAL, EJB, JMS, EJB_BEAN, EJB_CONTAINER, JMS_BEAN, JMS_CONTAINER ,WEBSERVICE,MQ,TMI
	}

	String getProtocolID();

	ProtocolType getProtocolType();

	Map<String, String> getOtherConf();
}
