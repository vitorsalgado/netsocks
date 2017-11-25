package com.netsocks.associatefan.libs;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class CorrelationIdHandler extends HandlerInterceptorAdapter {
    static final String HEADER_CORRELATION_ID = "X-Correlation-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String correlationId = request.getHeader(HEADER_CORRELATION_ID);

        if (response.containsHeader(HEADER_CORRELATION_ID)) {
            return true;
        }

        if ((correlationId == null || correlationId.isEmpty())) {
            correlationId = UUID.randomUUID().toString();
        }

        response.addHeader(HEADER_CORRELATION_ID, correlationId);

        return true;
    }
}
