package com.crypto.crypto_recommendation_service.service;

import static org.junit.jupiter.api.Assertions.*;

import com.crypto.crypto_recommendation_service.model.CryptoMetrics;
import com.crypto.crypto_recommendation_service.model.CryptoRecord;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

public class CryptoMetricsCalculatorTest {

    @Test
    public void testCalculateCryptoMetrics_ValidList() {
        LocalDateTime expectedOldest = LocalDateTime.ofInstant(Instant.ofEpochMilli(1641009600000L), ZoneOffset.UTC);
        LocalDateTime expectedNewest = LocalDateTime.ofInstant(Instant.ofEpochMilli(1641182400000L), ZoneOffset.UTC);

        List<CryptoRecord> records = Arrays.asList(
                new CryptoRecord(1641009600000L, "BTC", 30000.0),
                new CryptoRecord(1641096000000L, "BTC", 31000.0),
                new CryptoRecord(1641182400000L, "BTC", 32000.0)
        );
        CryptoMetricsCalculator processor = new CryptoMetricsCalculator();

        CryptoMetrics metrics = processor.calculateCryptoMetrics(records);

        assertNotNull(metrics);
        assertEquals(expectedOldest, metrics.getOldest().getTimestamp());
        assertEquals(expectedNewest, metrics.getNewest().getTimestamp());
        assertEquals(30000.0, metrics.getMinPrice(), 0.01);
        assertEquals(32000.0, metrics.getMaxPrice(), 0.01);
        assertEquals(0.06666666666666667, metrics.getNormalizedRange(), 0.01);
    }

    @Test
    public void testCalculateCryptoMetrics_SamePriceRecords() {
        double price = 30000.0;
        List<CryptoRecord> records = Arrays.asList(
                new CryptoRecord(1641009600000L, "BTC", price),
                new CryptoRecord(1641096000000L, "BTC", price),
                new CryptoRecord(1641182400000L, "BTC", price)
        );
        CryptoMetricsCalculator processor = new CryptoMetricsCalculator();

        CryptoMetrics metrics = processor.calculateCryptoMetrics(records);

        assertEquals(0, metrics.getNormalizedRange(), 0.01);
    }


}