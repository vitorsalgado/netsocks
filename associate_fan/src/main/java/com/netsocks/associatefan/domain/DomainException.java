package com.netsocks.associatefan.domain;

import org.springframework.http.HttpStatus;

public class DomainException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String code;

    public DomainException(final HttpStatus httpStatus, final String message) {
        this(httpStatus, message, ErrorCodes.GENERAL_ERROR);
    }

    public DomainException(final HttpStatus httpStatus, final String message, String code) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }
}
