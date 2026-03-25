package com.enlight.demo.common;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Beginner-friendly global mapper that turns common exceptions into JSON.
 */
@Provider
public class DemoExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof DemoValidationException validation) {
            return jsonError(Response.Status.BAD_REQUEST, "VALIDATION_ERROR", validation.getMessage());
        }

        if (exception instanceof DemoNotFoundException notFound) {
            return jsonError(Response.Status.NOT_FOUND, "NOT_FOUND", notFound.getMessage());
        }

        if (exception instanceof DemoDatabaseException database) {
            return jsonError(Response.Status.SERVICE_UNAVAILABLE,
                    "DATABASE_ERROR",
                    database.getMessage());
        }

        if (exception instanceof IllegalArgumentException badInput) {
            return jsonError(Response.Status.BAD_REQUEST, "INVALID_INPUT", badInput.getMessage());
        }

        return jsonError(
                Response.Status.INTERNAL_SERVER_ERROR,
                "UNEXPECTED_ERROR",
                "An unexpected error happened. Check application logs.");
    }

    private Response jsonError(Response.Status status, String code, String message) {
        return Response.status(status)
                .entity(new ApiErrorResponse(code, message, status.getReasonPhrase()))
                .build();
    }
}
