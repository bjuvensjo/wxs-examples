package com.test.wxs.distributed.side.cache.test;

import java.io.Serializable;

import com.test.wxs.cache.CacheKey;

public class DistributedSideCacheTestKey implements CacheKey {
    private final String key;

    public DistributedSideCacheTestKey(String key) {
        this.key = key;
    }

    @Override
    public Serializable getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "LocalSideCacheTestKey [key=" + key + "]";
    }
}
