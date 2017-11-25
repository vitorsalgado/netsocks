package com.netsocks.associatefan.domain.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.netsocks.associatefan.domain.campaign.Campaign;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Document
public class Customer {
    @Id
    private String id;

    @NotNull
    private String fullName;

    @NotNull
    @Indexed
    private String email;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate birthday;

    @NotNull
    private String favoriteTeam;

    @NotNull
    @JsonIgnore
    private List<String> campaigns;

    public Customer(final String fullName, final String email, final LocalDate birthday, final String favoriteTeam) {
        this.id = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.email = email;
        this.birthday = birthday;
        this.favoriteTeam = favoriteTeam;
        this.campaigns = new ArrayList<>();
    }

    Customer() {

    }

    public String getId() {
        return id;
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

    public List<String> getCampaigns() {
        if (campaigns == null) {
            return new ArrayList<>();
        }

        return campaigns;
    }

    void setCampaigns(final List<Campaign> campaigns) {
        this.campaigns = campaigns.stream().map(Campaign::getId).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return getId().equals(customer.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
