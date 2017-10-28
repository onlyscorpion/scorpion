package com.scorpion.huakerongtong.api.util;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: system constant information </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class Constant {
	
	public final static String DEFULT_METHOD_NAME = "defaultMethodName";
	
	/**系统参数配置 **/
	public final static String SYSTEM_ENV_PATH = "system_env_path";
	public final static String MICRO_KERNEL_CONFIG = "/META-INF/resources/scorpion-kennel-regedit.properties";
	public final static String CORE_CONFIG_PATTERN = "Scorpion.xml";
	public final static String LOG_CONFIG_PATTERN = "scorpion-log.xml";
	public final static String DATASOURCE_CONFIG_PATTERN = "scorpion-datasource.xml";
	public final static String SQL_CONFIG_PATTERN = "(Scorpion)-*(\\w)*-*(sql.xml)";
	public final static String EXCEPTION_CONFIG_PATTERN = "(Scorpion)-*(\\w)*-*(exception.properties)";
	
	public final static String IP_PATTERN = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
	
	/** 系统运行模式 **/
	public final static String DEVELOP_MODEL = "DEVELOP";
	public final static String PRODUCE_MODEL = "produce";
	
	/** 状态值  **/
	public static final byte STARTING = 1;
	public static final byte RUNNING = 2;
	public static final byte STOP = 3;
	public static final byte ERROR = 4;
	
	/** 核心未配置文件参数区 **/
	public final static String MACHINE_NAME = "machine-name";
	public final static String NODE_NAME = "node-name";
	public final static String NAME = "name";
	public final static String CORE_ANALYZER = "coreAnalyzer";
	public final static String ISCLUSTER = "iscluster";
	public final static String MEMO = "memo";
	public final static String SYSTEM_CHARSET = "systemcharset";
	public final static String DEFAULT_CHARSET = "defaultcharset";
	public final static String RUNNING_MODEL = "runningmodel";
	public final static String LOG_LEVEL = "loglevel";
	public final static String VALUE = "value";
	public final static String JMX_SERVER = "jmxserver";
	public final static String PORT = "port";
	public final static String CONTAINER_ENGINE = "container-engine";
	public final static String SCANNERS  ="scanners";
	public final static String SCANNER = "scanner";
	public final static String ANALYZER = "analyzer";
	public final static String CLASS = "class";
	public final static String COMPONENT = "component";
	public final static String LOG_FRAMEWORK = "logframework-conf";
	public final static String LOG = "log";
	public final static String ISENABLE = "enable";
	public final static String IS_LOCAL_QUEUE = "isLocalQueue";
	public final static String IS_LOCAL = "isLocal";
	public final static String MARSHAL = "marshal";
	public final static String USER_JMX = "UseJmx";
	public final static String PERSISTENT = "persistent";
	public final static String ENABLE = "enable";
	public final static String BROKER_NAME = "brokerName";
	public final static String THEME = "theme";
	public final static String BOOT_SEQUENCE = "bootsequence";
	public final static String PARAMETER = "parameters";
	public final static String JAREXPRESSION = "jarexpression";
	public final static String CLASSEXPRESSION = "classexpression";
	public final static String USERDEFINELIBPATH = "userDefineLibPath";
	public final static String EXPOSE = "expose";
	public final static String PROTOCOL = "protocol";
	public final static String RESP_TIMEOUT = "respConnTimeout";
	public final static String HANDSHAR_TIMEOUT = "handsharkTimeout";
	public final static String PROXY_TIMEOUT = "proxyConnTimeout";
	public final static String PARAM_REGEX = "\\b.*\\b=\\b.*\\b";
	public final static String IP = "ip";
	public static String SERVER_NODE;

	

	
	/** SQL配置文件常量区 **/
	public final static String SQL = "sql";
	public final static String TABLES = "tables";
	public final static String DYNAMICPARAM = "#dynamicParam#";
    public final static String PAGING_SQL_TITLE = "SELECT * FROM(SELECT ROWNUM as NUM, ScorpionNUM.* from (";
    public final static String MYSQL_SQL_TITLE = "SELECT * FROM(";
	
	/** 数据源配置区 ***/
	public final static String DATASOURCE = "datasource";
	public final static String DRIVER_CLASS = "driverClass";
	public final static String URL = "url";
	public final static String ISJNDI = "isJndi";
	public final static String USERNAME = "userName";
	public final static String PASSWD = "passwd";
	public final static String IS_DEFAULT_DS = "isDefaultds";
	public final static String MAX_ACTIVE = "maxActive";
	public final static String MAX_IDLE = "maxIdle";
	public final static String MAX_WAIT = "maxWait";
	public final static String INIT_SIZE = "initSize";
	
	/** 连接池类型  **/
	public final static String DSCPT = "DSCPT";
	/** 太极连接池 **/
	public final static String ScorpionDBCP = "ScorpionDBCP";
	/** C3P0连接池 **/
	public final static String C3P0 = "C3P0";
	
	public final static String JNDI = "JNDI";
	
	public final static String IS_DUMP_STACK = "isDumpStack";
	
	/** 路径转换类型 **/
	public final static byte URL_TYPE  =1;
	public final static byte PATH_ABSOLUTE = 2;
	public final static byte JAR_URL_TYPE = 3;
	public final static String URL_PATH_HEAD = "file:/";
	public final static String JAR_PATH_HEAD = "jar:file:/";
	public final static String JAR_PATH_END = "!/";
	
	/**容器引擎**/
	public final static String GUICE = "guice";
	public final static String SPRING = "spring";
	public final static String Scorpion = "Scorpion";
	
	/** 数据源类型  **/
    public final static byte DEFAULT_DATASOURCE = 1;
    public final static byte OTHER_DATASOURCE = 2;
    
    
    /** session signal **/
    public final static String Scorpion_SESSION ="Scorpion_session";
    
    public final static String PERSISTENCE = "com.SCORPION.Scorpion.persistence.handler.ScorpionPersistenceDAO";
    
    /** 上下文信息设置 **/
    public final static String WEBLOGIC_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    public final static int CONN_TIME_OUT = 10;
    public final static int CALL_TIME_OUT = 30;
    public final static String EJB_NAME = "";
    public final static int MAX_CALL_LEVEL = 40;
    public final static String HOSTNAME = "java.rmi.server.hostname";
    
    /** 消息返回状态  **/
    public final static int SUCCESS = 0;
    public final static int UNKNOWN = 5;
    public final static int FAILURE = 9;
    
    
    /** 命令执行  **/
    /** reload sq **/
    public final static String RLS = "RLS";
    /** RESTART PERSISTENCE **/
    public final static String RSP = "RSP";
    /** LOAD SERVICE **/
    public final static String LS = "LS";
    /** LOAD ACTION **/
    public final static String LA = "LA";
    /** PRINT APLICATION INFORMATION **/
    public final static String PSI = "PSI";
    /** SCAN SERVICE INFORMATION **/
    public final static String SSI = "SSI";
    /** SCAN ACTION INFORMATION **/
    public final static String SAI = "SAI";
    /** CREATE POJO INFORMATION **/
    public final static String CPO = "CPO";
    
    
    /** SCAN ACTION INFORMATION **/
    public final static String LDS = "LDS";
    
    /** SCAN ACTION INFORMATION **/
    public final static String RLE = "RLE";
    public final static String HELP = "HELP";
    
    /**　REMOTE SERVICE EXPOSE CONSTANT **/
    public final static int SERVICE_EXPOSE_PORT = 4649;
    public final static String SERVICE = "service";
    
    
    /** SERVICE EXPOSE AND REMOTE ROUTE CONFIGURATION **/
	public final static String ROUTE_FILE = "routeFile";
	public final static String EJB_CONF = "ejb-conf";
	public final static String JMS_CONF = "jms-conf";
	public final static String WS_CONF = "ws-conf";
	public final static String TMI_CONF = "tmi-conf";
	public final static String SERVICE_CONF="service-conf";
	public final static String SERVICE_NAME = "serviceName";
	public final static String PROTOCOL_TYPE = "protocolType";
	public final static String PROTOCOL_ID = "protocolId";
	public final static String CURRENT_NODE = "currentNode";
	public final static String NEXT_NODE = "next_node";
	public final static String PRIVODE_URL = "provideURL";
	public final static String CONN_TIMEOUT = "connTimeout";
	public final static String CALL_TIMEOUT = "callTimeout";
	public final static String WSDL_ADDRESS = "wsdladdress";
	public final static String METHOD = "method";
	public final static String INIT_TYPE = "initType";
	public final static String LAZY = "lazy";
	
	
	/** 服务调用  **/
    public final static String Scorpion_TEMP_DATA_STACK = "ScorpionTempDataStack";
    public final static String DESCRIPTION ="description";
    public final static String REQ_TMP_DATA = "reqTmpData";
    
    
    public final static String Scorpion_PERSISTENCE_FILE = "scorpion-scheduler.properties";

    public final static String CUSTOM_JOB_DATA = "custom_job_data";
    public final static String JOB_NAME = "jobName";
    public final static String JOB_GROUP = "jobGroup";
   
}
