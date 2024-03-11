package com.crypto.crypto_recommendation_service.service;

import com.crypto.crypto_recommendation_service.model.CryptoMetrics;
import com.crypto.crypto_recommendation_service.model.CryptoRecord;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CryptoMetricsCalculator {

    public CryptoMetrics calculateCryptoMetrics(List<CryptoRecord> records) {
        CryptoRecord oldest = Collections.min(records, Comparator.comparing(CryptoRecord::getTimestamp));
        CryptoRecord newest = Collections.max(records, Comparator.comparing(CryptoRecord::getTimestamp));

        double minPrice = records.stream()
                .min(Comparator.comparingDouble(CryptoRecord::getPrice))
                .orElseThrow(NoSuchElementException::new)
                .getPrice();

        double maxPrice = records.stream()
                .max(Comparator.comparingDouble(CryptoRecord::getPrice))
                .orElseThrow(NoSuchElementException::new)
                .getPrice();

        double normalizedRange = (maxPrice - minPrice) / minPrice;

        return new CryptoMetrics(oldest, newest, minPrice, maxPrice, normalizedRange);
    }

    public List<CryptoMetrics> processCryptoRecordsMap(Map<String, List<CryptoRecord>> cryptoRecordsMap) {
        List<CryptoMetrics> metricsList = new ArrayList<>();

        for (Map.Entry<String, List<CryptoRecord>> entry : cryptoRecordsMap.entrySet()) {
            metricsList.add(calculateCryptoMetrics(entry.getValue()));
        }

        metricsList.sort(Comparator.comparingDouble(CryptoMetrics::getNormalizedRange).reversed());

        return metricsList;
    }
}

