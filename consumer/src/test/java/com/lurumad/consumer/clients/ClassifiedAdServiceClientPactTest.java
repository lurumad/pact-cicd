package com.lurumad.consumer.clients;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ClassifiedAdService", pactVersion = PactSpecVersion.V3)
public class ClassifiedAdServiceClientPactTest {
    @Autowired
    private ClassifiedAdServiceClient classifiedAdServiceClient;

    @Pact(consumer = "MvcConsumer")
    public RequestResponsePact allClassifiedAds(PactDslWithProvider builder) {
        return builder
            .given("classified ads exists")
                .uponReceiving("get all classified ads")
                .path("/ads")
            .willRespondWith()
                .status(HttpStatus.OK.value())
                .body(
                    new PactDslJsonBody()
                        .minArrayLike("classifiedAds", 1, 2)
                            .stringType("id", "c4bb3d0e-cf29-4b5c-b16f-777637a61e0f")
                            .stringType("title", "Title 1")
                            .stringType("text", "Text 1")
                            .closeObject()
                        .closeArray()
                )
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "allClassifiedAds")
    void testAllClassifiedAds(MockServer mockServer) {
        classifiedAdServiceClient.setBaseUrl(mockServer.getUrl());
        var classifiedAds = classifiedAdServiceClient.fetchClassifiedAds().getClassifiedAds();
        assertThat(classifiedAds, hasSize(2));
        assertThat(classifiedAds.get(0).id(), is("c4bb3d0e-cf29-4b5c-b16f-777637a61e0f"));
        assertThat(classifiedAds.get(0).title(), is("Title 1"));
        assertThat(classifiedAds.get(0).text(), is("Text 1"));
    }

    @Pact(consumer = "MvcConsumer")
    public RequestResponsePact classsifiedAdById(PactDslWithProvider builder) {
        return builder
                .given("a classified ad with ID c4bb3d0e-cf29-4b5c-b16f-777637a61e0f exists", "id", "c4bb3d0e-cf29-4b5c-b16f-777637a61e0f")
                .uponReceiving("get classified ad with ID c4bb3d0e-cf29-4b5c-b16f-777637a61e0f")
                .path("/ads/c4bb3d0e-cf29-4b5c-b16f-777637a61e0f")
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .body(
                        new PactDslJsonBody()
                            .stringType("id", "c4bb3d0e-cf29-4b5c-b16f-777637a61e0f")
                            .stringType("title", "Title 1")
                            .stringType("text", "Text 1")
                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "classsifiedAdById")
    void classifiedAdById(MockServer mockServer) {
        classifiedAdServiceClient.setBaseUrl(mockServer.getUrl());
        var classifiedAd = classifiedAdServiceClient.getClassifiedAdById("c4bb3d0e-cf29-4b5c-b16f-777637a61e0f");
        assertThat(classifiedAd.id(), is("c4bb3d0e-cf29-4b5c-b16f-777637a61e0f"));
        assertThat(classifiedAd.title(), is("Title 1"));
        assertThat(classifiedAd.text(), is("Text 1"));
    }

    @Pact(consumer = "MvcConsumer")
    public RequestResponsePact noClassifiedAds(PactDslWithProvider builder) {
        return builder
            .given("no classified ads exists")
            .uponReceiving("get all classified ads")
            .path("/ads")
            .willRespondWith()
            .status(HttpStatus.OK.value())
            .body(
                new PactDslJsonBody().array("classifiedAds")
            )
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "noClassifiedAds")
    void testNoClassifiedAds(MockServer mockServer) {
        classifiedAdServiceClient.setBaseUrl(mockServer.getUrl());
        var classifiedAds = classifiedAdServiceClient.fetchClassifiedAds().getClassifiedAds();
        assertThat(classifiedAds, hasSize(0));
    }

    @Pact(consumer = "MvcConsumer")
    public RequestResponsePact noClassifiedAdById(PactDslWithProvider builder) {
        return builder
            .given("no classified ad with ID c4bb3d0e-cf29-4b5c-b16f-777637a61e0f exists", "id", "c4bb3d0e-cf29-4b5c-b16f-777637a61e0f")
            .uponReceiving("get classified ad with ID c4bb3d0e-cf29-4b5c-b16f-777637a61e0f")
            .path("/ads/c4bb3d0e-cf29-4b5c-b16f-777637a61e0f")
            .willRespondWith()
            .status(HttpStatus.NOT_FOUND.value())
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "noClassifiedAdById")
    void noClassifiedAdById(MockServer mockServer) {
        classifiedAdServiceClient.setBaseUrl(mockServer.getUrl());
        try {
            var classifiedAd = classifiedAdServiceClient.getClassifiedAdById("c4bb3d0e-cf29-4b5c-b16f-777637a61e0f");
        } catch (Exception e) {
            assertThat(e.getMessage(), containsString("404 Not Found"));
        }
    }
}
