package com.crypto.crypto_recommendation_service.utils;

import com.crypto.crypto_recommendation_service.model.CryptoRecord;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class CsvFileReader {


    @Value("${csv.files}")
    private List<String> files;

    private static final String DIRECTORY_PATH = "prices";
    private static final String TIMESTAMP = "timestamp";
    private static final String SYMBOL = "symbol";
    private static final String PRICE = "price";

    private final ResourceLoader resourceLoader;

    @Cacheable("csvRead")
    public List<CryptoRecord> readCsvFile(String filePath) throws IOException {
        List<CryptoRecord> records = new ArrayList<>();
        InputStream is = resourceLoader.getResourceAsStream(filePath);
        if (is == null) {
            System.err.println("File not found: " + filePath);
            throw new FileNotFoundException();
        }

        try (InputStreamReader reader = new InputStreamReader(is);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                long timestampMillis = Long.parseLong(csvRecord.get(TIMESTAMP));
                String symbol = csvRecord.get(SYMBOL);
                double price = Double.parseDouble(csvRecord.get(PRICE));
                records.add(new CryptoRecord(timestampMillis, symbol, price));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            throw new IOException();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number from the file: " + filePath);
            throw new NumberFormatException();
        }
        return records;
    }

    @Cacheable("fileProcessor")
    public Map<String, List<CryptoRecord>> processAllFiles() throws IOException {
        Map<String, List<CryptoRecord>> cryptoRecordsMap = new HashMap<>();

        for (String fileName : files) {
            String filePath = DIRECTORY_PATH + "/" + fileName;
            String symbol = extractSymbolFromFilename(fileName);
            List<CryptoRecord> records = readCsvFile(filePath);
            cryptoRecordsMap.put(symbol, records);
        }

        return cryptoRecordsMap;
    }

    private String extractSymbolFromFilename(String filename) {
        return filename.split("_")[0];
    }
}
