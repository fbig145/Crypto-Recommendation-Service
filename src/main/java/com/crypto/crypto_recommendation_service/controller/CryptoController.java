package com.crypto.crypto_recommendation_service.controller;

import com.crypto.crypto_recommendation_service.model.CryptoMetrics;
import com.crypto.crypto_recommendation_service.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/crypto/metrics")
public class CryptoController {

    private final CryptoService cryptoService;

    @GetMapping("/sorted")
    public List<CryptoMetrics> getAllSortedCryptoMetrics() throws IOException {
        return cryptoService.getAllSortedCryptoMetrics();
    }

    @GetMapping("/{symbol}")
    public CryptoMetrics getCryptoMetricsBySymbol(@PathVariable String symbol) throws IOException {
        return cryptoService.getCryptoMetricsBySymbol(symbol);
    }

    @GetMapping("/highest-range")
    public CryptoMetrics getCryptoWithHighestRange(@RequestParam String date) throws IOException {
        return cryptoService.getCryptoWithHighestRange(date);
    }
}

