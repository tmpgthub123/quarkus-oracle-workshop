package com.enlight.demo.common;

/**
 * Simple error payload used by the global exception mapper.
 */
public class ApiErrorResponse {
    private final String code;
    private final String error;
    private final String details;

    public ApiErrorResponse(String code, String error, String details) {
        this.code = code;
        this.error = error;
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getDetails() {
        return details;
    }
}
