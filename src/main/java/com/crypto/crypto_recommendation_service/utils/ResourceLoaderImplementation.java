package com.crypto.crypto_recommendation_service.utils;

import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class ResourceLoaderImplementation implements ResourceLoader {
    @Override
    public InputStream getResourceAsStream(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }
}

