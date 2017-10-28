package com.scorpion.huakerongtong.persistence.transaction;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ScorpionModifyMethodAdapter extends MethodAdapter{

	public ScorpionModifyMethodAdapter(MethodVisitor arg0) {
		super(arg0);
	}
	
	 public void visitCode() { 
		 visitMethodInsn(Opcodes.INVOKESTATIC, "TransactionBeforeInterceptor", 
			"doBeforeAdvice", "()V"); 
	 } 

}
