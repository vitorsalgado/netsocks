package com.netsocks.campaign.domain.shared;

import org.springframework.http.HttpStatus;

public class DomainException extends RuntimeException {
    private final HttpStatus httpStatus;

    public DomainException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
