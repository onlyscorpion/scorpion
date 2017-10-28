package com.scorpion.huakerongtong.persistence.handler;

import java.util.Collection;
import java.util.List;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;
import com.scorpion.huakerongtong.api.persistence.IScorpionExecuteService;

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
public class ScorpionExecuteHandler implements IScorpionExecuteService{


	@Override
	public int insertPO(Object po) throws ScorpionBaseException {
		return 0;
	}

	@Override
	public int insertPOs(Collection<? extends Object> pos)
			throws ScorpionBaseException {
		return 0;
	}

	@Override
	public int updatePO(Object po) throws ScorpionBaseException {
		return 0;
	}

	@Override
	public int updatePOs(Collection<?> pos) throws ScorpionBaseException {
		return 0;
	}

	@Override
	public int deletePO(Object po) throws ScorpionBaseException {
		return 0;
	}

	@Override
	public int deletePOs(Collection<?> pos) throws ScorpionBaseException {
		return 0;
	}

	

	@Override
	public void commit() throws ScorpionBaseException {
		
	}

	@Override
	public void commitAll() throws ScorpionBaseException {
		
	}

	@Override
	public void rollback() throws ScorpionBaseException {
		
	}

	@Override
	public void rollbackAll() throws ScorpionBaseException {
	}

	@Override
	public boolean dataChanged() throws ScorpionBaseException {
		return false;
	}

	@Override
	public int executeUpdateBySql(String name, String sql, String[] tables,com.scorpion.huakerongtong.api.configuration.DBParam parameter)throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdateByKey(String sqlKey,com.scorpion.huakerongtong.api.configuration.DBParam param) throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeByKey(String sqlKey,com.scorpion.huakerongtong.api.configuration.DBParam param) throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<Object> executeProcedureByKey(String procedureKey,com.scorpion.huakerongtong.api.configuration.DBParam parameter)
			throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> executeProcedure(String procedure,com.scorpion.huakerongtong.api.configuration.DBParam parameter)
			throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeTempJobByKey(String procedureKey,
			com.scorpion.huakerongtong.api.configuration.DBParam parameter)
			throws ScorpionBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeTempJob(String procedure,com.scorpion.huakerongtong.api.configuration.DBParam parameter)
			throws ScorpionBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int executeBatchByKey(String sqlKey,Collection<com.scorpion.huakerongtong.api.configuration.DBParam> params)
			throws ScorpionBaseException {
		// TODO Auto-generated method stub
		return 0;
	}


}
