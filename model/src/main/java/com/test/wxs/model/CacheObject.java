package com.test.wxs.model;

import java.io.Serializable;

public class CacheObject implements Serializable {
    private static final long serialVersionUID = 1L;
    // Can be used for audit logging
    private String id;
    // TODO Use Externalizable?
    private Serializable value;

    public CacheObject(String id, Serializable value) {
        super();
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public Serializable getValue() {
        return value;
    }
}
