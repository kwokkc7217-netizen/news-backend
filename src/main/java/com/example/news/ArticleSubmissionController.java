
package com.example.news;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent/articles")
public class ArticleSubmissionController {

    private final ArticleSubmissionService articleSubmissionService;

    @Value("${ARTICLE_API_KEY}")
    private String expectedApiKey;

    public ArticleSubmissionController(
            ArticleSubmissionService articleSubmissionService) {
        this.articleSubmissionService = articleSubmissionService;
    }

    @PostMapping
    public ResponseEntity<?> submitArticle(
            @RequestHeader(value = "X-API-Key", required = false)
            String providedApiKey,
            @RequestBody ArticleSubmissionRequest request) {

        // 1. Check API Key
        if (providedApiKey == null ||
                !MessageDigest.isEqual(
                        expectedApiKey.getBytes(StandardCharsets.UTF_8),
                        providedApiKey.getBytes(StandardCharsets.UTF_8))) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid API Key");
        }

        // 2. Submit article
        try {
            News savedNews = articleSubmissionService.submitArticle(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedNews);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
