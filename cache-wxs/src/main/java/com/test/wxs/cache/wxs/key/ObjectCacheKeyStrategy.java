package com.test.wxs.cache.wxs.key;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.test.wxs.cache.CacheKey;
import com.test.wxs.cache.Config;

/**
 * ObjectCacheKeyStrategy is a CacheKeyStrategy utilizing ObjectCacheKey.
 * 
 * @param <K>
 */
public class ObjectCacheKeyStrategy<K extends CacheKey> implements CacheKeyStrategy<K> {
    private Config config;
    
    /* (non-Javadoc)
     * @see com.test.wxs.cache.wxs.key.CacheKeyStrategy#init(com.test.wxs.cache.Config)
     */
    @Override
    public void init(Config config) {
        this.config = config;
    }

    /* (non-Javadoc)
     * @see com.test.wxs.cache.wxs.key.CacheKeyStrategy#createCacheKeys(java.util.List)
     */
    @Override
    public List<Serializable> createCacheKeys(List<K> cacheKeys) {
        List<Serializable> objectCacheKeys = new ArrayList<Serializable>();
        if (cacheKeys != null) {
            for (K cacheKey : cacheKeys) {
                objectCacheKeys.add(createCacheKey(cacheKey));
            }
        }
        return objectCacheKeys;
    }

    /* (non-Javadoc)
     * @see com.test.wxs.cache.wxs.key.CacheKeyStrategy#createCacheKey(com.test.wxs.cache.CacheKey)
     */
    @Override
    public Serializable createCacheKey(K cacheKey) {
        return new ObjectCacheKey(config.getContext(), config.getScope().name(), cacheKey.getKey());
    }
}
