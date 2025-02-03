package com.dev.bookstore;

import com.dev.bookstore.domain.dto.AuthorDto;

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
}
