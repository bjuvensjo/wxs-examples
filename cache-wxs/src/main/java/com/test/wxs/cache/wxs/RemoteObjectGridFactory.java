package com.test.wxs.cache.wxs;

import java.net.URL;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.wxs.cache.CacheException;
import com.test.wxs.cache.wxs.ExceptionCreator;
import com.ibm.websphere.objectgrid.ObjectGrid;
import com.ibm.websphere.objectgrid.security.config.ClientSecurityConfiguration;

/**
 * RemoteObjectGridFactory is a utility class which make it easier to configure a remote ("clustered") ObjectGrid with the Spring framework.
 * It can be used across Spring contexts.
 */
public class RemoteObjectGridFactory implements ObjectGridFactory {
    private Logger log = LoggerFactory.getLogger(RemoteObjectGridFactory.class);
    private String catalogServerAddresses;
    private URL overRideObjectGridXml;
    private String objectGridName;
    private ObjectGrid objectGrid;
    private ExceptionCreator exceptionCreator;
    // Not used
    private ClientSecurityConfiguration clientSecurityConfiguration = null;

    /**
     * Creates a RemoteObjectGridFactory
     */
    public RemoteObjectGridFactory() {
        exceptionCreator = new ExceptionCreator();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.test.wxs.cache.wxs.ObjectGridFactory#getObjectGrid()
     */
    @Override
    public synchronized ObjectGrid getObjectGrid() {
        if (objectGrid == null) {
            log.debug("objectGrid is null. catalogServerAddresses: {}, objectGridName: {}", catalogServerAddresses, objectGridName);
            try {
                objectGrid = ObjectGridCacheFactory.getInstance().getRemoteObjectGrid(objectGridName, overRideObjectGridXml,
                        catalogServerAddresses, clientSecurityConfiguration);
            } catch (Exception e) {
                CacheException cacheException = exceptionCreator.getCacheException(ExceptionCreator.Error.INITIALIZE, e);
                log.error("Can not connect to catalogServerAddresses: {}, objectGridName: {}", catalogServerAddresses, objectGridName,
                        cacheException);
                throw cacheException;
            }
        }
        return objectGrid;
    }

    public void setCatalogServerAddresses(String catalogServerAddresses) {
        this.catalogServerAddresses = catalogServerAddresses;
    }

    public void setOverRideObjectGridXml(URL overRideObjectGridXml) {
        this.overRideObjectGridXml = overRideObjectGridXml;
    }

    public void setObjectGridName(String objectGridName) {
        this.objectGridName = objectGridName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
