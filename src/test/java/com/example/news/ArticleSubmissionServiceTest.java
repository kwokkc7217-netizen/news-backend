package com.example.news;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * First real unit test in this project: the article submission service,
 * tested in isolation — no Spring context, no database, no Redis.
 *
 * The two repositories are Mockito mocks, so each test proves what the
 * service itself does: validation, category lookup, and saving.
 */
@ExtendWith(MockitoExtension.class)
class ArticleSubmissionServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ArticleSubmissionService service;

    @Test
    void submitValidArticle_savesAndReturnsNews() {
        Category category = new Category();
        category.setId(1L);
        category.setCode("AI_TECH");
        category.setName("AI & Tech");

        when(categoryRepository.findByCode("AI_TECH"))
                .thenReturn(Optional.of(category));
        // save() returns the entity it was given, like JPA does
        when(newsRepository.save(any(News.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        News result = service.submitArticle(validRequest());

        // The returned article carries the request data + resolved category
        assertEquals("Why Agentic Robotics Might Define Robot Intelligence",
                result.getTitle());
        assertEquals("AI_TECH", result.getCategory().getCode());

        // And the service really asked the repos to do their jobs
        verify(categoryRepository).findByCode("AI_TECH");
        ArgumentCaptor<News> saved = ArgumentCaptor.forClass(News.class);
        verify(newsRepository).save(saved.capture());
        assertEquals("Why Agentic Robotics Might Define Robot Intelligence",
                saved.getValue().getTitle());
        assertEquals(category, saved.getValue().getCategory());
    }

    @Test
    void submitBlankTitle_throwsAndNeverSaves() {
        ArticleSubmissionRequest request = validRequest();
        request.setTitle("   ");

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> service.submitArticle(request));

        assertEquals("Article title is required", error.getMessage());
        // Validation runs before any repository call
        verify(categoryRepository, never()).findByCode(anyString());
        verify(newsRepository, never()).save(any(News.class));
    }

    @Test
    void submitBlankUrl_throwsAndNeverSaves() {
        ArticleSubmissionRequest request = validRequest();
        request.setUrl("");

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> service.submitArticle(request));

        assertEquals("Article URL is required", error.getMessage());
        verify(categoryRepository, never()).findByCode(anyString());
        verify(newsRepository, never()).save(any(News.class));
    }

    @Test
    void submitBlankCategoryCode_throwsAndNeverSaves() {
        ArticleSubmissionRequest request = validRequest();
        request.setCategoryCode(null);

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> service.submitArticle(request));

        assertEquals("Category code is required", error.getMessage());
        verify(categoryRepository, never()).findByCode(anyString());
        verify(newsRepository, never()).save(any(News.class));
    }

    @Test
    void submitUnknownCategoryCode_throwsAndNeverSaves() {
        when(categoryRepository.findByCode("NOPE"))
                .thenReturn(Optional.empty());

        ArticleSubmissionRequest request = validRequest();
        request.setCategoryCode("NOPE");

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> service.submitArticle(request));

        assertEquals("Invalid category code", error.getMessage());
        verify(newsRepository, never()).save(any(News.class));
    }

    private ArticleSubmissionRequest validRequest() {
        ArticleSubmissionRequest request = new ArticleSubmissionRequest();
        request.setTitle("Why Agentic Robotics Might Define Robot Intelligence");
        request.setUrl("https://spectrum.ieee.org/agentic-robotics");
        request.setSource("IEEE Spectrum");
        request.setAuthor("Tom");
        request.setCategoryCode("AI_TECH");
        request.setReadingTimeMinutes(12);
        request.setSummary("A look at agentic robotics.");
        request.setWhyRead("Worth reading for the robotics angle.");
        request.setIsFree(true);
        return request;
    }
}
