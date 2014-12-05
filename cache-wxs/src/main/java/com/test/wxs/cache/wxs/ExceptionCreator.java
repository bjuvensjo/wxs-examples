package com.test.wxs.cache.wxs;

import com.test.wxs.cache.CacheException;

public class ExceptionCreator {
    /**
     * Error messages.
     */
    public enum Error {        
        INITIALIZE(100, "Can not initialize"),
        GET(101, "Error get from cache"),
        PUT(102, "Error put in cache"),
        REMOVE(103, "Error remove from cache");
        

        private String message;
        private final int code;

        Error(int code, String message) {
            this.message = message;
            this.code = code;
        }

        /**
         * Returns the error code.
         * 
         * @return the error code.
         */
        public int getCode() {
            return code;
        }

        /**
         * Returns the error message.
         * 
         * @return the error message.
         */
        public String getMessage() {
            return message;
        }
    }

    /**
     * Identifier used when handling exceptions in this module
     */
    public static final String CONTEXT = ExceptionCreator.class.getPackage().getName();

    /**
     * Creates an exception using the supplied {@link Error}
     * 
     * @param error the error.
     * @return a new {@link CacheException}.
     */
    public CacheException getCacheException(Error error) {
        return new CacheException(CONTEXT, error.getCode(), error.getMessage());
    }

    /**
     * Creates an exception using the supplied {@link Error}
     * 
     * @param error the error.
     * @return a new {@link CacheException}.
     */
    public CacheException getCacheException(Error error, String additionalInfo) {
        return new CacheException(CONTEXT, error.getCode(), error.getMessage() + " [" + additionalInfo + "]");
    }

    /**
     * Creates an exception using the supplied {@link Error} and cause
     * 
     * @param error the error.
     * @param cause the cause.
     * @return a new {@link CacheException}.
     */
    public CacheException getCacheException(Error error, Throwable cause) {
        return new CacheException(CONTEXT, error.getCode(), error.getMessage(), cause);
    }

    public CacheException getCacheException(Error error, String additionalInfo, Throwable cause) {
        return new CacheException(CONTEXT, error.getCode(), error.getMessage() + " [" + additionalInfo + "]", cause);
    }
}
