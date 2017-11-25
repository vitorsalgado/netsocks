package com.netsocks.associatefan.libs;

public class ErrorResponse {
    private final String message;
    private final String code;
    private final String traceId;

    ErrorResponse(String message, String code, String traceId) {
        this.message = message;
        this.code = code;
        this.traceId = traceId;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getTraceId() {
        return traceId;
    }
}
