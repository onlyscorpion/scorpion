package org.scorpion.api.kernel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

/**
 * @author GuoMing
 * @Package com.tj.Scorpion.platform.web.event
 * @Title: IReqData.java
 * @Description: RequestEvent用户接口DTO此接口根据{@link ScorpionDataSet}数据模型的实现获得用户接口。
 * @date 2015年5月14日 上午11:55:04
 * @version V1.0
 */
public interface IReqData extends Serializable {

	// form中同意key多value的连接标识
	public static final String AND_FLAG = "#*^@^*#";

	/**
	 * 设置对应attrName的值为obj
	 * 
	 * @param attrName
	 *            需要设置值的key
	 * @param obj
	 *            key对应的value值
	 */
	public void setAttr(String attrName, Object obj);

	/**
	 * 根据attrName名称，获得它所对应的value值。 <br>
	 * 
	 * @param attrName
	 *            key名称
	 * @return attr对应的value值
	 */
	public Object getAttr(String attrName);

	public List<FileItem> getUploadList();

	public Object getForm(Class<?> clz);

	public Map<String, String> getForm();

	public List<Map<String, String>> getTable(String tableName);

	public <T> List<T> getTable(String tableName, Class<?> clz);

	public List<Map<String, Object>> getTree(String tree);

	public HttpServletRequest getHttpReq();
}
