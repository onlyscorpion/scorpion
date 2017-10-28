package com.scorpion.huakerongtong.persistence.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  天蝎平台架构(SCORPION Security Controllable Platform)
 * <p>com.SCORPION.Scorpion.common
 * <p>File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: the annotation is used to signal the method of component </p>
 * <p>Copyright: Copyright (c) 2015 SCORPION.COM.CN</p>
 * <p>Company: SCORPION.COM.CN</p>
 * <p>module: common abstract class</p>
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class CacheConf implements Serializable{
	
	
	private static final long serialVersionUID = -4802431972667076107L;

	private String itemName;
	
	private String sql;
	
	private String datasoruce;
	
	private int refresh;
	
	private List<ViewItem> refs;
	
	private String keycolumn;
	
	
	
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public List<ViewItem> getRefs() {
		
		if(refs == null)
			refs = new ArrayList<ViewItem>();
		
		return refs;
	}

	public String getDatasoruce() {
		return datasoruce;
	}

	public void setDatasoruce(String datasoruce) {
		this.datasoruce = datasoruce;
	}

	public int getRefresh() {
		return refresh;
	}

	public void setRefresh(int refresh) {
		this.refresh = refresh;
	}

	public String getKeycolumn() {
		return keycolumn;
	}

	public void setKeycolumn(String keycolumn) {
		this.keycolumn = keycolumn;
	}
	
	
	
	/**
	 * @author zcl
	 *
	 */
	class ViewItem implements Serializable{
		
		private static final long serialVersionUID = -8655786133288312265L;
		private String viewItemName;
		private boolean isCache;
		private List<CacheRef> cacheRefs;
	
		
		public String getViewItemName() {
			return viewItemName;
		}
	
		public void setViewItemName(String viewItemName) {
			this.viewItemName = viewItemName;
		}
	
		public boolean isCache() {
			return isCache;
		}
	
		public void setCache(boolean isCache) {
			this.isCache = isCache;
		}
	
		public List<CacheRef> getCacheRefs() {
			if(cacheRefs == null)
				cacheRefs = new ArrayList<CacheRef>();
			return cacheRefs;
		}
	
		public void setCacheRefs(List<CacheRef> cacheRefs) {
			this.cacheRefs = cacheRefs;
		}
	}
	
	
	/**
	 * @author zcl
	 *
	 */
	class CacheRef implements Serializable{
		
		
		private static final long serialVersionUID = 3284939469384801664L;
		private String sql;
		private String sourceCode;
		private String targetCode;
		private String Caption;
		
		
	
		public String getSql() {
			return sql;
		}
		public void setSql(String sql) {
			this.sql = sql;
		}
		public String getSourceCode() {
			return sourceCode;
		}
		public void setSourceCode(String sourceCode) {
			this.sourceCode = sourceCode;
		}
		public String getTargetCode() {
			return targetCode;
		}
		public void setTargetCode(String targetCode) {
			this.targetCode = targetCode;
		}
		public String getCaption() {
			return Caption;
		}
		public void setCaption(String caption) {
			Caption = caption;
		}
		
	}
	

}