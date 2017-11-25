package com.netsocks.campaign.config;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DateFormat {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.dateFormat(new ISO8601DateFormat());
    }
}
