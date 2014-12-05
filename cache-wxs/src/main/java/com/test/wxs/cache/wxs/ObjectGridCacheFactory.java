package com.test.wxs.cache.wxs;

import java.net.URL;

import com.ibm.websphere.objectgrid.ClientClusterContext;
import com.ibm.websphere.objectgrid.ConnectException;
import com.ibm.websphere.objectgrid.ObjectGrid;
import com.ibm.websphere.objectgrid.ObjectGridException;
import com.ibm.websphere.objectgrid.ObjectGridManager;
import com.ibm.websphere.objectgrid.ObjectGridManagerFactory;
import com.ibm.websphere.objectgrid.security.config.ClientSecurityConfiguration;

/**
 * ObjectGridCacheFactory is a singleton which sets up and returns a handle to an ObjectGrid of Local or Remote type.
 */
public class ObjectGridCacheFactory {

    private static ObjectGridCacheFactory instance = new ObjectGridCacheFactory();

    private ObjectGridCacheFactory() {
        // Empty by design
    }

    public static ObjectGridCacheFactory getInstance() {
        return instance;
    }

    /**
     * Sets up and retrieves an ObjectGrid to a local object grid.
     * 
     * @param objectGridName
     * @param objectGridXML
     * @return a local object grid
     * @throws ObjectGridException
     */
    public synchronized ObjectGrid getLocalObjectGrid(String objectGridName, URL objectGridXML) throws ObjectGridException {
        ObjectGridManager objectGridManager = ObjectGridManagerFactory.getObjectGridManager();
        ObjectGrid objectGrid = objectGridManager.getObjectGrid(objectGridName);
        if (objectGrid == null) {
            objectGrid = objectGridManager.createObjectGrid(objectGridName, objectGridXML);
            objectGrid.initialize();
        }
        return objectGrid;
    }

    /**
     * Sets up and retrieves an ObjectGrid to a remote object grid.
     * 
     * @param objectGridName
     * @param overRideObjectGridXml
     * @param catalogServerAddresses
     * @param clientSecurityConfiguration
     * @return a remote object grid
     * @throws ConnectException
     */
    public synchronized ObjectGrid getRemoteObjectGrid(String objectGridName, URL overRideObjectGridXml, String catalogServerAddresses,
            ClientSecurityConfiguration clientSecurityConfiguration) throws ConnectException {
        ObjectGridManager objectGridManager = ObjectGridManagerFactory.getObjectGridManager();
        ClientClusterContext ccc = getClientClusterContext(objectGridManager, overRideObjectGridXml, catalogServerAddresses,
                clientSecurityConfiguration);
        return objectGridManager.getObjectGrid(ccc, objectGridName);
    }

    private ClientClusterContext getClientClusterContext(ObjectGridManager objectGridManager, URL overRideObjectGridXml,
            String catalogServerAddresses, ClientSecurityConfiguration clientSecurityConfiguration) throws ConnectException {
        if (catalogServerAddresses == null) {
            return objectGridManager.connect(clientSecurityConfiguration, overRideObjectGridXml);
        }
        return objectGridManager.connect(catalogServerAddresses, clientSecurityConfiguration, overRideObjectGridXml);
    }
}
