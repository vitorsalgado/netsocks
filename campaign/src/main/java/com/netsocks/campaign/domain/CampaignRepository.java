package com.netsocks.campaign.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CampaignRepository {
    List<Campaign> listByValidityRange(LocalDate start, LocalDate end);

    Optional<Campaign> findById(String id);

    List<Campaign> search(SearchCriteria criteria);

    void delete(String id);

    Campaign save(Campaign campaign);
}
