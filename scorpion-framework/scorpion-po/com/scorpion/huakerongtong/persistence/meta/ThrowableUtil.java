package com.scorpion.huakerongtong.persistence.meta;

public class ThrowableUtil {
	
	
	
	/**
	 * @param ex
	 * @return
	 */
	public static String e2s(Throwable ex){
		StringBuffer sb = new StringBuffer();
		sb.append(ex.getCause()+" "+ex.getMessage());
		/*for(StackTraceElement element:ex.getStackTrace()){
			
			sb.append(element.getFileName()+" "+element.getClassName()+" "+element.getMethodName()+" "+element.getLineNumber()).append("/n/r");
		}*/
		return sb.toString();
	}
	
	

}
