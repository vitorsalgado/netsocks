package com.netsocks.campaign.libs.errors;

public class ErrorResponse {
    private final String message;
    private final String traceId;

    ErrorResponse(final String message, final String traceId) {
        this.message = message;
        this.traceId = traceId;
    }

    public String getMessage() {
        return message;
    }

    public String getTraceId() {
        return traceId;
    }
}
