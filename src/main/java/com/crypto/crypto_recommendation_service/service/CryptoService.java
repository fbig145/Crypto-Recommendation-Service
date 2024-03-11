package com.crypto.crypto_recommendation_service.service;

import com.crypto.crypto_recommendation_service.exception.ValidationException;
import com.crypto.crypto_recommendation_service.model.CryptoMetrics;
import com.crypto.crypto_recommendation_service.model.CryptoRecord;
import com.crypto.crypto_recommendation_service.utils.CsvFileReader;
import com.crypto.crypto_recommendation_service.utils.CustomUTCFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CryptoService {

    private final CsvFileReader csvFileReader;

    private final CryptoMetricsCalculator cryptoMetricsCalculator;

    public List<CryptoMetrics> getAllSortedCryptoMetrics() throws IOException {
        Map<String, List<CryptoRecord>> retrievedCryptoRecordsMap = csvFileReader.processAllFiles();

        return cryptoMetricsCalculator.processCryptoRecordsMap(retrievedCryptoRecordsMap);
    }

    public CryptoMetrics getCryptoMetricsBySymbol(String symbol) throws IOException {
        List<CryptoMetrics> allSortedCryptoMetrics = getAllSortedCryptoMetrics();
        CryptoMetrics foundCryptoMetric = allSortedCryptoMetrics.stream().filter(m -> m.getNewest().getSymbol().equals(symbol) && m.getOldest().getSymbol().equals(symbol)).findFirst().orElse(null);

        if (foundCryptoMetric == null) {
            throw new ValidationException("Cannot find any metrics using the provided symbol.");
        } else {
            return foundCryptoMetric;
        }
    }

    public CryptoMetrics getCryptoWithHighestRange(String date) throws IOException {
        LocalDateTime targetDate = validateUTCDate(date);
        Map<String, List<CryptoRecord>> retrievedCryptoRecordsMap = csvFileReader.processAllFiles();
        CryptoMetrics highestRangeCrypto = null;
        double highestRange = -1;

        for (Map.Entry<String, List<CryptoRecord>> entry : retrievedCryptoRecordsMap.entrySet()) {
            List<CryptoRecord> dailyRecords = filterRecordsByDate(entry.getValue(), LocalDate.from(targetDate));
            if (!dailyRecords.isEmpty()) {
                CryptoMetrics metrics = cryptoMetricsCalculator.calculateCryptoMetrics(dailyRecords);
                if (metrics.getNormalizedRange() > highestRange) {
                    highestRange = metrics.getNormalizedRange();
                    highestRangeCrypto = metrics;
                }
            }
        }
        return highestRangeCrypto;
    }

    private List<CryptoRecord> filterRecordsByDate(List<CryptoRecord> records, LocalDate targetDate) {
        return records.stream()
                .filter(record -> record.getTimestamp().toLocalDate().equals(targetDate))
                .collect(Collectors.toList());
    }

    public static LocalDateTime validateUTCDate(String dateStr) throws ValidationException {
        try {
            return LocalDateTime.parse(dateStr, CustomUTCFormatter.getFormatter());
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid UTC date format: " + dateStr);
        }
    }
}
