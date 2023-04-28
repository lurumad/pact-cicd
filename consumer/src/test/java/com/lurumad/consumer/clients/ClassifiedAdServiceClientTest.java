package com.lurumad.consumer.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockResolver.Wiremock;
import ru.lanwen.wiremock.ext.WiremockUriResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver.WiremockUri;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@ExtendWith({ WiremockResolver.class, WiremockUriResolver.class })
public class ClassifiedAdServiceClientTest {

    @Autowired
    private ClassifiedAdServiceClient classifiedAdServiceClient;

    @Test
    void fetchClassifiedAds(@Wiremock WireMockServer server, @WiremockUri String uri) {
        classifiedAdServiceClient.setBaseUrl(uri);

        server.stubFor(
            get(urlPathEqualTo("/ads"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withBody("{\n" +
                        "\"classifiedAds\": [\n" +
                        "        {\n" +
                        "            \"id\": \"1\",\n" +
                        "            \"title\": \"Audi A4\",\n" +
                        "            \"description\": \"Audi A4 1.8T 2002\",\n" +
                        "            \"price\": 1000\n" +
                        "        }\n" +
                        "     ]\n" +
                        "\n}")
                    .withHeader("Content-Type", "application/json"))
        );

        var response = classifiedAdServiceClient.fetchClassifiedAds();
        assertThat(response.getClassifiedAds(), hasSize(1));
        assertThat(response.getClassifiedAds().get(0).id(), is("1"));
    }

    @Test
    void fetchClassifiedAdsById(@Wiremock WireMockServer server, @WiremockUri String uri) {
        classifiedAdServiceClient.setBaseUrl(uri);

        server.stubFor(
            get(urlPathEqualTo("/ads/1"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withBody("{\n" +
                        "    \"id\": \"1\",\n" +
                        "    \"title\": \"Audi A4\",\n" +
                        "    \"description\": \"Audi A4 1.8T 2002\",\n" +
                        "    \"price\": 1000\n" +
                        "}")
                    .withHeader("Content-Type", "application/json"))
        );

        var response = classifiedAdServiceClient.getClassifiedAdById("1");
        assertThat(response.id(), is("1"));
    }
}
