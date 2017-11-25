package com.netsocks.campaign.libs.errors;

import com.netsocks.campaign.domain.shared.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

import static com.netsocks.campaign.libs.interceptors.CorrelationIdHandler.HEADER_CORRELATION_ID;

@ControllerAdvice
class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DomainException.class)
    ResponseEntity<ErrorResponse> handleDomainException(HttpServletResponse response, DomainException ex) {
        LOGGER.error(ex.getMessage(), ex);

        String correlationId = response.getHeader(HEADER_CORRELATION_ID);
        ErrorResponse error = new ErrorResponse(ex.getMessage(), correlationId);

        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> handleUnknown(HttpServletResponse response, Exception ex) {
        LOGGER.error(ex.getMessage(), ex);

        String correlationId = response.getHeader(HEADER_CORRELATION_ID);
        ErrorResponse error = new ErrorResponse(ex.getMessage(), correlationId);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
