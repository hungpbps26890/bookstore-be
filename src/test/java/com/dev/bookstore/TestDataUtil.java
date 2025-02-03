package com.dev.bookstore;

import com.dev.bookstore.domain.dto.AuthorDto;
import com.dev.bookstore.domain.entities.AuthorEntity;

import java.util.ArrayList;

public class TestDataUtil {

    public static AuthorDto createTestAuthorDto() {
        return AuthorDto.builder()
                .name("Author Name")
                .age(30)
                .description("This is a description.")
                .image("author-image.jpg")
                .build();
    }

    public static AuthorDto expectedTestAuthorDto() {
        return AuthorDto.builder()
                .id(1L)
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
}
