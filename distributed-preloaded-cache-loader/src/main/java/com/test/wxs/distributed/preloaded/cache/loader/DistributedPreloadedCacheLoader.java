package com.test.wxs.distributed.preloaded.cache.loader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.wxs.agent.LoaderAgent;
import com.ibm.websphere.objectgrid.ClientClusterContext;
import com.ibm.websphere.objectgrid.ConnectException;
import com.ibm.websphere.objectgrid.ObjectGrid;
import com.ibm.websphere.objectgrid.ObjectGridException;
import com.ibm.websphere.objectgrid.ObjectGridManager;
import com.ibm.websphere.objectgrid.ObjectGridManagerFactory;
import com.ibm.websphere.objectgrid.ObjectMap;
import com.ibm.websphere.objectgrid.Session;
import com.ibm.websphere.objectgrid.datagrid.ReduceGridAgent;
import com.ibm.websphere.objectgrid.security.config.ClientSecurityConfiguration;

// TODO Change to use wxs agent and configured values
public class DistributedPreloadedCacheLoader {
    private URL overRideObjectGridXml;
    private String catalogServerAddresses;
    private ClientSecurityConfiguration clientSecurityConfiguration;
    private String objectGridName = "distributed-preloaded-grid";
    private String mapName = "distributed-preloaded-map";
    

    public void setOverRideObjectGridXml(URL overRideObjectGridXml) {
        this.overRideObjectGridXml = overRideObjectGridXml;
    }

    public void setCatalogServerAddresses(String catalogServerAddresses) {
        this.catalogServerAddresses = catalogServerAddresses;
    }

    public void setClientSecurityConfiguration(ClientSecurityConfiguration clientSecurityConfiguration) {
        this.clientSecurityConfiguration = clientSecurityConfiguration;
    }

    public void setObjectGridName(String objectGridName) {
        this.objectGridName = objectGridName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public void load() throws ObjectGridException {
        ObjectGrid objectGrid = getObjectGrid(objectGridName, overRideObjectGridXml, catalogServerAddresses, clientSecurityConfiguration);
        Session session = objectGrid.getSession();
        ObjectMap map = session.getMap(mapName);
        
        List<String> keys = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            keys.add(String.valueOf(i));
        }
        
        session.begin();        
        ReduceGridAgent agent = new LoaderAgent(); // TODO set LoadService        
        map.getAgentManager().callReduceAgent(agent, keys);          
        session.commit();
    }

    private ObjectGrid getObjectGrid(String objectGridName, URL overRideObjectGridXml,
            String catalogServerAddresses, ClientSecurityConfiguration clientSecurityConfiguration)
            throws ConnectException {
        ObjectGridManager objectGridManager = ObjectGridManagerFactory.getObjectGridManager();
        ClientClusterContext ccc = getClientClusterContext(objectGridManager, overRideObjectGridXml,
                catalogServerAddresses, clientSecurityConfiguration);
        return objectGridManager.getObjectGrid(ccc, objectGridName);
    }

    private ClientClusterContext getClientClusterContext(ObjectGridManager objectGridManager,
            URL overRideObjectGridXml, String catalogServerAddresses,
            ClientSecurityConfiguration clientSecurityConfiguration) throws ConnectException {
        if (catalogServerAddresses == null) {
            return objectGridManager.connect(clientSecurityConfiguration, overRideObjectGridXml);
        }
        return objectGridManager.connect(catalogServerAddresses, clientSecurityConfiguration, overRideObjectGridXml);
    }

    public static void main(String[] args) throws ObjectGridException {
        String config = "/com/test/wxs/distributed/preloaded/cache/loader/DistributedPreloadedCacheLoader-context.xml";
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        DistributedPreloadedCacheLoader distributedPreloadedCacheLoader = ctx.getBean(DistributedPreloadedCacheLoader.class);
        ctx.close();

        distributedPreloadedCacheLoader.load();
    }
}
