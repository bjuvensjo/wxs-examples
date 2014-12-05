package com.test.wxs.cache.wxs;

import java.net.URL;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.wxs.cache.CacheException;
import com.test.wxs.cache.wxs.ExceptionCreator;
import com.ibm.websphere.objectgrid.ObjectGrid;

/**
 * LocalObjectGridFactory is a utility class which make it easier to configure a
 * local in-memory ObjectGrid with the Spring framework. It can be used across
 * Spring contexts.
 */
public class LocalObjectGridFactory implements ObjectGridFactory {
    private Logger log = LoggerFactory.getLogger(LocalObjectGridFactory.class);
    private String objectGridName;
    private URL objectGridXML;
    private ObjectGrid objectGrid;
    private ExceptionCreator exceptionCreator;

    /**
     * Creates a LocalObjectGridFactory
     */
    public LocalObjectGridFactory() {
        exceptionCreator = new ExceptionCreator();
    }

    /** 
     * @see com.test.wxs.cache.wxs.ObjectGridFactory#getObjectGrid()
     */
    public ObjectGrid getObjectGrid() {
        if (objectGrid == null) {
            log.debug("objectGrid is null. objectGridXML: {}, objectGridName: {}", objectGridXML, objectGridName);
            try {
                objectGrid = ObjectGridCacheFactory.getInstance().getLocalObjectGrid(objectGridName, objectGridXML);

            } catch (Exception e) {
                CacheException cacheException = exceptionCreator.getCacheException(
                        ExceptionCreator.Error.INITIALIZE, e);
                log.error("Can not initialize objectGridXML: {}, objectGridName: {}", new Object[] { objectGridXML,
                        objectGridName }, cacheException);
                throw cacheException;
            }
        }
        return objectGrid;
    }

    public void setObjectGridName(String objectGridName) {
        this.objectGridName = objectGridName;
    }

    public void setObjectGridXML(URL objectGridXML) {
        this.objectGridXML = objectGridXML;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
