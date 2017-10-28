package com.scorpion.huakerongtong.kernel.container.tool;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.log.PlatformLogger;
import com.scorpion.huakerongtong.kernel.container.tool.BytecodeUtils.ClassWriterUtil;
import com.scorpion.huakerongtong.kernel.container.tool.commons.LocalVariablesSorter;
import com.scorpion.huakerongtong.kernel.container.tool.util.Printer;
import com.scorpion.huakerongtong.kernel.container.tool.util.Textifier;
import com.scorpion.huakerongtong.kernel.container.tool.util.TraceClassVisitor;

import java.util.Stack;

public abstract class BytecodeUtils extends ClassUtils implements Opcodes {

	public static boolean isPrimitiveType(String type) {
		if (type.equals("char")) {
			return true;
		} else if (type.equals("byte")) {
			return true;
		} else if (type.equals("int")) {
			return true;
		} else if (type.equals("short")) {
			return true;
		} else if (type.equals("long")) {
			return true;
		} else if (type.equals("float")) {
			return true;
		} else if (type.equals("double")) {
			return true;
		} else if (type.equals("boolean")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPrimitivePackageType(Class<?> clazz) {
		if (clazz == Character.class) {
			return true;
		} else if (clazz == Byte.class) {
			return true;
		} else if (clazz == Integer.class) {
			return true;
		} else if (clazz == Short.class) {
			return true;
		} else if (clazz == Long.class) {
			return true;
		} else if (clazz == Float.class) {
			return true;
		} else if (clazz == Double.class) {
			return true;
		} else if (clazz == Boolean.class) {
			return true;
		} else {
			return false;
		}
	}

	public static Class<?> getPrimitiveTypeByPackageType(Class<?> clazz) {
		if (clazz == Character.class) {
			return Character.TYPE;
		} else if (clazz == Byte.class) {
			return Byte.TYPE;
		} else if (clazz == Integer.class) {
			return Integer.TYPE;
		} else if (clazz == Short.class) {
			return Short.TYPE;
		} else if (clazz == Long.class) {
			return Long.TYPE;
		} else if (clazz == Float.class) {
			return Float.TYPE;
		} else if (clazz == Double.class) {
			return Double.TYPE;
		} else if (clazz == Boolean.class) {
			return Boolean.TYPE;
		} else {
			return clazz;
		}
	}

	public static Class<?> getPackageTypeByPrimitiveType(Class<?> clazz) {
		if (clazz == Character.TYPE) {
			return Character.class;
		} else if (clazz == Byte.TYPE) {
			return Byte.class;
		} else if (clazz == Integer.TYPE) {
			return Integer.class;
		} else if (clazz == Short.TYPE) {
			return Short.class;
		} else if (clazz == Long.TYPE) {
			return Long.class;
		} else if (clazz == Float.TYPE) {
			return Float.class;
		} else if (clazz == Double.TYPE) {
			return Double.class;
		} else if (clazz == Boolean.TYPE) {
			return Boolean.class;
		} else {
			return clazz;
		}
	}

	public static String getPackageTypeByPrimitiveType(String type) {
		if (type.equals("char")) {
			return "java.lang.Character";
		} else if (type.equals("byte")) {
			return "java.lang.Byte";
		} else if (type.equals("int")) {
			return "java.lang.Integer";
		} else if (type.equals("short")) {
			return "java.lang.Short";
		} else if (type.equals("long")) {
			return "java.lang.Long";
		} else if (type.equals("float")) {
			return "java.lang.Float";
		} else if (type.equals("double")) {
			return "java.lang.Double";
		} else if (type.equals("boolean")) {
			return "java.lang.Boolean";
		} else {
			return type;
		}
	}

	public static boolean checkMatch(final Class<?> class1, final Class<?> class2) {
		if (class1 == class2) {
			return true;
		} else if (getPackageTypeByPrimitiveType(class1) == getPackageTypeByPrimitiveType(class2)) {
			return true;
		} else {
			return class2.isAssignableFrom(class1);
		}
	}

	// --------------------------------------------------------------------------------------------------------------------------------------

	public static final class ClassWriterUtil {
		private final ClassWriter writer;
		private final TraceClassVisitor tracer;
		private final Stack<Object> stack;
		private final Map<Integer, Object> localVarTable;
		public LocalVariablesSorter lvs;
		public boolean isExceptionAStore = false;

		public ClassWriterUtil(final ClassWriter writer, final boolean enableTrace) {
			this.writer = writer;
			if (enableTrace) {
				boolean calcStack = true;
				final BytecodePrinter printer = new BytecodePrinter(this, calcStack);
				this.stack = printer.getStack();
				this.localVarTable = printer.getLocalVarTable();
				tracer = new TraceClassVisitor(writer, printer, new PrintWriter(System.out));
			} else {
				this.tracer = null;
				this.stack = null;
				this.localVarTable = null;
			}
		}

		public Stack<Object> getStack() {
			return stack;
		}

		public Map<Integer, Object> getLocalVarTable() {
			return localVarTable;
		}

		public ClassVisitor getVisitor() {
			if (this.tracer != null) {
				return this.tracer;
			} else {
				return this.writer;
			}
		}

		public byte[] visitEnd() {
			getVisitor().visitEnd();
			return this.writer.toByteArray();
		}
	}

	public static ClassWriterUtil createClassWriter(final String name) {
		return createClassWriter(name, null, null);
	}

	public static ClassWriterUtil createClassWriter(final String name, final String superName) {
		return createClassWriter(name, superName, null);
	}

	public static ClassWriterUtil createClassWriter(final String name, final String[] interfaces) {
		return createClassWriter(name, null, interfaces);
	}

	public static ClassWriterUtil createClassWriter(final String name, final String superName, final String[] interfaces) {
		return createClassWriter(name, null, superName, interfaces);
	}

	public static ClassWriterUtil createClassWriter(final String name, final String signature, final String superName,
			final String[] interfaces) {
		// Bytecode跟踪开关
		boolean enableTrace = false;

		final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		final ClassWriterUtil cwu = new ClassWriterUtil(cw, enableTrace);
		final String superClass = superName == null ? "java/lang/Object" : superName;
		cwu.getVisitor().visit(V1_6, ACC_PUBLIC + ACC_SUPER, name, signature, superClass, interfaces);
		final LocalVariablesSorter lvs = methodBegin(cwu, "<init>", "()V");
		{
			lvs.visitVarInsn(ALOAD, 0);
			lvs.visitMethodInsn(INVOKESPECIAL, superClass, "<init>", "()V", false);
			lvs.visitInsn(RETURN);
		}
		methodEnd(lvs);
		return cwu;
	}

	public static byte[] visitEnd(final ClassWriterUtil cwu) {
		return cwu.visitEnd();
	}

	public static LocalVariablesSorter methodBegin(final ClassWriterUtil cwu, final String name, final String desc) {
		return methodBegin(cwu, name, desc, null);
	}

	public static LocalVariablesSorter methodBegin(final ClassWriterUtil cwu, final int access, final String name, final String desc) {
		return methodBegin(cwu, access, name, desc, null);
	}

	public static LocalVariablesSorter methodBegin(final ClassWriterUtil cwu, final String name, final String desc,
			final String[] exceptions) {
		return methodBegin(cwu, ACC_PUBLIC, name, desc, exceptions);
	}

	public static LocalVariablesSorter methodBegin(final ClassWriterUtil cwu, final Method method) {
		final int access;
		if (isPublic(method)) {
			access = ACC_PUBLIC;
		} else if (isProtected(method)) {
			access = ACC_PROTECTED;
		} else {
			access = ACC_PRIVATE;
		}
		final String desc = Type.getMethodDescriptor(method);
		return methodBegin(cwu, access, method.getName(), desc);
	}

	public static LocalVariablesSorter methodBegin(final ClassWriterUtil cwu, final int access, final String name, final String desc,
			final String[] exceptions) {
		final MethodVisitor mv = cwu.getVisitor().visitMethod(access, name, desc, null, exceptions);
		final LocalVariablesSorter lvs = new LocalVariablesSorter(access, desc, mv);
		cwu.lvs = lvs;
		lvs.visitCode();
		return lvs;
	}

	public static void methodEnd(final LocalVariablesSorter lvs) {
		lvs.visitMaxs(1, 1);
		lvs.visitEnd();
	}

	public static void methodEnd(final LocalVariablesSorter lvs, final int var) {
		if (var >= 0) {
			lvs.visitVarInsn(ALOAD, var);
		}
		lvs.visitInsn(ARETURN);
		lvs.visitMaxs(1, 1);
		lvs.visitEnd();
	}

	// --------------------------------------------------------------------------------------------------------------------------------------

	public static int newObject(final ClassWriterUtil cwu, final String name, final int var) {
		cwu.lvs.visitTypeInsn(NEW, name);
		cwu.lvs.visitInsn(DUP);
		cwu.lvs.visitMethodInsn(INVOKESPECIAL, name, "<init>", "()V", false);
		if (var > 0) {
			cwu.lvs.visitVarInsn(ASTORE, var);
		}
		return var;
	}

	public static int cast(final ClassWriterUtil cwu, final int var1, final Class<?> clazz, final int var2) {
		return cast(cwu, var1, Type.getInternalName(clazz), var2);
	}

	public static int cast(final ClassWriterUtil cwu, final int var1, final String clazz, final int var2) {
		if (var1 > 0) {
			cwu.lvs.visitVarInsn(ALOAD, var1);
		}
		cwu.lvs.visitTypeInsn(CHECKCAST, clazz);
		if (var2 > 0) {
			cwu.lvs.visitVarInsn(ASTORE, var2);
		}
		return var2;
	}

	public static void packageTypeConvertPrimitiveType(final ClassWriterUtil cwu, final int var, final Class<?> clazz, boolean checkNull)
			throws ScorpionBaseException {
		packageTypeConvertPrimitiveType(cwu, var, Type.getDescriptor(clazz), checkNull);
	}

	public static void packageTypeConvertPrimitiveType(final ClassWriterUtil cwu, final int var, final String innerType,
			final boolean checkNull) throws ScorpionBaseException {
		final LocalVariablesSorter lvs = cwu.lvs;
		final Label l1 = new Label();
		final Label l2 = new Label();

		if ("Ljava/lang/Character;".equals(innerType)) {
			if (checkNull) {
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitJumpInsn(IFNONNULL, l1);
				{
					lvs.visitInsn(ICONST_0);
					lvs.visitJumpInsn(GOTO, l2);
				}
			}
			{
				lvs.visitLabel(l1);
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitTypeInsn(CHECKCAST, "java/lang/Character");
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C", false);
				lvs.visitLabel(l2);
			}
		} else if ("Ljava/lang/Byte;".equals(innerType)) {
			if (checkNull) {
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitJumpInsn(IFNONNULL, l1);
				{
					lvs.visitInsn(ICONST_0);
					lvs.visitJumpInsn(GOTO, l2);
				}
			}
			{
				lvs.visitLabel(l1);
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitTypeInsn(CHECKCAST, "java/lang/Byte");
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B", false);
				lvs.visitLabel(l2);
			}
		} else if ("Ljava/lang/Integer;".equals(innerType)) {
			if (checkNull) {
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitJumpInsn(IFNONNULL, l1);
				{
					lvs.visitInsn(ICONST_0);
					lvs.visitJumpInsn(GOTO, l2);
				}
			}
			{
				lvs.visitLabel(l1);
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitTypeInsn(CHECKCAST, "java/lang/Integer");
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
				lvs.visitLabel(l2);
			}
		} else if ("Ljava/lang/Short;".equals(innerType)) {
			if (checkNull) {
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitJumpInsn(IFNONNULL, l1);
				{
					lvs.visitInsn(ICONST_0);
					lvs.visitJumpInsn(GOTO, l2);
				}
			}
			{
				lvs.visitLabel(l1);
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitTypeInsn(CHECKCAST, "java/lang/Short");
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S", false);
				lvs.visitLabel(l2);
			}
		} else if ("Ljava/lang/Long;".equals(innerType)) {
			if (checkNull) {
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitJumpInsn(IFNONNULL, l1);
				{
					lvs.visitInsn(LCONST_0);
					lvs.visitJumpInsn(GOTO, l2);
				}
			}
			{
				lvs.visitLabel(l1);
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitTypeInsn(CHECKCAST, "java/lang/Long");
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
				lvs.visitLabel(l2);
			}
		} else if ("Ljava/lang/Float;".equals(innerType)) {
			if (checkNull) {
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitJumpInsn(IFNONNULL, l1);
				{
					lvs.visitInsn(FCONST_0);
					lvs.visitJumpInsn(GOTO, l2);
				}
			}
			{
				lvs.visitLabel(l1);
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitTypeInsn(CHECKCAST, "java/lang/Float");
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F", false);
				lvs.visitLabel(l2);
			}
		} else if ("Ljava/lang/Double;".equals(innerType)) {
			if (checkNull) {
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitJumpInsn(IFNONNULL, l1);
				{
					lvs.visitInsn(DCONST_0);
					lvs.visitJumpInsn(GOTO, l2);
				}
			}
			{
				lvs.visitLabel(l1);
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitTypeInsn(CHECKCAST, "java/lang/Double");
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
				lvs.visitLabel(l2);
			}
		} else if ("Ljava/lang/Boolean;".equals(innerType)) {
			if (checkNull) {
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitJumpInsn(IFNULL, l1);
				{
					lvs.visitInsn(ICONST_0);
					lvs.visitJumpInsn(GOTO, l2);
				}
			}
			{
				lvs.visitLabel(l1);
				lvs.visitVarInsn(ALOAD, var);
				lvs.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
				lvs.visitLabel(l2);
			}
		} else {
			throw new ScorpionBaseException("不支持的对象类型转换:" + innerType);
		}
	}

	public static void primitiveTypeConvertPackageType(final ClassWriterUtil cwu, final Class<?> clazz, final int var)
			throws ScorpionBaseException {
		primitiveTypeConvertPackageType(cwu, Type.getDescriptor(clazz), var);
	}

	public static void primitiveTypeConvertPackageType(final ClassWriterUtil cwu, final String innerType, final int var)
			throws ScorpionBaseException {
		final LocalVariablesSorter lvs = cwu.lvs;
		if ("C".equals(innerType)) {
			lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
		} else if ("B".equals(innerType)) {
			lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
		} else if ("I".equals(innerType)) {
			lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
		} else if ("S".equals(innerType)) {
			lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
		} else if ("J".equals(innerType)) {
			lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
		} else if ("F".equals(innerType)) {
			lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
		} else if ("D".equals(innerType)) {
			lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
		} else if ("Z".equals(innerType)) {
			lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
		} else {
			throw new ScorpionBaseException("不支持类型" + innerType);
		}
		if (var > 0) {
			lvs.visitVarInsn(ASTORE, var);
		}
	}

	public static void stringToPrimitiveType(final ClassWriterUtil cwu, final int var, final Class<?> clazz) {
		final LocalVariablesSorter lvs = cwu.lvs;
		final Label isNull = new Label();
		final Label end = new Label();

		lvs.visitVarInsn(ALOAD, var);
		lvs.visitJumpInsn(IFNULL, isNull);
		{
			cast(cwu, var, "java/lang/String", var);
			lvs.visitVarInsn(ALOAD, var);
			lvs.visitLdcInsn("\"\"");
			lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
			lvs.visitJumpInsn(IFNE, isNull);
			{
				lvs.visitVarInsn(ALOAD, var);
				if (clazz == Character.TYPE) {
					{
						lvs.visitInsn(ICONST_0);
						lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ICONST_0);
					}
					lvs.visitVarInsn(ISTORE, var);
				} else if (clazz == Byte.TYPE) {
					{
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "parseByte", "(Ljava/lang/String;)B", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ICONST_0);
					}
					lvs.visitVarInsn(ISTORE, var);

				} else if (clazz == Short.TYPE) {
					{
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "parseShort", "(Ljava/lang/String;)S", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ICONST_0);
					}
					lvs.visitVarInsn(ISTORE, var);
				} else if (clazz == Integer.TYPE) {
					{
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "parseInt", "(Ljava/lang/String;)I", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ICONST_0);
					}
					lvs.visitVarInsn(ISTORE, var);
				} else if (clazz == Long.TYPE) {
					{
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Lang", "parseLong", "(Ljava/lang/String;)J", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(LCONST_0);
					}
					lvs.visitVarInsn(LSTORE, var);
				} else if (clazz == Float.TYPE) {
					{
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "parseFloat", "(Ljava/lang/String;)F", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(FCONST_0);
					}
					lvs.visitVarInsn(FSTORE, var);
				} else if (clazz == Double.TYPE) {
					{
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "parseDouble", "(Ljava/lang/String;)D", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(DCONST_0);
					}
					lvs.visitVarInsn(DSTORE, var);
				} else if (clazz == Boolean.TYPE) {
					{
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "parseBoolean", "(Ljava/lang/String;)Z", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ICONST_0);
					}
					lvs.visitVarInsn(ISTORE, var);
				}
			}
		}
		lvs.visitLabel(end);
	}

	public static void stringToPackageType(final ClassWriterUtil cwu, final int var, final Class<?> clazz, final boolean checkNull) {
		final LocalVariablesSorter lvs = cwu.lvs;
		final Label isNull = new Label();
		final Label end = new Label();

		if (clazz == String.class || clazz == Object.class) {
			return;
		}

		if (checkNull) {
			lvs.visitVarInsn(ALOAD, var);
			lvs.visitJumpInsn(IFNULL, isNull);
		}
		{
			cast(cwu, var, "java/lang/String", var);
			lvs.visitVarInsn(ALOAD, var);
			lvs.visitLdcInsn("\"\"");
			lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
			lvs.visitJumpInsn(IFNE, isNull);
			{
				if (clazz == Character.class) {
					{
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitInsn(ICONST_0);
						lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C", false);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ICONST_0);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
					}
				} else if (clazz == Byte.class) {
					{
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(Ljava/lang/String;)Ljava/lang/Byte;", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ICONST_0);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
					}
				} else if (clazz == Short.class) {
					{
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(Ljava/lang/String;)Ljava/lang/Short;", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ICONST_0);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
					}
				} else if (clazz == Integer.class) {
					{
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(Ljava/lang/String;)Ljava/lang/Integer;", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ICONST_0);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
					}
				} else if (clazz == Long.class) {
					{
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(Ljava/lang/String;)Ljava/lang/Long;", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(LCONST_0);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
					}
				} else if (clazz == Float.class) {
					{
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitTypeInsn(CHECKCAST, "java/lang/String");
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(Ljava/lang/String;)Ljava/lang/Float;", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(FCONST_0);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
					}
				} else if (clazz == Double.class) {
					{
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(Ljava/lang/String;)Ljava/lang/Double;", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(DCONST_0);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
					}
				} else if (clazz == Boolean.class) {
					{
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Ljava/lang/String;)Ljava/lang/Boolean;", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitFieldInsn(GETSTATIC, "java/lang/Boolean", "FALSE", "Ljava/lang/Boolean;");
					}
				} else if (clazz == BigDecimal.class) {
					{
						lvs.visitTypeInsn(NEW, "java/math/BigDecimal");
						lvs.visitInsn(DUP);
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitMethodInsn(INVOKESPECIAL, "java/math/BigDecimal", "<init>", "(Ljava/lang/String;)V", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ACONST_NULL);
					}
				} else if (clazz == BigInteger.class) {
					{
						lvs.visitTypeInsn(NEW, "java/math/BigInteger");
						lvs.visitInsn(DUP);
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitMethodInsn(INVOKESPECIAL, "java/math/BigInteger", "<init>", "(Ljava/lang/String;)V", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ACONST_NULL);
					}
				} else if (clazz == java.sql.Date.class) {
					{
						lvs.visitTypeInsn(NEW, "java/sql/Date");
						lvs.visitInsn(DUP);
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitMethodInsn(INVOKESTATIC, "com/css/sword/kernel/utils/SwordDateUtils", "parseDate",
								"(Ljava/lang/String;)Ljava/util/Calendar;", false);
						lvs.visitMethodInsn(INVOKEINTERFACE, "java/util/Calendar", "getTimeInMillis", "()J", true);
						lvs.visitMethodInsn(INVOKESPECIAL, "java/sql/Date", "<init>", "(J)V", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ACONST_NULL);
					}
				} else if (clazz == java.util.Date.class) {
					{
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitMethodInsn(INVOKESTATIC, "com/css/sword/kernel/utils/SwordDateUtils", "parseDate",
								"(Ljava/lang/String;)Ljava/util/Calendar;", false);
						lvs.visitMethodInsn(INVOKEVIRTUAL, "java/util/Calendar", "getTime", "()Ljava/util/Date;", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ACONST_NULL);
					}
				} else if (clazz == java.util.Calendar.class) {
					{
						lvs.visitVarInsn(ALOAD, var);
						lvs.visitMethodInsn(INVOKESTATIC, "com/css/sword/kernel/utils/SwordDateUtils", "parseDate",
								"(Ljava/lang/String;)Ljava/util/Calendar;", false);
						lvs.visitJumpInsn(GOTO, end);
					}
					{
						lvs.visitLabel(isNull);
						lvs.visitInsn(ACONST_NULL);
					}
				} else {
					lvs.visitLabel(isNull);
					lvs.visitInsn(ACONST_NULL);
				}
			}
		}
		lvs.visitLabel(end);
		lvs.visitVarInsn(ASTORE, var);
	}

	private static final String SwordLogUtilsClassName = Type.getInternalName(PlatformLogger.class);

	public static void logError(final ClassWriterUtil cwu, final String innerType, final String... errorMessage) {
		final LocalVariablesSorter lvs = cwu.lvs;
		final StringBuilder builder = new StringBuilder();
		for (final String str : errorMessage) {
			builder.append(str);
		}
		lvs.visitLdcInsn(Type.getType("L" + innerType + ";"));
		lvs.visitMethodInsn(INVOKESTATIC, SwordLogUtilsClassName, "getLogger", "(Ljava/lang/Class;)L" + SwordLogUtilsClassName + ";", false);
		lvs.visitLdcInsn(builder.toString());
		lvs.visitMethodInsn(INVOKEVIRTUAL, SwordLogUtilsClassName, "error", "(Ljava/lang/Object;)V", false);
	}

	public static void debug(final LocalVariablesSorter lvs, final int VAR_DEBUG, final int objectID, final Class<?> type) {
		final String INFO = "Bytecode\u8c03\u8bd5\u4fe1\u606f:";

		if (objectID < 0) {
			lvs.visitVarInsn(ASTORE, VAR_DEBUG);

			lvs.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			lvs.visitTypeInsn(NEW, "java/lang/StringBuilder");
			lvs.visitInsn(DUP);
			lvs.visitLdcInsn(INFO);
			lvs.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
			lvs.visitVarInsn(ALOAD, VAR_DEBUG);
			lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
			lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
			lvs.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

			lvs.visitVarInsn(ALOAD, VAR_DEBUG);
		} else {
			lvs.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			lvs.visitTypeInsn(NEW, "java/lang/StringBuilder");
			lvs.visitInsn(DUP);
			lvs.visitLdcInsn(INFO);
			lvs.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
			if (type == Character.TYPE) {
				lvs.visitVarInsn(ILOAD, objectID);
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(C)Ljava/lang/StringBuilder;", false);
			} else if (type == Byte.TYPE) {
				lvs.visitVarInsn(ILOAD, objectID);
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(B)Ljava/lang/StringBuilder;", false);
			} else if (type == Integer.TYPE) {
				lvs.visitVarInsn(ILOAD, objectID);
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
			} else if (type == Short.TYPE) {
				lvs.visitVarInsn(ILOAD, objectID);
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(S)Ljava/lang/StringBuilder;", false);
			} else if (type == Long.TYPE) {
				lvs.visitVarInsn(LLOAD, objectID);
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
			} else if (type == Boolean.TYPE) {
				lvs.visitVarInsn(ILOAD, objectID);
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Z)Ljava/lang/StringBuilder;", false);
			} else {
				lvs.visitVarInsn(ALOAD, objectID);
				lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;",
						false);
			}
			lvs.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
			lvs.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
		}
	}

}

class BytecodePrinter extends Printer implements Opcodes {

	private final ClassWriterUtil cwu;
	private final Textifier p = new Textifier();
	private final boolean calcStack;

	private String THIS_CLASS_NAME;
	private final Stack<Object> stack = new Stack<Object>();
	private final Stack<String> exceptionStack = new Stack<String>();
	private final Map<Label, Stack<Object>> stackCopyIndex = new HashMap<Label, Stack<Object>>();
	private final Map<Integer, Object> localVarTable = new HashMap<Integer, Object>();
	private final Map<Label, String> labelNames = new HashMap<Label, String>();

	protected BytecodePrinter(final ClassWriterUtil cwu, final boolean calcStack) {
		super(Opcodes.ASM5);
		this.cwu = cwu;
		this.calcStack = calcStack;
	}

	private void stackPush(Object... objects) {
		for (final Object obj : objects) {
			this.stack.push(obj);
		}
	}

	private Object stackPop() {
		final Object object = this.stack.pop();
		return object;
	}

	private void appendLabel(final Label... labels) {
		for (final Label l : labels) {
			String name = labelNames.get(l);
			if (name == null) {
				name = "L" + labelNames.size();
				labelNames.put(l, name);
			}
		}
	}

	private Printer print() {
		final Object text = p.text.get(p.text.size() - 1);
		if (text instanceof String) {
			System.out.print(text);
		} else {
			System.out.print(p.text.get(p.text.size() - 2));
		}
		return this;
	}

	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		p.visit(version, access, name, signature, superName, interfaces);
		this.THIS_CLASS_NAME = name;
		print();
	}

	public void visitSource(String file, String debug) {
		p.visitSource(file, debug);
		print();
	}

	public void visitOuterClass(String owner, String name, String desc) {
		p.visitOuterClass(owner, name, desc);
		print();
	}

	public Printer visitClassAnnotation(String desc, boolean visible) {
		p.visitClassAnnotation(desc, visible);
		return print();
	}

	public Printer visitClassTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
		p.visitClassTypeAnnotation(typeRef, typePath, desc, visible);
		return print();
	}

	public void visitClassAttribute(Attribute attr) {
		p.visitClassAttribute(attr);
		print();
	}

	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		p.visitInnerClass(name, outerName, innerName, access);
		print();
	}

	public Printer visitField(int access, String name, String desc, String signature, Object value) {
		p.visitField(access, name, desc, signature, value);
		return print();
	}

	public Printer visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		p.visitMethod(access, name, desc, signature, exceptions);
		this.localVarTable.put(0, this.THIS_CLASS_NAME);
		final Type[] argumentTypes = Type.getArgumentTypes(desc);
		for (int i = 0; i < argumentTypes.length; i++) {
			this.localVarTable.put(i + 1, argumentTypes[i].getClassName());
		}
		return print();
	}

	public void visitClassEnd() {
		p.visitClassEnd();
		print();
	}

	public void visit(String name, Object value) {
		p.visit(name, value);
		print();
	}

	public void visitEnum(String name, String desc, String value) {
		p.visitEnum(name, desc, value);
		print();
	}

	public Printer visitAnnotation(String name, String desc) {
		p.visitAnnotation(name, desc);
		return print();
	}

	public Printer visitArray(String name) {
		p.visitArray(name);
		return print();
	}

	public void visitAnnotationEnd() {
		p.visitAnnotationEnd();
		print();
	}

	public Printer visitFieldAnnotation(String desc, boolean visible) {
		p.visitFieldAnnotation(desc, visible);
		return print();
	}

	public Printer visitFieldTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
		p.visitFieldTypeAnnotation(typeRef, typePath, desc, visible);
		return print();
	}

	public void visitFieldAttribute(Attribute attr) {
		p.visitFieldAttribute(attr);
		print();
	}

	public void visitFieldEnd() {
		p.visitFieldEnd();
		print();
	}

	public void visitParameter(String name, int access) {
		p.visitParameter(name, access);
		print();
	}

	public Printer visitAnnotationDefault() {
		p.visitAnnotationDefault();
		return print();
	}

	public Printer visitMethodAnnotation(String desc, boolean visible) {
		p.visitMethodAnnotation(desc, visible);
		return print();
	}

	public Printer visitMethodTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
		p.visitMethodTypeAnnotation(typeRef, typePath, desc, visible);
		return print();
	}

	public Printer visitParameterAnnotation(int parameter, String desc, boolean visible) {
		p.visitParameterAnnotation(parameter, desc, visible);
		return print();
	}

	public void visitMethodAttribute(Attribute attr) {
		p.visitMethodAttribute(attr);
		print();
	}

	public void visitCode() {
		p.visitCode();
		// print();
	}

	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		p.visitFrame(type, nLocal, local, nStack, stack);
		print();
	}

	public void visitInsn(int opcode) {
		p.visitInsn(opcode);
		print();
		if (!calcStack) {
			return;
		}
		switch (opcode) {
		case NOP: {
			break;
		}
		case ACONST_NULL: {
			stackPush(new Object[] { null });
			break;
		}
		case ICONST_M1:
		case ICONST_0:
		case ICONST_1:
		case ICONST_2:
		case ICONST_3:
		case ICONST_4:
		case ICONST_5: {
			stackPush(Integer.valueOf(opcode - ICONST_M1 - 1));
			break;
		}
		case LCONST_0:
		case LCONST_1: {
			stackPush(Long.valueOf(opcode - LCONST_0));
			break;
		}
		case FCONST_0:
		case FCONST_1:
		case FCONST_2: {
			stackPush(Float.valueOf(opcode - FCONST_0));
			break;
		}
		case DCONST_0:
		case DCONST_1: {
			stackPush(Double.valueOf(opcode - DCONST_0));
			break;
		}
		case IALOAD:
		case LALOAD:
		case FALOAD:
		case DALOAD:
		case AALOAD:
		case BALOAD:
		case CALOAD:
		case SALOAD: {
			break;
		}
		case IASTORE:
		case LASTORE:
		case FASTORE:
		case DASTORE:
		case AASTORE:
		case BASTORE:
		case CASTORE:
		case SASTORE: {
			final int var = (Integer) stackPop();
			final Object type = stackPop();
			this.localVarTable.put(var, type);
			break;
		}
		case POP:
		case POP2: {
			stackPop();
			break;
		}
		case DUP: {
			stackPush(this.stack.peek());
			break;
		}
		case DUP_X1: {
			final Object o2 = stackPop();
			final Object o1 = stackPop();
			stackPush(o1, o2, o1);
			break;
		}
		case DUP_X2: {
			final Object o3 = stackPop();
			final Object o2 = stackPop();
			final Object o1 = stackPop();
			stackPush(o1, o2, o3, o1);
			break;
		}
		case DUP2: {
			final Object o2 = stackPop();
			final Object o1 = stackPop();
			stackPush(o1, o2, o1, o2);
			break;
		}
		case DUP2_X1: {
			final Object o3 = stackPop();
			final Object o2 = stackPop();
			final Object o1 = stackPop();
			stackPush(o1, o2, o3, o1, o2);
			break;
		}
		case DUP2_X2: {
			final Object o4 = stackPop();
			final Object o3 = stackPop();
			final Object o2 = stackPop();
			final Object o1 = stackPop();
			stackPush(o1, o2, o3, o4, o1, o2);
			break;
		}
		case SWAP: {
			final Object o2 = stackPop();
			final Object o1 = stackPop();
			stackPush(o2, o1);
			break;
		}
		case IADD:
		case ISUB:
		case IMUL:
		case IDIV:
		case IREM:
		case IAND:
		case IXOR:
		case IOR: {
			stackPop();
		}
		case INEG:
		case ISHL:
		case ISHR:
		case IUSHR: {
			stackPop();
			stackPush(Integer.TYPE);
			break;
		}
		case LADD:
		case LSUB:
		case LMUL:
		case LDIV:
		case LREM:
		case LAND:
		case LOR:
		case LXOR: {
			stackPop();
		}
		case LNEG:
		case LSHL:
		case LSHR:
		case LUSHR: {
			stackPop();
			stackPush(Long.TYPE);
			break;
		}
		case FADD:
		case FSUB:
		case FMUL:
		case FDIV:
		case FREM: {
			stackPop();
		}
		case FNEG: {
			stackPop();
			stackPush(Double.TYPE);
			break;
		}
		case DADD:
		case DSUB:
		case DMUL:
		case DDIV:
		case DREM: {
			stackPop();
		}
		case DNEG: {
			stackPop();
			stackPush(Double.TYPE);
			break;
		}
		case I2L:
		case L2I:
		case F2I:
		case D2I:
		case I2B:
		case I2S:
		case I2C: {
			stackPop();
			stackPush(Integer.TYPE);
			break;
		}
		case I2F:
		case L2F:
		case D2F: {
			stackPop();
			stackPush(Float.TYPE);
			break;
		}
		case I2D:
		case L2D:
		case F2D: {
			stackPop();
			stackPush(Double.TYPE);
			break;
		}
		case F2L:
		case D2L: {
			stackPop();
			stackPush(Long.TYPE);
			break;
		}
		case LCMP:
		case FCMPL:
		case FCMPG:
		case DCMPL:
		case DCMPG: {
			stackPop();
			stackPop();
			stackPush(Integer.TYPE);
			break;
		}
		case IRETURN:
		case LRETURN:
		case FRETURN:
		case DRETURN:
		case ARETURN: {
			if (this.stack.size() != 1) {
				throw new RuntimeException("执行返回指令时堆栈长度不等于1");
			}
			break;
		}
		case RETURN: {
			if (!this.stack.isEmpty()) {
				throw new RuntimeException("执行返回指令时堆栈不为空");
			}
			break;
		}

		case ARRAYLENGTH: {
			stackPop();
			stackPush(Integer.TYPE);
			break;
		}
		case ATHROW:
		case MONITORENTER:
		case MONITOREXIT: {
			stackPop();
			break;
		}
		default:
			throw new RuntimeException("不支持指令" + opcode + "的堆栈操作分析");
		}
	}

	public void visitIntInsn(int opcode, int operand) {
		p.visitIntInsn(opcode, operand);
		print();
		if (!calcStack) {
			return;
		}
		if (opcode == BIPUSH || opcode == SIPUSH) {
			stackPush(operand);
		}
	}

	public void visitVarInsn(int opcode, int var) {
		p.visitVarInsn(opcode, var);
		print();
		if (!calcStack) {
			return;
		}
		if (opcode >= ILOAD && opcode <= SALOAD) {
			if (!this.localVarTable.containsKey(var)) {
				throw new RuntimeException("本地临时变量表中不存在变量" + var);
			} else {
				stackPush(this.localVarTable.get(var));
			}
		} else if (opcode >= ISTORE && opcode <= SASTORE) {
			Object obj = null;
			if (this.cwu.isExceptionAStore) {
				obj = this.exceptionStack.pop();
				this.cwu.isExceptionAStore = false;
			} else {
				obj = stackPop();
			}
			this.localVarTable.put(var, obj);
		}
	}

	public void visitTypeInsn(int opcode, String type) {
		p.visitTypeInsn(opcode, type);
		print();
		if (!calcStack) {
			return;
		}

		switch (opcode) {
		case NEW:
		case NEWARRAY:
		case ANEWARRAY: {
			stackPush(type);
			break;
		}
		case CHECKCAST: {
			stackPop();
			stackPush(type);
			break;
		}
		case INSTANCEOF: {
			stackPop();
			stackPush(Integer.TYPE);
			break;
		}
		default:
			throw new RuntimeException("不支持指令" + opcode + "的堆栈操作分析");
		}
	}

	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		p.visitFieldInsn(opcode, owner, name, desc);
		print();
		if (!calcStack) {
			return;
		}
		switch (opcode) {
		case GETSTATIC: {
			stackPush(owner + "." + name);
			break;
		}
		case PUTSTATIC: {
			stackPop();
			break;
		}
		case GETFIELD: {
			stackPop();
			stackPush(owner + "." + name);
			break;
		}
		case PUTFIELD: {
			stackPop();
			stackPop();
			stackPush(owner + "." + name);
			break;
		}
		default: {
			throw new RuntimeException("不支持指令" + opcode + "的堆栈操作分析");
		}
		}
	}

	@Deprecated
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		visitMethodInsn(opcode, owner, name, desc, opcode == INVOKEINTERFACE);
	}

	private void calcVisitMethodInsn(int opcode, String desc) {
		if (!calcStack) {
			return;
		}
		switch (opcode) {
		case INVOKEVIRTUAL:
		case INVOKESPECIAL:
		case INVOKEINTERFACE:
		case INVOKEDYNAMIC:
		case INVOKESTATIC: {
			final Type[] argumentType = Type.getArgumentTypes(desc);
			for (int i = argumentType.length - 1; i >= 0; i--) {
				final String argumentTypeName = argumentType[i].getClassName();
				final Object object = stackPop();
				if (argumentTypeName.equals("java.lang.Class")) {
					continue;
				}
				if (object == null) {
					System.err.println("\t类型推断失败，此语句有可能引发“Incompatible argument to function”异常");
					continue;
				}
				if (object instanceof String) {
					if (argumentTypeName.equals(object)) {
						continue;
					} else {
						try {
							final Class<?> argumentTypeClass = Class.forName(argumentTypeName);
							final Class<?> objectClass = Class.forName(object.toString());
							if (argumentTypeClass.isAssignableFrom(objectClass)) {
								continue;
							}
						} catch (ClassNotFoundException e) {
							continue;
						}
					}
				} else {
					if (object instanceof Integer && BytecodeUtils.isPrimitiveType(argumentTypeName) && ((Integer) object) == 0) {
						continue;
					} else if (BytecodeUtils.getPackageTypeByPrimitiveType(argumentTypeName).equals(object.getClass().getName())) {
						continue;
					}
				}
				throw new RuntimeException("数据类型不兼容");
			}
			if (opcode != INVOKESTATIC && opcode != INVOKEDYNAMIC) {
				stackPop();
			}
			final Type returnType = Type.getReturnType(desc);
			if (returnType != Type.VOID_TYPE) {
				stackPush(returnType.getClassName());
			}
			break;
		}
		default:
			throw new RuntimeException("不支持指令" + opcode + "的堆栈操作分析");
		}
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		p.visitMethodInsn(opcode, owner, name, desc, itf);
		print();
		calcVisitMethodInsn(opcode, desc);
		if (opcode == INVOKEINTERFACE && !itf) {
			throw new RuntimeException("调用INVOKEINTERFACE指令时，itf参数应当为true");
		}
	}

	public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
		p.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
		print();
		calcVisitMethodInsn(INVOKEDYNAMIC, desc);
	}

	public void visitJumpInsn(int opcode, Label label) {
		p.visitJumpInsn(opcode, label);
		print();
		if (!calcStack) {
			return;
		}
		switch (opcode) {
		case IF_ICMPEQ:
		case IF_ICMPNE:
		case IF_ICMPLT:
		case IF_ICMPGE:
		case IF_ICMPGT:
		case IF_ICMPLE:
		case IF_ACMPEQ:
		case IF_ACMPNE: {
			stackPop();
		}
		case IFEQ:
		case IFNE:
		case IFLT:
		case IFGE:
		case IFGT:
		case IFLE:
		case IFNULL:
		case IFNONNULL: {
			stackPop();
			break;
		}
		case GOTO: {
			break;
		}
		default:
			throw new RuntimeException("不支持指令" + opcode + "的堆栈操作分析");
		}

		final Stack<Object> stackCopy = new Stack<Object>();
		stackCopy.addAll(this.stack);
		appendLabel(label);
		this.stackCopyIndex.put(label, stackCopy);
	}

	public void visitLabel(Label label) {
		p.visitLabel(label);
		print();
		final Stack<Object> stackCopy = this.stackCopyIndex.get(label);
		if (stackCopy != null) {
			this.stack.clear();
			this.stack.addAll(stackCopy);
		}
		appendLabel(label);
	}

	public void visitLdcInsn(Object cst) {
		p.visitLdcInsn(cst);
		print();
		if (!calcStack) {
			return;
		}
		stackPush(cst);
	}

	public void visitIincInsn(int var, int increment) {
		p.visitIincInsn(var, increment);
		print();
		if (!calcStack) {
			return;
		}
		stackPop();
		stackPush(Integer.TYPE);
	}

	public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
		p.visitTableSwitchInsn(min, max, dflt, labels);
		print();
		if (!calcStack) {
			return;
		}
		stackPop();
		appendLabel(dflt);
		appendLabel(labels);
	}

	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		p.visitLookupSwitchInsn(dflt, keys, labels);
		print();
		if (!calcStack) {
			return;
		}
		stackPop();
		appendLabel(dflt);
		appendLabel(labels);
	}

	public void visitMultiANewArrayInsn(String desc, int dims) {
		p.visitMultiANewArrayInsn(desc, dims);
		print();
		if (!calcStack) {
			return;
		}
		for (int i = 0; i < dims; i++) {
			stackPop();
		}
		stackPush(desc + ":" + dims);
	}

	public Printer visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
		p.visitInsnAnnotation(typeRef, typePath, desc, visible);
		return print();
	}

	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		p.visitTryCatchBlock(start, end, handler, type);
		print();
		if (!calcStack) {
			return;
		}
		exceptionStack.push(type);
		this.appendLabel(start, end, handler);
	}

	public Printer visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
		p.visitTryCatchAnnotation(typeRef, typePath, desc, visible);
		return print();
	}

	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
		p.visitLocalVariable(name, desc, signature, start, end, index);
		print();
		appendLabel(start, end);
	}

	public Printer visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc,
			boolean visible) {
		p.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
		print();
		if (calcStack) {
			appendLabel(start);
			appendLabel(end);
		}
		return this;
	}

	public void visitLineNumber(int line, Label start) {
		p.visitLineNumber(line, start);
		print();
		if (calcStack) {
			appendLabel(start);
		}
	}

	public void visitMaxs(int maxStack, int maxLocals) {
		p.visitMaxs(maxStack, maxLocals);
		print();
		if (!calcStack) {
			return;
		}
		for (final Entry<Label, String> entry : this.labelNames.entrySet()) {
			try {
				entry.getKey().getOffset();
			} catch (IllegalStateException ex) {
				throw new IllegalStateException("Label offset position " + entry.getValue() + " has not been resolved yet");
			}
		}
	}

	public void visitMethodEnd() {
		this.stack.clear();
		this.stackCopyIndex.clear();
		this.localVarTable.clear();
		this.exceptionStack.clear();
		p.visitMethodEnd();
		print();
	}

	public Printer visitAnnotation(String desc, boolean visible) {
		p.visitAnnotation(desc, visible);
		return print();
	}

	public Printer visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
		p.visitTypeAnnotation(typeRef, typePath, desc, visible);
		return print();
	}

	public void visitAttribute(Attribute attr) {
		p.visitAttribute(attr);
		print();
	}

	@Override
	public void print(PrintWriter pw) {
	}

	public Stack<Object> getStack() {
		return this.stack;
	}

	public Map<Integer, Object> getLocalVarTable() {
		return localVarTable;
	}

}