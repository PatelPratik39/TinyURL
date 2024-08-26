package com.tinyUrl.tinyUrlGenerate.service;

import org.springframework.stereotype.Service;

@Service
public interface UrlShorteningService {

    String getOriginalUrl(String shortUrl);
    String createShortUrl(String originalUrl);
    String updateOriginalUrl(String shortUrl, String newOriginalUrl);
//    String deleteOriginalUrl(String originalUrl);
//    String deleteShortUrl(String shortUrl);
}
