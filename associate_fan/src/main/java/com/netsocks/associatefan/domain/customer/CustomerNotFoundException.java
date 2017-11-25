package com.netsocks.associatefan.domain.customer;

import com.netsocks.associatefan.domain.DomainException;
import org.springframework.http.HttpStatus;

class CustomerNotFoundException extends DomainException {
    CustomerNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Customer not found");
    }
}
