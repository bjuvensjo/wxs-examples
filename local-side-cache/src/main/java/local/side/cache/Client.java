package local.side.cache;

import java.net.URL;

import com.ibm.websphere.objectgrid.ObjectGrid;
import com.ibm.websphere.objectgrid.ObjectGridException;
import com.ibm.websphere.objectgrid.ObjectGridManagerFactory;
import com.ibm.websphere.objectgrid.ObjectMap;
import com.ibm.websphere.objectgrid.Session;

public class Client {
    private ObjectGrid objectGrid;
    
    public Client() throws ObjectGridException {
        objectGrid = getObjectGrid();
    }
    
    public Object get(Object key) throws ObjectGridException {
        Session session = objectGrid.getSession();
        ObjectMap map = session.getMap("Map");
        
        session.begin();        
        Object value = map.get(key);          
        session.commit();
        
        return value;
    }    
    
    public void put(Object key, Object value) throws ObjectGridException {
        Session session = objectGrid.getSession();
        ObjectMap map = session.getMap("Map");
        
        session.begin();        
        map.put(key, value);          
        session.commit();
    }
    
    public Object remove(Object key) throws ObjectGridException {
        Session session = objectGrid.getSession();
        ObjectMap map = session.getMap("Map");
        
        session.begin();        
        Object value = map.remove(key);          
        session.commit();
        
        return value;
    }
    
    public void update(Object key, Object value) throws ObjectGridException {
        Session session = objectGrid.getSession();
        ObjectMap map = session.getMap("Map");
        
        session.begin();        
        map.update(key, value);          
        session.commit();
    }
    
    private ObjectGrid getObjectGrid() throws ObjectGridException {
        String gridName = "Grid";
        ObjectGrid objectGrid = ObjectGridManagerFactory.getObjectGridManager().getObjectGrid(gridName);
        if (objectGrid == null) {
            URL objectGridXML = getClass().getResource("/local/side/cache/objectgrid.xml");
            objectGrid = ObjectGridManagerFactory.getObjectGridManager().createObjectGrid(gridName, objectGridXML);         
        }
        return objectGrid;
    }
}
