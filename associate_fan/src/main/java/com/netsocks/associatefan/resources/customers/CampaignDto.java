package com.netsocks.associatefan.resources.customers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.netsocks.associatefan.domain.campaign.Campaign;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CampaignDto {
    private String id;
    private String name;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate validityStart;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate validityEnd;

    private CampaignDto(String id, String name, LocalDate validityStart, LocalDate validityEnd) {
        this.id = id;
        this.name = name;
        this.validityStart = validityStart;
        this.validityEnd = validityEnd;
    }

    static CampaignDto mapToDto(Campaign campaign) {
        return new CampaignDto(campaign.getId(), campaign.getName(), campaign.getValidityStart(), campaign.getValidityEnd());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getValidityStart() {
        return validityStart;
    }

    public LocalDate getValidityEnd() {
        return validityEnd;
    }
}
