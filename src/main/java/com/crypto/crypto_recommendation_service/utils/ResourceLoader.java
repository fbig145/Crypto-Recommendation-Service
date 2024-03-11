package com.crypto.crypto_recommendation_service.utils;

import java.io.InputStream;

public interface ResourceLoader {
    InputStream getResourceAsStream(String path);
}
