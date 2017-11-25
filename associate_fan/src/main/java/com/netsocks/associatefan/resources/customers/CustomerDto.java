package com.netsocks.associatefan.resources.customers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.netsocks.associatefan.domain.customer.Customer;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDto {
    private String id;
    private String fullName;
    private String email;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate birthday;

    private String favoriteTeam;

    private CustomerDto(String id, String fullName, String email, LocalDate birthday, String favoriteTeam) {
        this.fullName = fullName;
        this.email = email;
        this.birthday = birthday;
        this.favoriteTeam = favoriteTeam;
    }

    static CustomerDto mapToDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getBirthday(),
                customer.getFavoriteTeam());
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
