package com.netsocks.campaign.resources.campaign;

import com.netsocks.campaign.domain.Campaign;
import com.netsocks.campaign.domain.CampaignRepository;
import com.netsocks.campaign.domain.notifier.UpdateNotifier;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CampaignControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignRepository campaignRepository;

    @MockBean
    private UpdateNotifier updateNotifier;

    @Test
    public void itShouldIncrementTheValidityOfSavedCampaignsByOneUntilNoOneHasTheSameIncludingTheOneBeignAdded() throws Exception {
        LocalDate start = LocalDate.of(2017, Month.OCTOBER, 1);
        LocalDate end = LocalDate.of(2017, Month.OCTOBER, 3);

        List<Campaign> campaigns = new ArrayList<>(Arrays.asList(
                new Campaign("one", "Campaign 1", "SEP",
                        LocalDate.of(2017, 10, 1), LocalDate.of(2017, 10, 3)),
                new Campaign("two", "Campaign 2", "SEP",
                        LocalDate.of(2017, 10, 1), LocalDate.of(2017, 10, 2))
        ));

        Campaign changedCampaignOne = new Campaign("one", "Campaign 1", "SEP", LocalDate.of(2017, 10, 1), LocalDate.of(2017, 10, 5));
        Campaign changedCampaignTwo = new Campaign("two", "Campaign 1", "SEP", LocalDate.of(2017, 10, 1), LocalDate.of(2017, 10, 4));

        when(campaignRepository.listByValidityRange(start, end)).thenReturn(campaigns);

        when(campaignRepository.save(changedCampaignOne)).thenReturn(changedCampaignOne);
        when(campaignRepository.save(changedCampaignTwo)).thenReturn(changedCampaignTwo);

        doNothing().when(updateNotifier).notify(any(Campaign.class));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/campaigns")
                        .content(new JSONObject("{\n" +
                                "  \"favoriteTeamId\": \"SEP\",\n" +
                                "  \"name\": \"Campaign 3\",\n" +
                                "  \"validityEnd\": \"2017-10-03\",\n" +
                                "  \"validityStart\": \"2017-10-01\"\n" +
                                "}").toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(campaignRepository, times(1)).save(changedCampaignOne);
        verify(campaignRepository, times(1)).save(changedCampaignTwo);
        verify(campaignRepository, times(3)).save(any(Campaign.class));

        verify(updateNotifier, times(1)).notify(changedCampaignOne);
        verify(updateNotifier, times(1)).notify(changedCampaignTwo);
        verify(updateNotifier, times(2)).notify(any(Campaign.class));
    }
}
