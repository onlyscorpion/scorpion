package org.scorpion.kernel.container.spring;

import java.util.Map;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.AbsScorpionComponent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
public class ScorpionIoCManager extends AbsScorpionComponent {
	private ClassPathXmlApplicationContext ctx;

	public <T> T findBean(Class<T> clazz) {
		return ctx.getBean(clazz);
	}

	public <T> T findBean(String name, Class<T> clazz) {
		T obj;
		if (name == null) {
			obj = findBean(clazz);
		} else {
			obj = ctx.getBean(name, clazz);
		}
		return obj;
	}

	@Override
	public void start(Map<String, String> arguments) throws ScorpionBaseException {

		String configFile = (String) arguments.get("config");
		if (configFile == null || "".equals(configFile)) {
			configFile = "spring-beans.xml";
		}
		ctx = new ClassPathXmlApplicationContext(configFile);
	}
}