package org.scorpion.persistence.transaction;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.scorpion.api.util.SystemUtils;


public class TscpModifyClassAdpater extends ClassAdapter{
	
	private String METHOD_REGEX = "(.*)(_Dao)$";

	public TscpModifyClassAdpater(ClassVisitor arg0) {
		super(arg0);
	}
	
	
	public MethodVisitor visitMethod(final int access, final String name,final String desc, final String signature, final String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,exceptions);
		MethodVisitor wrappedMv = mv;
		if (mv != null&& SystemUtils.regularExpressionValidate(name, METHOD_REGEX)) {
			wrappedMv = new TscpModifyMethodAdapter(mv);
		}
		return wrappedMv;
	}
	

}
