package com.test.wxs.cache.wxs;

import java.io.Serializable;

import com.test.wxs.cache.CacheKey;

public class LocalInlineCacheTestKey implements CacheKey, Serializable {
    private static final long serialVersionUID = -7390846285273862748L;
    private String foo;
    private String bar;

    public LocalInlineCacheTestKey(String foo, String bar) {
        super();
        this.foo = foo;
        this.bar = bar;
    }

    public String getFoo() {
        return foo;
    }

    public String getBar() {
        return bar;
    }

    @Override
    public Serializable getKey() {
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bar == null) ? 0 : bar.hashCode());
        result = prime * result + ((foo == null) ? 0 : foo.hashCode());
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
        LocalInlineCacheTestKey other = (LocalInlineCacheTestKey) obj;
        if (bar == null) {
            if (other.bar != null)
                return false;
        } else if (!bar.equals(other.bar))
            return false;
        if (foo == null) {
            if (other.foo != null)
                return false;
        } else if (!foo.equals(other.foo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LocalInlineCacheTestKey [foo=" + foo + ", bar=" + bar + "]";
    }
}
