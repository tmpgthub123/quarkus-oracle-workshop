package com.enlight.demo.common;

/**
 * Used when a database or JDBC call fails.
 */
public class DemoDatabaseException extends RuntimeException {
    public DemoDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
