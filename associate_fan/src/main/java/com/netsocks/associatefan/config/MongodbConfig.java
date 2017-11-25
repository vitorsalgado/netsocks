package com.netsocks.associatefan.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MongodbConfig {
    @Value("${MONGO_URI}")
    private String uri;

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(new MongoClientURI(uri));
    }
}
