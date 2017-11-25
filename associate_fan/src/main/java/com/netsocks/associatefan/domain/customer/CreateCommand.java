package com.netsocks.associatefan.domain.customer;

public class CreateCommand {
    private final String correlationId;
    private final Customer customer;

    public CreateCommand(final String correlationId, final Customer customer) {
        this.correlationId = correlationId;
        this.customer = customer;
    }

    String getCorrelationId() {
        return correlationId;
    }

    Customer getCustomer() {
        return customer;
    }
}
