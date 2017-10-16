package org.scorpion.api.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.scorpion.api.exception.ScorpionBaseException;
import org.scorpion.api.kernel.IScorpionGlobalSystemSession;
import org.scorpion.api.kernel.SecurityEnum;
import org.scorpion.api.util.Constant;

/**
 *  天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsScorpionFileUploadServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected String FILE_LIST = "fileList";
	
	private String savepath;
	
	protected IScorpionGlobalSystemSession session;
	
	protected String FILE_ROOT_PATH = "fileRootPath";
	
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws ScorpionBaseException
	 */
	public abstract void requestHandler(HttpServletRequest req, HttpServletResponse resp)throws ScorpionBaseException;

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
	
		try{
			setApplicationSession();
			requestHandler(req,resp);
		}catch (ScorpionBaseException e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	
		try{
			setApplicationSession();
			session.createOrInsteadOfSession(Constant.Scorpion_SESSION,request);
			requestDelegate(request,response);
			requestHandler(request,response);
		}catch(ScorpionBaseException e){
			throw new ServletException(e);
		}finally{
			session.clear();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void requestDelegate(HttpServletRequest request, HttpServletResponse response){
		DiskFileItemFactory factory = new DiskFileItemFactory();
		String path = null;
		if(savepath != null)
			path = savepath;
		else
			path = request.getRealPath("/upload");
		File file = new File(path);
		if(!file.exists())
			file.mkdirs();
		
		String realPath = null;
		
		if(getFilePath()==null)
			realPath = path;
		else
			realPath = path+File.separator+getFilePath();
		File fatherPath = new File(realPath);
		if(!fatherPath.exists())
			fatherPath.mkdirs();
		request.setAttribute(this.FILE_ROOT_PATH,realPath);
		factory.setRepository(new File(realPath));
		factory.setSizeThreshold(1024*1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		List<File> fileLis = new ArrayList<File>();
		try {
			List<FileItem> list = upload.parseRequest(request);
			List<Map<String,String>> fileInfo = new ArrayList<Map<String,String>>();
			for(FileItem item : list){
				if(item.isFormField()){
					String name = item.getFieldName();
					String value =item.getString("utf-8");
					if(name!=null&&name.indexOf("fileIndexInfo")>=0){
						Map<String,String> map = new HashMap<String,String>();
						map.put("md5String", value.split("----------HV2ymHFg03ehbqgZCaKO6jyH--d")[0]);
						map.put("securityLocalPath", " ".equals(value.split("----------HV2ymHFg03ehbqgZCaKO6jyH--d")[1])?"":value.split("----------HV2ymHFg03ehbqgZCaKO6jyH--d")[1]);
						map.put("unSecurityLocalPath", " ".equals(value.split("----------HV2ymHFg03ehbqgZCaKO6jyH--d")[2])?"":value.split("----------HV2ymHFg03ehbqgZCaKO6jyH--d")[2]);
						fileInfo.add(map);
					}else
						request.setAttribute(name, value);
					request.setAttribute("fileInfo", fileInfo);
				}else{
					String name = item.getFieldName();
					String value = item.getName();
					int start = value.lastIndexOf("\\");
					String fileName = value.substring(start + 1);
					request.setAttribute(name, fileName);
					File uploadFile = new File(realPath,fileName);
					if(!uploadFile.getParentFile().exists())
						uploadFile.getParentFile().mkdirs();
					if("nuCipher".equals(request.getAttribute("cipherType"))){
						item.write(uploadFile,1,fileName);
					}else if("DES".equals(request.getAttribute("cipherType")))
						item.write(uploadFile,SecurityEnum.DecoderAlgorithmType.DES.name(),"xAsVnvGdVJg=",fileName);
					else
						item.write(uploadFile);
					request.setAttribute(name,uploadFile.getPath());
					fileLis.add(uploadFile);
				}
			}
			request.setAttribute("fileList", fileLis);
		}catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public abstract void setApplicationSession();
	
	
	public String getFilePath(){
		return null;
	}


	@Override
	public void init(ServletConfig config) throws ServletException {
		savepath = config.getInitParameter("savepath");
		super.init(config);
	}
	

}
