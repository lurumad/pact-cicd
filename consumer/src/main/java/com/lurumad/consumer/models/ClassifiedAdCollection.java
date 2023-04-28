package com.lurumad.consumer.models;

import java.util.List;

public record ClassifiedAdCollection(
    String name,
    List<ClassifiedAd> classifiedAds
) {
}
