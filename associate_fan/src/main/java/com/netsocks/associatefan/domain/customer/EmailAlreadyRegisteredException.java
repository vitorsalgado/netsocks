package com.netsocks.associatefan.domain.customer;

import com.netsocks.associatefan.domain.DomainException;
import com.netsocks.associatefan.domain.ErrorCodes;
import org.springframework.http.HttpStatus;

class EmailAlreadyRegisteredException extends DomainException {
    EmailAlreadyRegisteredException() {
        super(HttpStatus.BAD_REQUEST, "Email address already registered", ErrorCodes.EMAIL_ALREADY_REGISTERED);
    }
}
