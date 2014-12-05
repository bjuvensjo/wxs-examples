package com.test.wxs.cache.wxs;

import com.ibm.websphere.objectgrid.ObjectGrid;

/**
 * ObjectGridFactory is used by WXSCache to get a reference to an ObjectGrid.
 */
public interface ObjectGridFactory {

    /**
     * Returns an ObjectGrid.
     * 
     * @return an ObjectGrid
     */
    ObjectGrid getObjectGrid();
}
