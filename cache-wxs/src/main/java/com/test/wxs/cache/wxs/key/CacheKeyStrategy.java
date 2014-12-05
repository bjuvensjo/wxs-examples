package com.test.wxs.cache.wxs.key;

import java.io.Serializable;
import java.util.List;

import com.test.wxs.cache.CacheKey;
import com.test.wxs.cache.Config;

/**
 * CacheKeyStrategy declares the behaviour of a cache key strategy.
 * 
 * @param <K>
 */
public interface CacheKeyStrategy<K extends CacheKey> {

    /**
     * Initializes the CacheStrategy utilizing the specified config.
     * 
     * @param config the config.
     */
    void init(Config config);
    
    /**
     * Returns the cache keys corresponding to the specified keys.
     * 
     * @param keys the keys
     * @return the cache keys corresponding to the specified keys
     */
    List<Serializable> createCacheKeys(List<K> keys);

    /**
     * Returns the cache key corresponding to the specified key
     * 
     * @param key the key
     * @return the cache key corresponding to the specified key
     */
    Serializable createCacheKey(K key);
}
