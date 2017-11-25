package com.netsocks.campaign.resources.campaign;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.netsocks.campaign.domain.Campaign;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

class CreateOrUpdateCampaignDto {
    @NotNull
    private String name;

    @NotNull
    private String favoriteTeamId;

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

    Campaign mapToDomain() {
        return new Campaign(getName(), getFavoriteTeamId(), getValidityStart(), getValidityEnd());
    }

    public String getName() {
        return name;
    }

    public String getFavoriteTeamId() {
        return favoriteTeamId;
    }

    public LocalDate getValidityStart() {
        return validityStart;
    }

    public LocalDate getValidityEnd() {
        return validityEnd;
    }
}
