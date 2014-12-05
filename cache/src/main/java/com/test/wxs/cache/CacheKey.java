package com.test.wxs.cache;

import java.io.Serializable;

/**
 * CacheKey defines the method(s) of a CacheKey.
 */
public interface CacheKey {

    /**
     * Returns a Serializable representation of this CacheKey that is used as key by Cache implementations. The Serializable must implement
     * equals() and hashCode().
     * 
     * @return a Serializable representation of this CacheKey that is used as key by Cache implementations
     */
    Serializable getKey();
}
