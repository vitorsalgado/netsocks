package com.netsocks.campaign.domain;

import com.netsocks.campaign.domain.notifier.UpdateNotifier;
import com.netsocks.campaign.domain.shared.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampaignService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignService.class);

    private final CampaignRepository campaignRepository;
    private final UpdateNotifier updateNotifier;

    CampaignService(CampaignRepository campaignRepository, UpdateNotifier updateNotifier) {
        this.campaignRepository = campaignRepository;
        this.updateNotifier = updateNotifier;
    }

    public Optional<Campaign> getById(final String id) {
        return campaignRepository.findById(id);
    }

    public List<Campaign> search(SearchCriteria searchCriteria) {
        return campaignRepository.search(searchCriteria);
    }

    public void delete(final String id) {
        final Optional<Campaign> optional = campaignRepository.findById(id);

        if (optional.isPresent()) {
            campaignRepository.delete(id);
            return;
        }

        throw new DomainException(HttpStatus.NOT_FOUND, String.format("No campaign found with id %s", id));
    }

    public Campaign addOrUpdate(final Campaign newCampaign) {
        final LocalDate start = newCampaign.getValidityStart();
        final LocalDate end = newCampaign.getValidityEnd();
        final List<Campaign> validCampaigns = campaignRepository.listByValidityRange(start, end);

        if (validCampaigns.isEmpty()) {
            campaignRepository.save(newCampaign);
            return newCampaign;
        }

        final List<Campaign> campaignCheckList = new ArrayList<>(validCampaigns);
        campaignCheckList.add(newCampaign);

        validCampaigns
                .stream()
                .sorted((o1, o2) -> o1.getValidityEnd().compareTo(o2.getValidityEnd()))
                .map(campaign -> incrementValidity(newCampaign, campaignCheckList, campaign))
                .filter(Campaign::isChanged)
                .collect(Collectors.toList())
                .parallelStream()
                .peek(campaign -> LOGGER.info(String.format("campaign changed %s", campaign.getName()), campaign.getValidityEnd()))
                .map(campaignRepository::save)
                .forEach(updateNotifier::notify);

        campaignRepository.save(newCampaign);

        return newCampaign;
    }

    private Campaign incrementValidity(final Campaign newCampaign, final List<Campaign> campaignsController, final Campaign campaign) {
        if (campaign.equals(newCampaign)) {
            return campaign;
        }

        campaign.incrementValidityInOneDay();
        campaignsController.remove(campaign);

        while (campaignsController.stream()
                .anyMatch(c -> c.getValidityEnd().equals(campaign.getValidityEnd()))) {
            campaign.incrementValidityInOneDay();
            campaign.markAsChanged();
        }

        campaignsController.add(campaign);

        return campaign;
    }
}
