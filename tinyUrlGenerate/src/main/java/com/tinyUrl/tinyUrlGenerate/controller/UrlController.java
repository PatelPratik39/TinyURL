package com.tinyUrl.tinyUrlGenerate.controller;

import com.tinyUrl.tinyUrlGenerate.service.UrlShorteningService;
import jakarta.servlet.http.HttpServletResponse;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/urls")
@Validated
public class UrlController {

    private final UrlShorteningService service;

    @PostMapping()
    public ResponseEntity<String> shortUrl(@RequestParam String url){
        String shortUrl = service.createShortUrl(url);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> redirectToUrl(
            @PathVariable @NotNull @Size(min=8, max=8) String shortUrl,
            HttpServletResponse response) throws IOException {

        String originalUrl = service.getOriginalUrl(shortUrl);
        response.sendRedirect(originalUrl);
//        return ResponseEntity.ok("URL retrieved Successfully!");
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    @PutMapping("{shortUrl}")
    public ResponseEntity<String> updateUrl(@PathVariable String shortUrl, @RequestParam String newUrl){
        String updateUrl = service.updateOriginalUrl(shortUrl,newUrl);
        return ResponseEntity.ok(updateUrl);
    }
}
