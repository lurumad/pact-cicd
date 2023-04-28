package com.lurumad.consumer.models;

import java.math.BigDecimal;

public record ClassifiedAd(
    String id,
    String title,
    String text,
    BigDecimal price,
    String ownerId
) { }
