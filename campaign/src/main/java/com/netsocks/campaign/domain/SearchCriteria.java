package com.netsocks.campaign.domain;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteria {
    private String teamId;
    private List<String> ids;

    public SearchCriteria(String teamId, List<String> ids) {
        this.teamId = teamId;
        this.ids = ids;
    }

    String getTeamId() {
        if (teamId == null) {
            return "";
        }

        return teamId;
    }

    List<String> getIds() {
        if (ids == null) {
            return new ArrayList<>();
        }

        return ids;
    }
}
