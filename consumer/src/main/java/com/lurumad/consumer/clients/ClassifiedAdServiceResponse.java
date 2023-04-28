package com.lurumad.consumer.clients;

import com.lurumad.consumer.models.ClassifiedAd;
import lombok.Data;

import java.util.List;

@Data
public class ClassifiedAdServiceResponse {
    private List<ClassifiedAd> classifiedAds;
}
