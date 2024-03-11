package com.crypto.crypto_recommendation_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CryptoMetrics {
    private CryptoRecord oldest;
    private CryptoRecord newest;
    private double minPrice;
    private double maxPrice;
    private double normalizedRange;

    public CryptoMetrics(CryptoRecord oldest, CryptoRecord newest, double minPrice, double maxPrice, double normalizedRange) {
        this.oldest = oldest;
        this.newest = newest;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.normalizedRange = normalizedRange;
    }

}

