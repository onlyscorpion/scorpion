package ${clazz.packagename};
 
import java.io.Serializable;
import com.taiji.tscp.common.annotation.Column;
import com.taiji.tscp.common.annotation.Entity;
import com.taiji.tscp.api.kernel.AbsTscpBasePo;
import java.util.Map;
import java.util.HashMap;

<#if clazz.imports??>
 <#list clazz.imports as being>
import ${being};
 </#list>
 </#if>
 
 /**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>package ${clazz.packagename}
 * <p>File:  ${clazz.classname}Po.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: SYSTEM GENERATE POJO INFORMATION CLASS </p>
 * <p>Description: POJO INFORMATION MAPPING DATABASE </p>
 * <p>Copyright: Copyright (c) 2015 TAIJI.COM.CN</p>
 * <p>Company: TAIJI.COM.CN</p>
 * <p>module: ENTITY CLASS</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
@Entity(tableName="${model.tableName}")
 public class ${clazz.classname}Po extends AbsTscpBasePo implements  Serializable{
 
 	private static final long serialVersionUID = 1L;

 <#list attributes as being>
    @Column(columnName="${being.columnName}" <#if being.isPrimaryKey == "P">,isPrimaryKey=true<#else>,isPrimaryKey=false</#if><#if being.nullenable == "Y">,nullable=true<#else>,nullable=false</#if><#if being.dataDefault != "null">,dataDefault="${being.dataDefault}"</#if>)
 	private ${being.attributeType} ${being.attributeName};
 </#list>
    private java.util.List<Object> primaryKeyValue;
    private java.util.List<java.lang.String> primaryKeyFieldName;
 
 
    public ${clazz.classname}Po(){
 	    init();
	}
	
 		
    private void init(){
    	
    	this.dynamicKey = new StringBuilder();
    	dynamicKey = new StringBuilder();
        this.dynamicKey.append("${clazz.packagename}.${clazz.classname}Po");
 	    initFieldMappingType();
    	
    	<#list attributes as being>
    	<#if (being.attributeType != "float"&&being.attributeType != "double")>
 	    ${being.attributeName} = null;
 	    </#if>
        </#list>
    }
 
 <#list attributes as being>
 	public ${being.attributeType} get${being.attributeName?cap_first}() {
 		return ${being.attributeName};
 	}
 <#if being.attributeType == "byte[]">
 	public String get${being.attributeName?cap_first}StringValue(String charset)throws UnsupportedEncodingException{
 	    return  new java.lang.String(${being.attributeName},charset);
 	}
 	
 	public String get${being.attributeName?cap_first}StringValue()throws UnsupportedEncodingException{
 	    return new java.lang.String(${being.attributeName},"UTF-8");
 	}
 </#if>
 	
 	public void set${being.attributeName?cap_first}(${being.attributeType} ${being.attributeName},boolean isCondition) {
 	    if(isCondition)
 	       this.setConditionField("${being.columnName}");
 	    else
 	       this.setFieldInfo("${being.columnName}");
 		this.${being.attributeName} = ${being.attributeName};
 	}
 	
    public void set${being.attributeName?cap_first}(${being.attributeType} ${being.attributeName}) {
 	    this.setFieldInfo("${being.columnName}");
 		this.${being.attributeName} = ${being.attributeName};
 	}
 </#list>
 
 	@Override
	public Map<String, Object> pojoToMap() {
	
	<#if attributes??>
	   Map<String,Object> map = new HashMap<String,Object>();
    <#list attributes as being>
 	   map.put("${being.attributeName}", ${being.attributeName});
 	</#list>
 	   return map;
 	<#else>
	   return null;
	</#if>
	}

	@Override
	public ${clazz.classname}Po convertMapByPo(Map<?, ?> map) {
		
	   <#if attributes??>
       <#list attributes as being>
       <#if being.attributeType == "float">
	    this.set${being.attributeName?cap_first}((java.lang.Float)map.get("${being.attributeName}"));
 	   <#elseif being.attributeType == "double">
 	   	this.set${being.attributeName?cap_first}((java.lang.Double)map.get("${being.attributeName}"));
 	   <#else>
 	     this.set${being.attributeName?cap_first}((${being.attributeType})map.get("${being.attributeName}"));
 	   </#if>
 	   </#list>
 	     return this;
 	   <#else>
	     return null;
	   </#if>
	}
	
	
	@Override
	public String getTableName() {
		return "${model.tableName}";
	}
	
	@Override
	public Object[] getPrimaryKeyValue(){
	
	    if(primaryKeyValue == null)
	       primaryKeyValue = new java.util.ArrayList<Object>();
	    primaryKeyValue.clear();
        <#list attributes as being>
        <#if being.isPrimaryKey == "P"> 
        primaryKeyValue.add(this.${being.attributeName});
        </#if>
        </#list>
        
        return primaryKeyValue.toArray();
	}
	
	@Override
	public String[] getPrimaryKeyFieldName(){
	      
	   if(primaryKeyFieldName == null) 
	        primaryKeyFieldName = new java.util.ArrayList<java.lang.String>();
	   primaryKeyFieldName.clear(); 
       <#list attributes as being>
       <#if being.isPrimaryKey == "P"> 
        primaryKeyFieldName.add("${being.attributeName}");
       </#if>
       </#list>
        return (String[])primaryKeyFieldName.toArray();
	}
	
	@Override
	public String[] getFieldName(){
	
	  return null;
	}
	
	public Object getValueByFieldName(String fieldName){
	 
	  return null;
	}
	
	@Override
	public boolean isNotNull(){
	
	   return false;
	}
	
	
	@Override
	public String getFieldMappingSQLByFieldName(String fieldName) {
		return this.fieldMappingSQL.get(fieldName);
	}


	@Override
	protected void initFieldMappingType() {
			
    <#list attributes as being>
 	    this.fieldMappingType.put("${being.columnName}", ${being.attributeType}.class);
 	</#list>
	}
	
	
	@Override
	public void clear() {
		
		init();
	}
	
 }