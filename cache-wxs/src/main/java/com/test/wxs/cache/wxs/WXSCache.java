package com.test.wxs.cache.wxs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.wxs.cache.Cache;
import com.test.wxs.cache.CacheException;
import com.test.wxs.cache.CacheKey;
import com.test.wxs.cache.Config;
import com.test.wxs.cache.wxs.ExceptionCreator;
import com.test.wxs.cache.wxs.ExceptionCreator.Error;
import com.test.wxs.cache.wxs.key.CacheKeyStrategy;
import com.test.wxs.cache.wxs.key.StringCacheKeyStrategy;
import com.test.wxs.model.CacheObject;
import com.ibm.websphere.objectgrid.ObjectMap;
import com.ibm.websphere.objectgrid.Session;

/**
 * The WXSCache is an implementation of the {@link Cache} interface that delegates the actual caching to the WebSphere eXstreme Scale cache
 * implementation.
 * 
 * Replication and timeToLive is supported by configuration of WXS, i.e. Config values are not used for this.
 */
public class WXSCache<K extends CacheKey, V extends Serializable> implements Cache<K, V> {
    private Logger log = LoggerFactory.getLogger(WXSCache.class);
    private Config config;
    private ObjectGridFactory objectGridFactory;
    private String mapName;
    private ExceptionCreator exceptionCreator;
    private CacheKeyStrategy<K> cacheKeyStrategy;

    /**
     * Creates a WXSCache with the specified parameters.
     * 
     * @param config
     *            the config
     * @param objectGridFactory
     *            the objectGridFactory
     * @param mapName
     *            the mapName
     */
    public WXSCache(Config config, ObjectGridFactory objectGridFactory, String mapName) {
        this(config, objectGridFactory, mapName, null);
    }

    /**
     * Creates a WXSCache with the specified parameters.
     * 
     * @param config
     *            the config
     * @param objectGridFactory
     *            the objectGridFactory
     * @param mapName
     *            the mapName
     * @param cacheKeyStrategy
     *            the cacheKeyStrategy
     */
    public WXSCache(Config config, ObjectGridFactory objectGridFactory, String mapName, CacheKeyStrategy<K> cacheKeyStrategy) {
        if (log.isDebugEnabled()) {
            log.debug("config: {}, objectGridFactory: {}, mapName: {}, cacheKeyStrategy: {}", new Object[] { config, objectGridFactory,
                    mapName, cacheKeyStrategy });
        }
        this.config = config;
        this.objectGridFactory = objectGridFactory;
        this.mapName = mapName;
        this.exceptionCreator = new ExceptionCreator();
        if (cacheKeyStrategy == null) {
            // Default CacheKeyStrategy. This default CacheKeyStrategy should be used for all remote objectGrids.
            this.cacheKeyStrategy = new StringCacheKeyStrategy<K>();
        } else {
            this.cacheKeyStrategy = cacheKeyStrategy;
        }
        this.cacheKeyStrategy.init(config);
        log.debug("this.cacheKeyStrategy {}", cacheKeyStrategy);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.test.wxs.cache.Cache#get(com.test.wxs.cache.CacheKey)
     */
    @Override
    @SuppressWarnings("unchecked")
    public V get(K key) {
        log.debug("Enter. key: {}", key);
        V value = null;
        if (config.isOn()) {
            Serializable cacheKey = cacheKeyStrategy.createCacheKey(key);
            log.debug("cacheKey: {}, mapName: {}", cacheKey, mapName);
            try {
                Session session = objectGridFactory.getObjectGrid().getSession();
                session.begin();
                ObjectMap objectMap = session.getMap(mapName);
                CacheObject cacheObject = (CacheObject) objectMap.get(cacheKey);
                log.debug("cacheObject: {}", cacheObject);
                // String currentRequestId = null;
                if (cacheObject != null) {
                    value = (V) cacheObject.getValue();
                    // currentRequestId = cacheObject.getServiceRequestId();
                }
                session.commit();
                // String timestamp = new DateTime().toString();
                // LogContextFetcher.get().getSupportData()
                // .addEvent(new CacheResponse(timestamp, currentRequestId, mapName, cacheKey.toString()));
                // AuditLogger.getInstance().logCacheResponse(timestamp, currentRequestId, mapName, cacheKey.toString());
            } catch (Exception e) {
                CacheException exception = exceptionCreator.getCacheException(Error.GET, "Key [" + cacheKey + "]", e);
                log.error("Can not get {}.", key, exception);
                throw exception;
            }
        }
        log.debug("Exit. key: {}, value: {}", key, value);
        return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.test.wxs.cache.Cache#get(java.util.List)
     */
    @Override
    public List<V> get(List<K> keys) {
        if (objectGridFactory instanceof RemoteObjectGridFactory) {
            log.debug("Getting from Remote Cache");
            return this.getOneByOne(keys);
        } else if (objectGridFactory instanceof LocalObjectGridFactory) {
            log.debug("Getting from Local Cache");
            return this.getAllForLocalCache(keys);
        } else {
            log.warn("objectGridFactory is neither RemoteObjectGridFactory or LocalObjectGridFactory. Defaulting to Remote");
            return this.getAllForRemoteCache(keys);
        }
    }

    @SuppressWarnings("unchecked")
    private List<V> getOneByOne(List<K> keys) {
        log.debug("Enter. mapName {} keys: {}", mapName, keys);
        long start = System.currentTimeMillis();
        List<V> values = new ArrayList<V>();
        if (config.isOn()) {
            for (K key : keys) {
                Serializable cacheKey = cacheKeyStrategy.createCacheKey(key);
                V value = null;
                try {
                    Session session = objectGridFactory.getObjectGrid().getSession();
                    session.begin();
                    ObjectMap objectMap = session.getMap(mapName);
                    CacheObject cacheObject = (CacheObject) objectMap.get(cacheKey);
                    log.debug("{} = {}", cacheKey, cacheObject);

                    if (cacheObject != null) {
                        value = (V) cacheObject.getValue();
                    }
                    session.commit();
                } catch (Exception e) {
                    log.error("Cannot get {}.", key);
                }
                values.add(value);
            }

        }
        log.debug("Exit. Values: {} Response time: {} ms", keys, System.currentTimeMillis() - start);
        return values;
    }

    @SuppressWarnings("unchecked")
    private List<V> getAllForRemoteCache(List<K> keys) {

        List<V> values = new ArrayList<V>();
        if (config.isOn()) {
            List<Serializable> cacheKeys = cacheKeyStrategy.createCacheKeys(keys);
            log.debug("cacheKeys: {}, mapName: {}", cacheKeys, mapName);
            try {
                Session session = objectGridFactory.getObjectGrid().getSession();
                ObjectMap objectMap = session.getMap(mapName);
                for (Object cacheKey : cacheKeys) {
                    session.begin();
                    CacheObject cacheObject = (CacheObject) objectMap.get(cacheKey);
                    session.commit();
                    log.debug("cacheObject: {}", cacheObject);
                    V value = null;
                    // String currentRequestId = null;
                    if (cacheObject != null) {
                        value = (V) cacheObject.getValue();
                        // currentRequestId = cacheObject.getServiceRequestId();
                    }
                    values.add(value);

                    // String timestamp = new DateTime().toString();
                    // LogContextFetcher.get().getSupportData()
                    // .addEvent(new CacheResponse(timestamp, currentRequestId, mapName, cacheKey.toString()));
                    // AuditLogger.getInstance().logCacheResponse(timestamp, currentRequestId, mapName,
                    // cacheKey.toString());
                }

            } catch (Exception e) {
                CacheException exception = exceptionCreator.getCacheException(Error.GET, "Key [" + cacheKeys + "]", e);
                log.error("Can not get {}.", keys, exception);
                throw exception;
            }
        }
        log.debug("Exit. Keys: {} Values: {}", keys, values);
        return values;
    }

    @SuppressWarnings("unchecked")
    private List<V> getAllForLocalCache(List<K> keys) {
        log.debug("Enter. keys: {}", keys);
        List<V> values = null;
        if (config.isOn()) {
            List<Serializable> cacheKeys = cacheKeyStrategy.createCacheKeys(keys);
            log.debug("cacheKeys: {}, mapName: {}", cacheKeys, mapName);
            try {
                Session session = objectGridFactory.getObjectGrid().getSession();
                session.begin();
                ObjectMap objectMap = session.getMap(mapName);
                List<CacheObject> cacheObjects = objectMap.getAll(cacheKeys);
                if (cacheObjects != null) {
                    values = new ArrayList<V>();
                    for (CacheObject cacheObject : cacheObjects) {
                        log.debug("cacheObject: {}", cacheObject);
                        V value = null;
                        if (cacheObject != null) {
                            value = (V) cacheObject.getValue();
                        }
                        values.add(value);
                    }
                }
                session.commit();
            } catch (Exception e) {
                CacheException exception = exceptionCreator.getCacheException(Error.GET, "Key [" + cacheKeys + "]", e);
                log.error("Can not get {}.", keys, exception);
                throw exception;
            }
        }
        log.debug("Exit. keys: {}, values: {}", keys, values);
        return values;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.test.wxs.cache.Cache#put(com.test.wxs.cache.CacheKey, java.io.Serializable)
     */
    @Override
    public void put(K key, V value) {
        log.debug("Enter. key: {}, value: {}", key, value);
        if (config.isOn()) {
            // CacheObject cacheObject = new CacheObject(CommonContextFetcher.get().getServiceRequestId(), value);
            // TODO! Set serviceRequestId correctly!
            CacheObject cacheObject = new CacheObject(java.util.UUID.randomUUID().toString(), value);
            Serializable cacheKey = cacheKeyStrategy.createCacheKey(key);
            log.debug("cacheKey: {}, mapName: {}", cacheKey, mapName);
            try {
                Session session = objectGridFactory.getObjectGrid().getSession();
                session.begin();
                ObjectMap objectMap = session.getMap(mapName);
                if (objectMap.containsKey(cacheKey)) {
                    log.debug("mapName {} does contain key: {}", mapName, cacheKey);
                    objectMap.update(cacheKey, cacheObject);
                } else {
                    log.debug("mapName {} does not contain key: {}", mapName, cacheKey);
                    objectMap.insert(cacheKey, cacheObject);
                }
                session.commit();
            } catch (Exception e) {
                CacheException exception = exceptionCreator.getCacheException(Error.PUT, "Key [" + cacheKey + "]", e);
                log.error("Can not get {}.", key, exception);
                throw exception;
            }
        }
        log.debug("Exit. key: {}, value: {}", key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.test.wxs.cache.Cache#remove(com.test.wxs.cache.CacheKey)
     */
    @Override
    public void remove(K key) {
        log.debug("Enter. key: {}", key);
        if (config.isOn()) {
            Serializable cacheKey = cacheKeyStrategy.createCacheKey(key);
            log.debug("cacheKey: {}, mapName: {}", cacheKey, mapName);
            try {
                Session session = objectGridFactory.getObjectGrid().getSession();
                session.begin();
                ObjectMap objectMap = session.getMap(mapName);
                objectMap.remove(cacheKey);
                session.commit();
            } catch (Exception e) {
                CacheException exception = exceptionCreator.getCacheException(Error.REMOVE, "Key [" + cacheKey + "]", e);
                log.error("Can not get {}.", key, exception);
                throw exception;
            }
        }
        log.debug("Exit. key: {}", key);
    }
}
