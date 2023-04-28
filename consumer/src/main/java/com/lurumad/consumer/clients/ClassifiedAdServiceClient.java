package com.lurumad.consumer.clients;

import com.lurumad.consumer.models.ClassifiedAd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClassifiedAdServiceClient {

    private final RestTemplate restTemplate;

    @Value("${serviceClients.classifiedAds.baseUrl}")
    private String baseUrl;

    public ClassifiedAdServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ClassifiedAdServiceResponse fetchClassifiedAds() {
        return restTemplate.getForObject(this.baseUrl + "/ads", ClassifiedAdServiceResponse.class);
    }

    public ClassifiedAd getClassifiedAdById(String id) {
        return restTemplate.getForObject(this.baseUrl + "/ads/" + id, ClassifiedAd.class);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
