package com.test.wxs.service;

import java.util.List;

public interface LoadService {

    /**
     * Returns the values that corresponds to the specified keys. The values are retrieved from backend. If a value is
     * not possible to retrieve, Loader.KEY_NOT_FOUND is returned for that specific value.
     * 
     * @param keys
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Result getValues(List keys);
}
