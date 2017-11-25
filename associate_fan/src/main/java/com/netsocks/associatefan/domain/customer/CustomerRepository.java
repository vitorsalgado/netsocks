package com.netsocks.associatefan.domain.customer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    @Query(value = "{'_id': ?0 }")
    Optional<Customer> findById(String id);

    @Query(value = "{'email': ?0}")
    Optional<Customer> findByEmail(String email);
}
