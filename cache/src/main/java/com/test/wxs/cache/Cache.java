package com.test.wxs.cache;

import java.io.Serializable;
import java.util.List;

/**
 * The Cache interface defines the methods of a cache implementation.
 */
public interface Cache<K extends CacheKey, V extends Serializable> {

    /**
     * Puts the specified value in the cache with the specified key as identifier.
     * 
     * @param key
     *            the key
     * @param value
     *            the value
     */
    void put(K key, V value);

    /**
     * Returns the value in the cache which is identified by the specified key, or null if no such value exists.
     * 
     * @param key
     *            the key
     * @return the value in the cache which is identified by the specified key, or null if no such value exists
     */
    V get(K key);

    /**
     * Returns the value(s) in the cache that are identified by the specified keys. If a key in the list cannot be found, a null value will
     * be set at the corresponding position in the returned list.
     * 
     * @param keys
     *            a list of keys
     * @return the value(s) in the cache that are identified by the specified keys
     */
    List<V> get(List<K> keys);

    /**
     * Removes the value in the cache which is identified by the specified key.
     * 
     * @param key
     *            the key
     */
    void remove(K key);
}
