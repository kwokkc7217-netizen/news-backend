
package com.example.news;

import java.time.OffsetDateTime;
import java.math.BigDecimal;

public class ArticleSubmissionRequest {

    private String title;
    private String url;
    private String source;
    private String author;
    private OffsetDateTime publishedAt;
    private String categoryCode;
    private Integer readingTimeMinutes;
    private String summary;
    private String whyRead;
    private Boolean isFree;
    private String verificationStatus;
    private BigDecimal aiScore;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public OffsetDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(OffsetDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Integer getReadingTimeMinutes() {
        return readingTimeMinutes;
    }

    public void setReadingTimeMinutes(Integer readingTimeMinutes) {
        this.readingTimeMinutes = readingTimeMinutes;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWhyRead() {
        return whyRead;
    }

    public void setWhyRead(String whyRead) {
        this.whyRead = whyRead;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public BigDecimal getAiScore() {
        return aiScore;
    }
    
    public void setAiScore(BigDecimal aiScore) {
        this.aiScore = aiScore;
    }
}
