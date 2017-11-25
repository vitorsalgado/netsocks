package com.netsocks.associatefan.config;

import feign.Feign;
import feign.hystrix.HystrixFeign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FeignConfig {
    @Bean
    public Feign.Builder feignBuilder() {
        return HystrixFeign.builder();
    }
}
