package com.netsocks.associatefan.resources.customers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.netsocks.associatefan.domain.customer.Customer;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

class CreateOrUpdateCustomerDto {
    @NotNull
    private String fullName;

    @NotNull
    private String email;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate birthday;

    @NotNull
    private String favoriteTeam;

    Customer mapToDomain() {
        return new Customer(getFullName(), getEmail(), getBirthday(), getFavoriteTeam());
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getFavoriteTeam() {
        return favoriteTeam;
    }
}
