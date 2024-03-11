package com.crypto.crypto_recommendation_service.utils;

import com.crypto.crypto_recommendation_service.model.CryptoRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CsvFileReaderTest {

    private CsvFileReader csvFileReader;
    private ResourceLoader mockResourceLoader;

    @BeforeEach
    void setUp() {
        mockResourceLoader = mock(ResourceLoader.class);
        csvFileReader = new CsvFileReader(mockResourceLoader);
    }

    @Test
    void testReadCsvFile_Success() throws IOException {
        String csvContent = "timestamp,symbol,price\n123456,ABC,100.0";
        when(mockResourceLoader.getResourceAsStream(anyString()))
                .thenReturn(new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8)));


            List<CryptoRecord> result = csvFileReader.readCsvFile("test.csv");
            assertFalse(result.isEmpty());
            CryptoRecord record = result.get(0);
            assertEquals("1970-01-01T00:02:03.456", record.getTimestamp().toString());
            assertEquals("ABC", record.getSymbol());
            assertEquals(100.0, record.getPrice());

    }

    @Test
    void testReadCsvFile_FileNotFound() throws IOException {
        when(mockResourceLoader.getResourceAsStream(anyString())).thenReturn(null);
        assertThrows(FileNotFoundException.class, () -> csvFileReader.readCsvFile("nonexistent.csv"));
    }

    @Test
    void testReadCsvFile_InvalidDataFormat() {
        String invalidCsvContent = "timestamp,symbol,price\nnot_a_number,ABC,not_a_price";
        when(mockResourceLoader.getResourceAsStream(anyString()))
                .thenReturn(new ByteArrayInputStream(invalidCsvContent.getBytes(StandardCharsets.UTF_8)));

        assertThrows(NumberFormatException.class, () -> csvFileReader.readCsvFile("invalid.csv"));
    }

}