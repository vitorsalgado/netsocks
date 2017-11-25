package com.netsocks.associatefan.resources;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.mongodb.MongoClient;
import com.netsocks.associatefan.domain.customer.Customer;
import com.netsocks.associatefan.domain.customer.CustomerRepository;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "feign.hystrix.enabled=false"})
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    @Test
    public void isShouldRegisterUserAndAddHisTeamCampaignsToHisRecord() throws Exception {
        String email = "teste@email.com.br";

        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(
                new Customer("Teste da Silva", email, LocalDate.of(1988, 5, 9), "SEP"));

        givenThat(get(urlPathEqualTo("/campaigns"))
                .withQueryParam("teamId", equalTo("SEP"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[\n" +
                                "    {\n" +
                                "        \"id\": \"be743a05-a59a-4dd5-9fd1-0859a9558561\",\n" +
                                "        \"name\": \"Campaign 1\",\n" +
                                "        \"favoriteTeamId\": \"SEP\",\n" +
                                "        \"validityStart\": \"2017-12-01\",\n" +
                                "        \"validityEnd\": \"2017-12-05\",\n" +
                                "        \"changed\": true\n" +
                                "    },\n" +
                                "    {\n" +
                                "        \"id\": \"04632a1d-54ee-4e54-a0b5-01fba0e214c5\",\n" +
                                "        \"name\": \"Campaign 2\",\n" +
                                "        \"favoriteTeamId\": \"SEP\",\n" +
                                "        \"validityStart\": \"2017-12-01\",\n" +
                                "        \"validityEnd\": \"2017-12-04\",\n" +
                                "        \"changed\": true\n" +
                                "    }\n" +
                                "]")));

        String json = new JSONObject("{\n" +
                "  \"favoriteTeam\": \"SEP\",\n" +
                "  \"fullName\": \"Teste da Silva\",\n" +
                "  \"birthday\": \"1988-05-09\",\n" +
                "  \"email\": \"teste@email.com.br\"\n" +
                "}").toString();

        MvcResult mvcResult = mockMvc
                .perform(
                        post("/customers")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(instanceOf(ResponseEntity.class)))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated());

        verify(customerRepository, times(1)).findByEmail(email);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}
