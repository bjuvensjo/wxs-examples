package com.test.wxs.cache;


/**
 * The Config class represents the configuration of a element stored within a {@link Cache}. It is intended to be used
 * by implementations of the {@link Cache} interface.
 */
public class Config {
    private boolean replication;
    private boolean on;
    private Scope scope;
    private int timeToLive;
    private String context;
    
    /**
     * The Scope defines the supported scopes.
     */
    public enum Scope {
        APPLICATION,
        PROFILE,
        REQUEST;
    }

    /**
     * Creates a Config.
     */
    public Config() {
        // Default values;
        this.on = this.replication = true;
        this.scope = Scope.APPLICATION;
        this.timeToLive = 900;
    }

    /**
     * Returns the time in seconds that the cache entry should remain in the cache.
     * 
     * @return the time in seconds that the cache entry should remain in the cache
     */
    public int getTimeToLive() {
        return timeToLive;
    }

    /**
     * Sets the time in seconds that the cache entry should remain in the cache.
     * 
     * @param timeToLive the time in seconds that the cache entry should remain in the cache
     */
    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    /**
     * Returns the on.
     * 
     * @return the on
     */
    public boolean isOn() {
        return on;
    }

    /**
     * Sets the on.
     * 
     * @param on the on
     */
    public void setOn(boolean on) {
        this.on = on;
    }

    /**
     * Returns the replication.
     * 
     * @return the replication
     */
    public boolean isReplication() {
        return replication;
    }

    /**
     * Sets the replication.
     * 
     * @param replicable the replication
     */
    public void setReplication(boolean replicate) {
        this.replication = replicate;
    }

    /**
     * Returns the scope.
     * 
     * @return the scope
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Sets the scope.
     * 
     * @param scope the scope
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Sets the context.
     * 
     * @param context the context
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * Returns the context.
     * 
     * @return the context
     */
    public String getContext() {
        return context;
    }
}
