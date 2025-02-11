package com.dev.bookstore;

import com.dev.bookstore.domain.dto.AuthorDto;
import com.dev.bookstore.domain.dto.AuthorSummaryDto;
import com.dev.bookstore.domain.dto.BookSummaryDto;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.domain.entities.BookEntity;
import com.dev.bookstore.domain.requests.AuthorSummary;
import com.dev.bookstore.domain.requests.BookSummary;

import java.util.ArrayList;

public class TestDataUtil {

    public static AuthorDto createTestAuthorDto() {
        return AuthorDto.builder()
                .id(null)
                .name("Author Name")
                .age(30)
                .description("This is a description.")
                .image("author-image.jpg")
                .build();
    }

    public static AuthorDto expectedTestAuthorDto() {
        return AuthorDto.builder()
                .id(null)
                .name("Author Name")
                .age(30)
                .description("This is a description.")
                .image("author-image.jpg")
                .build();
    }

    public static AuthorDto updateTestAuthorDto(Long id) {
        return AuthorDto.builder()
                .id(id)
                .name("Updated Author Name")
                .age(30)
                .description("This is an updated description.")
                .image("updated-author-image.jpg")
                .build();
    }

    public static AuthorDto expectedUpdatedTestAuthorDto(Long id) {
        return AuthorDto.builder()
                .id(id)
                .name("Updated Author Name")
                .age(30)
                .description("This is an updated description.")
                .image("updated-author-image.jpg")
                .build();
    }

    public static AuthorDto partialUpdateTestAuthorDto(Long id) {
        return AuthorDto.builder()
                .name("Partial Updated Author Name")
                .build();
    }

    public static AuthorDto expectedPartialUpdatedTestAuthorDto(Long id) {
        return AuthorDto.builder()
                .id(id)
                .name("Partial Updated Author Name")
                .age(30)
                .description("This is an updated description.")
                .image("updated-author-image.jpg")
                .build();
    }

    public static AuthorEntity testAuthorEntity(Long id) {
        return AuthorEntity.builder()
                .id(id)
                .name("Author Name")
                .age(30)
                .description("This is a description.")
                .image("author-image.jpg")
                .build();
    }

    public static AuthorEntity createTestAuthorEntity() {
        return AuthorEntity.builder()
                .name("Author Name")
                .age(30)
                .description("This is a description.")
                .image("author-image.jpg")
                .build();
    }

    public static AuthorEntity expectedTestAuthorEntity(Long id) {
        return AuthorEntity.builder()
                .id(id)
                .name("Author Name")
                .age(30)
                .description("This is a description.")
                .image("author-image.jpg")
                .build();
    }

    public static AuthorEntity updateTestAuthorEntity(Long existingAuthorId) {
        return AuthorEntity.builder()
                .id(existingAuthorId)
                .name("Updated Author Name")
                .age(30)
                .description("This is an updated description.")
                .image("updated-author-image.jpg")
                .build();
    }

    public static AuthorEntity expectedUpdateTestAuthorEntity(Long existingAuthorId) {
        return AuthorEntity.builder()
                .id(existingAuthorId)
                .name("Updated Author Name")
                .age(30)
                .description("This is an updated description.")
                .image("updated-author-image.jpg")
                .build();
    }

    public static AuthorEntity expectedPartialUpdatedTestAuthorEntity(Long id) {
        return AuthorEntity.builder()
                .id(id)
                .name("Partial Updated Author Name")
                .age(30)
                .description("This is an updated description.")
                .image("updated-author-image.jpg")
                .build();
    }

    public static AuthorSummaryDto testAuthorSummaryDto(Long id) {
        return AuthorSummaryDto.builder()
                .id(id)
                .name("Test Author Name")
                .image("test-author-image.jpg")
                .build();
    }

    public static AuthorSummary testAuthorSummary(Long id) {
        return AuthorSummary.builder()
                .id(id)
                .name("Test Author Name")
                .image("test-author-image.jpg")
                .build();
    }

    public static final String BOOK_ISBN = "978-3-16-148410-0";

    public static BookEntity testBookEntity(String isbn, AuthorEntity author) {
        return BookEntity.builder()
                .isbn(isbn)
                .title("Test Book Title")
                .description("Test Book Description.")
                .image("test-book-image.jpg")
                .author(author)
                .build();
    }

    public static BookSummaryDto testBookSummaryDto(String isbn, AuthorSummaryDto authorSummaryDto) {
        return BookSummaryDto.builder()
                .isbn(isbn)
                .title("Test Book Title")
                .title("Test Book  Description.")
                .image("test-book-image.jpg")
                .author(authorSummaryDto)
                .build();
    }

    public static BookSummary testBookSummary(String isbn, AuthorSummary authorSummary) {
        return BookSummary.builder()
                .isbn(isbn)
                .title("Test Book Title")
                .description("Test Book  Description.")
                .image("test-book-image.jpg")
                .author(authorSummary)
                .build();
    }

    public static BookSummary testUpdatedBookSummary(String isbn, AuthorSummary authorSummary) {
        return BookSummary.builder()
                .isbn(isbn)
                .title("Updated Test Book Title")
                .description("Updated Test Book  Description.")
                .image("updated-test-book-image.jpg")
                .author(authorSummary)
                .build();
    }
}
