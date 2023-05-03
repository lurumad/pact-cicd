package com.lurumad.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.lurumad.provider.models.ClassifiedAd;
import com.lurumad.provider.models.ClassifiedAdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("ClassifiedAdService")
@PactBroker
public class PactVerificationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ClassifiedAdRepository classifiedAdRepository;

    @BeforeEach
    void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("classified ads exists")
    void classifiedAdExists() {
        classifiedAdRepository.deleteAll();
        classifiedAdRepository.saveAll(Arrays.asList(
            new ClassifiedAd("c4bb3d0e-cf29-4b5c-b16f-777637a61e0f", "Title 1", "Text 1", new BigDecimal(50), "test owner"),
            new ClassifiedAd("ef1d4d51-4c05-4e02-9ecd-e32440f59494", "Title 2", "Text 2", new BigDecimal(150), "test owner"),
            new ClassifiedAd("c90e0111-d600-4416-b8fd-4058fbe49ede", "Title 3", "Text 3", new BigDecimal(75), "test owner")
        ));
    }

    @State("a classified ad with ID c4bb3d0e-cf29-4b5c-b16f-777637a61e0f exists")
    void classifiedAdWithIdExists(Map<String, Object> params) {
        var classifiedAdId = (String) params.get("id");
        var product = classifiedAdRepository.findById(classifiedAdId);
        if (!product.isPresent()){
            classifiedAdRepository.save(new ClassifiedAd("c4bb3d0e-cf29-4b5c-b16f-777637a61e0f", "Title 1", "Text 1", new BigDecimal(50), "test owner"));
        }
    }

    @State(value = "no classified ads exists", action = StateChangeAction.SETUP)
    void noClassifiedAdsExists() {
        classifiedAdRepository.deleteAll();
    }

    @State("no classified ad with ID c4bb3d0e-cf29-4b5c-b16f-777637a61e0f exists")
    void noClassifiedAdWithIdExists(Map<String, Object> params) {
        var classifiedAdId = (String) params.get("id");
        var product = classifiedAdRepository.findById(classifiedAdId);
        if (product.isPresent()){
            classifiedAdRepository.delete(product.get());
        }
    }
}
