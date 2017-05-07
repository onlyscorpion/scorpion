package org.scorpion.api.kernel;

import java.util.HashMap;
import java.util.Map;

import org.scorpion.api.common.Lifecycle;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.util.Constant;

/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: if developer want to create a component. the developer must extends the abstract </p>
 * <p>class ATscpComponet. the ATscpComponent exist life cycle. developer can override</p>
 * <p>the initialization method or service method or destroy method to handle themselves business</p>
 * <p>but we don't suggest the developer do that </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsTscpComponent implements Lifecycle{
	
	public Map<String,String> arguement;
	
	protected int componentstate = Constant.STOP;
	
	protected boolean iskernelcomponent = false;
	
	
/*	public static class ComponentStatus {
		public static final byte STARTING = 1;
		public static final byte RUNNING = 2;
		public static final byte STOP = 3;
		public static final byte ERROR = 4;
	}*/
	

	/**
	 * @Description This method is used to start user custom component
	 * 
	 * @param arguments The argument is that you default at your component in the tscp.xml
	 * 
	 * @throws TscpBaseException
	 */
	public abstract void start(Map<String,String> arguments) throws TscpBaseException;
     

	@Override
	public void initialization() throws TscpBaseException {

		if(arguement == null) 
			arguement = new HashMap<String,String>();
		this.start(arguement);
	}

	@Override
	public void handler() throws TscpBaseException {
	
	}

	@Override
	public void destroy() throws TscpBaseException {
	
	}


	public int getComponentstate() {
		return componentstate;
	}


	public void setComponentstate(int componentstate) {
		this.componentstate = componentstate;
	}
	
}
