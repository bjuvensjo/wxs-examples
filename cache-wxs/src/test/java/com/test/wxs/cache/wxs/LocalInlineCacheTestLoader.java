package com.test.wxs.cache.wxs;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.wxs.cache.wxs.key.ObjectCacheKey;
import com.test.wxs.model.CacheObject;
import com.ibm.websphere.objectgrid.BackingMap;
import com.ibm.websphere.objectgrid.Session;
import com.ibm.websphere.objectgrid.TxID;
import com.ibm.websphere.objectgrid.plugins.Loader;
import com.ibm.websphere.objectgrid.plugins.LoaderException;
import com.ibm.websphere.objectgrid.plugins.LogSequence;
import com.ibm.websphere.objectgrid.plugins.OptimisticCollisionException;

public class LocalInlineCacheTestLoader implements Loader {
	private Logger log = LoggerFactory.getLogger(LocalInlineCacheTestLoader.class);

	@Override
	public void batchUpdate(TxID txID, LogSequence logSequence) throws LoaderException, OptimisticCollisionException {
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List get(TxID txID, List keys, boolean forUpdate) throws LoaderException {
        List result = new ArrayList();
        for (int i = 0; i < keys.size(); i++) {
            String serviceRequestId = String.valueOf(i);
            ObjectCacheKey objectCacheKey = (ObjectCacheKey) keys.get(i);
            LocalInlineCacheTestKey localInlineCacheTestKey = (LocalInlineCacheTestKey) objectCacheKey.getKey();
            String value = localInlineCacheTestKey.toString();
            CacheObject cacheObject = new CacheObject(serviceRequestId, value);
            result.add(cacheObject);
        }
        log.debug("Result is: {}", result);
        return result;
    }

	@Override
	public void preloadMap(Session session, BackingMap backingMap) throws LoaderException {
	}
}
