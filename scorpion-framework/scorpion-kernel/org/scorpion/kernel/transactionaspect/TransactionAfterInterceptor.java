package org.scorpion.kernel.transactionaspect;

import java.util.Map.Entry;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ITscpAopAfterAdvice;
import org.scorpion.api.persistence.ITscpPersistenceSession;
import org.scorpion.common.session.ApplicationSession;
import org.scorpion.common.util.TscpSystemSessionUtils;

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
 * Description: if developer want to create a component. the developer must
 * extends the abstract
 * </p>
 * <p>
 * class ATscpComponet. the ATscpComponent exist life cycle. developer can
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
// "TransactionAfterInterceptor",serviceName=".+",classRegex="com.taiji.tscp.api.persistence",methodRegex=".+",aopMode=AopMode.DO_AFTER)
public class TransactionAfterInterceptor implements ITscpAopAfterAdvice {

	@Override
	public void doAfterAdvice() throws TscpBaseException {

		if (((ApplicationSession) TscpSystemSessionUtils.getSession()).getServiceCalledLevel().decrementAndGet() == 0) {
			ITscpPersistenceSession defualtPersistence = ((ApplicationSession) TscpSystemSessionUtils.getSession()).getDefaultPersistenceSession();

			if (!defualtPersistence.isCommitTransaction()|| defualtPersistence.getConnection() != null)
				defualtPersistence.getPersistenceServcie().commit();

			for (Entry<String, ITscpPersistenceSession> entry : ((ApplicationSession) TscpSystemSessionUtils.getSession()).getOtherPersistenceSession().entrySet()) {
				if (!entry.getValue().isCommitTransaction()|| entry.getValue().getConnection() != null)
					entry.getValue().commit();
			}
		}
	}

}
