package com.lurumad.provider.models;

import lombok.Data;

import java.util.List;

public record ClassifiedAdResponse(List<ClassifiedAd> classifiedAds) {
}
