package com.test.wxs.cache.wxs;

import java.io.Serializable;

import com.test.wxs.cache.CacheKey;

public class LocalSideCacheTestKey implements CacheKey {
    private final String key;

    public LocalSideCacheTestKey(String key) {
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
