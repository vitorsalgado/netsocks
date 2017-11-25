package com.netsocks.campaign.domain.notifier;

import com.netsocks.campaign.domain.Campaign;

public interface UpdateNotifier {
    void notify(Campaign campaign);
}
