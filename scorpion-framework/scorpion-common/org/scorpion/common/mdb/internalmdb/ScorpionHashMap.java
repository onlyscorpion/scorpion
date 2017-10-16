package org.scorpion.common.mdb.internalmdb;

import java.util.HashMap;
import java.util.Map;

import org.scorpion.api.exception.ScorpionBaseException;

/**
 * 天蝎平台架构(TAIJI Security Controllable Platform)
 * <p>
 * com.taiji.Scorpion.common
 * <p>
 * File: AbsScorpionFactory.java create time:2015-5-8下午07:57:37
 * </p>
 * <p>
 * Title: abstract factory class
 * </p>
 * <p>
 * Description: the annotation is used to signal the method of component
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 taiji.com.cn
 * </p>
 * <p>
 * Company: taiji.com.cn
 * </p>
 * <p>
 * module: common abstract class
 * </p>
 * 
 * @author 郑承磊
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public class ScorpionHashMap<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = 4188900615503386814L;

	private static ScorpionHashMap<?, ?> map;

	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Class<T> clazz) {
		return (T) get(key);
	};

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		return (V) ((DataEntity) super.get(key)).getCacheData();
	}

	@Override
	public V put(K key, V value) {
		DataEntity entity = new DataEntity();
		entity.setCacheData(value);
		return super.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public void putAll(K key, Map<? extends K, ? extends V> m) throws ScorpionBaseException {

		if (!this.containsKey(key))
			throw new ScorpionBaseException("scorpion-4398:Not exist key [" + key + "] mapping value !");

		if (!(this.get(key) instanceof Map))
			throw new ScorpionBaseException("scorpion-4095:The type of key [" + key + "] mapping is not Map type !");

		((Map<K, V>) this.get(key)).putAll(m);
		super.put(key, this.get(key));

	}

	/**
	 * 
	 * @return
	 */
	public synchronized ScorpionHashMap<?, ?> getKVCacheInstance() {

		if (map == null)
			map = new ScorpionHashMap<Object, Object>();

		return map;

	}

}
