package com.test.wxs.cache.wxs.key;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.test.wxs.cache.CacheKey;
import com.test.wxs.cache.Config;

/**
 * StringCacheKeyStrategy is a CacheKeyStrategy utilizing java.lang.String.
 * 
 * @param <K>
 */
public class StringCacheKeyStrategy<K extends CacheKey> implements CacheKeyStrategy<K> {
    private static final String KEY_SEPARATOR = "#";
    private Config config;

    /*
     * (non-Javadoc)
     * 
     * @see com.test.wxs.cache.wxs.key.CacheKeyStrategy#init(com.test.wxs.cache.Config)
     */
    @Override
    public void init(Config value) {
        this.config = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.test.wxs.cache.wxs.key.CacheKeyStrategy#createCacheKeys(java.util.List)
     */
    @Override
    public List<Serializable> createCacheKeys(List<K> cacheKeys) {
        List<Serializable> prefixedKeys = new ArrayList<Serializable>();
        for (K key : cacheKeys) {
            prefixedKeys.add(createCacheKey(key));
        }
        return prefixedKeys;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.test.wxs.cache.wxs.key.CacheKeyStrategy#createCacheKey(com.test.wxs.cache.CacheKey)
     */
    @Override
    public Serializable createCacheKey(K cacheKey) {
        StringBuilder stringBuilder = new StringBuilder();
        appendContext(stringBuilder);
        appendScope(stringBuilder);
        stringBuilder.append(cacheKey.getKey());
        return stringBuilder.toString();
    }

    private void appendContext(StringBuilder stringBuilder) {
        if (config.getContext() != null) {
            stringBuilder.append(config.getContext()).append(KEY_SEPARATOR);
        }
    }

    private void appendScope(StringBuilder stringBuilder) {
        if (config.getScope() != null) {
            if (Config.Scope.APPLICATION.equals(config.getScope())) {
                // Empty by design.
            } else if (Config.Scope.PROFILE.equals(config.getScope())) {
                // TODO Implement!
                // stringBuilder.append(ActiveProfileImpl.get().getId()).append(KEY_SEPARATOR);
            } else if (Config.Scope.REQUEST.equals(config.getScope())) {
                // TODO Implement!
                // stringBuilder.append(CommonContextFetcher.get().getServiceRequestId()).append(KEY_SEPARATOR);
            }
        }
    }
}
