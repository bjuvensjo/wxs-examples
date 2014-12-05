package com.test.wxs.cache;

/**
 * CacheException is the class for unchecked exception and represents exceptions with a technical cause.
 */
public class CacheException extends RuntimeException {
    private static final long serialVersionUID = -9193690588804877691L;
    private final String context;
    private final int code;

    /**
     * Creates an CacheException with the specified context, code, message and cause.
     * 
     * @param context a module specific namespace string used to avoid naming conflicts between error codes of different
     *            modules
     * @param code a numerical code that is unique within the specified context
     * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is
     *            permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public CacheException(String context, int code, String message, Throwable cause) {
        super(message, cause);
        this.context = context;
        this.code = code;
    }

    /**
     * Creates an CacheException with the specified context, code, message. Should only be used if there exists no
     * cause.
     * 
     * @param context a module specific namespace string used to avoid naming conflicts between error codes of different
     *            modules
     * @param code a numerical code that is unique within the specified context
     * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     */
    public CacheException(String context, int code, String message) {
        this(context, code, message, null);
    }

    /**
     * Return the context.
     * 
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * Returns the code.
     * 
     * @return the code
     */
    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(getClass().getName()).append("[context: ").append(context).append(", code: ")
                .append(code).append(", message: ").append(getMessage()).append("]").toString();
    }
}
