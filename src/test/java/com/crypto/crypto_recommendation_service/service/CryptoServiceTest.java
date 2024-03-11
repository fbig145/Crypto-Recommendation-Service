package com.crypto.crypto_recommendation_service.service;

import com.crypto.crypto_recommendation_service.exception.ValidationException;
import com.crypto.crypto_recommendation_service.model.CryptoMetrics;
import com.crypto.crypto_recommendation_service.model.CryptoRecord;
import com.crypto.crypto_recommendation_service.utils.CsvFileReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;

public class CryptoServiceTest {

    private CsvFileReader mockCsvFileReader;
    private CryptoMetricsCalculator mockMetricsCalculator;
    private CryptoService cryptoService;

    @BeforeEach
    public void setUp() {
        mockCsvFileReader = mock(CsvFileReader.class);
        mockMetricsCalculator = mock(CryptoMetricsCalculator.class);
        cryptoService = new CryptoService(mockCsvFileReader, mockMetricsCalculator);
    }

    @Test
    public void testGetAllSortedCryptoMetrics() throws IOException {
        Map<String, List<CryptoRecord>> mockData = new HashMap<>();
        when(mockCsvFileReader.processAllFiles()).thenReturn(mockData);

        List<CryptoMetrics> mockMetrics = new ArrayList<>();
        when(mockMetricsCalculator.processCryptoRecordsMap(mockData)).thenReturn(mockMetrics);

        List<CryptoMetrics> result = cryptoService.getAllSortedCryptoMetrics();

        assertNotNull(result);
        verify(mockCsvFileReader).processAllFiles();
        verify(mockMetricsCalculator).processCryptoRecordsMap(mockData);
    }

    @Test
    public void testGetCryptoMetricsBySymbol() throws IOException {
        String symbol = "BTC";

        List<CryptoMetrics> mockMetrics = Collections.singletonList(
                new CryptoMetrics(new CryptoRecord(1641009600000L, "BTC", 30000.0),
                        new CryptoRecord(1641182400000L, "BTC", 32000.0),
                        30000.0, 32000.0, 0.0667)
        );
        when(cryptoService.getAllSortedCryptoMetrics()).thenReturn(mockMetrics);

        CryptoMetrics result = cryptoService.getCryptoMetricsBySymbol(symbol);

        assertNotNull(result);
        assertEquals("BTC", result.getNewest().getSymbol());
    }

    @Test
    public void testGetCryptoMetricsBySymbol_NotFound() throws IOException {
        String symbol = "UNKNOWN";

        List<CryptoMetrics> mockMetrics = new ArrayList<>();
        when(cryptoService.getAllSortedCryptoMetrics()).thenReturn(mockMetrics);

        assertThrows(ValidationException.class, () -> {
            cryptoService.getCryptoMetricsBySymbol(symbol);
        });
    }


}
