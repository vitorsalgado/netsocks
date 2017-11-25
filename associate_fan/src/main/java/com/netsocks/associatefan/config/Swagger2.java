package com.netsocks.associatefan.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
class Swagger2 {
    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .consumes(Collections.singleton("application/json"))
                .produces(Collections.singleton("application/json"))
                .tags(new Tag("associate", "Associate Fan"), new Tag("health", "Health Check"))
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .protocols(new HashSet<>(Arrays.asList("http", "https")))
                .select()
                .apis(input -> !RequestHandlerSelectors.basePackage("org.springframework.boot").apply(input))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("NetSocks Associate Fan API")
                .description("Associate Fan Management API")
                .license("MIT License")
                .licenseUrl("https://github.com/vitorsalgado/netsocks/blob/master/LICENSE")
                .version("1.0")
                .build();
    }
}
