package org.scorpion.api.persistence;

import java.util.List;
import java.util.Map;

import org.scorpion.api.configuration.DBParam;
import org.scorpion.api.exception.TscpBaseException;

public interface ITscpQueryService {
	

	/**
	 * @param key
	 * @param param
	 * @return
	 * @throws TscpBaseException
	 */
	public Map<String,Object> queryMapByKey(String key,DBParam param)throws TscpBaseException;
	
	/**
	 * @param key
	 * @param param
	 * @return
	 * @throws TscpBaseException
	 */
	public List<Map<String,Object>>queryLisMapByKey(String key,DBParam param)throws TscpBaseException;
	
	/**
	 * @param key
	 * @param param
	 * @param clazz
	 * @return
	 * @throws TscpBaseException
	 */
	public <T> T queryPoBykey(String key,DBParam param,Class<T> clazz) throws TscpBaseException;

	/**
	 * @param key
	 * @param param
	 * @param clazz
	 * @return
	 * @throws TscpBaseException
	 */
    public <T> List<T>queryLisPoByKey(String key,DBParam param,Class<T> clazz) throws TscpBaseException;
    
    /**
     * @param sql
     * @param param
     * @return
     * @throws TscpBaseException
     */
    public Object queryBySQL(String sql,DBParam param)throws TscpBaseException;

	

}
