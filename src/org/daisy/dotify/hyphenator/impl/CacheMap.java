package org.daisy.dotify.impl.hyphenator.latex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

class CacheMap<K, V> {
	private static final int DEFAULT_CACHE_SIZE = 100000;
	private final Set<K> cacheQueue;
	private final Map<K, V> cache;
	private final int size;

	CacheMap() {
		this(DEFAULT_CACHE_SIZE);
	}
	
	CacheMap(int size) {
		this.cacheQueue = new LinkedHashSet<>();
		this.cache = new HashMap<>();
		this.size = size;
	}
	
	V get(K key) {
		V ret = cache.get(key);
		if (ret!=null) {
			// move it to the top again
			cacheQueue.remove(key);
			cacheQueue.add(key);
			return ret;
		} else {
			return null;
		}
	}
	
	void put(K key, V value) {
		if (cacheQueue.contains(key)) {
			cacheQueue.remove(key);
		}
		cacheQueue.add(key);
		cache.put(key, value);
		trimCache();
	}
	
	void clear() {
		cache.clear();
		cacheQueue.clear();
	}
	
	private void trimCache() {
		Iterator<K> iter = cacheQueue.iterator();
		while (cacheQueue.size()>size && iter.hasNext()) {
			iter.next();
			iter.remove();
		}
	}

}
