package org.scorpion.kernel.transactionaspect;

import java.sql.SQLException;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IScorpionAopBeforeAdvice;
import org.scorpion.api.util.Constant;
import org.scorpion.common.annotation.Interceptor;
import org.scorpion.common.session.ApplicationSession;

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
@Interceptor(name = "ConnectionValidateBeforeInterceptor", classRegex = "com.taiji.Scorpion.persistence.handler.ScorpionPersistenceDAO", methodRegex = "(^(query)(.*))")
public class ConnectionValidateBeforeInterceptor implements IScorpionAopBeforeAdvice {

	@Override
	public void doBeforeAdvice() throws ScorpionBaseException {

		ApplicationSession session = (ApplicationSession) ApplicationSession.getSession();
		try {
			
			if (session.getCurrentPersistence().getConnection() == null) {
				if (session.getCurrentPersistence().isDefaultDataSource())
					session.resetConnection(null,session.getDefaultPersistenceSession(),Constant.DEFAULT_DATASOURCE);
				else
					session.resetConnection(session.getCurrentPersistence().getDataSourceName(), session.getOtherPersistenceSessionByName(session.getCurrentPersistence().getDataSourceName()),Constant.OTHER_DATASOURCE);
			}
		} catch (SQLException e) {
			throw new ScorpionBaseException("scorpion-9034:create persistence session failure !", e);
		}
	}
}
