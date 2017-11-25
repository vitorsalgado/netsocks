package com.netsocks.campaign.resources.campaign;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

class SearchDto {
    @Nullable
    private String teamId;

    @Nullable
    private List<String> ids;

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
