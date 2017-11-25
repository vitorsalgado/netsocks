package com.netsocks.associatefan.domain.customer;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class MongoCustomerRepository implements CustomerRepository {
    private final MongoTemplate mongoTemplate;

    MongoCustomerRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Customer> findById(String id) {
        Customer customer = mongoTemplate.findById(id, Customer.class);
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        Customer customer = mongoTemplate.findOne(query(where("email").is(email)), Customer.class);
        return Optional.ofNullable(customer);
    }

    @Override
    public Customer save(Customer customer) {
        mongoTemplate.save(customer);
        return customer;
    }
}
