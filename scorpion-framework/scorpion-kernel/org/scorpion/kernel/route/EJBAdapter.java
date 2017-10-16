package org.scorpion.kernel.route;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.scorpion.api.util.Constant;

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
public class EJBAdapter {

	public final static int WEBLOGIC_CONTAINER = 1;

	public final static int TONWEB_CONTAINER = 2;

	public final static int APUSIC_CONTAINER = 3;

	private static Map<String, Object> cache = new HashMap<String, Object>();

	/**
	 * @param providerUrl
	 * 
	 * @param userName
	 * 
	 * @param password
	 * 
	 * @return
	 */
	private static Hashtable<String, Object> getWeblogicDefault(String providerUrl, String userName, String password) {

		Hashtable<String, Object> props = new Hashtable<String, Object>();
		props.put(Context.PROVIDER_URL, providerUrl);
		props.put(Context.INITIAL_CONTEXT_FACTORY,Constant.WEBLOGIC_CONTEXT_FACTORY);
		props.put("weblogic.jndi.requestTimeout", new Long(Constant.CONN_TIME_OUT * 1000));
		props.put("weblogic.rmi.clientTimeout", new Long(Constant.CALL_TIME_OUT * 1000));

		if (null != userName) {
			props.put(Context.SECURITY_PRINCIPAL, userName);
			props.put(Context.SECURITY_CREDENTIALS, (password == null) ? "": password);
		}
		return props;

	}

	/**
	 * @description user custom JNDI information
	 * 
	 * @param providerUrl
	 * 
	 * @param contextFactory
	 * 
	 * @param ConntimeOutKey
	 * 
	 * @param connTimeOut
	 * 
	 * @param callTimeOutKey
	 * 
	 * @param callTimeOut
	 * 
	 * @param username
	 * 
	 * @param password
	 * 
	 * @return
	 */
	public static Hashtable<String, Object> customContextPro(String providerUrl, String contextFactory, String ConntimeOutKey,long connTimeOut, String callTimeOutKey, long callTimeOut,String username, String password) {

		Hashtable<String, Object> props = new Hashtable<String, Object>();
		props.put(Context.PROVIDER_URL, providerUrl);
		props.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		props.put(ConntimeOutKey, new Long(Constant.CONN_TIME_OUT * 1000));
		props.put(callTimeOutKey, new Long(Constant.CALL_TIME_OUT * 1000));

		if (null != username) {
			props.put(Context.SECURITY_PRINCIPAL, username);
			props.put(Context.SECURITY_CREDENTIALS, (password == null) ? "" : password);
		}
		return props;
	}

	/**
	 * @param providerUrl
	 * 
	 * @param userName
	 * 
	 * @param password
	 * 
	 * @return
	 */
	private static Hashtable<String, Object> getTonWebDefault(String providerUrl, String userName, String password) {

		return null;
	}

	/**
	 * @param providerUrl
	 * 
	 * @param userName
	 * 
	 * @param password
	 * 
	 * @return
	 */
	private static Hashtable<String, Object> getApusicDefault(String providerUrl, String userName, String password) {

		return null;
	}

	/**
	 * @param provideUrl
	 * 
	 * @param username
	 * 
	 * @param password
	 * 
	 * @return
	 * 
	 * @throws NamingException
	 */
	public synchronized static Object getProxyObject(String provideUrl,String username, String password) throws NamingException {

		if (cache.containsKey(provideUrl + "#" + username + "#" + password))
			return cache.get(provideUrl + "#" + username + "#" + password);

		InitialContext context = new InitialContext(getInitialContextProperty(1, provideUrl, username, password));
		Object obj = context.lookup(Constant.EJB_NAME);
		cache.put(provideUrl + "#" + username + "#" + password, obj);
		return obj;
	}

	/**
	 * @param containerType
	 * 
	 * @param provideUrl
	 * 
	 * @param username
	 * 
	 * @param password
	 * 
	 * @return
	 */
	private static Hashtable<String, Object> getInitialContextProperty(int containerType, String provideUrl, String username,String password) {

		if (WEBLOGIC_CONTAINER == containerType)
			return getWeblogicDefault(provideUrl, username, password);
		else if (TONWEB_CONTAINER == containerType)
			return getTonWebDefault(provideUrl, username, password);
		else if (APUSIC_CONTAINER == containerType)
			return getApusicDefault(provideUrl, username, password);
		else
			return null;
	}

}
