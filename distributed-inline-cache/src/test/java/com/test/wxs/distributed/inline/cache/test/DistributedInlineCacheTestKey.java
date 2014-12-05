package com.test.wxs.distributed.inline.cache.test;

import java.io.Serializable;

import com.test.wxs.cache.CacheKey;

public class DistributedInlineCacheTestKey implements CacheKey {
    private final String key;

    public DistributedInlineCacheTestKey(String key) {
        this.key = key;
    }

    @Override
    public Serializable getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "DistributedInlineCacheTestKey [key=" + key + "]";
    }
}
