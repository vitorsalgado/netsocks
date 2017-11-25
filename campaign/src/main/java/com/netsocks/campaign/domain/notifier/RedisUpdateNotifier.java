package com.netsocks.campaign.domain.notifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netsocks.campaign.domain.Campaign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.netsocks.campaign.libs.Preconditions.checkNotNull;

/**
 * This is a simple to notify subscribe systems about Campaign changes.
 * It uses Redis for a Pub/Sub schema
 */
@Service
class RedisUpdateNotifier implements UpdateNotifier {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUpdateNotifier.class);
    private static final String CHANNEL = "campaign-update";
    private final RedisTemplate<String, Object> redisTemplate;

    RedisUpdateNotifier(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @HystrixCommand(fallbackMethod = "notifyFallback")
    @Override
    public void notify(Campaign campaign) {
        checkNotNull(campaign);

        ObjectMapper mapper = new ObjectMapper();

        try {
            redisTemplate.convertAndSend(CHANNEL, mapper.writeValueAsString(campaign));
        } catch (JsonProcessingException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    void notifyFallback(Campaign campaign) {
        LOGGER.error(String.format("Failed to publish campaign %s update notification to Redis", campaign.getName()), campaign.toString());
    }
}
