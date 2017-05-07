package org.scorpion.api.persistence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.scorpion.api.configuration.PojoEntityInfo.PoProperty;
import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.kernel.ApplicationContext;

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
public abstract class AbsTscpResultHandler<T> implements ITscpResultHandler<T>{
	
	protected ApplicationContext context;
	
	
	
	/**
	 * @param name
	 * 
	 * @return
	 * 
	 * @throws TscpBaseException
	 */
    protected String columnConvert(String name)throws TscpBaseException{
    
    	name = name.toLowerCase();
    	String[] namesplit = name.split("_");
    	
    	if(namesplit.length == 1)
    		return name;
    	
    	StringBuilder sb = new StringBuilder();
    	
    	for(int i=0;i<namesplit.length;i++){
    	
    		if(i == 0)
    			sb.append(namesplit[i]);
    		else
    			sb.append(namesplit[i].replaceFirst(Character.toString(namesplit[i].charAt(0)), Character.toString(namesplit[i].charAt(0)).toUpperCase()));
    	}
    	
    	return sb.toString();
    }
    
    
    /**
     * @param obj
     * 
     * @param result
     * 
     * @throws TscpBaseException
     * 
     * @throws SQLException
     * 
     * @throws IllegalAccessException 
     * 
     * @throws InvocationTargetException 
     * 
     * @throws InstantiationException 
     */
    protected Object resultConvertPojo(Class<?> clazz,ResultSet result)throws TscpBaseException, SQLException, IllegalAccessException, InvocationTargetException, InstantiationException{ 
    	
    	PoProperty pojoInfo = context.getSystemPojoInformation().getPojoByName(clazz.getName());
    
    	Object resultObj = clazz.newInstance();
    	try{
    		for(int i=0;i<result.getMetaData().getColumnCount();i++){
    			Method method = pojoInfo.getSetAttributeByName(result.getMetaData().getColumnLabel(i+1));
    	
    			if(method == null)
    				throw new TscpBaseException("TSCP-9086:注解不正确!");
    			
    			method.invoke(resultObj, dbDataTypeConvert(result.getObject(i+1)));
    		}
    	}catch(SQLException e){
    		throw e;
    	} catch (IllegalArgumentException e) {
    		throw e;
		} catch (IllegalAccessException e) {
			throw e;
		} catch (InvocationTargetException e) {
			throw e;
		}
    	return resultObj;
    }
    
    
    /**
     * @param inType
     * 
     * @return
     * @throws SQLException 
     * 
     * @throws TscpBaseException
     */
	protected Object dbDataTypeConvert(Object inType) throws SQLException{
		
		if (inType == null) 
			return null;
		
		    if (inType instanceof Timestamp)
				return (Timestamp)inType;
			
			else if (inType instanceof java.sql.Date)
				return (Date)inType;
			
			else if (inType instanceof BigDecimal){
				if(((BigDecimal)inType).scale()>0){
					return ((BigDecimal)inType).floatValue();
				}else{
					return ((BigDecimal)inType).intValue();
				}
			}else if(inType instanceof Clob)
				return ((Clob)inType).getSubString(1, (int)((Clob)inType).length());
			
			return inType;
	}
	
}
