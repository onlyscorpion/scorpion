package com.scorpion.huakerongtong.api.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class DBParam {
	
	private List<Object> params = new ArrayList<Object>();
	
    private List<String> dynamicParams;
    
    private boolean isUseCache;
    
    private Map<Integer,Integer> procedureOutParamType;
    
    
	
	public Map<Integer, Integer> getProcedureOutParamType() {
		return procedureOutParamType;
	}

	public void setProcedureOutParamType(Map<Integer, Integer> procedureOutParamType) {
		this.procedureOutParamType = procedureOutParamType;
	}

	public boolean isUseCache() {
		return isUseCache;
	}

	public void setUseCache(boolean isUseCache) {
		this.isUseCache = isUseCache;
	}

	public List<String> getDynamicParams() {
		return dynamicParams;
	}

	public void setDynamicParams(List<String> dynamicParams) {
		this.dynamicParams = dynamicParams;
	}

	/*public void addParam(String param){
		
	}
	
	public void addParam(Integer param){
		
	}
	
	public void addParam(Character param){
		
	}
	
	public void addParam(Double param){
		
	}
	
	public void addParam(Float param){
		
	}
	
	public void addParam(Short param){
		
	}
	
	public void addParam(Boolean param){
		
	}
	
	public void addParam(Byte param){
		
	}
	
	public void addParam(Long param){
		
	}
	
	public void addParam(java.util.Date param){
		
	}
	
	public void addParam(java.sql.Date param){
		
	}
	
	public void addParam(BigDecimal param){
		
	}*/
	
	
	public DBParam addParam(Object param){
	
		if(params == null)
			params = new ArrayList<Object>();
		
		params.add(param);
		return this;
	}
	
	public DBParam addDynamicParam(String dynamicParam){
		
		if(this.dynamicParams == null)
			this.dynamicParams = new ArrayList<String>();
		
		dynamicParams.add(dynamicParam);
		return this;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}
	
	
	public void clear(){
	
		if(params != null)
			params.clear();
	
		if(dynamicParams != null)
			dynamicParams.clear();

		if(procedureOutParamType != null)
			procedureOutParamType.clear();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean isEmpty(){
		
		if(params == null||params.isEmpty())
			return true;
	
		else
			return false;
	}
	
	public enum ProcedureType{
		IN,OUT,INOUT
	}

	@Override
	public String toString() {
	
		if(params == null&& dynamicParams == null)
			return null;
		if(params != null&&dynamicParams == null)
			return params.toString();
		if(params == null&& dynamicParams != null)
			return dynamicParams.toString();
	
		if(params != null&&dynamicParams != null){
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(Object param:params){
					sb.append(param+",");
				}
			for(String dynamic:dynamicParams){
				sb.append(dynamic+",");
			}
		
			String temp = sb.toString();
			if(temp.lastIndexOf(",") == temp.length()-1)
				temp  =	temp.substring(0, temp.length()-1);
			
			return temp+"]";
		}
		
		return params.toString();
	}

}
