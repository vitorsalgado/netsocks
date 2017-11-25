package com.netsocks.associatefan.domain.campaign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CampaignApiClientFallback implements CampaignRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignApiClientFallback.class);

    @Override
    public List<Campaign> findByTeamId(String teamId) {
        LOGGER.error("failed to call \"GET /campaigns?teamId=?\"", teamId);
        return new ArrayList<>();
    }

    @Override
    public List<Campaign> findByIds(String ids) {
        LOGGER.error("failed to call \"GET /campaigns?ids=?\"", ids);
        return new ArrayList<>();
    }
}
