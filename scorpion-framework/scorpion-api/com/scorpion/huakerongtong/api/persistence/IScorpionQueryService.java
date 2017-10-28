package com.scorpion.huakerongtong.api.persistence;

import java.util.List;
import java.util.Map;

import com.scorpion.huakerongtong.api.configuration.DBParam;
import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

public interface IScorpionQueryService {
	

	/**
	 * @param key
	 * @param param
	 * @return
	 * @throws ScorpionBaseException
	 */
	public Map<String,Object> queryMapByKey(String key,DBParam param)throws ScorpionBaseException;
	
	/**
	 * @param key
	 * @param param
	 * @return
	 * @throws ScorpionBaseException
	 */
	public List<Map<String,Object>>queryLisMapByKey(String key,DBParam param)throws ScorpionBaseException;
	
	/**
	 * @param key
	 * @param param
	 * @param clazz
	 * @return
	 * @throws ScorpionBaseException
	 */
	public <T> T queryPoBykey(String key,DBParam param,Class<T> clazz) throws ScorpionBaseException;

	/**
	 * @param key
	 * @param param
	 * @param clazz
	 * @return
	 * @throws ScorpionBaseException
	 */
    public <T> List<T>queryLisPoByKey(String key,DBParam param,Class<T> clazz) throws ScorpionBaseException;
    
    /**
     * @param sql
     * @param param
     * @return
     * @throws ScorpionBaseException
     */
    public Object queryBySQL(String sql,DBParam param)throws ScorpionBaseException;

	

}
