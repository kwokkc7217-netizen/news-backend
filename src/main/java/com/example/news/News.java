
package com.example.news;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String source;

    @Column(name = "published_at")
    private OffsetDateTime publishedAt;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getSource() {
        return source;
    }

    public OffsetDateTime getPublishedAt() {
        return publishedAt;
    }
}
