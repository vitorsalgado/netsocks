package com.netsocks.campaign.domain;

import com.netsocks.campaign.libs.DateUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class MongoCampaignRepository implements CampaignRepository {
    private final MongoTemplate mongoTemplate;

    MongoCampaignRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Campaign> listByValidityRange(LocalDate start, LocalDate end) {
        return mongoTemplate.find(query(
                where("validityStart").gte(DateUtils.asDate(start))
                        .and("validityEnd").lte(DateUtils.asDate(end))), Campaign.class);
    }

    @Override
    public Optional<Campaign> findById(String id) {
        Campaign byId = mongoTemplate.findById(id, Campaign.class);
        return Optional.ofNullable(byId);
    }

    @Override
    public List<Campaign> search(SearchCriteria criteria) {
        Query query = query(where("validityEnd").gte(DateUtils.asDate(LocalDate.now())));

        if (!Objects.equals(criteria.getTeamId(), "")) {
            query.addCriteria(where("favoriteTeamId").is(criteria.getTeamId()));
        }

        if (!criteria.getIds().isEmpty()) {
            query.addCriteria(where("_id").in(criteria.getIds()));
        }

        return mongoTemplate.find(query, Campaign.class);
    }

    @Override
    public void delete(String id) {
        mongoTemplate.remove(query(where("_id").is(id)));
    }

    @Override
    public Campaign save(Campaign campaign) {
        mongoTemplate.save(campaign);
        return campaign;
    }
}
