
package com.example.news;

import org.springframework.stereotype.Service;

@Service
public class ArticleSubmissionService {

    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

    public ArticleSubmissionService(
            NewsRepository newsRepository,
            CategoryRepository categoryRepository) {
        this.newsRepository = newsRepository;
        this.categoryRepository = categoryRepository;
    }

    public News submitArticle(ArticleSubmissionRequest request) {

        // 1. Basic validation
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("Article title is required");
        }

        if (request.getUrl() == null || request.getUrl().isBlank()) {
            throw new IllegalArgumentException("Article URL is required");
        }

        if (request.getCategoryCode() == null
                || request.getCategoryCode().isBlank()) {
            throw new IllegalArgumentException("Category code is required");
        }

        // 2. Find category
        Category category = categoryRepository
                .findByCode(request.getCategoryCode())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid category code"));

        // 3. Create News entity
        News news = new News();

        news.setTitle(request.getTitle());
        news.setUrl(request.getUrl());
        news.setSource(request.getSource());
        news.setAuthor(request.getAuthor());
        news.setPublishedAt(request.getPublishedAt());
        news.setCategory(category);
        news.setReadingTimeMinutes(request.getReadingTimeMinutes());
        news.setSummary(request.getSummary());
        news.setWhyRead(request.getWhyRead());
        news.setIsFree(request.getIsFree());
        news.setVerificationStatus(request.getVerificationStatus());
        news.setAiScore(request.getAiScore());

        // 4. Save to database
        return newsRepository.save(news);
    }
}
