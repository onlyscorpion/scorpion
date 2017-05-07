package org.scorpion.api.kernel;

import java.io.Serializable;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

import org.scorpion.api.exception.TscpBaseException;
import org.scorpion.api.persistence.ITscpConnection;

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
public class TscpConnection implements ITscpConnection, Serializable{
	
	private static final long serialVersionUID = 1L;

	private boolean isOpenTransaction;
	
	private boolean isCommitTransaction;
	
	private Connection connetion;
	
	private Integer dbType;
	

	public TscpConnection(Connection connetion,Integer dbType,boolean isOpenTrasaction,boolean isCommitTransaction) throws TscpBaseException {
		super();
		try {
			this.connetion = connetion;
			this.isOpenTransaction = isOpenTrasaction;
			this.isCommitTransaction = isCommitTransaction;
			this.dbType = dbType;
			this.setAutoCommit(true);
		} catch (Exception e) {
			throw new TscpBaseException("TSCP-9065:Create database conneciton failure !",e);
		}
	}



	@Override
	public void updateTransactionStatus(boolean isOpenTransaction,boolean isCommitTransaction) throws TscpBaseException {
		this.isOpenTransaction = isOpenTransaction;
		this.isCommitTransaction = isCommitTransaction;
		
	}

	@Override
	public boolean isOpenTransaction() {
		return this.isOpenTransaction;
	}


	@Override
	public boolean isCommitTransaction() throws TscpBaseException {
		return this.isCommitTransaction;
	}



	@Override
	public Statement createStatement() throws SQLException {
		return connetion.createStatement();
	}


	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return connetion.prepareStatement(sql);
	}


	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return connetion.prepareCall(sql);
	}


	@Override
	public String nativeSQL(String sql) throws SQLException {
		return connetion.nativeSQL(sql);
	}


	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		connetion.setAutoCommit(autoCommit);
	}


	@Override
	public boolean getAutoCommit() throws SQLException {
		return connetion.getAutoCommit();
	}


	@Override
	public void commit() throws SQLException {
		if(connetion.getAutoCommit())
			return;
		connetion.commit();
	}


	@Override
	public void rollback() throws SQLException {
		connetion.rollback();
	}


	@Override
	public void close() throws SQLException {
		connetion.close();
	}


	@Override
	public boolean isClosed() throws SQLException {
		return connetion.isClosed();
	}


	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return connetion.getMetaData();
	}


	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		connetion.setReadOnly(readOnly);
	}


	@Override
	public boolean isReadOnly() throws SQLException {
		return connetion.isReadOnly();
	}


	@Override
	public void setCatalog(String catalog) throws SQLException {
		connetion.setCatalog(catalog);
	}


	@Override
	public String getCatalog() throws SQLException {
		return connetion.getCatalog();
	}


	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		connetion.setTransactionIsolation(level);
	}


	@Override
	public int getTransactionIsolation() throws SQLException {
		return connetion.getTransactionIsolation();
	}


	@Override
	public SQLWarning getWarnings() throws SQLException {
		return connetion.getWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException {
		connetion.clearWarnings();
	}


	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency)throws SQLException {
		return connetion.createStatement(resultSetType, resultSetConcurrency);
	}


	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType,int resultSetConcurrency) throws SQLException {
		return connetion.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}


	@Override
	public CallableStatement prepareCall(String sql, int resultSetType,int resultSetConcurrency) throws SQLException {
		return connetion.prepareCall(sql, resultSetType, resultSetConcurrency);
	}


	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return connetion.getTypeMap();
	}


	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		connetion.setTypeMap(map);
	}


	@Override
	public void setHoldability(int holdability) throws SQLException {
		connetion.setHoldability(holdability);
	}


	@Override
	public int getHoldability() throws SQLException {
		return connetion.getHoldability();
	}


	@Override
	public Savepoint setSavepoint() throws SQLException {
		return connetion.setSavepoint();
	}


	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		return connetion.setSavepoint(name);
	}


	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		connetion.rollback(savepoint);
	}


	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		connetion.releaseSavepoint(savepoint);
	}


	@Override
	public Statement createStatement(int resultSetType,int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return connetion.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}


	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType,int resultSetConcurrency, int resultSetHoldability)throws SQLException {
		return connetion.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}


	@Override
	public CallableStatement prepareCall(String sql, int resultSetType,int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return connetion.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}


	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)throws SQLException {
		return connetion.prepareStatement(sql, autoGeneratedKeys);
	}


	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)throws SQLException {
		return connetion.prepareStatement(sql, columnIndexes);
	}


	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames)throws SQLException {
		return connetion.prepareStatement(sql, columnNames);
	}


	@Override
	public Clob createClob() throws SQLException {
		return connetion.createClob();
	}


	@Override
	public Blob createBlob() throws SQLException {
		return connetion.createBlob();
	}


	@Override
	public NClob createNClob() throws SQLException {
		return connetion.createNClob();
	}


	@Override
	public SQLXML createSQLXML() throws SQLException {
		return connetion.createSQLXML();
	}


	@Override
	public boolean isValid(int timeout) throws SQLException {
		return connetion.isValid(timeout);
	}


	@Override
	public void setClientInfo(String name, String value)throws SQLClientInfoException {
		connetion.setClientInfo(name, value);
	}


	@Override
	public void setClientInfo(Properties properties)throws SQLClientInfoException {
		connetion.setClientInfo(properties);
	}


	@Override
	public String getClientInfo(String name) throws SQLException {
		return connetion.getClientInfo(name);
	}


	@Override
	public Properties getClientInfo() throws SQLException {
		return connetion.getClientInfo();
	}


	@Override
	public Array createArrayOf(String typeName, Object[] elements)throws SQLException {
		return connetion.createArrayOf(typeName, elements);
	}


	@Override
	public Struct createStruct(String typeName, Object[] attributes)throws SQLException {
		return connetion.createStruct(typeName, attributes);
	}


	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return connetion.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return connetion.isWrapperFor(iface);
	}



	@Override
	public Integer getDBType() {
		return this.dbType;
	}


}
