package com.netsocks.associatefan.domain.customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(String id);

    Optional<Customer> findByEmail(String email);

    Customer save(Customer customer);
}
