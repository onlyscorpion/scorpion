package org.scorpion.kernel.container.guice;

import org.scorpion.kernel.container.tool.BytecodeUtils;



public class TscpProxyClassGenerator extends BytecodeUtils{}
/*
import com.css.sword.kernel.platform.component.utils.com.taiji.tscp.common.bytecode.Type;
import com.css.sword.kernel.utils.SwordLogUtils;
import com.taiji.tscp.api.configuration.TscpSystemScanInfo.ServiceInfo;
import com.taiji.tscp.api.log.PlatformLogger;
import com.taiji.tscp.kernel.container.tool.BytecodeUtils;
import com.taiji.tscp.kernel.container.tool.BytecodeUtils.ClassWriterUtil;
import com.taiji.tscp.kernel.container.tool.Label;
import com.taiji.tscp.kernel.container.tool.Opcodes;
import com.taiji.tscp.kernel.container.tool.commons.LocalVariablesSorter;


public class TscpProxyClassGenerator extends BytecodeUtils{
	
	
	
	    private static String SERVICE_PROXY_SUPER_CLASS = "com.taiji.tscp.api.kernel.AbsTscpProxyService";
		private static final int THIS = 0;
		private static final int PARAM_SERVICE_NAME = THIS + 1;
		private static final int PARAM_METHOD_INDEX = PARAM_SERVICE_NAME + 1;
		// 方法参数3在本地变量表中的位置
		private static final int PARAM_ARGS = PARAM_METHOD_INDEX + 1;
		// 方法参数4在本地变量表中的位置
		private static final int PARAM_CONTAINER = PARAM_ARGS + 1;
		// 服务类
		private static final int PARAM_SERVICE_CLASS = PARAM_CONTAINER + 1;
		// 第1个临时变量在本地变量表中的位置
		private static final int VAR_VALUE = PARAM_SERVICE_CLASS + 1;
		// 临时变量
		private static final int VAR_TMP_OBJECT = VAR_VALUE + 1;
		// 方法调用参数数组
		private static final int VAR_NEW_ARRAY = VAR_TMP_OBJECT + 1;
		// 变长参数类型转换临时的for循环计数变量
		private static final int VAR_PARAM_ARGS_INDEX = VAR_NEW_ARRAY + 1;
		// 调用参数数组长度
		private static final int VAR_PARAM_ARGS_LENGTH = VAR_PARAM_ARGS_INDEX + 1;
		// 变长参数类型转换临时的for循环计数变量
		private static final int VAR_NEW_ARRAY_INDEX = VAR_PARAM_ARGS_LENGTH + 1;
		// 新数组的长度
		private static final int VAR_NEW_ARRAY_LENGTH = VAR_NEW_ARRAY_INDEX + 1;
		// 调试对象位置，最后一个变量之后
		static final int VAR_DEBUG = VAR_NEW_ARRAY_LENGTH + 1;
		
		private static final String LOG_UTILS = Type.getDescriptor(PlatformLogger.class);

	
	
	
	private static byte[] generateCode(final String serviceClass, final String serviceProxyClass, final ServiceInfo info)
			throws Exception {
		final ClassWriterUtil cwu = createClassWriter(serviceProxyClass, SERVICE_PROXY_SUPER_CLASS);
		// 生成服务方法代码
		{
			// 方法头定义
			// public Object callService(String serviceName, int methodIndex, Object[] args,
			// SwordServiceContainer container,Class<?> serviceClass) throws Exception {

			final LocalVariablesSorter lvsivks = methodBegin(cwu, "invokeService", "()Ljava/lang/Object;", new String[] {"com/taiji/tscp/api/"
					+ "exception/TscpBaseException" });
			lvsivks.visitVarInsn(Opcodes.ALOAD,Opcodes.ACONST_NULL);
			lvsivks.visitInsn(Opcodes.ARETURN);
			lvsivks.visitEnd();
			
			final LocalVariablesSorter lvs = methodBegin(cwu, "callService", "(Ljava/lang/String;I[Ljava/lang/Object;L"
					+ "" + ";Ljava/lang/Class;)Ljava/lang/Object;", new String[] { "java/lang/Exception" });
			{
				// 声名第1个临时变量，并赋值为null
				// 例: Object value = null;
				addLineLabel(lvs, 1, "Object value = null;");
				lvs.visitInsn(ACONST_NULL);
				lvs.visitVarInsn(ASTORE, VAR_VALUE);

				// 输出服务调用信息
				if (PlatformLogger.isShowServiceCall) {
					// 输出被调用服务的位置信息
					addLineLabel(lvs, 2, "服务对象.log.debug(......);");
					lvs.visitVarInsn(ALOAD, THIS);
					lvs.visitFieldInsn(GETFIELD, serviceProxyClass, "log", LOG_UTILS);
					StringBuilder msg = new StringBuilder();
					msg.append("开始调用服务容器").append(info.getClass().getName()).append("的").append(info.getServiceName()).append("服务");
					lvs.visitLdcInsn(msg.toString());
					lvs.visitMethodInsn(INVOKEVIRTUAL, LOG_UTILS_CLASS, "debug", "(Ljava/lang/Object;)V", false);

					if (info.deprecatedInfo != null && !"".equals(info.deprecatedInfo.trim())) {
						// 输出被调用服务的警告信息
						addLineLabel(lvs, 4, "服务对象.log..warn(调用了不在支持的服务);");
						lvs.visitVarInsn(ALOAD, THIS);
						lvs.visitFieldInsn(GETFIELD, serviceProxyClass, "log", LOG_UTILS);
						lvs.visitVarInsn(ALOAD, PARAM_CONTAINER);
						lvs.visitFieldInsn(GETFIELD, SERVICE_CONTAINER_CLASS, "serviceInfo", SERVICE_INFO);
						lvs.visitFieldInsn(GETFIELD, SERVICE_INFO_CLASS, "deprecatedInfo", "Ljava/lang/String;");
						lvs.visitMethodInsn(INVOKEVIRTUAL, LOG_UTILS_CLASS, "warn", "(Ljava/lang/Object;)V", false);
					}
				}

				// 调用服务类的静态方法或对象方法
				if (info.isStatic) {
					// 调用服务类的静态服务方法
					// 例: Service1.howOldIs(...);
					addLineLabel(lvs, 5, "服务类.服务方法(......)");
					lvs.visitMethodInsn(INVOKESTATIC, serviceClass, info.methodName,
							processParamAnReturn(lvs, info.methodParameters, info.returnType, info.isVarArgs), false);
				} else {
					addLineLabel(lvs, 6, "(服务类)引用对象实例");
					// 获取服务代理类的成员变量service，并将其强制造型为对应的服务类，然后将其赋值为第2个临时变量
					if (info.singleMode) {
						// 例:Service1 s = (Service1) this.service;
						lvs.visitVarInsn(ALOAD, THIS);
						lvs.visitFieldInsn(GETFIELD, serviceProxyClass, "service", "Ljava/lang/Object;");
						lvs.visitTypeInsn(CHECKCAST, serviceClass);
					} else {
						if (SwordComponentRef.serviceManager.isUseIoC()) {
							// 例:Service1 s = SwordIoCUtils.findBean(Service1.class);
							lvs.visitVarInsn(ALOAD, PARAM_SERVICE_CLASS);
							lvs.visitMethodInsn(INVOKESTATIC, IOC_UTILS, "findBean", "(Ljava/lang/Class;)Ljava/lang/Object;", false);
							lvs.visitTypeInsn(CHECKCAST, serviceClass);
						} else {
							// 例:Service1 s = new Service1();
							lvs.visitTypeInsn(NEW, serviceClass);
							lvs.visitInsn(DUP);
							lvs.visitMethodInsn(INVOKESPECIAL, serviceClass, "<init>", "()V", false);
						}
					}

					*//**
					 * 优化强制类型转换后到调用对象方法的对象出入栈动作<br>
					 * mv.visitVarInsn(ASTORE, VAR_2); <br>
					 * mv.visitVarInsn(ALOAD, VAR_2);
					 *//*

					// 调用服务对象的服务方法
					// 例: s.howOldIs(...);
					addLineLabel(lvs, 7, "服务对象.服务方法(......);");

					// 输出调试信息
					// debug(mv, -1);

					lvs.visitMethodInsn(INVOKEVIRTUAL, serviceClass, info.methodName,
							processParamAnReturn(lvs, info.methodParameters, info.returnType, info.isVarArgs), false);
				}

				// 处理服务方法调用返回值
				// value=...
				processReturn(lvs, info.returnType, true, 8);

				methodEnd(lvs, VAR_VALUE);
			}
		}
		return cwu.visitEnd();
	}
	
	
	private static void addLineLabel(final LocalVariablesSorter lvs, final int line, final String des) {
		final Label label = new Label() {
			public String toString() {
				return des;
			}
		};
		lvs.visitLabel(label);
		lvs.visitLineNumber(line, label);
	}
	
	

}
*/