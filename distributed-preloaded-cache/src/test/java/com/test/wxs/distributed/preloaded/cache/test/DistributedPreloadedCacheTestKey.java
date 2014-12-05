package com.test.wxs.distributed.preloaded.cache.test;

import java.io.Serializable;

import com.test.wxs.cache.CacheKey;

public class DistributedPreloadedCacheTestKey implements CacheKey {
    private final String key;

    public DistributedPreloadedCacheTestKey(String key) {
        this.key = key;
    }

    @Override
    public Serializable getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "LocalPreloadedCacheTestKey [key=" + key + "]";
    }
}
