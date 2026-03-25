package com.enlight.demo.common;

/**
 * Thrown when a requested resource does not exist.
 */
public class DemoNotFoundException extends RuntimeException {
    public DemoNotFoundException(String message) {
        super(message);
    }
}
