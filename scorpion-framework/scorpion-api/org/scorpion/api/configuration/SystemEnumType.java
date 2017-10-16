package org.scorpion.api.configuration;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: system resource enum information </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public enum SystemEnumType {
	
	systemcoreconfigresource("systemCoreConfigResource","系统核心资源配置信息"),
	
	systemlogconfigresource("systemLogConfigResource","系统日志配置文件信息"),
	
	systemexceptionconfigresource("systemExceptonConfigResource","系统异常配置文件信息"),
	
	systemcomponentresource("systemComponentResource","系统组件配置资源"),
	
	systemsqlconfigresource("systemSQLConfigResource","系统SQL文件配置信息"),
	
	systemdatasourceresource("systemDataSourceResource","数据源配置文件"),
	
	persistentMediate("persistentMediate","数据元数据信息"),
	
	concurrentcomputingmodelsyn("syn","同步模式"),
	
	concurrentcomputingmodelasyn("asyn","异步模式"),
	
	threadgroup("g","线程组"),
	
	threadpool("p","线程池"),
	
	logconfigfileparser("logparser","日志文件解析器"),
	
	sqlconfigfileparser("sqlparser","SQL解析器"),
	
	exceptionconfigparser("exceptionparser","异常解析器"),
	
	coreconfigfileparser("systemparser","核心文件解析器"),
	
	datasourceconfigparser("datasourceparser","数据库配置文件解析器"),
	
	datasource("datasource","数据源缓存");
	
	
	private String value;
	
	private String comment;
	
	SystemEnumType(String value,String comment) {
		this.value = value;
		this.comment = comment;
	}
	
	public String getValue(){
		return this.value;
	}
	
	/**
	 * get value of the enum
	 * @return
	 */
	public String getComment(){
		return this.comment;
	}
	
	
	

}
