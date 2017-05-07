package org.scorpion.api.kernel;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.persistence.ITscpDataSource;


/**
 *  自主可控工程中心平台架构(TAIJI Security Controllable Platform)
 * <p>com.taiji.tscp.common
 * <p>File: AbsTscpFactory.java create time:2015-5-8下午07:57:37</p> 
 * <p>Title: abstract factory class </p>
 * <p>Description: all the class have life cycle characteristic will implement the interface. it concludes three methods</p>
 * <p>Copyright: Copyright (c) 2015 taiji.com.cn</p>
 * <p>Company: taiji.com.cn</p>
 * <p>module: common abstract class</p>
 * @author  郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class TscpDataSource implements ITscpDataSource{
	
	private boolean isDefaultDataSource;
	
	private String dataSourceName;
	
	private DataSource dataSource;
	
	private Integer dbType;
	

	
	/**
	 * @param dataSource
	 * 
	 * @param dataSourceName
	 * 
	 * @param isDefaultDataSource
	 */
	public TscpDataSource(String dataSourceName,DataSource dataSource,Integer dbType,boolean isDefaultDataSource) {
		
		this.isDefaultDataSource = isDefaultDataSource;
		this.dataSourceName = dataSourceName;
		this.dataSource = dataSource;
		this.dbType = dbType;
	
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password)throws SQLException {
		return dataSource.getConnection(username, password);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		dataSource.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		this.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return dataSource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return dataSource.isWrapperFor(iface);
	}

	@Override
	public boolean isDefaultDataSource() throws TscpBaseException {
		return this.isDefaultDataSource;
	}

	@Override
	public String getDataSourceName() throws TscpBaseException {
		return this.dataSourceName;
	}

	public Integer getDbType() {
		return dbType;
	}

	public void setDbType(Integer dbType) {
		this.dbType = dbType;
	}

}
