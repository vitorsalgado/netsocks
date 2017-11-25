package com.netsocks.associatefan.domain.customer;

import java.time.LocalDate;

public class UpdateCommand {
    private final String correlationId;
    private final String id;
    private final String fullName;
    private final String email;
    private final LocalDate birthday;
    private final String favoriteTeam;

    public UpdateCommand(
            final String correlationId, final String id, final String fullName,
            final String email, final LocalDate birthday, final String favoriteTeam) {
        this.correlationId = correlationId;
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.birthday = birthday;
        this.favoriteTeam = favoriteTeam;
    }

    String getCorrelationId() {
        return correlationId;
    }

    String getId() {
        return id;
    }

    String getFullName() {
        return fullName;
    }

    String getEmail() {
        return email;
    }

    LocalDate getBirthday() {
        return birthday;
    }

    String getFavoriteTeam() {
        return favoriteTeam;
    }
}
