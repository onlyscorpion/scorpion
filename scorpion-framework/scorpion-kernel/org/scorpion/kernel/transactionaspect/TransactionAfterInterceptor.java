package org.scorpion.kernel.transactionaspect;

import java.util.Map.Entry;

import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IScorpionAopAfterAdvice;
import org.scorpion.api.persistence.IScorpionPersistenceSession;
import org.scorpion.common.session.ApplicationSession;
import org.scorpion.common.util.ScorpionSystemSessionUtils;

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
 * Description: if developer want to create a component. the developer must
 * extends the abstract
 * </p>
 * <p>
 * class AScorpionComponet. the AScorpionComponent exist life cycle. developer can
 * override
 * </p>
 * <p>
 * the initialization method or service method or destroy method to handle
 * themselves business
 * </p>
 * <p>
 * but we don't suggest the developer do that
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
// @Interceptor( name =
// "TransactionAfterInterceptor",serviceName=".+",classRegex="com.taiji.Scorpion.api.persistence",methodRegex=".+",aopMode=AopMode.DO_AFTER)
public class TransactionAfterInterceptor implements IScorpionAopAfterAdvice {

	@Override
	public void doAfterAdvice() throws ScorpionBaseException {

		if (((ApplicationSession) ScorpionSystemSessionUtils.getSession()).getServiceCalledLevel().decrementAndGet() == 0) {
			IScorpionPersistenceSession defualtPersistence = ((ApplicationSession) ScorpionSystemSessionUtils.getSession()).getDefaultPersistenceSession();

			if (!defualtPersistence.isCommitTransaction()|| defualtPersistence.getConnection() != null)
				defualtPersistence.getPersistenceServcie().commit();

			for (Entry<String, IScorpionPersistenceSession> entry : ((ApplicationSession) ScorpionSystemSessionUtils.getSession()).getOtherPersistenceSession().entrySet()) {
				if (!entry.getValue().isCommitTransaction()|| entry.getValue().getConnection() != null)
					entry.getValue().commit();
			}
		}
	}

}
