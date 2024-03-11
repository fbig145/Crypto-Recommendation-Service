package com.crypto.crypto_recommendation_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@NoArgsConstructor
public class CryptoRecord {
    private LocalDateTime timestamp;
    private String symbol;
    private double price;

    public CryptoRecord(long timestampMillis, String symbol, double price) {
        this.timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillis), ZoneOffset.UTC);
        this.symbol = symbol;
        this.price = price;
    }
}

