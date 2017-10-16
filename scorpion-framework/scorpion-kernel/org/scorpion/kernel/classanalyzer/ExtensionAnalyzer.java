package org.scorpion.kernel.classanalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.scorpion.api.common.ICommandExecutor;
import org.scorpion.api.configuration.DrivenBean;
import org.scorpion.api.configuration.MessageSenderInfo;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IAnnotationAnalyzerListener;
import org.scorpion.api.kernel.IBaseMessageSender;
import org.scorpion.api.kernel.IMessageReceiveHandler;
import org.scorpion.api.log.PlatformLogger;
import org.scorpion.api.util.Constant;
import org.scorpion.api.util.SystemUtils;
import org.scorpion.common.annotation.MessageDrivenBean;
import org.scorpion.common.annotation.Sender;
import org.scorpion.common.context.ApplicationOuterContext;
import org.scorpion.common.context.SystemContext;
import org.scorpion.common.enums.SenderType;
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
public class ExtensionAnalyzer implements IAnnotationAnalyzerListener {
	
	
	
	
	/**
	 * @Description Message Driven Bean Analyzer...
	 * 
	 * @param clazz
	 * 
	 * @throws ScorpionBaseException
	 */
	public void analyseMessageDrivenBean(Class<?> clazz) throws ScorpionBaseException{
		
		if(clazz.getAnnotation(MessageDrivenBean.class)==null||!IMessageReceiveHandler.class.isAssignableFrom(clazz))
			return;
		
		MessageDrivenBean drivenBean = clazz.getAnnotation(MessageDrivenBean.class);
		DrivenBean db = new DrivenBean();
		db.setBrokerName(drivenBean.brokerName());
		db.setTheme(drivenBean.theme());
		db.setHandlerClass(clazz);
		
		if(drivenBean.parameter().length>0){
			Map<String, String> arguments = new HashMap<String, String>();
			for (String param : drivenBean.parameter()) {
				if (!SystemUtils.regularExpressionValidate(param != null ? param.replace(" ", "") : param,Constant.PARAM_REGEX))
					throw new ScorpionBaseException("扫描组件["+ drivenBean.name()+ "]出现异常, 组件参数属性格式不正确,异常类出现在["+ clazz.getName()+ "]中，param正确属性为 params={\"KEY1=VALUE1\",\"KEY2==VALUE2\"}");

				arguments.put(param.split("=")[0], param.split("=")[1]);
			}
			db.setParameter(arguments);
		}
		
		ApplicationOuterContext.getOuterContextInstance().getDrivenBeans().add(db);
	}
	
	

	/**
	 * 
	 * @description message sender analyzer ...
	 * 
	 * @param clazz
	 * 
	 * @param jarName
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws IllegalAccessException
	 * 
	 * @throws InstantiationException
	 */
	private void messageSenderAnalyzer(Class<?> clazz, String jarName)throws ScorpionBaseException, InstantiationException,IllegalAccessException {

		if (clazz.getAnnotation(Sender.class) != null&& SenderType.STANDARD_SENDER.name().equals(clazz.getAnnotation(Sender.class).sendertype())) {
			
			if (!clazz.isAssignableFrom(IBaseMessageSender.class))
				throw new ScorpionBaseException("scorpion-6904:The sender must implement interface 'IMessageSender' ");

			MessageSenderInfo senderInfo = new MessageSenderInfo();
			senderInfo.setClassName(clazz);
			senderInfo.setStandardSender(true);
			senderInfo.setProtocolType(clazz.getAnnotation(Sender.class).protocolType().name());
			SystemContext.getApplicationContext().getMessageSenders().put(clazz.getAnnotation(Sender.class).protocolType().name(), senderInfo);
			return;
		
		} else if (!clazz.isAssignableFrom(IBaseMessageSender.class))
			return;

		MessageSenderInfo senderInfo = new MessageSenderInfo();
		senderInfo.setClassName(clazz);
		senderInfo.setStandardSender(true);
		senderInfo.setProtocolType(clazz.getAnnotation(Sender.class).protocolType().name());
		senderInfo.setSender((IBaseMessageSender) clazz.newInstance());
		SystemContext.getApplicationContext().getMessageSenders().put(SenderType.EXTERNAL_SENDER.name(), senderInfo);
	}

	
	
	/**
	 * @description message receive analyzer
	 * 
	 * @param clazz
	 * 
	 * @throws ScorpionBaseException
	 * 
	 * @throws IllegalAccessException
	 * 
	 * @throws InstantiationException
	 */
	public void messageRecevieAnalyze(Class<?> clazz) throws ScorpionBaseException,InstantiationException, IllegalAccessException {

		if (!clazz.isAssignableFrom(IMessageReceiveHandler.class))
			return;

		((SystemContext) SystemContext.getApplicationContext()).setMessageReceiver((IMessageReceiveHandler) clazz.newInstance());

	}

	
	/**
	 * @Description command analyzer ...
	 * 
	 * @param clazz
	 * 
	 * @throws InstantiationException
	 * 
	 * @throws IllegalAccessException
	 * 
	 * @throws ScorpionBaseException
	 * 
	 */
	public void loadSystemCommand(Class<?> clazz)throws InstantiationException, IllegalAccessException,ScorpionBaseException {

		if (!Constant.DEVELOP_MODEL.equals(SystemContext.getApplicationContext().getSystemCoreConfig().getRunModel()))
			return;

		if (!ICommandExecutor.class.isAssignableFrom(clazz))
			return;

		if (ScorpionCommand.executors == null || ScorpionCommand.executors.size() == 0)
			ScorpionCommand.executors = new ArrayList<ICommandExecutor>();

		ScorpionCommand.executors.add((ICommandExecutor) clazz.newInstance());

	}

	@Override
	public void analyse(Class<?> clazz, String jarName)throws ScorpionBaseException {

		try {
			messageSenderAnalyzer(clazz, jarName);
		} catch (Exception e) {
			PlatformLogger.error("scorpion-8965:Scanning the system sender exception!",e);
		}

		try {
			messageRecevieAnalyze(clazz);
		} catch (Exception e) {
			PlatformLogger.error("scorpion-4097：Loading extension receiver exception",e);
		}

		try {
			loadSystemCommand(clazz);
		} catch (Exception e) {
			PlatformLogger.error("scorpion-4097：Loading extension command exception",e);
		}
		
		try{
			analyseMessageDrivenBean(clazz);
		}catch(Exception e){
			PlatformLogger.error("scorpion-4098:Loading message driven bean failure !",e);
		}
		
	}

}
