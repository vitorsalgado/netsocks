package com.netsocks.campaign.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

import static com.netsocks.campaign.libs.Preconditions.checkNotNull;
import static com.netsocks.campaign.libs.Preconditions.checkNotNullOrEmpty;

public class Campaign {
    @Id
    private String id;

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

    @NotNull
    private boolean changed;

    public Campaign(final String name, final String favoriteTeamId, final LocalDate validityStart, final LocalDate validityEnd) {
        this.id = UUID.randomUUID().toString();
        this.name = checkNotNullOrEmpty(name);
        this.favoriteTeamId = checkNotNullOrEmpty(favoriteTeamId);
        this.validityStart = checkNotNull(validityStart);
        this.validityEnd = checkNotNull(validityEnd);
        this.changed = false;
    }

    public Campaign(String id, String name, String favoriteTeamId, LocalDate validityStart, LocalDate validityEnd) {
        this(name, favoriteTeamId, validityStart, validityEnd);
        this.id = id;
    }

    Campaign() {

    }

    void incrementValidityInOneDay() {
        this.validityEnd = this.validityEnd.plusDays(1);
    }

    void markAsChanged() {
        this.changed = true;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Campaign campaign = (Campaign) o;

        return id.equals(campaign.id);
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += (this.id == null) ? 0 : this.id.hashCode() * 31;
        return hashCode;
    }

    @Override
    public String toString() {
        return String.format("Campaign - %s - %s - %s - %s - %s",
                getId(), getName(), getFavoriteTeamId(), getValidityStart(), getValidityEnd());
    }
}
