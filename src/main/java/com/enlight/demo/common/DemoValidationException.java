package com.enlight.demo.common;

/**
 * Thrown when request data does not pass basic workshop-friendly validation.
 */
public class DemoValidationException extends RuntimeException {
    public DemoValidationException(String message) {
        super(message);
    }
}
