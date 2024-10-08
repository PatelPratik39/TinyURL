package com.tinyUrl.tinyUrlGenerate.service.Impl;

import com.tinyUrl.tinyUrlGenerate.entity.UrlMapping;
import com.tinyUrl.tinyUrlGenerate.exception.UrlNotFoundException;
import com.tinyUrl.tinyUrlGenerate.repository.UrlMappingRepository;
import com.tinyUrl.tinyUrlGenerate.service.UrlShorteningService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import static org.apache.kafka.common.config.ConfigResource.Type.TOPIC;

@Service
@AllArgsConstructor
public class UrlShorteningServiceImpl implements UrlShorteningService {

    private final UrlMappingRepository repository;  //repository injection into Service Impl class
    private final KafkaTemplate<String, String> kafkaTemplate;  // KafkaTemplate injection


    private static final String TOPIC_NAME = "url-shortener-topic";  // Kafka topic name

    @Override
    @Cacheable(value = "urlCache", key = "#shortUrl")
    public String getOriginalUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl)
                .map(UrlMapping::getOriginalUrl)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found: " + shortUrl));
    }

    @Override
    @Transactional
    public String createShortUrl ( String originalUrl ) {

        Optional< UrlMapping > existingMapping = repository.findByOriginalUrl(originalUrl); //find a original Url from repository and store into existingMapping

//        if the original Url is present into repository, then try to get short url
        if(existingMapping.isPresent()){
            return existingMapping.get().getShortUrl();
        }

        String shortUrl =
                generateShortUrl(originalUrl); //generate short Url based on originalUrl

        UrlMapping urlMapping = new UrlMapping();  //create new Object from Entity class
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setCreationAt(LocalDateTime.now());
        urlMapping.setUpdatedAt(LocalDateTime.now());
        repository.save(urlMapping); //save into repository class

        publishToKafka(shortUrl, originalUrl);
        return shortUrl;  // finaly retaurn short url from original Url
    }



    @Override
    @Transactional
    public String updateOriginalUrl (String shortUrl, String newOriginalUrl ) {
          UrlMapping urlMapping = repository.findByShortUrl(shortUrl)
                                    .orElseThrow(() -> new RuntimeException("ShortUrl Not Found"));

          urlMapping.setOriginalUrl(newOriginalUrl);
          urlMapping.setUpdatedAt(LocalDateTime.now());

          publishToKafka(shortUrl,newOriginalUrl);

          repository.save(urlMapping);

        return urlMapping.getShortUrl();
    }


//    Generate Short Url

    private String generateShortUrl(String originalUrl) {
        // Using MD5 hashing to generate a hash from the original URL
        String hashUrl = DigestUtils.md5DigestAsHex(originalUrl.getBytes());

        // Encoding the hash into a Base64 string
        String base64Url = Base64.getEncoder().encodeToString(hashUrl.getBytes());

        // Returning the first 8 characters as the shortened URL
        return base64Url.substring(0, 8);
    }

//    share with Kafka
    private void publishToKafka ( String shortUrl, String originalUrl ) {

        String message = String.format("Shortend URL : %s -> %s", shortUrl,originalUrl);  //create a message to send
        kafkaTemplate.send(TOPIC_NAME,message); // send the message to the specific kafka topic
        System.out.println("Published message to Kafka : " + message);
    }
}
