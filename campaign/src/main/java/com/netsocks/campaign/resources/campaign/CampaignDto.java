package com.netsocks.campaign.resources.campaign;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.netsocks.campaign.domain.Campaign;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

class CampaignDto {
    private String id;
    private String name;
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

    private boolean changed;

    private CampaignDto(String id, String name, String favoriteTeamId, LocalDate validityStart, LocalDate validityEnd, boolean changed) {
        this.id = id;
        this.name = name;
        this.favoriteTeamId = favoriteTeamId;
        this.validityStart = validityStart;
        this.validityEnd = validityEnd;
        this.changed = changed;
    }

    static CampaignDto mapDomain(Campaign campaign) {
        return new CampaignDto(
                campaign.getId(),
                campaign.getName(),
                campaign.getFavoriteTeamId(),
                campaign.getValidityStart(),
                campaign.getValidityEnd(),
                campaign.isChanged());
    }

    public String getId() {
        return id;
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

    public boolean isChanged() {
        return changed;
    }
}
