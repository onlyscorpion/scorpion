package org.scorpion.api.kernel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author GuoMing
 * @Package com.tj.tscp.platform.web.event
 * @Title: IResData.java
 * @Description: ResponseEvent用户接口DTO <br>
 * @date 2015年6月3日 上午10:58:09
 * @version V1.0
 */
public interface IResData extends Serializable{

	/** 
	* <p>Description: </p> 
	* @param total 可添null,null默认当前条数，
	* @param objList 
	*/
	public void addEasyUiTable(List<?> objList,int totalnum);

	public void addEasyUiTree(List<Map<String, Object>> objList);

	public void addEasyUiSelectData(List<Map<String, Object>> resXysslxList);

	public void addForm(Map<String, Object> map);

	/**
	 * 设置对应key的值为value。
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            值
	 */
	public void addAttr(String key, Object value);

	public void addJsonStringOutWrite(String json);

	/**
	 * 用于页面跳转，传入页面的绝对路径。
	 * 
	 * @param pageName
	 *            页面路径
	 */
	public void addPage(String pageName);

	/**
	 * 获得ResData的Json数据。用作前台组件解析。
	 * 
	 * @return String类型的Json数据
	 */
	public String getJson();

	/**
	 * 添加提示信息。
	 * 
	 * @param value
	 *            提示信息
	 */
	public void addMessage(Object value);

	/**
	 * 合并IResData
	 * 
	 * @param res
	 *            传入要合并的Res
	 * @return IResData
	 */
	public IResData joinRes(IResData res);

	/**
	 * 添加jstl支持
	 * 
	 * @param name
	 *            attr的key
	 * @param data
	 *            attr的value
	 */
	public void addJSTL(String name, Object data);

	/**
	 * 设置业务参数。用于不同服务之间的传值。
	 * 
	 * @param name
	 *            key
	 * @param obj
	 *            value值
	 */
	public void put(String name, Object obj);

	/**
	 * 获取业务参数。用于不同服务之间的传值。
	 * 
	 * @param name
	 *            key
	 * @return value值
	 */
	public Object get(String name);

}
