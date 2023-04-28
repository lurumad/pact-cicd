package com.lurumad.provider.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "classified_ads")
public class ClassifiedAd {
    @Id
    private String id;
    private String title;
    private String text;
    private BigDecimal price;
    private String ownerId;
}
