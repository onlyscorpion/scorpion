package com.scorpion.huakerongtong.api.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.JarURLConnection;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import com.scorpion.huakerongtong.api.configuration.DBParam;
import com.scorpion.huakerongtong.api.configuration.SystemEnumType;
import com.scorpion.huakerongtong.api.configuration.SystemResourcePool;
import com.scorpion.huakerongtong.api.configuration.ScorpionCoreConfig;
import com.scorpion.huakerongtong.api.configuration.PojoEntityInfo.PoProperty;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.kernel.AbsScorpionBasePo;
import com.scorpion.huakerongtong.api.log.PlatformLogger;

import sun.misc.BASE64Encoder;
import sun.misc.VM;

import com.sun.management.OperatingSystemMXBean;

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
public class SystemUtils {
	
    public final static String systemsignal	 = ScorpionSequenceUtil.generateSequeueString();
    
    private static final int PERCENT = 100;   
  
    private static final int FAULTLENGTH = 10;  
    
 //   private static String linuxVersion = null; 
    
    
    /**
     * @param collection
     * 
     * @return
     */
    public static boolean collectionIsNotNull(Collection<?> collection){
    
    	   if(collection == null||collection.isEmpty())
    		   return false;
    	 
    	   return true;
    }
    
    
	/**
	 * stop server
	 */
	public static void exitApplication(){
		Runtime.getRuntime().exit(0);
	}
	
	public static String getComputerName(){
		if(isLinuxOs())
			return System.getenv("HOSTNAME");
		return System.getenv("COMPUTERNAME");
	}
	
	public static String getCurrentThreadName(){
		return "java";
	}
	
	public static String getCurrentThreadPid(){
		return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
	}
	
	
	/**
	 * @description Validate whether parameter implements serializable or not..
	 * 
	 * @param arguments
	 * 
	 * @throws ScorpionBaseException
	 */
	public static void argumentSerializableValidate(Object... arguments) throws NotSerializableException{
	
		if(arguments == null)
			return;
		
		for(Object obj:arguments){
			if(!(obj instanceof Serializable))
				throw new NotSerializableException("scorpion-9853:The parameter must be implemented serializable !");
		
			if(Constant.DEVELOP_MODEL.equals(SystemResourcePool.getDefaultInstance().getResourceByKey(SystemEnumType.systemcoreconfigresource.getValue(), ScorpionCoreConfig.class).getRunModel()))
				try {
					objectConvertString(obj);
				} catch (Exception e) {
					throw new NotSerializableException("scorpion-9854:The parameter must be implemented serializable !");
				}
		}
	}
    
    
	/**
	 * @param po
	 * 
	 * @param poProperty
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public static DBParam getSQLParamByPo(AbsScorpionBasePo po,PoProperty poProperty)throws ScorpionBaseException{
		
		if(po == null)
			return null;
	
		DBParam params = new DBParam();
	
		try{
			for(String field:poProperty.getFieldSequence())
				params.addParam(poProperty.getGetAttributeByName(field).invoke(po));
		}catch(Exception e){
			throw new ScorpionBaseException("scorpion-8065:设置PO参数异常",e);
		}
		
		return params;
	}
	
	
	/**
	 * 对象转字符串
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String objectConvertString(Object object) throws Exception{
		ByteArrayOutputStream bos = null;
		ObjectOutputStream out = null;
		try{
		 bos = new ByteArrayOutputStream();
		 out = new ObjectOutputStream(bos);
		 out.writeObject(object);
		}finally{
			if(bos!=null)
				bos.close();
			if(out!=null)
				out.close();
		}
		return new BASE64Encoder().encode(bos.toByteArray());
	}
	
	
	/**
	 * @param date
	 * @param regex
	 * @return
	 */
	public static String formatDataToChars(Date date,String regex){
		if(regex == null||"".equals(regex))
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		else
			return new SimpleDateFormat(regex).format(date);
	}
	
	
	/**
	 * @description scan file directory
	 * @param path 扫描的根路径
	 * @param pattern 正则表达式
	 * @param relation 表达式之间的关系
	 * @param isRecursionDir 是否需要递归查找
	 * @return
	 * @throws ScorpionBaseException
	 */
	public final static List<String> systemFileFilter(String path,final String[] pattern,boolean relation,boolean isRecursionDir) throws ScorpionBaseException{
		File file = new File(path);
		
		if(!file.exists())
			throw new ScorpionBaseException("TSC-9609:Directory or file not exist !["+file.getPath()+"]");
	
		List<File> lisFiles = new ArrayList<File>();
		systemFileFilter(file,pattern,relation,isRecursionDir,lisFiles);
		List<String> paths = new LinkedList<String>();
	
		for(File lisFile:lisFiles){
			paths.add(lisFile.getPath());
		}
		
		return paths;
	}
	
	
	public static String getCurrentServiceIP() throws UnknownHostException{
		
		return InetAddress.getLocalHost().getHostAddress();
	}
	
	
	/**
	 * @param file
	 * @param pattern
	 * @param relation
	 * @param isRecursionDir
	 * @return
	 * @throws ScorpionBaseException
	 */
	public final static File[] systemFileFilter(File file,final String[] pattern,boolean relation,boolean isRecursionDir) throws ScorpionBaseException{
		if(!file.exists())
			throw new ScorpionBaseException("TSC-9609:The directory or file not exist ["+file.getPath()+"] !");
		
		List<File> lisFiles = new ArrayList<File>();
		systemFileFilter(file,pattern,relation,isRecursionDir,lisFiles);
		File[] files = new File[lisFiles.size()];
		
		for(int i=0;i<files.length;i++){
			files[i]=lisFiles.get(i);
		}
		
		return files;
	}
	
	
	/**
	 * @description Scanning file system ...
	 * @param file 文件系统
	 * @param pattern 正在表达式组
	 * @param relation @description 表达式之间的关系
	 * @param isRecursionDir 是否递归扫描多层目录
	 * @return
	 * @throws ScorpionBaseException
	 */
	public final static void systemFileFilter(File file,final String[] pattern,boolean relation,final boolean isRecursionDir,List<File> lisFile) throws ScorpionBaseException{
		if(file.isFile()){
			if(filterChain(file, pattern,relation)){
				lisFile.add(file);
			}
			return;
		}else{
			File[] files = file.listFiles(new FileFilter() {
				private boolean relation;
				public boolean accept(File pathname) {
					if(isRecursionDir&&pathname.isDirectory())
						return true;
					else
					    return filterChain(pathname,pattern,relation);
				}
			   public FileFilter setRelation(boolean relation){
				   this.relation = relation;
				   return this;
			   }
			}.setRelation(relation));
			if(isRecursionDir){
				for(File children:files)
				 systemFileFilter(children,pattern,relation,isRecursionDir,lisFile);
			}else{
				for(File filterFile:files)
				lisFile.add(filterFile);
			}
		}
	}
	
	
	/**
	 * 
	 * @return
	 * @throws CheckedException
	 */
	public static String getScorpionRootPath() throws ScorpionBaseException {
		String ScorpionCoreFileName = "/Scorpion.xml";
		URL url = SystemUtils.class.getResource(ScorpionCoreFileName);
		try {
			String rootPath = URLDecoder.decode(url.getPath(), "UTF-8");// 解决路径中含空格、中文问题
			return rootPath.substring(0, rootPath.lastIndexOf(ScorpionCoreFileName));
		} catch (UnsupportedEncodingException ex) {
			throw new ScorpionBaseException("字符类型UTF-8不支持", ex);
		}

	}
	
	/**
	 * @param pathname
	 * @param expressions
	 * @return
	 */
	private final static boolean filterChain(File pathname,String[] expressions,boolean relation){
		if(relation){
	      for(String expression:expressions){
			 if(regularExpressionValidate(pathname.getName(),expression))
					return true;
	    	}
	      return false;
		}else{
			for(String expression:expressions){
				 if(!regularExpressionValidate(pathname.getName(),expression))
						return false;
		    	}
			return true;
		}
	}
	
	/**
	 * @param paths
	 * @param type
	 * @return
	 * @throws ScorpionBaseException 
	 */
	public final static String[] pathTransformer(String paths[],int type) throws ScorpionBaseException{
	      
		String[] temp = new String[paths.length];
	       
		for(int i=0;i<paths.length;i++){
			temp[i] = pathTransformer(paths[i],type);
		}
	      
		return temp;
	}
	
	/**
	 * @param path
	 * @param type
	 * @return
	 */
	public final static String pathTransformer(String path,int type) throws ScorpionBaseException{
		if(path == null||"".equals(path))
			return null;
		
		if(Constant.URL_TYPE==type){
			if(Constant.URL_PATH_HEAD.equals(path))
			   return path;
			else
			   return Constant.URL_PATH_HEAD+path;
		}else if(Constant.PATH_ABSOLUTE==type){
			if(Constant.URL_PATH_HEAD.equals(path))
				return path.substring(6, path.length());
			else
			  return path;
		}else if(Constant.JAR_URL_TYPE == type){
			if(Constant.URL_PATH_HEAD.equals(path)){
				return path.replace(Constant.URL_PATH_HEAD, Constant.JAR_PATH_HEAD)+Constant.JAR_PATH_END;
			}else if(Constant.JAR_PATH_HEAD.equals(path)){
				return path;
			}else{
				return Constant.JAR_PATH_HEAD+path+Constant.JAR_PATH_END;
			}
		}else{
	    	  throw new ScorpionBaseException("TCU:2001未找到对应的路径转换类型！");
		}
	}
	
	/**
	 * @param file
	 * @return
	 * @throws ScorpionBaseException
	 */
	public static URL createURLFromLocalFile(File file) throws ScorpionBaseException {
	
		URL url = null;
		
		try {
			final String name = file.getCanonicalPath().replace('\\', '/');
			if (file.isDirectory()) {
				url = new URL("file:" + name + "/");
			} else {
				if (file.getName().endsWith(".jar") || file.getName().endsWith(".zip")) {
					url = new URL("jar:file:" + name + "!/");
				} else {
					url = new URL("file:" + name);
				}
			}
		} catch (Exception ex) {
			throw new ScorpionBaseException("TSC-6008:生成" + file + "的URL对象时发生错误", ex);
		}
		return url;
	}
	
	/**
	 * @param jarURL
	 * @return
	 * @throws ScorpionBaseException
	 */
	public static JarFile getJarFile(URL jarURL) throws ScorpionBaseException {
		URLConnection con = null;
		try {
			con = jarURL.openConnection();
		} catch (IOException ex) {
			throw new ScorpionBaseException("TSC-9976:读取Jar文件" + jarURL + "失败，请检查文件或网络是否正常", ex);
		}

		JarFile jarFile = null;

		if (con instanceof JarURLConnection) {
			JarURLConnection jarCon = (JarURLConnection) con;
			jarCon.setUseCaches(true);
			try {
				jarFile = jarCon.getJarFile();
			} catch (IOException ex) {
				throw new ScorpionBaseException("TSC-4469:获取Jar文件" + jarURL + "的文件对象失败，请检查文件是否损坏", ex);
			}
		}
		return jarFile;
	}
	
	
	/**
	 * @param validateObj
	 * @param regex
	 * @return
	 */
    public static boolean regularExpressionValidate(String validateObj,String regex){
    	Pattern pattern = Pattern.compile(regex);
    	return pattern.matcher(validateObj).matches();
    }
    
    
    public static final int PRE_CALL_STACK_LEVEL;
	private static String networkInterfaceInfonownHost;
	private static final String localIP;
	private static final List<String> allIP = new ArrayList<String>();

	static {
		if (getJavaVendor().toLowerCase().startsWith("ibm")) {
			PRE_CALL_STACK_LEVEL = 4;
		} else {
			PRE_CALL_STACK_LEVEL = 3;
		}

		// 获取主机名
		try {
			networkInterfaceInfonownHost = InetAddress.getLocalHost().toString();
		} catch (Exception ex) {
			networkInterfaceInfonownHost = "UnknownHost";
		}

		// 获取本机IP
		byte[] ip = null;
		try {
			ip = InetAddress.getLocalHost().getAddress();
		} catch (Throwable ex) {
			ip = new byte[] { 0, 0, 0, 0 };
		}
		localIP = (ip[0] >= 0 ? ip[0] : ip[0] + 256) + "." + (ip[1] >= 0 ? ip[1] : ip[1] + 256) + "." + (ip[2] >= 0 ? ip[2] : ip[2] + 256) + "."
				+ (ip[3] >= 0 ? ip[3] : ip[3] + 256);

		// 获取所有可用网卡IP
		Enumeration<?> allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration<?> addresses = ni.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress address = (InetAddress) addresses.nextElement();
					if (address != null && !address.isLoopbackAddress() && !address.isLinkLocalAddress() && address instanceof Inet4Address) {
						allIP.add(address.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			allIP.add(localIP);
		}
	}


	/**
	 * @description Stop server immediate ...
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-10上午9:32:11
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static void shutdownServerNow() {
		PlatformLogger.info("服务器已停止");
		System.exit(0);
	}

	/**
	 * Java 虚拟机可用处理器的数目
	 * 
	 * @Time 创建时间:2011-9-27上午9:38:20
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static int getAvailableProcessors() {
		return Runtime.getRuntime().availableProcessors();
	}

	/**
	 * 返回 Java 虚拟机的正常运行时间(以毫秒为单位)
	 * 
	 * @Description 相关说明
	 * @return
	 * @Time 创建时间:2013-6-3上午9:43:38
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getUptime() {
		return ManagementFactory.getRuntimeMXBean().getUptime();
	}

	/**
	 * 获取进程的CPU占用时间(以毫秒为单位)
	 * 
	 * @Description 相关说明
	 * @return
	 * @Time 创建时间:2013-6-3上午9:43:38
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getProcessCpuTime() {
		final Object obj = ManagementFactory.getOperatingSystemMXBean();
		if (obj instanceof OperatingSystemMXBean) {
			return ((OperatingSystemMXBean) obj).getProcessCpuTime();
		} else {
			return -1;
		}
	}

	/**
	 * 返回当前线程的总 CPU 时间(以毫微秒为单位)
	 * 
	 * @Description 返回当前线程的总 CPU 时间（以毫微秒为单位）。返回的值具有毫微秒的精度，但不具有毫微秒的准确度。<br>
	 *              如果实现对用户模式时间和系统模式时间加以区别，则返回的 CPU 时间为当前线程在用户模式或系统模式中执行的时间总量。<br>
	 *              如果当前系统未开启CPU测量或不支持CPU测量，则调用System.nanoTime()并返回其值。
	 * @return
	 * @Time 创建时间:2013-6-3下午2:33:35
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getCurrentThreadCpuTime() {
		final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		if (threadMXBean.isThreadCpuTimeEnabled()) {
			return threadMXBean.getCurrentThreadCpuTime();
		} else {
			return System.nanoTime();
		}
	}

	/**
	 * 返回最后一分钟内系统加载平均值
	 * 
	 * @Description
	 *              返回最后一分钟内系统加载平均值。系统加载平均值是排队到可用处理器的可运行实体数目与可用处理器上可运行实体数目的总和在某一段时间进行平均的结果。计算加载平均值的方式是特定于操作系统的
	 *              ，但通常是衰减的与时间相关的平均值。 如果加载平均值不可用，则返回负值。
	 *              设计此方法的目的是提供关于系统加载的提示，还可以频繁地查询此方法。加载平均值在某些平台上可能是不可用的，在这些平台上实现此方法代价太高。
	 * 
	 * @return 系统加载平均值；如果加载平均值不可用，则返回负值。
	 * 
	 * @Time 创建时间:2013-6-3下午3:27:29
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static double getSystemLoadAverage() {
		return ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
	}

	/**
	 * 返回当前线程在用户模式中执行的 CPU 时间(以毫微秒为单位)
	 * 
	 * @Description 返回当前线程在用户模式中执行的 CPU 时间（以毫微秒为单位）。返回的具有毫微秒的精度，但不具有毫微秒的准确度。 <br>
	 *              如果当前系统未开启CPU测量或不支持CPU测量，则调用System.nanoTime()并返回其值。
	 * @return
	 * @Time 创建时间:2013-6-3下午2:38:50
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getCurrentThreadUserTime() {
		final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		if (threadMXBean.isThreadCpuTimeEnabled() || threadMXBean.isCurrentThreadCpuTimeSupported()) {
			return threadMXBean.getCurrentThreadUserTime();
		} else {
			return System.nanoTime();
		}
	}

//	private static long prevUpTime = 0; // 上一次记录时, JVM已运行总时间
//	private static long prevProcessCpuTime = 0; // 上一次记录时, JVM耗费CPU总时间

	/**
	 * 获取当前JVM的CPU占用比
	 * 
	 * @Description
	 * @return
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Time 创建时间:2013-6-3下午1:54:17
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static double getCpuUsage() throws IOException, InterruptedException {
    	
		double cpuRatio = 0;   
		String osName = System.getProperty("os.name"); 
		if (osName.toLowerCase().startsWith("windows")) {   
			cpuRatio = getCpuRatioForWindows();   
		}else {   
			cpuRatio = getCpuRateForLinux();   
		}   
		return Math.round(cpuRatio*100)/100.0;
	}
	
    
    /**
     * @return
     * 
     * @throws IOException
     * 
     * @throws InterruptedException 
     */
    public static double getCpuRateForLinux() throws IOException, InterruptedException{   
    	
    	BufferedReader br = null;
    	try{
    		List<String> array = new ArrayList<String>();
    		br = new BufferedReader(new FileReader("/proc/stat"));
    		String cpuStr = br.readLine();
    		String[] cpuArray = cpuStr.replace("  ", " ").split(" ");
    		for(int j=1;j<cpuArray.length;j++)
    			array.add(cpuArray[j]);
    		if(array.size()<1)
    			return -1;
    		double total = 0;
    		for(String arg:array)
    			total  = total + Double.parseDouble(arg);
    		return 100*((total-Double.parseDouble(array.get(3)))/total);
    	}finally{
    		if(br != null)
    			br.close();
    	}
        
        /*try{   
            Process process = Runtime.getRuntime().exec("top -b -n 1");   
            is = process.getInputStream();                     
            isr = new InputStreamReader(is);   
            if("2.4".equals(linuxVersion)){
            	return getCpuInfoFromLinux(isr);
            }else{
            	return getCpuInfoFromCommonLinux(isr);
            }   
        } finally{   
            freeResource(is, isr);   
        }   */
  
    } 
    
    /**
     * @param isr
     * @return
     * @throws IOException
     */
    /*private static double getCpuInfoFromLinux(InputStreamReader isr) throws IOException{
    	  
    	BufferedReader brStat = new BufferedReader(isr);
    	try{
    		brStat.readLine();     brStat.readLine();   
    		brStat.readLine();     brStat.readLine();   
    		StringTokenizer tokenStat = new StringTokenizer(brStat.readLine());   
    		tokenStat.nextToken();     tokenStat.nextToken();   
    		String user = tokenStat.nextToken();     tokenStat.nextToken();   
    		String system = tokenStat.nextToken();   tokenStat.nextToken();   
    		String nice = tokenStat.nextToken();   
    		user = user.substring(0,user.indexOf("%"));   
    		system = system.substring(0,system.indexOf("%"));   
    		nice = nice.substring(0,nice.indexOf("%"));   
    		float userUsage = new Float(user).floatValue();   
    		float systemUsage = new Float(system).floatValue();   
    		float niceUsage = new Float(nice).floatValue();   
    		return (userUsage+systemUsage+niceUsage)/100;   
    	}finally{
    		if(brStat != null)
    			brStat.close();
    	}
    
    }*/
    
    /**
     * @param isr
     * @return
     * @throws IOException
     */
  /*  private static double getCpuInfoFromCommonLinux(InputStreamReader isr) throws IOException{
    	   
    	BufferedReader brStat = new BufferedReader(isr);  
    	try{
    		brStat.readLine();   
    		brStat.readLine();   
    		StringTokenizer tokenStat = new StringTokenizer(brStat.readLine());   
    		tokenStat.nextToken();   
    		tokenStat.nextToken();   
    		tokenStat.nextToken();   
    		tokenStat.nextToken();   
    		tokenStat.nextToken();   
    		tokenStat.nextToken();   
    		tokenStat.nextToken();   
    		String cpuUsage = tokenStat.nextToken();   
    		Float usage = new Float(cpuUsage.substring(0,cpuUsage.indexOf("%")));   
    		return (1-usage.floatValue()/100);   
    	}finally{
    		if(brStat != null)
    			brStat.close();
    	}
    
    }*/
    
    /**
     * @param is
     * @param isr
     * @param br
     * @throws IOException 
     */
   /* private static void freeResource(InputStream is, InputStreamReader isr) throws IOException{   
            if(is!=null)   
                is.close();   
            if(isr!=null)   
                isr.close();   
    }   
  */

    /**
     * @return
     */
    private static double getCpuRatioForWindows() {   
        try {   
            String procCmd = System.getenv("windir")   
                    + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"   
                    + "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";   
            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));   
          //  Thread.sleep(CPUTIME);   
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));   
            if (c0 != null && c1 != null) {   
                long idletime = c1[0] - c0[0];   
                long busytime = c1[1] - c0[1];   
                return Double.valueOf(PERCENT * (busytime) / (busytime + idletime)).doubleValue();   
            } else {   
                return 0.0;   
            }   
        } catch (Exception ex) {   
            ex.printStackTrace();   
            return 0.0;   
        }   
    }   
    
    
  
    /**
     * @param proc
     * @return
     */
   private static long[] readCpu(final Process proc) {   
	   long[] retn = new long[2];   
	   try {   
		   proc.getOutputStream().close();   
           InputStreamReader ir = new InputStreamReader(proc.getInputStream());   
           LineNumberReader input = new LineNumberReader(ir);   
           String line = input.readLine();   
           if (line == null || line.length() < FAULTLENGTH) {   
               return null;   
           }   
           int capidx = line.indexOf("Caption");   
           int cmdidx = line.indexOf("CommandLine");   
           int rocidx = line.indexOf("ReadOperationCount");   
           int umtidx = line.indexOf("UserModeTime");   
           int kmtidx = line.indexOf("KernelModeTime");   
           int wocidx = line.indexOf("WriteOperationCount");   
           long idletime = 0;   
           long kneltime = 0;   
           long usertime = 0;   
           while ((line = input.readLine()) != null) {   
               if (line.length() < wocidx) {   
                   continue;   
               }   
               if("".equals(Bytes.substring(line, kmtidx, rocidx - 1).trim()))
            	   break;
               String caption = Bytes.substring(line, capidx, cmdidx - 1).trim();   
               String cmd = Bytes.substring(line, cmdidx, kmtidx - 1).trim();   
               if (cmd.indexOf("wmic.exe") >= 0) {   
                   continue;   
               }   
               if (caption.equals("System Idle Process")|| caption.equals("System")) {   
                   idletime += Long.valueOf(Bytes.substring(line, kmtidx, rocidx - 1).trim()).longValue();   
                   idletime += Long.valueOf(Bytes.substring(line, umtidx, wocidx - 1).trim()).longValue();   
                   continue;   
               }   
 
               kneltime += Long.valueOf(Bytes.substring(line, kmtidx, rocidx - 1).trim()).longValue();   
               usertime += Long.valueOf(Bytes.substring(line, umtidx, wocidx - 1).trim()).longValue();   
           }   
           retn[0] = idletime;   
           retn[1] = kneltime + usertime;   
           return retn;   
       } catch (Exception ex) {   
           ex.printStackTrace();   
       } finally {   
           try {   
               proc.getInputStream().close();   
           } catch (Exception e) {   
               e.printStackTrace();   
           }   
       }   
       return null;   
   }   
    
   

  

	/**
	 * 获取操作系统物理内存大小(字节)
	 * 
	 * @Description 相关说明
	 * @return
	 * @Time 创建时间:2013-6-3上午9:43:38
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getTotalPhysicalMemorySize() {
		final Object obj = ManagementFactory.getOperatingSystemMXBean();
		if (obj instanceof OperatingSystemMXBean) {
			return ((OperatingSystemMXBean) obj).getTotalPhysicalMemorySize();
		} else {
			return -1;
		}
	}

	/**
	 * 获取操作系统物理内存空闲大小(字节)
	 * 
	 * @Description 相关说明
	 * @return
	 * @Time 创建时间:2013-6-3上午9:43:38
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getFreePhysicalMemorySize() {
		final Object obj = ManagementFactory.getOperatingSystemMXBean();
		if (obj instanceof OperatingSystemMXBean) {
			return ((OperatingSystemMXBean) obj).getFreePhysicalMemorySize();
		} else {
			return -1;
		}
	}

	/**
	 * Java 虚拟机试图使用的最大内存量(字节)
	 * 
	 * @Description 当前JVM实例试图使用的最大内存数量
	 * @Time 创建时间:2011-9-27上午9:39:07
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getMaxMemory() {
		return Runtime.getRuntime().maxMemory();
	}

	/**
	 * 获取直接本地内存的最大容量(字节)
	 * 
	 * @Description 相关说明
	 * @return
	 * @Time 创建时间:2013-2-20下午1:33:31
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getMaxDirectMemory() {
		return VM.maxDirectMemory();
	}

	/**
	 * Java 虚拟机中的内存总量(字节)
	 * 
	 * @Description 当前JVM实例试图使用的最大内存数量
	 * @Time 创建时间:2011-9-27上午9:39:07
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getTotalMemory() {
		return Runtime.getRuntime().totalMemory();
	}

	/**
	 * Java 虚拟机中的空闲内存量(字节)
	 * 
	 * @Description 当前JVM实例试图使用的最大内存数量
	 * @Time 创建时间:2011-9-27上午9:39:07
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getFreeMemory() {
		return Runtime.getRuntime().freeMemory();
	}

	/**
	 * 获取操作系统总的Swap空间大小(字节)
	 * 
	 * @Description 相关说明
	 * @return
	 * @Time 创建时间:2013-6-3上午9:43:38
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getTotalSwapSpaceSize() {
		final Object obj = ManagementFactory.getOperatingSystemMXBean();
		if (obj instanceof OperatingSystemMXBean) {
			return ((OperatingSystemMXBean) obj).getTotalSwapSpaceSize();
		} else {
			return -1;
		}
	}

	/**
	 * 获取操作系统空闲Swap空间大小(字节)
	 * 
	 * @Description 相关说明
	 * @return
	 * @Time 创建时间:2013-6-3上午9:43:38
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getFreeSwapSpaceSize() {
		final Object obj = ManagementFactory.getOperatingSystemMXBean();
		if (obj instanceof OperatingSystemMXBean) {
			return ((OperatingSystemMXBean) obj).getFreeSwapSpaceSize();
		} else {
			return -1;
		}
	}
	
	
	public static boolean isLinuxOs(){
		
		if(getOSName().toLowerCase().indexOf("windows")>-1)
			return false;
		
		return true;
	}
	

	/**
	 * 操作系统架构
	 * 
	 * @Time 创建时间:2011-9-27上午9:25:51
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getOSArch() {
		return System.getProperty("os.arch");
	}

	/**
	 * 操作系统名称
	 * 
	 * @Description 当前JVM实例所在操作系统名称
	 * @Time 创建时间:2011-9-27上午9:32:38
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getOSName() {
		return System.getProperty("os.name");
	}

	/**
	 * 操作系统版本
	 * 
	 * @Description 当前JVM实例所在操作系统版本
	 * @Time 创建时间:2011-9-27上午9:33:00
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getOSVersion() {
		return System.getProperty("os.version");
	}

	/**
	 * 操作补丁版本
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2011-11-18下午10:32:02
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getOSPatch() {
		return System.getProperty("sun.os.patch.level");
	}

	/**
	 * Java运行时名称
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2011-11-18下午10:28:57
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getJavaRuntimeName() {
		return System.getProperty("java.runtime.name");
	}

	/**
	 * Java安装目录
	 * 
	 * @Description 当前平台使用的Java的安装目录
	 * @Time 创建时间:2011-9-27上午9:33:20
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getJavaHome() {
		return System.getProperty("java.home");
	}

	/**
	 * Java版本
	 * 
	 * @Description 当前平台使用的Java的版本
	 * @Time 创建时间:2011-9-27上午9:33:32
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getJavaVersion() {
		return System.getProperty("java.version");
	}

	/**
	 * Java主版本号
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2011-11-18下午11:32:29
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getSpecificationVersion() {
		return System.getProperty("java.specification.version");
	}

	/**
	 * Java运行时环境版本
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2011-11-18下午10:35:39
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getJavaRuntimeVersion() {
		return System.getProperty("java.runtime.version");
	}

	/**
	 * Java生产厂商
	 * 
	 * @Description 当前平台使用的Java的生产厂商
	 * @Time 创建时间:2011-9-27上午9:33:47
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getJavaVendor() {
		return System.getProperty("java.vendor");
	}

	/**
	 * Java虚拟机生产厂商
	 * 
	 * @Description 当前平台使用的Java虚拟机的生产厂商
	 * @Time 创建时间:2011-9-27上午9:33:47
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getJVMVendor() {
		return System.getProperty("java.vm.vendor");
	}

	/**
	 * JVM虚拟机架构数据模型
	 * 
	 * @Description 当前平台使用的Java虚拟机的架构数据模型：32位/64位
	 * @Time 创建时间:2011-11-8下午12:58:54
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getSunArchDataModel() {
		return System.getProperty("sun.arch.data.model");
	}

	/**
	 * 获取JVM编译器信息
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2011-11-18下午10:49:21
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getCompilerInfo() {
		return System.getProperty("sun.management.compiler");
	}

	/**
	 * 获取JVM运行模式信息
	 * 
	 * @Description 获取JVM运行模式信息：mixed mode
	 * @Time 创建时间:2011-11-8下午1:05:30
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getJVMMode() {
		return System.getProperty("java.vm.info");
	}

	/**
	 * Java虚拟机的输入变量
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2011-11-18下午10:58:02
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static List<String> getInputArguments() {
		return ManagementFactory.getRuntimeMXBean().getInputArguments();
	}

	/**
	 * 引导类加载器用于搜索类文件的引导类路径
	 * 
	 * @Description 引导类加载器用于搜索类文件的引导类路径
	 * @Time 创建时间:2011-9-27上午9:33:47
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getBootClassPathInfo() {
		return System.getProperty("sun.boot.class.path");
	}

	/**
	 * ClassPath信息
	 * 
	 * @Description 当前JVM实例的ClassPath信息
	 * @Time 创建时间:2011-9-27上午9:33:47
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getClassPathInfo() {
		return System.getProperty("java.class.path");
	}

	/**
	 * 获取启动参数
	 * 
	 * @Description 获取当前JVM实例启动时的参数信息
	 * @Time 创建时间:2011-9-26下午4:05:05
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static List<String> getBootArguments() {
		return ManagementFactory.getRuntimeMXBean().getInputArguments();
	}

	/**
	 * 动态链接库的搜索路径
	 * 
	 * @Description 获取当前JVM实例的动态链接库的搜索路径
	 * @Time 创建时间:2011-9-26下午4:07:34
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getLibraryScanPath() {
		return System.getProperty("java.library.path");
	}

	/**
	 * 获取JVM进程号
	 * 
	 * @Description 获取当前JVM的进程号:当前JVM生产厂商Sun时使用MXBean获取当前进程号，否则从环境变量中获取进程号。
	 *              <P>
	 *              <P>
	 * @Time 创建时间:2011-9-23下午10:43:50
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getPid() {
		String pid = System.getProperty("pid");

		if (pid != null) {
			return pid;
		}

		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		String name = runtime.getName();
		String[] infos = name.split("@");
		if (infos != null && infos.length > 1) {
			pid = infos[0];
		} else {
			// Linux/Unix操作下使用命令格式启动系统 exec java -Dpid=$$
		}

		return pid;
	}

	/**
	 * 获取进程号和主机名
	 * 
	 * @Description 获取当前JVM的进程号和主机名:从环境变量中获取主机名，如果为空则使用下面规则，则依次使用以下规则至止 <br>
	 *              1.当前JVM生产厂商为Sun时使用MXBean获取当前主机名。 <br>
	 *              2.使用InetAddress获取主机名 <br>
	 *              注：Linux/Unix操作下使用命令格式启动系统 exec java -Dhostnme="主机名"
	 *              <P>
	 * @Time 创建时间:2011-9-23下午10:43:50
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getHostname() {
		String hostname = System.getProperty("hostname");

		if (hostname != null) {
			return hostname;
		}

		if (getJavaVendor().toLowerCase().startsWith("sun")) {
			RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
			String name = runtime.getName();
			hostname = name.split("@")[1];
		} else {
			try {
				hostname = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException ex) {

			}
		}

		return hostname;
	}

	/**
	 * 获取当前用户名
	 * 
	 * @Description 获取启动JVM实例的用户名
	 * @Time 创建时间:2011-11-8下午1:02:53
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getUserName() {
		return System.getProperty("user.name");
	}

	/**
	 * 获取当前用户的语言
	 * 
	 * @Description 获取启动JVM实例的用户系统的语言
	 * @Time 创建时间:2011-11-8下午1:10:10
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getUesrLanguage() {
		return System.getProperty("user.language");
	}

	/**
	 * 获取当前用户的国家
	 * 
	 * @Description 获取启动JVM实例的用户系统的国家
	 * @Time 创建时间:2011-11-8下午1:10:39
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getUserCountry() {
		return System.getProperty("user.country");
	}

	/**
	 * 获取当前用户的时区
	 * 
	 * @Description 获取启动JVM实例的用户系统的时区
	 * @Time 创建时间:2011-11-8下午1:13:38
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getUserTimezone() {
		return System.getProperty("user.timezone");
	}

	/**
	 * 获取编译文件的编码
	 * 
	 * @Description 获取当前JVM的Class文件的编译字符集
	 * @Time 创建时间:2011-11-8下午1:15:41
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getFileEncoding() {
		return System.getProperty("file.encoding");
	}

	/**
	 * 获取指定的系统参数信息
	 * 
	 * @Description 获取启动当前JVM实例的用户的指定系统参数信息
	 * @Time 创建时间:2011-11-8下午1:17:21
	 * @param envName
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getSystemEnv(String envName) {
		return System.getenv(envName);
	}

	/**
	 * 默认字符集
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-9-16下午10:55:13
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getDefaultCharsetName() {
		return Charset.defaultCharset().name();
	}

	/**
	 * Zip自定义字符集
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-9-16下午10:55:13
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getZipAltEncoding() {
		return System.getProperty("sun.zip.altEncoding");
	}

	/**
	 * 默认显示语言
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-9-16下午10:57:44
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getDefaultLanguage() {
		return Locale.getDefault().getDisplayLanguage();
	}

	/**
	 * 返回活动线程的当前数目，包括守护线程和非守护线程。
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-4下午12:49:56
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static int getThreadCount() {
		return ManagementFactory.getThreadMXBean().getThreadCount();
	}

	/**
	 * 返回自从 Java 虚拟机启动以来创建和启动的线程总数目。
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-4下午12:50:16
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getTotalStartedThreadCount() {
		return ManagementFactory.getThreadMXBean().getTotalStartedThreadCount();
	}

	/**
	 * 返回自从 Java 虚拟机启动或峰值重置以来峰值活动线程计数。
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-4下午12:50:51
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static int getPeakThreadCount() {
		return ManagementFactory.getThreadMXBean().getPeakThreadCount();
	}

	/**
	 * 返回活动守护线程的当前数目。
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-4下午12:51:05
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static int getDaemonThreadCount() {
		return ManagementFactory.getThreadMXBean().getDaemonThreadCount();
	}

	/**
	 * 查找因为等待获得对象监视器或可拥有同步器而处于死锁状态的线程循环。
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-4下午12:51:19
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long[] findDeadlockedThreads() {
		return ManagementFactory.getThreadMXBean().findDeadlockedThreads();
	}

	/**
	 * 找到处于死锁状态（等待获取对象监视器）的线程的周期
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-4下午12:51:32
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long[] findMonitorDeadlockedThreads() {
		return ManagementFactory.getThreadMXBean().findMonitorDeadlockedThreads();
	}

	/**
	 * 返回所有活动线程的线程信息，并带有堆栈跟踪和同步信息。
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-4下午12:51:46
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static ThreadInfo[] dumpAllThreadInfo() {
		return ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);
	}

	/**
	 * 返回当前加载到 Java 虚拟机中的类的数量
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-4下午12:48:46
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static int getLoadedClassJCount() {
		return ManagementFactory.getClassLoadingMXBean().getLoadedClassCount();
	}

	/**
	 * 返回自 Java 虚拟机开始执行到目前已经加载的类的总数。
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-4下午12:48:59
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getTotalLoadedClassCount() {
		return ManagementFactory.getClassLoadingMXBean().getTotalLoadedClassCount();
	}

	/**
	 * 返回自 Java 虚拟机开始执行到目前已经卸载的类的总数。
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-4下午12:49:13
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static long getUnloadedClassCount() {
		return ManagementFactory.getClassLoadingMXBean().getUnloadedClassCount();
	}

	/**
	 * 获取GC管理器
	 * 
	 * @Description 强制JVM执行GC操作
	 * @Time 创建时间:2011-10-31下午10:29:32
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static List<GarbageCollectorMXBean> getGarbageCollectorMXBean() {
		return ManagementFactory.getGarbageCollectorMXBeans();
	}

	/**
	 * 执行垃圾回收操作
	 * 
	 * @Description 强制JVM执行GC操作
	 * @Time 创建时间:2011-10-31下午10:29:32
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static void gc() {
		PlatformLogger.info("启动GC，回收资源");
		ManagementFactory.getMemoryMXBean().gc();
	}

	/**
	 * 返回用于对象分配的堆的当前内存使用量
	 * 
	 * @Description 堆由一个或多个内存池组成。返回的内存使用量中的已使用大小和已提交大小为所有堆内存池的对应值的总和，<br>
	 *              而返回的内存使用量中表示堆内存设置的初始大小和最大大小则可能不等于所有堆内存池对应值的总和。<br>
	 *              返回的内存使用量中已使用内存量为活动对象和尚未回收的垃圾对象（如果有）所占用内存的总量。
	 * 
	 * 
	 * @Time 创建时间:2012-2-4下午1:07:01
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static MemoryUsage getHeapMemoryUsage() {
		return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
	}

	/**
	 * 返回 Java虚拟机使用的非堆内存的当前内存使用量
	 * 
	 * @Description 非堆内存由一个或多个内存池组成。 返回的内存使用量中的已使用大小和已提交大小为所有非堆内存池的对应值的总和，<br>
	 *              而返回的内存使用量中表示非堆内存设置的初始大小和最大大小则可能不等于所有非堆内存池对应值的总和 。
	 * @Time 创建时间:2012-2-4下午1:19:09
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static MemoryUsage getNonHeapMemoryUsage() {
		return ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
	}

	/**
	 * 获取未分配磁盘空间
	 * 
	 * @Description 相关说明
	 * @param type
	 * @return
	 * @Time 创建时间:2013-1-15下午3:41:26
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static Map<String, Long> getFreeSpace(char type) {
		File[] drivers = File.listRoots();
		Map<String, Long> info = new TreeMap<String, Long>();
		for (File driver : drivers) {
			long freeSpace = driver.getFreeSpace();
			switch (type) {
			case 'G':
			case 'g':
				freeSpace /= 1024;
			case 'M':
			case 'm':
				freeSpace /= 1024;
			case 'K':
			case 'k':
				freeSpace /= 1024;
			default:
			}
			info.put(driver.getAbsolutePath(), freeSpace);
		}
		return info;
	}

	/**
	 * 获取分区大小
	 * 
	 * @Description 返回此抽象路径名指定的分区大小
	 * @Time 创建时间:2012-7-13下午4:48:42
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static Map<String, Long> getTotalSpace() {
		File[] drivers = File.listRoots();
		Map<String, Long> info = new HashMap<String, Long>(drivers.length);
		for (File driver : drivers) {
			info.put(driver.getAbsolutePath(), driver.getTotalSpace());
		}
		return info;
	}

	/**
	 * 获取可用磁盘空间
	 * 
	 * @Description 返回此抽象路径名指定的分区上可用于此虚拟机的字节数
	 * @Time 创建时间:2012-7-13下午4:48:55
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static Map<String, Long> getUsableSpace() {
		File[] drivers = File.listRoots();
		Map<String, Long> info = new HashMap<String, Long>(drivers.length);
		for (File driver : drivers) {
			info.put(driver.getAbsolutePath(), driver.getUsableSpace());
		}
		return info;
	}

	/**
	 * 启用或禁用内存系统的 verbose 输出
	 * 
	 * @Description verbose 输出信息和 verbose 信息发送到的输出流都与实现有关。<br>
	 *              通常，只要垃圾回收时释放了内存，Java 虚拟机实现就会输出一条消息。<br>
	 *              此方法的每次调用都会全局启用或禁用 verbose 输出。
	 * 
	 * @Time 创建时间:2012-2-4下午1:21:16
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static void setVerbose(boolean value) {
		ManagementFactory.getMemoryMXBean().setVerbose(value);
	}

	/**
	 * 测试内存系统的 verbose 输出是否已启用。
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-2-4下午1:23:40
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static boolean isVerbose() {
		return ManagementFactory.getMemoryMXBean().isVerbose();
	}

	/**
	 * 获取本地网卡信息
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-12-6上午11:41:38
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getNetworkInterfaceInfo() {
		return networkInterfaceInfonownHost;
	}

	/**
	 * 获取本地IP
	 * 
	 * @Description 相关说明
	 * @Time 创建时间:2012-12-20上午9:45:26
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getLocalIP() {
		return localIP;
	}

	/**
	 * 本机所有绑定的IP
	 * 
	 * @Description 相关说明
	 * @return
	 * @Time 创建时间:2013年11月1日上午10:08:06
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static List<String> getAllIP() {
		return new ArrayList<String>(allIP);
	}

	/**
	 * 设置系统环境变量
	 * 
	 * @Description 相关说明
	 * @param name
	 * @param value
	 * @return
	 * @Time 创建时间:2014年12月18日下午2:14:16
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static Object setSystemProperty(final String name, final String value) {
		if (value != null) {
			System.setProperty(name, value);
		}
		return value;
	}
	
	public static String getUserDir() {
		return System.getProperty("user.dir");
	}
	
	/**
	 * 获取临时文件目录
	 * 
	 * @Time 创建时间:2011-9-27上午9:26:08
	 * @return
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static String getTempFileDir() {
		return System.getProperty("java.io.tmpdir");
	}
	
	
	
	/**
	 * 
	 * @param path
	 * 
	 * @param buildPaths
	 * 
	 * @return
	 * 
	 * @throws ScorpionBaseException
	 */
	public static String transformerAbsolutePath(String path,String[] buildPaths) throws ScorpionBaseException{
		boolean flag = false ;
		if(path == null||"".equals(path))
			return null;
	
		for(String bp:buildPaths){
		
			if(path.substring(0, path.lastIndexOf(".")).replace('\\','/').indexOf(bp.replace('\\', '/'))<0)
				continue;
		
			path =  path.substring(0, path.lastIndexOf(".")).replace('\\','/').replace(bp.replace('\\', '/'), "").replace('/', '.');
			flag = true;
		}
		
		if(!flag)
			path =  path.substring(0, path.lastIndexOf(".")).replace('/', '.');
		return path;
	}
	
	/**
	 * @param method
	 * @param paramnum
	 * @return
	 */
	public static String redefinemethod(String method,int paramnum){
		return method+"&"+paramnum+"&"+systemsignal;
	}

}

class Bytes {   
	   
    public static String substring(String src, int start_idx, int end_idx){   
        byte[] b = src.getBytes();   
        String tgt = "";   
        for(int i=start_idx; i<=end_idx; i++){   
            tgt +=(char)b[i];   
        }   
        return tgt;   
    }   
    
}
