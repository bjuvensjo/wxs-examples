package com.test.wxs.cache.wxs.key;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * ObjectCacheKey adds context and scope to keys.
 */
public class ObjectCacheKey implements Serializable {
    private static final long serialVersionUID = 7384130214955017988L;
    private String context;
    // The CacheScopeType enumeration should not be used, to avoid the need for that class in the cfa-wxs classpath.
    private String scope;
    private Serializable key;

    /**
     * Creates an ObjectCacheKey.
     * 
     * @param context
     *            the context
     * @param scope
     *            the scope
     * @param key
     *            the key
     */
    public ObjectCacheKey(String context, String scope, Serializable key) {
        super();
        this.context = context;
        this.scope = scope;
        this.key = key;
    }

    public String getContext() {
        return context;
    }

    public String getScope() {
        return scope;
    }

    public Serializable getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((context == null) ? 0 : context.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ObjectCacheKey other = (ObjectCacheKey) obj;
        if (context == null) {
            if (other.context != null)
                return false;
        } else if (!context.equals(other.context))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (scope == null) {
            if (other.scope != null)
                return false;
        } else if (!scope.equals(other.scope))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
