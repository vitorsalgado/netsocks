package com.netsocks.associatefan.domain.campaign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@FeignClient(name = "campaign", url = "${CAMPAIGN_API_URL}", fallback = CampaignApiClientFallback.class)
public interface CampaignRepository {
    @RequestMapping(method = RequestMethod.GET, path = "/campaigns", consumes = APPLICATION_JSON_VALUE)
    List<Campaign> findByTeamId(@RequestParam("teamId") String teamId);

    @RequestMapping(method = RequestMethod.GET, path = "/campaigns", consumes = APPLICATION_JSON_VALUE)
    List<Campaign> findByIds(@RequestParam("ids") String ids);
}
