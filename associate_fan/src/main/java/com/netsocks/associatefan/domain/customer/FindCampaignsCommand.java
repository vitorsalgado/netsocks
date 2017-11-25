package com.netsocks.associatefan.domain.customer;

public class FindCampaignsCommand {
    private final String correlationId;
    private final String email;

    public FindCampaignsCommand(final String correlationId, final String email) {
        this.correlationId = correlationId;
        this.email = email;
    }

    String getCorrelationId() {
        return correlationId;
    }

    String getEmail() {
        return email;
    }
}
