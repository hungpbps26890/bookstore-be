package com.dev.bookstore;

import com.dev.bookstore.domain.dto.AuthorDto;
import com.dev.bookstore.domain.entities.AuthorEntity;

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
}
